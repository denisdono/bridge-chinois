package Vue;

import java.awt.BorderLayout;
import java.awt.Font;
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
    
    JPanel mainPanel;

    public StartingMenu() {
        setTitle("Bridge Chinois");
        setSize(500,500);
        setLocationRelativeTo(null);
    

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JLabel titre = new JLabel("Menu principal");
        titre.setFont(new Font("Calibri", Font.PLAIN, 24));
        mainPanel.add(titre, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
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
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        JButton confB = new JButton("Configuration");
        confB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConfigWindow lesConf = new ConfigWindow();
                lesConf.montrer();
            }
        });
        mainPanel.add(confB, BorderLayout.SOUTH);    
        add(mainPanel);
        setIconImage(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("Back Blue 1.png")).getImage());
        setVisible(true);       
    }
    
}
