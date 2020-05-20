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
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 *
 * @author dodee
 */
public class Historique extends JFrame {

    JPanel hisPanel;
    private final JLabel tot1;
    private final JLabel tot2;
    private int lastNumManche=0;

    public Historique() {
        this.setTitle("Historique");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) screenSize.getWidth() / 3, (int) screenSize.getHeight() / 3);
        setLocationRelativeTo(null);
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        hisPanel = new JPanel(new GridLayout(0, 3));
        initPanel();
        
        JScrollPane scrollPane = new JScrollPane(hisPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        container.add(scrollPane);

        JPanel total = new JPanel(new GridLayout(1, 3));
        JLabel tot = new JLabel("Total", SwingConstants.CENTER);
        tot1 = new JLabel("0", SwingConstants.CENTER);
        tot2 = new JLabel("0", SwingConstants.CENTER);
        tot.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tot1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tot2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        total.add(tot);
        total.add(tot1);
        total.add(tot2);
        container.add(total);
        this.add(container);
    }

    public void ajouterManche(int numManche, int nbPlis1, int nbPlis2, int score1, int score2) {
        String infoManche, infoj1, infoj2;
        if(numManche <=lastNumManche){
            initPanel();
        }
        lastNumManche = numManche;
        if (nbPlis1 + nbPlis2 == 26) {
            infoManche = "" + numManche;
            infoj1 = "" + nbPlis1;
            infoj2 = "" + nbPlis2;
        } else {
            infoManche = numManche + " (Abandon)";
            infoj1 = "";
            infoj2 = "";
        }
        JLabel l1 = new JLabel(infoManche, SwingConstants.CENTER);
        l1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        hisPanel.add(l1);
        JLabel l2 = new JLabel(infoj1, SwingConstants.CENTER);
        l2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        hisPanel.add(l2);
        JLabel l3 = new JLabel(infoj2, SwingConstants.CENTER);
        l3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        hisPanel.add(l3);
        tot1.setText("" + score1);
        tot2.setText("" + score2);
    }

    public void montrer() {
        this.setVisible(true);
    }

    private void initPanel() {
        hisPanel.removeAll();
        JLabel titre = new JLabel("Historique");
        titre.setFont(new Font("Calibri", Font.PLAIN, 24));
        hisPanel.add(titre);
        JLabel invLab = new JLabel("");
        JLabel invLab2 = new JLabel("");
        hisPanel.add(invLab);
        hisPanel.add(invLab2);
        ///////////////////////
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
    }

}
