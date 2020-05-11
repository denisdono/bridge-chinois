package Vue;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Plateau extends JPanel implements Observateur {

	Jeu jeu;
	private int largeurCase = 1, hauteurCase = 1;
	private CollecteurEvenements c;
	private Menu m;
	private ArrayList<JLabel> arrows;
	private ArrayList<JLabel> hand1;
	private ArrayList<JLabel> hand2;
	private ArrayList<JLabel> centreDecks;
	private ArrayList<JLabel> playedCards;
	private ArrayList<ArrayList<JLabel>> hands;
	// Dimensions a revoir, adapter a la taille de l'écran
	private Dimension dimlabel;
	private int etapePrecedente;
	private int manchePrec = 1;
	private boolean chgtManche = false;
	private int lastnbPlis1 = 0;
	private int lastnbPlis2 = 0;
	private JLabel indPioche;
	private JLabel background;

	public Plateau(Jeu j, CollecteurEvenements c, Menu m) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dimlabel = new Dimension(screenSize.width / 25, screenSize.height / 10);
		this.m = m;
		this.c = c;
		//setLayout(new BorderLayout());
        //add(new JLabel(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("arrow.png"))));
		setLayout(new BorderLayout());
        background=new JLabel(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("Background.jpg")));
        add(background);
        background.setLayout(new FlowLayout());
		jeu = j;
		// On ajoute le plateau dans la liste des observateur
		// Les observateurs seront mis à jour par le jeu dès que nécessaire
		jeu.ajouteObservateur(this);
		m.setTaille(new Dimension(dimlabel.width * 4, dimlabel.height * 7),
				new Dimension((int) (dimlabel.width * 1.6), dimlabel.height));
		background.setLayout(new BoxLayout(background, BoxLayout.PAGE_AXIS));
		// this.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 20));
		this.setPreferredSize(new Dimension(dimlabel.width * 14, dimlabel.height * 7));
		creerPlateau();
//        majFleche();
//        majMainJoueur(0);
//        majPaquets();
//        majMainJoueur(1);
//        majCarteJouees();
//        this.m.indiqueAtout(jeu.getAtout().name());

		// repaint();
	}

	public void creerPlateau() {
		hand1 = new ArrayList<>();
		hand2 = new ArrayList<>();
		centreDecks = new ArrayList<>();
		playedCards = new ArrayList<>();
		arrows = new ArrayList<>();
		hands = new ArrayList<>();
		hands.add(hand1);
		hands.add(hand2);
		System.out.println("creerplat");
		JLabel nomJ = new JLabel("Joueur 1");
		background.add(nomJ);
		JPanel hand1Pane = new JPanel();
		hand1Pane.setOpaque(false);
		// Ajout de la fl�che indiquant le tour du joueur 1
		JLabel arrow1Label = new JLabel();
		hand1Pane.add(arrow1Label);
		this.arrows.add(arrow1Label);

		for (int i = 0; i < 11; i++) {
			JLabel l = new JLabel();
			hand1Pane.add(l);
			hand1.add(l);
		}
		background.add(hand1Pane);
		JLabel space = new JLabel();
		space.setOpaque(false);
		space.setPreferredSize(new Dimension(dimlabel.width, dimlabel.height / 2));
		background.add(space);

		indPioche = new JLabel("Pioches");
		background.add(indPioche);
		JPanel paquetCentrePane = new JPanel();
		paquetCentrePane.setOpaque(false);
		for (int i = 0; i < 6; i++) {
			JLabel l = new JLabel();
			paquetCentrePane.add(l);
			centreDecks.add(l);
		}
		background.add(paquetCentrePane);

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
		background.add(carteJoueePane);
		JLabel space2 = new JLabel();
		space2.setPreferredSize(new Dimension(dimlabel.width, dimlabel.height / 2));
		background.add(space2);

		JLabel nomJ2 = new JLabel("Joueur 2");
		background.add(nomJ2);
		JPanel hand2Pane = new JPanel();
		hand2Pane.setOpaque(false);

		// Ajout de la fl�che indiquant le tour du joueur 2
		JLabel arrow2Label = new JLabel();
		hand2Pane.add(arrow2Label);
		this.arrows.add(arrow2Label);

		for (int i = 0; i < 11; i++) {
			JLabel l = new JLabel();
			hand2Pane.add(l);
			hand2.add(l);
		}
		background.add(hand2Pane);
		miseAJour();
		this.revalidate();
	}

	// Fonction de mise a jour appelée dès que necessaire
	@Override
	public void miseAJour() {
		// Mise à jour des infos du menu
		System.out.println("MAJ");
		if (manchePrec != jeu.getMancheactuelle()) {
			System.out.println("changement de manche");
			chgtManche = true;
			this.removeAll();
			this.revalidate();
			this.repaint();
			showFinManche();
		} else {
			m.setNumManche(jeu.getMancheactuelle());
			m.indiqueAtout(jeu.getAtout().name(), dimlabel);
			m.setPlis(jeu.getMains()[0].getnbPlis(), jeu.getMains()[1].getnbPlis(), jeu.getMains()[0].getnbScore(),
					jeu.getMains()[1].getnbScore());
			if (etapePrecedente == 1) {
				m.setResDernierPlis(jeu.j_dom(), jeu.getC_sub().getResourceName(), jeu.getC_dom().getResourceName(),
						dimlabel);
			}
			etapePrecedente = jeu.etape();
			majFleche();
			majMainJoueur(0);
			majPaquets();
			majMainJoueur(1);
			majCarteJouees();
		}
		manchePrec = jeu.getMancheactuelle();
		lastnbPlis1 = jeu.getMains()[0].getnbPlis();
		lastnbPlis2 = jeu.getMains()[1].getnbPlis();
	}

	private void majMainJoueur(int numJ) {

		for (int i = 0; i < 11; i++) {
			if (i < jeu.getMains()[numJ].getnbCarte()) {

				// Si on veut montrer les cartes ou non
//                if (numJ ==1 && jeu.getIA()) {
//                    icon = new ImageIcon(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("Back Blue 1.png")).getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_SMOOTH));
//
//                } else {
//                    icon = new ImageIcon(new ImageIcon(ClassLoader.getSystemClassLoader().getResource(jeu.getMains()[numJ].getMain()[i].getResourceName())).getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_SMOOTH));
//
//                }
				// Icon img = new ImageIcon(GrayFilter.createDisabledImage(icon.getImage()));
				// hands.get(numJ).get(i).setIcon(img);
				ImageIcon icon2 = new ImageIcon(new ImageIcon(ClassLoader.getSystemClassLoader()
						.getResource(jeu.getMains()[numJ].getMain()[i].getResourceName())).getImage()
								.getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_SMOOTH));
				hands.get(numJ).get(i).setIcon(icon2);
				if ((jeu.etape() == 0 || jeu.etape() == 1) && jeu.joueurActuelle() == numJ && jeu.peutJouer(i, numJ)) {

					if (hands.get(numJ).get(i).getMouseListeners().length == 0) {
						hands.get(numJ).get(i).addMouseListener(new JoueurCarteListener(i, c));

					}
				} else {
					if ((jeu.etape() == 0 || jeu.etape() == 1) && jeu.joueurActuelle() == numJ
							&& !jeu.peutJouer(i, numJ)) {
						// ImageIcon icon = new ImageIcon(new
						// ImageIcon(ClassLoader.getSystemClassLoader().getResource(jeu.getMains()[numJ].getMain()[i].getResourceName())).getImage().getScaledInstance(dimlabel.width,
						// dimlabel.height, Image.SCALE_SMOOTH));
						Icon img = new ImageIcon(GrayFilter.createDisabledImage(icon2.getImage()));
						hands.get(numJ).get(i).setIcon(img);
					}
					if (hands.get(numJ).get(i).getMouseListeners().length > 0) {
						hands.get(numJ).get(i).removeMouseListener(hands.get(numJ).get(i).getMouseListeners()[0]);
					}
				}
			} else {
				hands.get(numJ).get(i).setIcon(null);
			}
		}
	}

	private void majPaquets() {
		if (jeu.etape() == 2 || jeu.etape() == 3) {
			indPioche.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		} else {
			indPioche.setBorder(null);
		}
		for (int i = 0; i < 6; i++) {
			if (jeu.getPiles()[i].estVide()) {
				centreDecks.get(i).setIcon(null);
				centreDecks.get(i).setText("Vide");
				if (centreDecks.get(i).getMouseListeners().length > 0) {
					centreDecks.get(i).removeMouseListener(centreDecks.get(i).getMouseListeners()[0]);
				}
			} else {
				// version texte
				// l.setText(jeu.getPiles()[i].topDeck().toString());
				// version image
				Icon img = new ImageIcon(new ImageIcon(
						ClassLoader.getSystemClassLoader().getResource(jeu.getPiles()[i].topDeck().getResourceName()))
								.getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_SMOOTH));
				centreDecks.get(i).setIcon(img);

				// SI on est a une étape de pioche
				if (jeu.etape() == 2 || jeu.etape() == 3) {
					if (centreDecks.get(i).getMouseListeners().length == 0) {
						centreDecks.get(i).addMouseListener(new JoueurCarteListener(i, c));
					}
				} else if (centreDecks.get(i).getMouseListeners().length > 0) {
					centreDecks.get(i).removeMouseListener(centreDecks.get(i).getMouseListeners()[0]);
				}
			}
			// l.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			centreDecks.get(i).setPreferredSize(dimlabel);
		}
	}

	private void majCarteJouees() {
		playedCards.get(0).setIcon(null);
		playedCards.get(1).setIcon(null);
		if (jeu.etape() == 1 || jeu.etape() == 2) {
			// version texte
			// joue1.setText(jeu.getC_dom().toString());
			// version image
			// ImageIcon icon = new
			// ImageIcon(ClassLoader.getSystemClassLoader().getResource(jeu.getC_dom().getResourceName()));;
			ImageIcon icon = new ImageIcon(
					new ImageIcon(ClassLoader.getSystemClassLoader().getResource(jeu.getC_dom().getResourceName()))
							.getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_SMOOTH));

			playedCards.get(0).setIcon(icon);
		}
		if (jeu.etape() == 2) {
			// version texte
			// joue2.setText(jeu.getC_sub().toString());
			// version image
			Icon img = new ImageIcon(
					new ImageIcon(ClassLoader.getSystemClassLoader().getResource(jeu.getC_sub().getResourceName()))
							.getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_SMOOTH));
			;
			playedCards.get(1).setIcon(img);
		}
	}

	private void majFleche() {
		Icon imgArrow = new ImageIcon(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("arrow.png"))
				.getImage().getScaledInstance(dimlabel.width, dimlabel.height / 2, Image.SCALE_SMOOTH));

		this.arrows.get(jeu.joueurActuelle()).setIcon(imgArrow);
		System.out.println("joueur actif : " + jeu.joueurActuelle() + "\n");
		this.arrows.get((jeu.joueurActuelle() + 1) % 2).setIcon(null);
	}

//	@Override
//	protected void paintComponent(Graphics g) {
//		super.paintComponent(g); // paint the background image and scale it to fill the entire space
//		BufferedImage image;
//		 try {                
//	          image = ImageIO.read(ClassLoader.getSystemClassLoader().getResource("Background.jpg"));
//	       } catch (IOException ex) {
//	    	   System.out.println("ca marche pas");
//	            image = null;
//	       }
//		g.drawImage(image,0,0,this);
//	}

	private void showFinManche() {
		JPanel finManchePane = new JPanel();
		finManchePane.setLayout(new BoxLayout(finManchePane, BoxLayout.PAGE_AXIS));
		finManchePane.setFont(new Font("Calibri", Font.PLAIN, 28));
		JLabel finJ = new JLabel("La manche " + (jeu.getMancheactuelle() - 1) + " est terminee");
		finJ.setFont(new Font("Calibri", Font.PLAIN, 28));
		finManchePane.add(finJ);
		JLabel finRemporte;
		if (lastnbPlis1 > lastnbPlis2) {
			finRemporte = new JLabel("Remportee par le joueur 1 avec " + lastnbPlis1 + " plis a " + lastnbPlis2);
		} else if (lastnbPlis1 < lastnbPlis2) {
			finRemporte = new JLabel("Remportee par le joueur 2 avec " + lastnbPlis2 + " plis a " + lastnbPlis1);
		} else {
			finRemporte = new JLabel("Egalité sur cette manche");
		}
		finManchePane.add(finRemporte);

		JButton contBout = new JButton("Continuer la partie");
		finManchePane.add(contBout);
		background.add(finManchePane);
		this.revalidate();
		final Plateau p = this;
		contBout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				p.initNouvelleManche();
			}
		});
	}

	private void initNouvelleManche() {
		this.removeAll();
		this.creerPlateau();
		this.revalidate();
		this.repaint();
	}
}
