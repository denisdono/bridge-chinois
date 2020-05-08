package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class menuBar extends JMenuBar { // menu du haut
	private CollecteurEvenements c;
	private boolean menu;
	private JMenu menuParametres;
	private JMenu menuAction;
	private JMenu menuHelp;

	private JMenuItem sauvegarder;
	private JMenuItem charger;
	private JMenuItem undo;
	private JMenuItem redo;
	private JMenuItem conceed;
	private JMenuItem restart;
	private JMenuItem config;
	private JMenuItem regles;

	menuBar() {
		completeIHM();
	}

	menuBar(CollecteurEvenements c) {
		this.c = c;
		

		menuAction = new JMenu("actions");
		undo = new JMenuItem("undo(crtl+z)");
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// c.annule();
			}
		});
		menuAction.add(undo);
		redo = new JMenuItem("redo(crtl+y)");
		menuAction.add(redo);
		conceed = new JMenuItem("abandonner");
		menuAction.add(conceed);
		restart = new JMenuItem("recomencer");
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.recommencer();
			}
		});
		menuAction.add(restart);
		add(menuAction);

		completeIHM();

	}

	public void completeIHM() {
		
		menuParametres = new JMenu("parametres");
		sauvegarder = new JMenuItem("sauvegarder");
		menuParametres.add(sauvegarder);
		charger = new JMenuItem("charger");
		menuParametres.add(charger);
		config = new JMenuItem("configuration");
		config.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfigWindow lesConf = new ConfigWindow();
				lesConf.montrer();
			}
		});

		menuParametres.add(config);

		add(menuParametres);

		menuHelp = new JMenu("aide");

		regles = new JMenuItem("regles");
		regles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReglesWindow lesRegles = new ReglesWindow();
				lesRegles.montrer();
			}
		});
		menuHelp.add(regles);
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
