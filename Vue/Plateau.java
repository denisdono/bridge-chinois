package Vue;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Plateau extends JPanel implements Observateur {

    Jeu jeu;
    int largeurCase = 1, hauteurCase = 1;
    CollecteurEvenements c;
    Menu m;
    //Dimensions a revoir, adapter a la taille de l'écran
    private Dimension dimlabel = new Dimension(50, 80);

    public Plateau(Jeu j, CollecteurEvenements c, Menu m) {
        this.m = m;
        this.c = c;
        jeu = j;
        //On ajoute le plateau dans la liste des observateur
        //Les observateurs seront mis à jour par le jeu dès que nécessaire
        jeu.ajouteObservateur(this);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 20));
        miseAJour();

    }

    //Fonction de mise a jour appelée dès que necessaire
    @Override
    public void miseAJour() {
        //Mise à jour des infos du menu
        m.indiqueAtout(jeu.getAtout().name());
        m.setPlis(jeu.getMains()[0].getnbPlis(), jeu.getMains()[1].getnbPlis());
//        System.out.println("etape: "+jeu.etape());
//        System.out.println("j:"+(jeu.joueurActuelle()+1));
//        System.out.println("miseAJour");

        //mise a zero du plateau
        this.removeAll();

        /////////////////////////////
        //Gestion de la main du joueur 1
        /////////////////////////////
        creerMainJoueur(0);
        JLabel space = new JLabel();
        space.setPreferredSize(dimlabel);
        this.add(space);
        /////////////////////////////
        //Gestion des paquets du centre
        /////////////////////////////
        JLabel indPioche = new JLabel("Pioches");
        this.add(indPioche);
        JPanel paquetCentre = new JPanel();
        for (int i = 0; i < 6; i++) {
            JLabel l = new JLabel();
            if (jeu.getPiles()[i].estVide()) {
                l.setText("Vide");
                 //SI on est a une étape de pioche
               
            } else {
                //version texte
                l.setText(jeu.getPiles()[i].topDeck().toString());
                //version image
//                Icon img = new ImageIcon(new ImageIcon(ClassLoader.getSystemClassLoader().getResource(jeu.getPiles()[i].topDeck().getResourceName())).getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_DEFAULT));
//                l.setIcon(img);
                
                if (jeu.etape() == 2 || jeu.etape() == 3) {
                    l.addMouseListener(new JoueurCarteListener(i, c));
                }
            }
            l.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            l.setPreferredSize(dimlabel);
           
            paquetCentre.add(l);
        }
        if (jeu.etape() == 2 || jeu.etape() == 3) {
            paquetCentre.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        }
        this.add(paquetCentre);

        /////////////////////////////
        //Gestion des 2 cartes jouees
        /////////////////////////////
        JPanel carteJouee = new JPanel();

        JLabel joue1 = new JLabel();
        joue1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        joue1.setPreferredSize(dimlabel);
        carteJouee.add(joue1);
        //Si c'est au deuxieme joueur de jouer une carte, on affiche la première carte
        if (jeu.etape() == 1 || jeu.etape() == 2) {
            joue1.setText(jeu.getC_dom().toString());
            //version image
//            Icon img = new ImageIcon(new ImageIcon(ClassLoader.getSystemClassLoader().getResource(jeu.getC_dom().getResourceName())).getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_DEFAULT));;
//            joue1.setIcon(img);
        } else {
            joue1.setText("Vide");
        }
        JLabel joue2 = new JLabel();
        joue2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        joue2.setPreferredSize(dimlabel);
        //Si le deuxieme joueur a joue, on affiche aussi sa carte
        if (jeu.etape() == 2) {
            joue2.setText(jeu.getC_sub().toString());
            //version image
//            Icon img = new ImageIcon(new ImageIcon(ClassLoader.getSystemClassLoader().getResource(jeu.getC_sub().getResourceName())).getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_DEFAULT));;
//            joue2.setIcon(img);
        } else {
            joue2.setText("Vide");
        }
        carteJouee.add(joue2);

        this.add(carteJouee);

        /////////////////////////////
        //Gestion de la main du joueur 2
        /////////////////////////////
        JLabel space2 = new JLabel();
        space2.setPreferredSize(dimlabel);
        this.add(space2);
        creerMainJoueur(1);

        //Recharger le panel
        this.revalidate();
    }

    private void creerMainJoueur(int numJ) {
        JLabel nomJ = new JLabel("Joueur " + (numJ + 1));
        this.add(nomJ);
        JPanel main = new JPanel();
        //affichage de toutes les cartes du joueur
        for (int i = 0; i < jeu.getMains()[numJ].getnbCarte(); i++) {
            JLabel l = new JLabel();
            //l.setText(jeu.getMains()[numJ].getMain()[i].toString());
            //l.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            l.setPreferredSize(dimlabel);
            ///////////////////////////////////
            //Si on utilise les images
//            System.out.println(jeu.getMains()[numJ].getMain()[i].getResourceName());
            Icon img = new ImageIcon(new ImageIcon(ClassLoader.getSystemClassLoader().getResource(jeu.getMains()[numJ].getMain()[i].getResourceName())).getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_DEFAULT));;
            l.setIcon(img);
            ///////////////////////////////////
            //Si c'est au joueur numJ de jouer et qu'on est a une étape de pioche
            //On active le listener
            if ((jeu.etape() == 0 || jeu.etape() == 1) && jeu.joueurActuelle() == numJ && jeu.peutJouer(i, numJ)) {
                l.addMouseListener(new JoueurCarteListener(i, c));
            }
            main.add(l);
        }
        //Si c'est au joueur numJ de jouer et qu'on est a une étape de pioche
        //La main du joueur est en surbrillance
        if (jeu.joueurActuelle() == numJ && (jeu.etape() == 0 || jeu.etape() == 1)) {
            main.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        }
        this.add(main);
    }

    public Icon creerIcone(String resource){
        Icon i = new ImageIcon(new ImageIcon(resource).getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_DEFAULT));
        return i;
    }
}
