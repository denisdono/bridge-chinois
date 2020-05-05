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
    private final JLabel labelTitrePlis;
    private final JLabel labelTitreScore;
    private final JLabel labelPlis2;
    private final JButton boutHis;
    private final JLabel labelAtout;
    private final JLabel labelTitreAtout;
    JLabel labelPlis1;
    //private final JLabel invisLab;
    private JLabel labelScore1;
    private JLabel labelScore2;

    public Menu(Jeu j) {
        super();
        his = new Historique();
       
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBorder( BorderFactory.createEmptyBorder(0,20,0,100)); 
        labelTitreAtout = new JLabel("Atout");
        labelTitreAtout.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
        labelTitreAtout.setFont(new Font("Calibri", Font.PLAIN, 24));
       
        this.add(labelTitreAtout);

        labelAtout = new JLabel("");

        //Icon atout = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("Clubs 1.png"));
        labelAtout.setBorder( BorderFactory.createEmptyBorder(10,10,10,10) );   
        //lbLabel8.setIcon(atout);
       
        this.add(labelAtout);

        labelTitrePlis = new JLabel("Plis");
        labelTitrePlis.setFont(new Font("Calibri", Font.PLAIN, 24));

        labelTitrePlis.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));

       
        this.add(labelTitrePlis);
        
        labelPlis1 = new JLabel("Joueur 1 : 0");
        labelPlis2 = new JLabel("Joueur 2 : 0");
        labelPlis2.setBorder( BorderFactory.createEmptyBorder(0,0,10,0) );   
        this.add(labelPlis1);
        this.add(labelPlis2);
        

        labelTitreScore = new JLabel("Score");
        labelTitreScore.setFont(new Font("Calibri", Font.PLAIN, 24));

        labelTitreScore.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));

       
        this.add(labelTitreScore);
        labelScore1 = new JLabel("Joueur 1 : 0");
        labelScore2 = new JLabel("Joueur 2 : 0");
       
        this.add(labelScore1);
        this.add(labelScore2);
        boutHis = new JButton("Historique");
        boutHis.addActionListener(this);
       
        this.add(boutHis);
     
    }

    /**
     */
    @Override
    public void actionPerformed(ActionEvent e) {
       
        if (e.getSource() == boutHis) {
            System.out.println("historique");
            his.montrer();
            
        }
    }

    @Override
    public void miseAJour() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void indiqueAtout(String atout){
        this.labelAtout.setText(atout);
    }
    public void setPlis(int nbPlis1, int nbPlis2){
        labelPlis1.setText("Joueur 1 : "+nbPlis1);
        labelPlis2.setText("Joueur 2 : "+nbPlis2);
    }
}
