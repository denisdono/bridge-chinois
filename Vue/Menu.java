/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.Jeu;
import Patterns.Observateur;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JLabel;

/**
 * @author Administrator
 * @created April 28, 2020
 */
class Menu extends JPanel implements ActionListener, Observateur {

    JPanel pnPanel4;
    ButtonGroup rbgPanel4;
    JButton btBut0;
    JButton btBut1;
    JButton btBut2;
    JButton btBut3;
    JToggleButton tbtTglBut0;
    JToggleButton tbtTglBut1;
    JButton btBut4;
    JLabel lbLabel1;
    Historique his;
    private final JLabel lbLabel9;
    private final JLabel lbLabel10;
    private final JLabel lbLabel11;
    private final JLabel lbLabel14;
    private final JButton btBut7;
    private final JLabel lbLabel8;
    private final JLabel lbLabel7;
    //private final JLabel invisLab;

    /**
     * Constructor for the Panel0 object
     */
    public Menu(Jeu j) {
        super();
        his = new Historique();
       
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
       
        lbLabel7 = new JLabel("Atout");
        lbLabel7.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
        lbLabel7.setFont(new Font("Calibri", Font.PLAIN, 24));
       
        this.add(lbLabel7);

        lbLabel8 = new JLabel("");

        //Icon atout = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("Clubs 1.png"));
        lbLabel8.setBorder( BorderFactory.createEmptyBorder(10,10,10,10) );   
        //lbLabel8.setIcon(atout);
       
        this.add(lbLabel8);

        lbLabel9 = new JLabel("Plis");
        lbLabel9.setFont(new Font("Calibri", Font.PLAIN, 24));

        lbLabel9.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));

       
        this.add(lbLabel9);

        lbLabel10 = new JLabel("Joueur 1 : Joueur 2 :");
        lbLabel10.setBorder( BorderFactory.createEmptyBorder(10,10,10,10) );   
      
        this.add(lbLabel10);

        lbLabel11 = new JLabel("Score");
        lbLabel11.setFont(new Font("Calibri", Font.PLAIN, 24));

        lbLabel11.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));

       
        this.add(lbLabel11);

        lbLabel14 = new JLabel("Joueur 1 : Joueur 2 :");
        lbLabel14.setBorder( BorderFactory.createEmptyBorder(10,10,10,10) );   
       
        this.add(lbLabel14);

        btBut7 = new JButton("Historique");
        btBut7.addActionListener(this);
       
        this.add(btBut7);
     
    }

    /**
     */
    @Override
    public void actionPerformed(ActionEvent e) {
       
        if (e.getSource() == btBut7) {
            System.out.println("historique");
            his.montrer();
            
        }
    }

    @Override
    public void miseAJour() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void indiqueAtout(String atout){
        this.lbLabel8.setText(atout);
    }
}
