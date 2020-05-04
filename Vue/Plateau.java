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
    private Dimension dimlabel = new Dimension(50,80);
    public Plateau(Jeu j, CollecteurEvenements c, Menu m) {
        this.m = m;
        this.c = c;
        jeu = j;
        jeu.ajouteObservateur(this);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        miseAJour();
        

    }

//    private String createImgName(Carte c) {
//        return c.getCouleur().getNom() + " "+c.getValeur()+".png";
//    }
//      
    int largeur() {
        return getWidth();
    }

    int hauteur() {
        return getHeight();
    }

    public int largeurCase() {
        return largeurCase;
    }

    public int hauteurCase() {
        return hauteurCase;
    }

    @Override
    public void miseAJour() {
        m.indiqueAtout(jeu.getAtout().name());
        System.out.println("etape: "+jeu.etape());
        System.out.println("j:"+(jeu.joueurActuelle()+1));
        System.out.println("miseAJour");
        this.removeAll();
        
        
        /////////////////////////////
        //Gestion de la main du joueur 1
        /////////////////////////////
        creerMainJoueur(0);

        //Gestion des paquets du centre
        JPanel paquetCentre = new JPanel();
        for (int i = 0; i < 6; i++) {
            JLabel l = new JLabel(jeu.getPiles()[i].topDeck().toString());
            l.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            l.setPreferredSize(dimlabel);
            if(jeu.etape()==2 || jeu.etape()==3)
                l.addMouseListener(new JoueurCarteListener(i, c));
            paquetCentre.add(l);
        }
        if(jeu.etape()==2 || jeu.etape()==3){
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
        if(jeu.etape() == 1 || jeu.etape()==2){
           joue1.setText(jeu.getC_dom().toString());
        } else{
           joue1.setText("Vide"); 
        }
        JLabel joue2 = new JLabel();
        joue2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        joue2.setPreferredSize(dimlabel);
        if(jeu.etape() == 2){
           joue2.setText(jeu.getC_sub().toString());
        } else{
           joue2.setText("Vide"); 
        }
        carteJouee.add(joue2);
        
        this.add(carteJouee);
        
        /////////////////////////////
        //Gestion de la main du joueur 2
        /////////////////////////////
        creerMainJoueur(1);

        //Recharger le panel
        this.revalidate();
    }
    
    private void creerMainJoueur(int numJ){
         JPanel main = new JPanel();
        for (int i = 0; i < jeu.getMains()[numJ].getnbCarte(); i++) {
            JLabel l = new JLabel(jeu.getMains()[numJ].getMain()[i].toString());
            l.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            l.setPreferredSize(dimlabel);
            if(jeu.etape()==0 || jeu.etape()==1 && jeu.joueurActuelle()==numJ )
                l.addMouseListener(new JoueurCarteListener(i, c));
            main.add(l);
        }
        if(jeu.joueurActuelle()==numJ && (jeu.etape()==0 || jeu.etape()==1)){
            main.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        }
        this.add(main);
    }

}
