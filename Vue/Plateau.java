package Vue;

import Modele.Jeu;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Plateau extends JPanel implements Observateur {

    Jeu jeu;
    int largeurCase = 1, hauteurCase = 1;

    public Plateau(Jeu j) {
        jeu = j;
        jeu.ajouteObservateur(this);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        Dimension dimlabel = new Dimension(50,80);
        JPanel main1 = new JPanel();
        for (int i = 0; i < jeu.getMains()[0].getnbCarte(); i++) {
            JLabel l = new JLabel(jeu.getMains()[0].getMain()[i].toString());
            l.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            l.setPreferredSize(dimlabel);
            main1.add(l);
        }
        this.add(main1);

        JPanel paquetCentre = new JPanel();
        for (int i = 0; i < 6; i++) {
            JLabel l = new JLabel(jeu.getPiles()[i].topDeck().toString());
            l.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            l.setPreferredSize(dimlabel);
            paquetCentre.add(l);
        }
        this.add(paquetCentre);
        
        JPanel carteJouee = new JPanel();
        for (int i = 0; i < 2; i++) {
            JLabel l = new JLabel("je " + (i + 1));
            l.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            l.setPreferredSize(dimlabel);
            carteJouee.add(l);
        }
        this.add(carteJouee);

        JPanel main2 = new JPanel();
        for (int i = 0; i < jeu.getMains()[1].getnbCarte(); i++) {
            JLabel l = new JLabel(jeu.getMains()[1].getMain()[i].toString());
            l.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            l.setPreferredSize(dimlabel);
            main2.add(l);
        }
        this.add(main2);

    }

//    private String createImgName(Carte c) {
//        return c.getCouleur().getNom() + " "+c.getValeur()+".png";
//    }
//      
    int largeur() {
        return getWidth();
    }

    int hauteur() {
        return getHeight();
    }

    public int largeurCase() {
        return largeurCase;
    }

    public int hauteurCase() {
        return hauteurCase;
    }

    @Override
    public void miseAJour() {
        repaint();
    }

}
