/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

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
    private final JLabel invisLab;

    /**
     * Constructor for the Panel0 object
     */
    public Menu() {
        super();
        his = new Historique();
        GridBagLayout gbPanel0 = new GridBagLayout();
       
        GridBagConstraints gbcPanel0 = new GridBagConstraints();
        setLayout(gbPanel0);
        pnPanel4 = new JPanel();
        rbgPanel4 = new ButtonGroup();
        GridBagLayout gbPanel4 = new GridBagLayout();
        GridBagConstraints gbcPanel4 = new GridBagConstraints();
        pnPanel4.setLayout(gbPanel4);

        btBut0 = new JButton("Sauvegarder");
        btBut0.addActionListener(this);
        gbcPanel4.gridx = 0;
        gbcPanel4.gridy = 1;
        gbcPanel4.gridwidth = 3;
        gbcPanel4.gridheight = 1;
        gbcPanel4.fill = GridBagConstraints.BOTH;
        gbcPanel4.weightx = 1;
        gbcPanel4.weighty = 1;
        gbcPanel4.anchor = GridBagConstraints.NORTH;
        gbPanel4.setConstraints(btBut0, gbcPanel4);
        pnPanel4.add(btBut0);

        btBut1 = new JButton("Charger");
        btBut1.addActionListener(this);
        gbcPanel4.gridx = 3;
        gbcPanel4.gridy = 1;
        gbcPanel4.gridwidth = 3;
        gbcPanel4.gridheight = 1;
        gbcPanel4.fill = GridBagConstraints.BOTH;
        gbcPanel4.weightx = 1;
        gbcPanel4.weighty = 0;
        gbcPanel4.anchor = GridBagConstraints.NORTH;
        gbPanel4.setConstraints(btBut1, gbcPanel4);
        pnPanel4.add(btBut1);

        btBut2 = new JButton("Retour");
        btBut2.addActionListener(this);
        gbcPanel4.gridx = 0;
        gbcPanel4.gridy = 2;
        gbcPanel4.gridwidth = 3;
        gbcPanel4.gridheight = 1;
        gbcPanel4.fill = GridBagConstraints.BOTH;
        gbcPanel4.weightx = 1;
        gbcPanel4.weighty = 0;
        gbcPanel4.anchor = GridBagConstraints.NORTH;
        gbPanel4.setConstraints(btBut2, gbcPanel4);
        pnPanel4.add(btBut2);

        btBut3 = new JButton("Annuler");
        btBut3.addActionListener(this);
        gbcPanel4.gridx = 3;
        gbcPanel4.gridy = 2;
        gbcPanel4.gridwidth = 3;
        gbcPanel4.gridheight = 1;
        gbcPanel4.fill = GridBagConstraints.BOTH;
        gbcPanel4.weightx = 1;
        gbcPanel4.weighty = 0;
        gbcPanel4.anchor = GridBagConstraints.NORTH;
        gbPanel4.setConstraints(btBut3, gbcPanel4);
        pnPanel4.add(btBut3);

        tbtTglBut0 = new JToggleButton("Recommencer");
        tbtTglBut0.addActionListener(this);
        rbgPanel4.add(tbtTglBut0);
        gbcPanel4.gridx = 0;
        gbcPanel4.gridy = 3;
        gbcPanel4.gridwidth = 3;
        gbcPanel4.gridheight = 1;
        gbcPanel4.fill = GridBagConstraints.BOTH;
        gbcPanel4.weightx = 1;
        gbcPanel4.weighty = 0;
        gbcPanel4.anchor = GridBagConstraints.NORTH;
        gbPanel4.setConstraints(tbtTglBut0, gbcPanel4);
        pnPanel4.add(tbtTglBut0);

        tbtTglBut1 = new JToggleButton("Abandonner");
        tbtTglBut1.addActionListener(this);
        rbgPanel4.add(tbtTglBut1);
        gbcPanel4.gridx = 0;
        gbcPanel4.gridy = 4;
        gbcPanel4.gridwidth = 3;
        gbcPanel4.gridheight = 1;
        gbcPanel4.fill = GridBagConstraints.BOTH;
        gbcPanel4.weightx = 1;
        gbcPanel4.weighty = 0;
        gbcPanel4.anchor = GridBagConstraints.NORTH;
        gbPanel4.setConstraints(tbtTglBut1, gbcPanel4);
        pnPanel4.add(tbtTglBut1);

        btBut4 = new JButton("Parametres");
        //btBut4.setBorder( BorderFactory.createEmptyBorder(10,10,10,10) );   
        btBut4.addActionListener(this);
        gbcPanel4.gridx = 0;
        gbcPanel4.gridy = 5;
        gbcPanel4.gridwidth = 3;
        gbcPanel4.gridheight = 2;
        gbcPanel4.fill = GridBagConstraints.BOTH;
        gbcPanel4.weightx = 1;
        gbcPanel4.weighty = 1;
        gbcPanel4.anchor = GridBagConstraints.NORTH;
        gbPanel4.setConstraints(btBut4, gbcPanel4);
        pnPanel4.add(btBut4);

        lbLabel1 = new JLabel("Menu");
        lbLabel1.setFont(new Font("Calibri", Font.PLAIN, 24));

        lbLabel1.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
        gbcPanel4.gridx = 0;
        gbcPanel4.gridy = 0;
        gbcPanel4.gridwidth = 3;
        gbcPanel4.gridheight = 1;
        gbcPanel4.fill = GridBagConstraints.BOTH;
        gbcPanel4.weightx = 1;
        gbcPanel4.weighty = 1;
        gbcPanel4.anchor = GridBagConstraints.NORTH;
        gbPanel4.setConstraints(lbLabel1, gbcPanel4);
        pnPanel4.add(lbLabel1);
        gbcPanel0.gridx = 14;
        gbcPanel0.gridy = 0;
        gbcPanel0.gridwidth = 6;
        gbcPanel0.gridheight = 20;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 1;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(pnPanel4, gbcPanel0);
        add(pnPanel4);
        
        invisLab = new JLabel("");
        invisLab.setBorder( BorderFactory.createEmptyBorder(10,10,10,10) );
        gbcPanel4.gridx = 0;
        gbcPanel4.gridy = 7;
        gbcPanel4.gridwidth = 3;
        gbcPanel4.gridheight = 1;
        gbcPanel4.fill = GridBagConstraints.BOTH;
        gbcPanel4.weightx = 1;
        gbcPanel4.weighty = 1;
        gbcPanel4.anchor = GridBagConstraints.NORTH;
        gbPanel4.setConstraints(invisLab, gbcPanel4);
        pnPanel4.add(invisLab);
        
        lbLabel7 = new JLabel("Atout");
        lbLabel7.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
        lbLabel7.setFont(new Font("Calibri", Font.PLAIN, 24));
        gbcPanel4.gridx = 0;
        gbcPanel4.gridy = 8;
        gbcPanel4.gridwidth = 3;
        gbcPanel4.gridheight = 2;
        gbcPanel4.fill = GridBagConstraints.BOTH;
        gbcPanel4.weightx = 1;
        gbcPanel4.weighty = 1;
        gbcPanel4.anchor = GridBagConstraints.NORTH;
        gbPanel4.setConstraints(lbLabel7, gbcPanel4);
        pnPanel4.add(lbLabel7);

        lbLabel8 = new JLabel("");

        Icon atout = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("Clubs 1.png"));
        lbLabel8.setBorder( BorderFactory.createEmptyBorder(10,10,10,10) );   
        lbLabel8.setIcon(atout);
        gbcPanel4.gridx = 0;
        gbcPanel4.gridy = 10;
        gbcPanel4.gridwidth = 6;
        gbcPanel4.gridheight = 5;
        gbcPanel4.fill = GridBagConstraints.BOTH;
        gbcPanel4.weightx = 1;
        gbcPanel4.weighty = 1;
        gbcPanel4.anchor = GridBagConstraints.NORTH;
        gbPanel4.setConstraints(lbLabel8, gbcPanel4);
        pnPanel4.add(lbLabel8);

        lbLabel9 = new JLabel("Plis");
        lbLabel9.setFont(new Font("Calibri", Font.PLAIN, 24));

        lbLabel9.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));

        gbcPanel4.gridx = 0;
        gbcPanel4.gridy = 15;
        gbcPanel4.gridwidth = 3;
        gbcPanel4.gridheight = 1;
        gbcPanel4.fill = GridBagConstraints.BOTH;
        gbcPanel4.weightx = 1;
        gbcPanel4.weighty = 1;
        gbcPanel4.anchor = GridBagConstraints.NORTH;
        gbPanel4.setConstraints(lbLabel9, gbcPanel4);
        pnPanel4.add(lbLabel9);

        lbLabel10 = new JLabel("Joueur 1 : Joueur 2 :");
        lbLabel10.setBorder( BorderFactory.createEmptyBorder(10,10,10,10) );   
        gbcPanel4.gridx = 0;
        gbcPanel4.gridy = 16;
        gbcPanel4.gridwidth = 6;
        gbcPanel4.gridheight = 1;
        gbcPanel4.fill = GridBagConstraints.BOTH;
        gbcPanel4.weightx = 1;
        gbcPanel4.weighty = 1;
        gbcPanel4.anchor = GridBagConstraints.NORTH;
        gbPanel4.setConstraints(lbLabel10, gbcPanel4);
        pnPanel4.add(lbLabel10);

        lbLabel11 = new JLabel("Score");
        lbLabel11.setFont(new Font("Calibri", Font.PLAIN, 24));

        lbLabel11.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));

        gbcPanel4.gridx = 0;
        gbcPanel4.gridy = 17;
        gbcPanel4.gridwidth = 3;
        gbcPanel4.gridheight = 1;
        gbcPanel4.fill = GridBagConstraints.BOTH;
        gbcPanel4.weightx = 1;
        gbcPanel4.weighty = 1;
        gbcPanel4.anchor = GridBagConstraints.NORTH;
        gbPanel4.setConstraints(lbLabel11, gbcPanel4);
        pnPanel4.add(lbLabel11);

        lbLabel14 = new JLabel("Joueur 1 : Joueur 2 :");
        lbLabel14.setBorder( BorderFactory.createEmptyBorder(10,10,10,10) );   
        gbcPanel4.gridx = 0;
        gbcPanel4.gridy = 18;
        gbcPanel4.gridwidth = 6;
        gbcPanel4.gridheight = 1;
        gbcPanel4.fill = GridBagConstraints.BOTH;
        gbcPanel4.weightx = 1;
        gbcPanel4.weighty = 1;
        gbcPanel4.anchor = GridBagConstraints.NORTH;
        gbPanel4.setConstraints(lbLabel14, gbcPanel4);
        pnPanel4.add(lbLabel14);

        btBut7 = new JButton("Historique");
        btBut7.addActionListener(this);
        gbcPanel4.gridx = 0;
        gbcPanel4.gridy = 19;
        gbcPanel4.gridwidth = 3;
        gbcPanel4.gridheight = 1;
        gbcPanel4.fill = GridBagConstraints.BOTH;
        gbcPanel4.weightx = 1;
        gbcPanel4.weighty = 0;
        gbcPanel4.anchor = GridBagConstraints.NORTH;
        gbPanel4.setConstraints(btBut7, gbcPanel4);
        pnPanel4.add(btBut7);
        gbcPanel0.gridx = 14;
        gbcPanel0.gridy = 0;
        gbcPanel0.gridwidth = 6;
        gbcPanel0.gridheight = 20;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(pnPanel4, gbcPanel0);
        add(pnPanel4);
    }

    /**
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btBut0) {
            System.out.println("save");
        }
        if (e.getSource() == btBut1) {
            System.out.println("charge");

        }
        if (e.getSource() == btBut2) {
            System.out.println("retour");
        }
        if (e.getSource() == btBut3) {
            System.out.println("annuler");
        }
        if (e.getSource() == tbtTglBut0) {
            System.out.println("recommencer");
        }
        if (e.getSource() == tbtTglBut1) {
            System.out.println("abandonner");
        }
        if (e.getSource() == btBut4) {
            System.out.println("paramètres");
        }
        if (e.getSource() == btBut7) {
            System.out.println("historique");
            his.montrer();
            
        }
    }

    @Override
    public void miseAJour() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
