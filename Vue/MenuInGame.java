/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Patterns.Observateur;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author dodee
 */
public class MenuInGame implements Observateur {

    Jeu jeu;
    JPanel panel;
    CollecteurEvenements ctrl;
    public MenuInGame(Jeu j, CollecteurEvenements c) {
        ctrl = c;
        int nbBout = 5;
        this.jeu = j;
        jeu.ajouteObservateur(this);
        String[] nomBout = new String[nbBout];
        nomBout[0] = "Charger";
        nomBout[1] = "Sauvegarder";
        nomBout[2] = "Retour";
        nomBout[3] = "Recommencer";
        nomBout[4] = "Quitter";
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(150,300));
        for(int i = 0; i < nbBout; i++){
            JButton b = new JButton(nomBout[i]);
            b.setMaximumSize(new Dimension(150, 40));
            final String role = nomBout[i];
            b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ctrl.boutonEvent(role);
                System.out.println(role);
            }          
            });
            panel.add(b);
        }
    }
    
    @Override
    public void miseAJour() {
        System.out.println("balek");
    }

    public JPanel getPanel() {
        return panel;
    }
    
    
}
