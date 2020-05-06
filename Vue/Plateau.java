package Vue;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Plateau extends JPanel implements Observateur {

    Jeu jeu;
    private int largeurCase = 1, hauteurCase = 1;
    private CollecteurEvenements c;
    private Menu m;
    private JLabel arrowJ1;
    private JLabel arrowJ2;
    private ArrayList<JLabel> hand1;
    private ArrayList<JLabel> hand2;
    private ArrayList<JLabel> centreDecks;
    private ArrayList<JLabel> playedCards;
    private ArrayList<ArrayList<JLabel>> hands;
    //Dimensions a revoir, adapter a la taille de l'écran
    private Dimension dimlabel;

    public Plateau(Jeu j, CollecteurEvenements c, Menu m) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        dimlabel = new Dimension(screenSize.width / 25, screenSize.height / 10);
        this.m = m;
        this.c = c;
        hand1 = new ArrayList<>();
        hand2 = new ArrayList<>();
        centreDecks = new ArrayList<>();
        playedCards = new ArrayList<>();
        hands = new ArrayList<>();
        hands.add(hand1);
        hands.add(hand2);
        jeu = j;
        //On ajoute le plateau dans la liste des observateur
        //Les observateurs seront mis à jour par le jeu dès que nécessaire
        jeu.ajouteObservateur(this);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 20));

        JLabel nomJ = new JLabel("Joueur 1");
        this.add(nomJ);
        JPanel hand1Pane = new JPanel();
        
        // Ajout de la fl�che indiquant le tour du joueur 1
        JLabel arrow1Label = new JLabel(); 
        hand1Pane.add(arrow1Label);
        this.arrowJ1 = arrow1Label;
        
        for (int i = 0; i < 11; i++) {
            JLabel l = new JLabel();
            hand1Pane.add(l);
            hand1.add(l);
        }
        this.add(hand1Pane);
        JLabel space = new JLabel();
        space.setPreferredSize(dimlabel);
        this.add(space);

        JLabel indPioche = new JLabel("Pioches");
        this.add(indPioche);
        JPanel paquetCentrePane = new JPanel();
        for (int i = 0; i < 6; i++) {
            JLabel l = new JLabel();
            paquetCentrePane.add(l);
            centreDecks.add(l);
        }
        this.add(paquetCentrePane);

        JPanel carteJoueePane = new JPanel();
        JLabel joue1 = new JLabel();
        joue1.setPreferredSize(dimlabel);
        carteJoueePane.add(joue1);
        playedCards.add(joue1);

        JLabel joue2 = new JLabel();
        joue2.setPreferredSize(dimlabel);
        carteJoueePane.add(joue2);
        playedCards.add(joue2);
        this.add(carteJoueePane);
        JLabel space2 = new JLabel();
        space.setPreferredSize(dimlabel);
        this.add(space2);

        JLabel nomJ2 = new JLabel("Joueur 2");
        this.add(nomJ2);
        JPanel hand2Pane = new JPanel();
        
     // Ajout de la fl�che indiquant le tour du joueur 2
        JLabel arrow2Label = new JLabel(); 
        hand2Pane.add(arrow2Label);
        this.arrowJ2 = arrow2Label;
        
        for (int i = 0; i < 11; i++) {
            JLabel l = new JLabel();
            hand2Pane.add(l);
            hand2.add(l);
        }
        this.add(hand2Pane);
        majMainJoueur(0);
        majPaquets();
        majMainJoueur(1);
        majCarteJouees();
        this.m.indiqueAtout(jeu.getAtout().name());
        //miseAJour();

    }

    //Fonction de mise a jour appelée dès que necessaire
    @Override
    public void miseAJour() {
        //Mise à jour des infos du menu
        m.indiqueAtout(jeu.getAtout().name());
        m.setPlis(jeu.getMains()[0].getnbPlis(), jeu.getMains()[1].getnbPlis());
        majMainJoueur(0);
        majPaquets();
        majMainJoueur(1);
        majCarteJouees();
    }
    
    private void majMainJoueur(int numJ) {
    	
    	if(jeu.joueurActuelle() == numJ) {
    		System.out.println("jecrashici");
    		Icon imgArrow = new ImageIcon(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("arrow.png")).getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_SMOOTH));
    		this.arrowJ1.setIcon(imgArrow);
    	}
    	else {
    		System.out.println("jecrashl�");
    		this.arrowJ1.setIcon(null);
    	}
    	
        for (int i = 0; i < 11; i++) {
            if (i < jeu.getMains()[numJ].getnbCarte()) {
                Icon img = new ImageIcon(new ImageIcon(ClassLoader.getSystemClassLoader().getResource(jeu.getMains()[numJ].getMain()[i].getResourceName())).getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_SMOOTH));
                hands.get(numJ).get(i).setIcon(img);
                if ((jeu.etape() == 0 || jeu.etape() == 1) && jeu.joueurActuelle() == numJ && jeu.peutJouer(i, numJ)) {
                    if (hands.get(numJ).get(i).getMouseListeners().length == 0) {
                        hands.get(numJ).get(i).addMouseListener(new JoueurCarteListener(i, c));
                    }
                } else if (hands.get(numJ).get(i).getMouseListeners().length > 0) {
                    hands.get(numJ).get(i).removeMouseListener(hands.get(numJ).get(i).getMouseListeners()[0]);
                }
            } else {
                hands.get(numJ).get(i).setIcon(null);
            }
        }
    }

    private void majPaquets() {
        for (int i = 0; i < 6; i++) {
            if (jeu.getPiles()[i].estVide()) {
                centreDecks.get(i).setIcon(null);
                centreDecks.get(i).setText("Vide");
                if (centreDecks.get(i).getMouseListeners().length > 0) {
                    centreDecks.get(i).removeMouseListener(centreDecks.get(i).getMouseListeners()[0]);
                }
            } else {
                //version texte
                //l.setText(jeu.getPiles()[i].topDeck().toString());
                //version image
                Icon img = new ImageIcon(new ImageIcon(ClassLoader.getSystemClassLoader().getResource(jeu.getPiles()[i].topDeck().getResourceName())).getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_SMOOTH));
                centreDecks.get(i).setIcon(img);

                //SI on est a une étape de pioche
                if (jeu.etape() == 2 || jeu.etape() == 3) {
                    if (centreDecks.get(i).getMouseListeners().length == 0) {
                        centreDecks.get(i).addMouseListener(new JoueurCarteListener(i, c));
                    }
                } else if (centreDecks.get(i).getMouseListeners().length > 0) {
                    centreDecks.get(i).removeMouseListener(centreDecks.get(i).getMouseListeners()[0]);
                }
            }
            //l.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            centreDecks.get(i).setPreferredSize(dimlabel);
        }
    }

    private void majCarteJouees() {
        playedCards.get(0).setIcon(null);
        playedCards.get(1).setIcon(null);
        if (jeu.etape() == 1 || jeu.etape() == 2) {
            //version texte
            //joue1.setText(jeu.getC_dom().toString());
            //version image
            Icon img = new ImageIcon(new ImageIcon(ClassLoader.getSystemClassLoader().getResource(jeu.getC_dom().getResourceName())).getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_SMOOTH));;
            playedCards.get(0).setIcon(img);
        }
        if (jeu.etape() == 2) {
            //version texte
            //joue2.setText(jeu.getC_sub().toString());
            //version image
            Icon img = new ImageIcon(new ImageIcon(ClassLoader.getSystemClassLoader().getResource(jeu.getC_sub().getResourceName())).getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_SMOOTH));;
            playedCards.get(1).setIcon(img);
        }
    }
}
