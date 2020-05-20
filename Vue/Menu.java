
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.Jeu;
import Patterns.Observateur;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

/**
 * @author Administrator
 * @created April 28, 2020
 */
//Menu sur la droite du jeu donnant des infos sur la partie en cours
class Menu extends JPanel implements ActionListener{

    Historique his;
    private final JLabel labelTitrePlis;
    private final JLabel labelTitreScore;
    private final JLabel labelPlis2;
    private final JButton boutHis;
    private final JLabel labelAtout;
    private final JLabel labelTitreAtout;
    JLabel labelPlis1;
    private JLabel labelScore1;
    private JLabel labelScore2;
    private final JLabel labelTitreDernierPlis;
    private final JLabel labelDernierPlisGagnant;
    private final JLabel dernierCarte1;
    private final JLabel dernierCarte2;
    private final JLabel invis0;
    private final JLabel labelTitreManche;
    

    public Menu(Jeu j, Dimension dim) {
        super();
        his = new Historique();

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        this.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        Font titreFont = new Font("Calibri", Font.PLAIN, 24);
        //Affichage du n°manche
        labelTitreManche = new JLabel("Manche n");
        labelTitreManche.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelTitreManche.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
        labelTitreManche.setFont(titreFont);
        this.add(labelTitreManche);
        //Affichage du dernier plis
        labelTitreDernierPlis = new JLabel("Dernier pli");
        labelTitreDernierPlis.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
        labelTitreDernierPlis.setFont(titreFont);

        this.add(labelTitreDernierPlis);

        labelDernierPlisGagnant = new JLabel(" ");
        this.add(labelDernierPlisGagnant);
        JPanel paneDernierPlis = new JPanel();
        paneDernierPlis.setAlignmentX(Component.LEFT_ALIGNMENT);
        paneDernierPlis.setBorder(BorderFactory.createLineBorder(Color.BLACK,0));
        dernierCarte1 = new JLabel();
        dernierCarte2 = new JLabel();
        
        Icon back = new ImageIcon(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("Back Blue 1.png")).getImage().getScaledInstance(dim.width, dim.height, Image.SCALE_SMOOTH));

        dernierCarte1.setIcon(back);
        dernierCarte2.setIcon(back);
        
        paneDernierPlis.add(dernierCarte1);
        paneDernierPlis.add(dernierCarte2);
        this.add(paneDernierPlis);
        
        invis0 = new JLabel(" ");
        this.add(invis0);
        //Affichage de l'atout de la partie
        labelTitreAtout = new JLabel("Atout");
        labelTitreAtout.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
        labelTitreAtout.setFont(titreFont);

        this.add(labelTitreAtout);

        labelAtout = new JLabel("");

        labelAtout.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        labelAtout.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel paneAtout = new JPanel();
        paneAtout.setAlignmentX(Component.LEFT_ALIGNMENT);
        paneAtout.add(labelAtout);
        this.add(paneAtout);
        JLabel invis = new JLabel();
        invis.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(invis);
        //Affichage du nombre de plis de la partie
        labelTitrePlis = new JLabel("Nombre de plis");
        labelTitrePlis.setFont(titreFont);

        labelTitrePlis.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));

        this.add(labelTitrePlis);

        labelPlis1 = new JLabel("Joueur 1 : 0");
        labelPlis2 = new JLabel("Joueur 2 : 0");
        labelPlis2.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        this.add(labelPlis1);
        this.add(labelPlis2);

        //Affichage du score total des joueurs sur plusieurs parties
        labelTitreScore = new JLabel("Score total");
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

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == boutHis) {
            System.out.println("historique");
            his.montrer();

        }
    }

    //Indiquer la couleur de l'atout dans le menu
    public void indiqueAtout(String atout, Dimension d) {
        this.labelAtout.setText("");
        Icon img;
        if(atout == "Neutre"){
            this.labelAtout.setIcon(null);
            this.labelAtout.setText("Sans atout");
        } else{
            int borderSize =3;
            img = new ImageIcon(new ImageIcon(ClassLoader.getSystemClassLoader().getResource(atout+".png")).getImage().getScaledInstance(d.width-borderSize, d.height-(d.height/4), Image.SCALE_SMOOTH));
            this.labelAtout.setIcon(img);
            this.labelAtout.setBorder(new LineBorder(Color.BLACK, borderSize, true));
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

    public void setNumManche(int n){
        labelTitreManche.setText("Manche "+n);
    }
    public void ajouterManche(int numManche, int nbPlis1, int nbPlis2, int s1, int s2){
            his.ajouterManche(numManche, nbPlis1, nbPlis2, s1, s2);
    }
}

