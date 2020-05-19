package Vue;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import Modele.Jeu;

public class menuBar extends JMenuBar { // menu du haut
	private CollecteurEvenements c;
	private boolean menu;
	private Jeu jeu;
	private Plateau pl;
	private JMenu menuParametres;
	private JMenu menuAction;
	private JMenu menuHelp;

	private JMenuItem sauvegarder;
	private JMenuItem charger;
	private JMenuItem undo;
	private JMenuItem redo;
	private JMenuItem conceed;
	private JMenuItem restart;
	private JMenuItem nouvelle;
	private JMenuItem config;
	private JMenuItem regles;
        private JMenuItem suggere;
	private JMenuItem menuB;
	private JFrame frame;
	menuBar() {
		completeIHM();
	}

	menuBar(CollecteurEvenements c, JFrame frame, Jeu jeu, Plateau pl) {
		this.c = c;
		this.frame = frame;
		this.jeu = jeu;
		this.pl=pl;
		menuAction = new JMenu("Actions");
		undo = new JMenuItem("Annuler (crtl+z)");
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 c.annule();
			}
		});
		menuAction.add(undo);
		redo = new JMenuItem("Restaurer (crtl+y)");
		menuAction.add(redo);
		redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 c.refait();
			}
		});
		conceed = new JMenuItem("Abandonner la manche");
		conceed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.abandonner();
			}
		});
		menuAction.add(conceed);
		
		menuB = new JMenuItem("Retour au menu");
		menuB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartingMenu m = new StartingMenu();
				m.setSize(500,500);
				m.setVisible(true);
				frame.setVisible(false);
				frame.dispose();
			}
		});
		menuAction.add(menuB);
		
		restart = new JMenuItem("Recommencer");
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.recommencer();
			}
		});
		menuAction.add(restart);
		
		nouvelle = new JMenuItem("Nouvelle Partie");
		nouvelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.nouvellePartie();
			}
		});
		menuAction.add(nouvelle);
		add(menuAction);

		completeIHM();

	}

	public void completeIHM() {
		
		menuParametres = new JMenu("Paramètres");
		sauvegarder = new JMenuItem("Sauvegarder");
		sauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 c.sauvegarde();
                                 FenetreAvertissement f = new FenetreAvertissement("Partie Sauvegardée. Vous pouvez la reprendre plus tard avec le menu Paramètres > Charger",new Dimension(600,120));
			}
		});
		menuParametres.add(sauvegarder);
		charger = new JMenuItem("Charger");
		menuParametres.add(charger);
		charger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
					c.charge();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		config = new JMenuItem("Configuration");
		config.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfigWindow lesConf = new ConfigWindow(jeu,pl);
				lesConf.montrer();
			}
		});

		menuParametres.add(config);

		add(menuParametres);

		menuHelp = new JMenu("Aide");

		regles = new JMenuItem("Règles");
		regles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReglesWindow lesRegles = new ReglesWindow();
				lesRegles.montrer();
			}
		});
                suggere = new JMenuItem("Suggérer un coup");
		suggere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pl.suggererUnCoup();
			}
		});
		menuHelp.add(regles);
                menuHelp.add(suggere);
		add(menuHelp);
	}

	public JMenu getMenuParametres() {
		return menuParametres;
	}

	public JMenu getMenuAction() {
		return menuAction;
	}

	public JMenuItem getSauvegarder() {
		return sauvegarder;
	}

	public JMenuItem getCharger() {
		return charger;
	}

	public JMenuItem getUndo() {
		return undo;
	}

	public JMenuItem getRedo() {
		return redo;
	}

	public JMenuItem getConceed() {
		return conceed;
	}

	public JMenuItem getRestart() {
		return restart;
	}

	public JMenuItem getConfig() {
		return config;
	}

}
