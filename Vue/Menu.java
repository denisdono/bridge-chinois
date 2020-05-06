/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.Jeu;
import Patterns.Observateur;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

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
    private final JLabel labelTitreDernierPlis;
    private final JLabel labelDernierPlisGagnant;
    private final JLabel dernierCarte1;
    private final JLabel dernierCarte2;

    public Menu(Jeu j) {
        super();
        his = new Historique();

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        //this.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 50));
        Font titreFont = new Font("Calibri", Font.PLAIN, 24);
        //Affichage du dernier plis
        labelTitreDernierPlis = new JLabel("Dernier Plis");
        labelTitreDernierPlis.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
        labelTitreDernierPlis.setFont(titreFont);

        this.add(labelTitreDernierPlis);

        labelDernierPlisGagnant = new JLabel("Le joueur n l'emporte");
        this.add(labelDernierPlisGagnant);
        //Icon atout = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("Clubs 1.png"));
        JPanel paneDernierPlis = new JPanel();
        //paneDernierPlis.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //lbLabel8.setIcon(atout);
        dernierCarte1 = new JLabel();
        dernierCarte2 = new JLabel();
        paneDernierPlis.add(dernierCarte1);
        paneDernierPlis.add(dernierCarte2);
        this.add(paneDernierPlis);
        JLabel invis0 = new JLabel();
        invis0.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(invis0);

        //Affichage de l'atout de la partie
        labelTitreAtout = new JLabel("Atout");
        labelTitreAtout.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
        labelTitreAtout.setFont(titreFont);

        this.add(labelTitreAtout);

        labelAtout = new JLabel("");

        //Icon atout = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("Clubs 1.png"));
        labelAtout.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //lbLabel8.setIcon(atout);

        this.add(labelAtout);
        JLabel invis = new JLabel();
        invis.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(invis);
        //Affichage du nombre de plis de la partie
        labelTitrePlis = new JLabel("Plis");
        labelTitrePlis.setFont(titreFont);

        labelTitrePlis.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));

        this.add(labelTitrePlis);

        labelPlis1 = new JLabel("Joueur 1 : 0");
        labelPlis2 = new JLabel("Joueur 2 : 0");
        labelPlis2.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        this.add(labelPlis1);
        this.add(labelPlis2);

        //Affichage du score total des joueurs sur plusieurs parties
        //Non fonctionnel
        labelTitreScore = new JLabel("Score");
        labelTitreScore.setFont(titreFont);

        labelTitreScore.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));

        this.add(labelTitreScore);
        labelScore1 = new JLabel("Joueur 1 : 0");
        labelScore2 = new JLabel("Joueur 2 : 0");

        this.add(labelScore1);
        this.add(labelScore2);
        
        JLabel invis2 = new JLabel();
        invis2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(invis2);
        //Affichage de l'historique des parties (non fonctionnel)
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

    //Indiquer la couleur de l'atout dans le menu
    public void indiqueAtout(String atout) {
        //this.labelAtout.setText(atout);
        Icon img;
        if(atout == "Neutre"){
            this.labelAtout.setText("Manche sans atout");
        } else{
            img = new ImageIcon(ClassLoader.getSystemClassLoader().getResource(atout+".png"));//.getImage().getScaledInstance(dimlabel.width, dimlabel.height, Image.SCALE_SMOOTH));
            this.labelAtout.setIcon(img);
            this.labelAtout.setBorder(new LineBorder(Color.BLACK, 3, true));
        }
    }

    //Indiquer le nombre de plis remporte par les joueurs
    public void setPlis(int nbPlis1, int nbPlis2, int score1, int score2) {
        labelPlis1.setText("Joueur 1 : " + nbPlis1);
        labelPlis2.setText("Joueur 2 : " + nbPlis2);
        
        labelScore1.setText("Joueur 1 : " + score1);
        labelScore2.setText("Joueur 2 : " + score2);
    }
    public void setResDernierPlis(int jWin, String carte1, String carte2, Dimension dim){
        labelDernierPlisGagnant.setText("Joueur "+(jWin+1)+" emporte");
        System.out.println("carte1"+carte1+"-carte2"+carte2);
        Icon img = new ImageIcon(new ImageIcon(ClassLoader.getSystemClassLoader().getResource(carte1)).getImage().getScaledInstance(dim.width, dim.height, Image.SCALE_SMOOTH));
        Icon img2 = new ImageIcon(new ImageIcon(ClassLoader.getSystemClassLoader().getResource(carte2)).getImage().getScaledInstance(dim.width, dim.height, Image.SCALE_SMOOTH));

        dernierCarte1.setIcon(img);
        dernierCarte2.setIcon(img2);
    }
    public void setTaille(Dimension d){
        this.setPreferredSize(d);
    }
}
