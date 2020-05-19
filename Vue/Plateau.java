package Vue;

import Controleur.IASimple;
import Modele.Jeu;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

public class Plateau extends JPanel implements Observateur {

    Jeu jeu;
    private CollecteurEvenements c;
    private Menu m;
    private ArrayList<JLabel> arrows;
    private ArrayList<JLabel> hand1;
    private ArrayList<JLabel> hand2;
    private ArrayList<JLabel> centreDecks;
    private ArrayList<JLabel> playedCards;
    private ArrayList<ArrayList<JLabel>> hands;
    private Dimension dimlabel;
    private boolean timed = false;
    private boolean waitPioche = false;
    private boolean chgtJoueur = false;
    private int etapePrecedente;
    private int manchePrec = 1;
    private boolean chgtManche = false;
    private int lastnbPlis1 = 0;
    private int lastnbPlis2 = 0;
    private JLabel indPioche;
    private JLabel background;
    private JFrame frame;

    public Plateau(Jeu j, CollecteurEvenements c, Menu m, Dimension d, JFrame frame) {
        dimlabel = d;
        this.m = m;
        this.c = c;
        this.frame = frame;
        setLayout(new BorderLayout());

        // On ajoute le plateau dans la liste des observateur
        // Les observateurs seront mis à jour par le jeu dès que nécessaire
        jeu = j;
        jeu.ajouteObservateur(this);

        this.setPreferredSize(new Dimension(dimlabel.width * 14, dimlabel.height * 7));
        creerPlateau();
    }

    public void creerPlateau() {
        //Permet de creer tous elements graphiques du plateau
        removeAll();
        background = new JLabel(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("Background" + jeu.getSelFond() + ".jpg")));
        hand1 = new ArrayList<>();
        hand2 = new ArrayList<>();
        centreDecks = new ArrayList<>();
        playedCards = new ArrayList<>();
        arrows = new ArrayList<>();
        hands = new ArrayList<>();
        hands.add(hand1);
        hands.add(hand2);

        add(background);
        background.setLayout(new BoxLayout(background, BoxLayout.PAGE_AXIS));
        JLabel nomJ = new JLabel();
        if(jeu.getIA())
            nomJ.setText("Joueur 2 (IA)");
        else
            nomJ.setText("Joueur 2");
        background.add(nomJ);

        JPanel hand1Pane = new JPanel();
        hand1Pane.setOpaque(false);
        // Ajout de la fleche indiquant le tour du joueur 1
        JLabel arrow1Label = new JLabel();
        arrow1Label.setPreferredSize(dimlabel);
        hand1Pane.add(arrow1Label);
        this.arrows.add(arrow1Label);

        for (int i = 0; i < 11; i++) {
            JLabel l = new JLabel();
            hand1Pane.add(l);
            hand1.add(l);
        }
        JLabel space = new JLabel();
        space.setOpaque(false);
        space.setPreferredSize(new Dimension(dimlabel.width, dimlabel.height / 2));

        indPioche = new JLabel("Pioches");
        JPanel paquetCentrePane = new JPanel();
        paquetCentrePane.setOpaque(false);
        for (int i = 0; i < 6; i++) {
            JLabel l = new JLabel();
            paquetCentrePane.add(l);
            centreDecks.add(l);
        }

        JPanel carteJoueePane = new JPanel();
        carteJoueePane.setOpaque(false);
        JLabel joue1 = new JLabel();
        joue1.setPreferredSize(dimlabel);
        carteJoueePane.add(joue1);
        playedCards.add(joue1);

        JLabel joue2 = new JLabel();
        joue2.setPreferredSize(dimlabel);
        carteJoueePane.add(joue2);
        playedCards.add(joue2);
        JLabel space2 = new JLabel();
        space2.setPreferredSize(new Dimension(dimlabel.width, dimlabel.height / 2));

        JLabel nomJ2 = new JLabel("Joueur 1");
        JPanel hand2Pane = new JPanel();
        hand2Pane.setOpaque(false);

        // Ajout de la fleche indiquant le tour du joueur 2
        JLabel arrow2Label = new JLabel();
        arrow2Label.setPreferredSize(dimlabel);
        hand2Pane.add(arrow2Label);
        this.arrows.add(arrow2Label);

        for (int i = 0; i < 11; i++) {
            JLabel l = new JLabel();
            hand2Pane.add(l);
            hand2.add(l);
        }
        background.add(hand2Pane);
        background.add(space);
        background.add(indPioche);
        background.add(paquetCentrePane);
        background.add(carteJoueePane);
        background.add(space2);
        background.add(nomJ2);
        background.add(hand1Pane);
        miseAJour();
        this.revalidate();
        this.repaint();
    }

    // Fonction de mise a jour appelée dès que necessaire
    @Override
    public void miseAJour() {
        //Fonction qui met a jour toutes les infos sur le plateau et le menu de droite
        //Si on est a 2 joueur et a une nouvelle main, on veut cacher les cartes et indiquer le chgt de joueur
        if(((jeu.etape()==0 && etapePrecedente==3) || (jeu.etape()==0 && etapePrecedente==1)) && !jeu.getIA() && chgtJoueur){
            majMainJoueur(0, true);
            majMainJoueur(1, true);
            JoueurCarteListener.active=false;
            FenetreAvertissement f = new FenetreAvertissement("Changement de joueur : c'est au tour du Joueur "+(jeu.joueurActuelle()+1), new Dimension(350,120),this);
            chgtJoueur = false;
        } else{
        //Si on est à l'attente de la pioche de l'IA, ne rien faire
        if (!waitPioche) {
            int cartePioche = jeu.getCarteApiocher();

            //Si on est a un une nouvelle manche
            if (manchePrec != jeu.getMancheactuelle()) {
                System.out.println("changement de manche");
                chgtManche = true;
                m.ajouterManche(manchePrec, lastnbPlis1, lastnbPlis2, jeu.getMains()[0].getnbScore(),
                        jeu.getMains()[1].getnbScore());
                this.removeAll();
                this.revalidate();
                this.repaint();
                if (jeu.enCours()) {
                    showFinManche();
                } else {
                    showFinPartie();
                }

                //Si c'est a l'IA de piocher, on veut indiquer la carte qu'elle choisit pendant un court instant
            } else if (jeu.getIA() && jeu.joueurActuelle() == 1 && (jeu.etape() == 2 || jeu.etape() == 3) && cartePioche != -1) {

                waitPioche = true;
                centreDecks.get(cartePioche).setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                Timer t = new Timer(1500, (ActionEvent e) -> majTimePioche(cartePioche));
                t.setRepeats(false);
                t.start();

                //Si on est pas a un moment d'indication de carte (cas classique)
            } else if (cartePioche == -1) {
                //Mise a jour des infos du menu de coté
                m.setNumManche(jeu.getMancheactuelle());
                m.indiqueAtout(jeu.getAtout().name(), dimlabel);
                m.setPlis(jeu.getMains()[0].getnbPlis(), jeu.getMains()[1].getnbPlis(), jeu.getMains()[0].getnbScore(),
                        jeu.getMains()[1].getnbScore());
                //Si l'etape precedente est 1, CAD que les 2 joueurs ont placé leur carte du tour
                if (etapePrecedente == 1 && jeu.getC_sub() != null && jeu.getC_dom() != null && !timed) {
                    m.setResDernierPlis(jeu.j_dom(), jeu.getC_sub().getResourceName(), jeu.getC_dom().getResourceName(),
                            dimlabel);
                    //Si il n'y a plus de pioche, on souhaite attendre un court temps pour laisser ces 2 cartes affichées
                    if (jeu.pilesvide()) {
                        System.out.println("majTimer");
                        timed = true;
                        JoueurCarteListener.active = false;
                        majCarteJouees();
                        Timer t = new Timer(1500, (ActionEvent e) -> majTime());
                        t.setRepeats(false);
                        t.start();
                        //Si il y a encore des pioches, maj classique
                    } else if (!jeu.pilesvide()) {
                        etapePrecedente = jeu.etape();
                        majFleche();
                        majMainJoueur(0, false);
                        majPaquets();
                        majMainJoueur(1, false);
                        majCarteJouees();
                    }
                    //Si on est pas dans ces cas ni en attente d'un timer, maj classique
                } else if (!timed) {
                    etapePrecedente = jeu.etape();
                    majFleche();
                    majMainJoueur(0, false);
                    majPaquets();
                    majMainJoueur(1, false);
                    majCarteJouees();
                }
            }
            manchePrec = jeu.getMancheactuelle();
            lastnbPlis1 = jeu.getMains()[0].getnbPlis();
            lastnbPlis2 = jeu.getMains()[1].getnbPlis();
        }
        chgtJoueur = true;
        }
    }

    private void majMainJoueur(int numJ, boolean hide) {

        ImageIcon icon2;
        for (int i = 0; i < 11; i++) {
            hands.get(numJ).get(i).setBorder(null);
            if (i < jeu.getMains()[numJ].getnbCarte()) {

                if (jeu.getShowCarte() == false && ((jeu.getIA() == true && numJ == 1)
                        || (jeu.getIA() == false && jeu.joueurActuelle() != numJ)) || hide) {

                    icon2 = new ImageIcon(
                            new ImageIcon(ClassLoader.getSystemClassLoader().getResource("Back" + jeu.getSelCarte() + ".png")).getImage()
                            .getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_SMOOTH));
                } else {
                    icon2 = new ImageIcon(new ImageIcon(ClassLoader.getSystemClassLoader()
                            .getResource(jeu.getMains()[numJ].getMain()[i].getResourceName())).getImage()
                            .getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_SMOOTH));
                }
                hands.get(numJ).get(i).setIcon(icon2);

                if ((jeu.etape() == 0 || jeu.etape() == 1) && jeu.joueurActuelle() == numJ && jeu.peutJouer(i, numJ)) {
                    //Ajout des listeners de carte si necessaire
                    if (hands.get(numJ).get(i).getMouseListeners().length == 0 && !(jeu.getIA() && jeu.joueurActuelle() == 1)) {
                        hands.get(numJ).get(i).addMouseListener(new JoueurCarteListener(i, c, dimlabel));

                    }
                } else {
                    //Grisage des cartes du joueur courant qu'il ne peut jouer si ce n'est pas une IA
                    if ((jeu.etape() == 0 || jeu.etape() == 1) && jeu.joueurActuelle() == numJ
                            && !jeu.peutJouer(i, numJ) && !(jeu.getIA() && jeu.joueurActuelle() == 1)) {
                        Icon img = new ImageIcon(GrayFilter.createDisabledImage(icon2.getImage()));
                        hands.get(numJ).get(i).setIcon(img);
                    }
                    //On enleve les listeners des cartes pour les autres cas ou on en a pas besoin
                    if (hands.get(numJ).get(i).getMouseListeners().length > 0) {
                        hands.get(numJ).get(i).removeMouseListener(hands.get(numJ).get(i).getMouseListeners()[0]);
                    }
                }
            } else {
                hands.get(numJ).get(i).setIcon(null);
                if (hands.get(numJ).get(i).getMouseListeners().length > 0) {
                    hands.get(numJ).get(i).removeMouseListener(hands.get(numJ).get(i).getMouseListeners()[0]);
                }
            }
        }
    }

    private void majPaquets() {
        if (jeu.etape() == 2 || jeu.etape() == 3) {
            indPioche.setText("<html><span bgcolor=\"yellow\">Pioches</span></html>");
        } else {
            indPioche.setText("Pioches");
        }
        for (int i = 0; i < 6; i++) {
            centreDecks.get(i).setBorder(null);
            if (jeu.getPiles()[i].estVide()) {
                centreDecks.get(i).setIcon(null);
                centreDecks.get(i).setPreferredSize(dimlabel);
                centreDecks.get(i).setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
                if (centreDecks.get(i).getMouseListeners().length > 0) {
                    centreDecks.get(i).removeMouseListener(centreDecks.get(i).getMouseListeners()[0]);
                }
            } else {

                Icon img = new ImageIcon(new ImageIcon(
                        ClassLoader.getSystemClassLoader().getResource(jeu.getPiles()[i].topDeck().getResourceName()))
                        .getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_SMOOTH));
                centreDecks.get(i).setIcon(img);

                // SI on est a une étape de pioche
                if (jeu.etape() == 2 || jeu.etape() == 3 && !(jeu.getIA() && jeu.joueurActuelle() == 1)) {
                    if (centreDecks.get(i).getMouseListeners().length == 0 && !(jeu.getIA() && jeu.joueurActuelle() == 1)) {
                        centreDecks.get(i).addMouseListener(new JoueurCarteListener(i, c, dimlabel));
                    }
                } else if (centreDecks.get(i).getMouseListeners().length > 0) {
                    centreDecks.get(i).removeMouseListener(centreDecks.get(i).getMouseListeners()[0]);
                }
            }
            
        }
    }

    private void majCarteJouees() {
        if (jeu.etape() != 2 && jeu.etape() != 3) {
            playedCards.get(0).setIcon(null);
            playedCards.get(1).setIcon(null);
        }
        if (jeu.etape() == 1 || jeu.etape() == 2 || timed) {

            ImageIcon icon = new ImageIcon(
                    new ImageIcon(ClassLoader.getSystemClassLoader().getResource(jeu.getC_dom().getResourceName()))
                    .getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_SMOOTH));

            playedCards.get(0).setIcon(icon);
        }
        if (jeu.etape() == 2 || timed) {

            Icon img = new ImageIcon(
                    new ImageIcon(ClassLoader.getSystemClassLoader().getResource(jeu.getC_sub().getResourceName()))
                    .getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_SMOOTH));
            ;
            playedCards.get(1).setIcon(img);
        }
    }

    private void majFleche() {
       String j1ImgName;
       String j2ImgName;
       
       //Nom de l'image en fonction de l'état du jeu
       if (jeu.getIA() == true) {
    	   j2ImgName = "IA";
    	   System.out.println("Niveau IA : " + jeu.niveauIA());
    	   
    	   switch(jeu.niveauIA()) {
    	   case 0:
    		   System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAH");
    		   j2ImgName =  j2ImgName.concat("Eas");
    		   break;
    	   case 1:
    		   j2ImgName = j2ImgName.concat("Med");
    		   break;
    	   case 2:
    		   j2ImgName = j2ImgName.concat("Har");
    		   break;
    	   }
    	   
       }else {
    	   j2ImgName = "H2";
       }
       
       j1ImgName = "H1";
       
       
       System.out.println("Joueur actuel : " + jeu.joueurActuelle());
       if (jeu.joueurActuelle() == 0) {
    	   j1ImgName = j1ImgName.concat("w");
    	   j2ImgName = j2ImgName.concat("s");
       }else {
    	   j1ImgName = j1ImgName.concat("s");
    	   j2ImgName = j2ImgName.concat("w");
       }
       
       j1ImgName = j1ImgName.concat(".png");
       j2ImgName = j2ImgName.concat(".png");
       
       System.out.println(j1ImgName);
       System.out.println(j2ImgName);
       
       Icon imgJ1 = new ImageIcon(new ImageIcon(ClassLoader.getSystemClassLoader()
    		   .getResource(j1ImgName)).getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_AREA_AVERAGING));
       
       Icon imgJ2 = new ImageIcon(new ImageIcon(ClassLoader.getSystemClassLoader()
    		   .getResource(j2ImgName)).getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_AREA_AVERAGING));
       
       
       this.arrows.get(0).setIcon(imgJ1);
       this.arrows.get(1).setIcon(imgJ2);
    }

    private void showFinManche() {
        //affichage de l'écran de fin de manche
        JPanel finManchePane = new JPanel();
        background.removeAll();
        finManchePane.setLayout(new BorderLayout());
        finManchePane.add(background);
        background.setLayout(new BorderLayout());
        JPanel textPan = new JPanel();
        textPan.setLayout(new BorderLayout());
        JLabel finRemporte;
        if (lastnbPlis1 > lastnbPlis2) {
            finRemporte = new JLabel("<html><p style=\"font-size:30px;color:red\">La manche " + (jeu.getMancheactuelle() - 1) + " est terminée</p>" + "Remportée par le joueur 1 avec " + lastnbPlis1 + " plis a " + lastnbPlis2 + "<html>");
        } else if (lastnbPlis1 < lastnbPlis2) {
            finRemporte = new JLabel("<html><p style=\"font-size:30px;color:red\">La manche " + (jeu.getMancheactuelle() - 1) + " est terminée</p>" + "Remportée par le joueur 2 avec " + lastnbPlis2 + " plis a " + lastnbPlis1 + "<html>");
        } else {
            finRemporte = new JLabel("<html><p style=\"font-size:30px;color:red\">La manche " + (jeu.getMancheactuelle() - 1) + " est terminée</p>" + "Egalité sur cette manche<html>");
        }
        finRemporte.setFont(new Font("Calibri", Font.PLAIN, 28));
        finRemporte.setHorizontalAlignment(JLabel.CENTER);
        finRemporte.setVerticalAlignment(JLabel.CENTER);
        background.add(finRemporte, BorderLayout.CENTER);

        JButton contBout = new JButton("Continuer la partie");
        contBout.setFont(new Font("Calibri", Font.PLAIN, 28));
        background.add(contBout, BorderLayout.SOUTH);
        this.add(finManchePane);
        this.revalidate();
        final Plateau p = this;
        contBout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.initNouvelleManche();
            }
        });
    }

    private void showFinPartie() {
        //Affichage de l'écran de fin de partie
        this.manchePrec = 1;
        JPanel finManchePane = new JPanel();
        background.removeAll();
        finManchePane.setLayout(new BorderLayout());
        finManchePane.add(background);
        background.setLayout(new BorderLayout());
        JPanel textPan = new JPanel();
        textPan.setLayout(new BorderLayout());
        JLabel finRemporte;
        if (jeu.getMains()[0].getnbScore() > jeu.getMains()[1].getnbScore()) {
            finRemporte = new JLabel("<html><p style=\"font-size:30px;color:red\">La partie est terminée</p>" + "Remportée par le joueur 1 avec un score de " + jeu.getMains()[0].getnbScore() + " a " + jeu.getMains()[1].getnbScore() + "<html>");
        } else if (jeu.getMains()[1].getnbScore() < jeu.getMains()[0].getnbScore()) {
            finRemporte = new JLabel("<html><p style=\"font-size:30px;color:red\">La partie est terminée</p>" + "Remportée par le joueur 2 avec un score de" + jeu.getMains()[1].getnbScore() + " a " + jeu.getMains()[0].getnbScore() + "<html>");
        } else {
            finRemporte = new JLabel("<html><p style=\"font-size:30px;color:red\">La partie est terminée</p>" + "Egalité <html>");
        }
        finRemporte.setFont(new Font("Calibri", Font.PLAIN, 28));
        finRemporte.setHorizontalAlignment(JLabel.CENTER);
        finRemporte.setVerticalAlignment(JLabel.CENTER);
        background.add(finRemporte, BorderLayout.CENTER);
        JPanel ButtonP = new JPanel();
        ButtonP.setLayout(new GridLayout(1, 2));
        JButton menuButton = new JButton("Retour au menu");
        menuButton.setFont(new Font("Calibri", Font.PLAIN, 28));
        ButtonP.add(menuButton);
        JButton restartButton = new JButton("Recommencer");
        restartButton.setFont(new Font("Calibri", Font.PLAIN, 28));
        ButtonP.add(restartButton);
        background.add(ButtonP, BorderLayout.SOUTH);

        this.add(finManchePane);
        this.revalidate();
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartingMenu m = new StartingMenu();
                m.setSize(500, 500);
                m.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initNouvellePartie();
            }
        });
    }

    private void initNouvelleManche() {
        background.removeAll();
        this.creerPlateau();
    }

    private void initNouvellePartie() {
        c.recommencer();
        background.removeAll();
        this.creerPlateau();
    }

    public void resetCarteJoue() {
        playedCards.get(0).setIcon(null);
        playedCards.get(1).setIcon(null);
    }

    private void majTime() {
        //fonction appelée après l'attente qui permet de montrer les 2 cartes jouees
        etapePrecedente = jeu.etape();
        majFleche();
        majMainJoueur(0, false);
        majPaquets();
        majMainJoueur(1, false);

        majCarteJouees();
        if (jeu.getIA()) {
            playedCards.get(1).setIcon(null);
            if (jeu.joueurActuelle() == 0 && jeu.etape() == 0) {
                playedCards.get(0).setIcon(null);
            }
        } else {
            resetCarteJoue();
        }
        timed = false;
        JoueurCarteListener.active = true;
    }

    private void majTimePioche(int pioche) {
        //fonction appelée après l'attente qui montre la carte que l'IA pioche
        System.out.println("FINTIMERPIOCHE");
        centreDecks.get(pioche).setBorder(null);
        waitPioche = false;
        miseAJour();

    }

    //Permet de mettre en surbrillance un coup proposé par l'ia simple si le joueur le demande
    void suggererUnCoup() {
        if (!(jeu.getIA() && jeu.joueurActuelle() == 1)) {
            IASimple suggereIA = new IASimple(jeu.joueurActuelle(), jeu);
            int coupSuggere = suggereIA.IAjeu();
            if (jeu.etape() == 0 || jeu.etape() == 1) {
                hands.get(jeu.joueurActuelle()).get(coupSuggere).setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
            } else {
                centreDecks.get(coupSuggere).setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
            }
        }
    }
}
