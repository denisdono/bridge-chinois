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
	private JFrame frame;

	public Plateau(Jeu j, CollecteurEvenements c, Menu m, Dimension d, JFrame frame) {
		dimlabel = d;
		this.m = m;
		this.c = c;
		this.frame=frame;
		// setLayout(new BorderLayout());
		// add(new JLabel(new
		// ImageIcon(ClassLoader.getSystemClassLoader().getResource("arrow.png"))));
		setLayout(new BorderLayout());

		jeu = j;
		// On ajoute le plateau dans la liste des observateur
		// Les observateurs seront mis à jour par le jeu dès que nécessaire
		jeu.ajouteObservateur(this);
//		m.setTaille(new Dimension(dimlabel.width * 4, dimlabel.height * 7),
//				new Dimension((int) (dimlabel.width * 1.6), dimlabel.height));
		

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
		background = new JLabel(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("Background"+jeu.getSelFond()+".jpg")));
		hand1 = new ArrayList<>();
		hand2 = new ArrayList<>();
		centreDecks = new ArrayList<>();
		playedCards = new ArrayList<>();
		arrows = new ArrayList<>();
		hands = new ArrayList<>();
		hands.add(hand1);
		hands.add(hand2);

		System.out.println("creerplat");

		add(background);
		background.setLayout(new BoxLayout(background, BoxLayout.PAGE_AXIS));

		JLabel nomJ = new JLabel("Joueur 1");

		background.add(nomJ);

		JPanel hand1Pane = new JPanel();
		hand1Pane.setOpaque(false);
		// Ajout de la fl�che indiquant le tour du joueur 1
		JLabel arrow1Label = new JLabel();
		arrow1Label.setPreferredSize(dimlabel);
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
		arrow2Label.setPreferredSize(dimlabel);
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
			m.ajouterManche(manchePrec, lastnbPlis1, lastnbPlis2, jeu.getMains()[0].getnbScore(),
					jeu.getMains()[1].getnbScore());
			this.removeAll();
			this.revalidate();
			this.repaint();
			if(jeu.enCours()) {
				showFinManche();
			}
			else {
				showFinPartie();
			}
		} else {
			m.setNumManche(jeu.getMancheactuelle());
			m.indiqueAtout(jeu.getAtout().name(), dimlabel);
			m.setPlis(jeu.getMains()[0].getnbPlis(), jeu.getMains()[1].getnbPlis(), jeu.getMains()[0].getnbScore(),
					jeu.getMains()[1].getnbScore());
			if (etapePrecedente == 1 && jeu.getC_sub()!=null && jeu.getC_dom()!=null ) {
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

		ImageIcon icon2;

		for (int i = 0; i < 11; i++) {
			if (i < jeu.getMains()[numJ].getnbCarte()) {

				if (jeu.getShowCarte() == false && ((jeu.getIA() == true && numJ == 1)
						|| (jeu.getIA() == false && jeu.joueurActuelle() != numJ))) {

					icon2 = new ImageIcon(
							new ImageIcon(ClassLoader.getSystemClassLoader().getResource("Back Blue 1.png")).getImage()
									.getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_SMOOTH));

				} else {

					icon2 = new ImageIcon(new ImageIcon(ClassLoader.getSystemClassLoader()
							.getResource(jeu.getMains()[numJ].getMain()[i].getResourceName())).getImage()
									.getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_SMOOTH));

				}
				hands.get(numJ).get(i).setIcon(icon2);

				if ((jeu.etape() == 0 || jeu.etape() == 1) && jeu.joueurActuelle() == numJ && jeu.peutJouer(i, numJ)) {
                                        //Ajout des listeners de carte si necessaire
					if (hands.get(numJ).get(i).getMouseListeners().length == 0 && !(jeu.getIA() && jeu.joueurActuelle()==1)) {
						hands.get(numJ).get(i).addMouseListener(new JoueurCarteListener(i, c));

					}
				} else {
                                        //Grisage des cartes
					if ((jeu.etape() == 0 || jeu.etape() == 1) && jeu.joueurActuelle() == numJ
							&& !jeu.peutJouer(i, numJ) && !(jeu.getIA() && jeu.joueurActuelle()==1)) {					
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
	
				Icon img = new ImageIcon(new ImageIcon(
						ClassLoader.getSystemClassLoader().getResource(jeu.getPiles()[i].topDeck().getResourceName()))
								.getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_SMOOTH));
				centreDecks.get(i).setIcon(img);

				// SI on est a une étape de pioche
				if (jeu.etape() == 2 || jeu.etape() == 3) {
					if (centreDecks.get(i).getMouseListeners().length == 0 && !(jeu.getIA() &&jeu.joueurActuelle()==1)) {
						centreDecks.get(i).addMouseListener(new JoueurCarteListener(i, c));
					}
				} else if (centreDecks.get(i).getMouseListeners().length > 0) {
					centreDecks.get(i).removeMouseListener(centreDecks.get(i).getMouseListeners()[0]);
				}
			}
			centreDecks.get(i).setPreferredSize(dimlabel);
		}
	}

	private void majCarteJouees() {
		playedCards.get(0).setIcon(null);
		playedCards.get(1).setIcon(null);
		if (jeu.etape() == 1 || jeu.etape() == 2) {
		
			ImageIcon icon = new ImageIcon(
					new ImageIcon(ClassLoader.getSystemClassLoader().getResource(jeu.getC_dom().getResourceName()))
							.getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_SMOOTH));

			playedCards.get(0).setIcon(icon);
		}
		if (jeu.etape() == 2) {

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

	private void showFinManche() {
		JPanel finManchePane = new JPanel();
		background.removeAll();
		finManchePane.setLayout(new BorderLayout());
		finManchePane.add(background);
		background.setLayout(new BorderLayout());
		JPanel textPan= new JPanel();
		textPan.setLayout(new BorderLayout());
		//JLabel finJ = new JLabel("La manche " + (jeu.getMancheactuelle() - 1) + " est terminee\n");
		//finJ.setFont(new Font("Calibri", Font.PLAIN, 28));
		//finJ.setHorizontalAlignment(JLabel.CENTER);
		//finJ.setVerticalAlignment(JLabel.CENTER);
		//textPan.add(finJ,BorderLayout.NORTH);
		JLabel finRemporte;
		if (lastnbPlis1 > lastnbPlis2) {
			finRemporte = new JLabel("<html><p style=\"font-size:30px;color:red\">La manche " + (jeu.getMancheactuelle() - 1) + " est terminée</p>"+"Remportée par le joueur 1 avec " + lastnbPlis1 + " plis a " + lastnbPlis2+"<html>");
		} else if (lastnbPlis1 < lastnbPlis2) {
			finRemporte = new JLabel("<html><p style=\"font-size:30px;color:red\">La manche " + (jeu.getMancheactuelle() - 1) + " est terminée</p>"+"Remportée par le joueur 2 avec " + lastnbPlis2 + " plis a " + lastnbPlis1+"<html>");
		} else {
			finRemporte = new JLabel("<html><p style=\"font-size:30px;color:red\">La manche " + (jeu.getMancheactuelle() - 1) + " est terminée</p>"+"Egalité sur cette manche<html>");
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
		this.manchePrec=1;
		JPanel finManchePane = new JPanel();
		background.removeAll();
		finManchePane.setLayout(new BorderLayout());
		finManchePane.add(background);
		background.setLayout(new BorderLayout());
		JPanel textPan= new JPanel();
		textPan.setLayout(new BorderLayout());
		//JLabel finJ = new JLabel("La manche " + (jeu.getMancheactuelle() - 1) + " est terminee\n");
		//finJ.setFont(new Font("Calibri", Font.PLAIN, 28));
		//finJ.setHorizontalAlignment(JLabel.CENTER);
		//finJ.setVerticalAlignment(JLabel.CENTER);
		//textPan.add(finJ,BorderLayout.NORTH);
		JLabel finRemporte;
		if (lastnbPlis1 > lastnbPlis2) {
			finRemporte = new JLabel("<html><p style=\"font-size:30px;color:red\">La partie est terminée</p>"+"Remportée par le joueur 1 avec un score de " + jeu.getMains()[0].getnbScore() + " a " +  jeu.getMains()[1].getnbScore()+"<html>");
		} else if (lastnbPlis1 < lastnbPlis2) {
			finRemporte = new JLabel("<html><p style=\"font-size:30px;color:red\">La partie est terminée</p>"+"Remportée par le joueur 2 avec un score de" + jeu.getMains()[1].getnbScore() + " a " + jeu.getMains()[0].getnbScore()+"<html>");
		} else {
			finRemporte = new JLabel("<html><p style=\"font-size:30px;color:red\">La partie est terminée</p>"+"Egalité <html>");
		}
		finRemporte.setFont(new Font("Calibri", Font.PLAIN, 28));
		finRemporte.setHorizontalAlignment(JLabel.CENTER);
		finRemporte.setVerticalAlignment(JLabel.CENTER);
		background.add(finRemporte, BorderLayout.CENTER);
		JPanel ButtonP = new JPanel();
		ButtonP.setLayout(new GridLayout(1,2));
		JButton menuButton = new JButton("Retour au menu");
		menuButton.setFont(new Font("Calibri", Font.PLAIN, 28));
		ButtonP.add(menuButton);
		JButton restartButton = new JButton("Recommencer");
		restartButton.setFont(new Font("Calibri", Font.PLAIN, 28));
		ButtonP.add(restartButton);
		background.add(ButtonP, BorderLayout.SOUTH);
		
		this.add(finManchePane);
		this.revalidate();
		final Plateau p = this;
		menuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StartingMenu m = new StartingMenu();
				m.setSize(500,500);
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
		this.removeAll();
		this.creerPlateau();
		this.revalidate();
		this.repaint();
	}
	private void initNouvellePartie() {
		jeu.start();
		background.removeAll();
		this.removeAll();
		this.creerPlateau();
		this.revalidate();
		this.repaint();
	}
}
