package Vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Controleur.ControleurMediateur;
import Modele.Jeu;
import javax.swing.ImageIcon;

public class StartingMenu extends JFrame {
    
	JLabel mainPanel;

    public StartingMenu() {
        setTitle("Bridge Chinois");
        setSize(510,530);
        setLocationRelativeTo(null);
    
        mainPanel = new JLabel(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("StartBackground.jpg")));
        mainPanel.setLayout(new BorderLayout());
       
        JLabel buttonPanel = new JLabel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setVerticalAlignment(JButton.CENTER);
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
        startIAB.setPreferredSize(new Dimension(200,50));
        start2PB.setPreferredSize(new Dimension(200,50));
        buttonPanel.add(startIAB);
        buttonPanel.add(start2PB);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        JButton confB = new JButton("Configuration");
        confB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConfigWindow lesConf = new ConfigWindow(null);
                lesConf.montrer();
            }
        });
        mainPanel.add(confB, BorderLayout.SOUTH);    
        add(mainPanel);
        setIconImage(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("Back Blue 1.png")).getImage());
        setVisible(true);       
    }
    
}
