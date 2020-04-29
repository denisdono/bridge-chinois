/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Patterns.Observateur;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 *
 * @author dodee
 */
public class Historique extends JFrame implements Observateur {
    JPanel hisPanel;
    
    public Historique(){
        this.setTitle("Historique");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int)screenSize.getWidth()/3, (int)screenSize.getHeight()/4);
        
        miseAJour();
    }
    
    
    
    
    @Override
    public void miseAJour() {
        //btBut4.setBorder( BorderFactory.createEmptyBorder(10,10,10,10) );
        
        hisPanel = new JPanel(new GridLayout(18,3));
        JLabel titre = new JLabel("Historique");
        titre.setFont(new Font("Calibri", Font.PLAIN, 24));
        hisPanel.add(titre);
        JLabel invLab = new JLabel("");
        JLabel invLab2 = new JLabel("");
        hisPanel.add(invLab);
        hisPanel.add(invLab2);
        
        //Entete tableau
        JLabel ent1 = new JLabel("Manche num", SwingConstants.CENTER);
        ent1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel ent2 = new JLabel("Joueur 1", SwingConstants.CENTER);
        ent2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel ent3 = new JLabel("Joueur 2", SwingConstants.CENTER);
        ent3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        hisPanel.add(ent1);
        hisPanel.add(ent2);
        hisPanel.add(ent3);
        ////////////////
        for(int i = 0; i<16*3;i++){
            JLabel l = new JLabel("vals"+i, SwingConstants.CENTER);
            l.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            hisPanel.add(l);
        }
        JScrollPane scrollPane = new JScrollPane(hisPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        this.add(scrollPane);
       
    }

    public void montrer() {
        this.setVisible(true);
    }
    
    
    
}
