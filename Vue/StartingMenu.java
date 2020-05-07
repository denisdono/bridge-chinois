package Vue;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Patterns.Observateur;

public class StartingMenu  extends JFrame {
	
	
	public StartingMenu(){
		
		menuBar menuBar = new menuBar();
		setJMenuBar(menuBar);
		JPanel mainPanel= new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		JLabel titre = new JLabel("Bridge Chinois");
        titre.setFont(new Font("Calibri", Font.PLAIN, 24));
        mainPanel.add(titre);
		JPanel buttonPanel= new JPanel();
		JButton startIAB = new JButton("Jouer contrel'IA");
		startIAB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Jeu j = new Jeu();
				CollecteurEvenements control = new ControleurMediateur(j, true);
				InterfaceGraphique.demarrer(j, control);
				setVisible(false);
				dispose();
			}
		});
		JButton start2PB = new JButton("Jouer à 2");
		start2PB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Jeu j = new Jeu();
				CollecteurEvenements control = new ControleurMediateur(j, false);
				InterfaceGraphique.demarrer(j, control);
				setVisible(false);
				dispose();
			}
		});
		buttonPanel.add(startIAB);
		buttonPanel.add(start2PB);
		mainPanel.add(buttonPanel);
		JButton confB = new JButton("Configuration");
		confB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfigWindow lesConf = new ConfigWindow();
				lesConf.montrer();
			}
		});
		mainPanel.add(confB);
		add(mainPanel);
		
	}

	

}
