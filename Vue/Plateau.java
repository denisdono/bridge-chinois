package Vue;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Plateau extends JPanel implements Observateur {

    Jeu jeu;
    int largeurCase = 1, hauteurCase = 1;
    CollecteurEvenements c;
    public Plateau(Jeu j, CollecteurEvenements c) {
        this.c = c;
        jeu = j;
        jeu.ajouteObservateur(this);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        miseAJour();
        

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
        System.out.println("etape: "+jeu.etape());
        System.out.println("miseAJour");
        this.removeAll();
        Dimension dimlabel = new Dimension(50,80);
        JPanel main1 = new JPanel();
        for (int i = 0; i < jeu.getMains()[0].getnbCarte(); i++) {
            JLabel l = new JLabel(jeu.getMains()[0].getMain()[i].toString());
            l.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            l.setPreferredSize(dimlabel);
            if(jeu.etape()==0)
                l.addMouseListener(new JoueurCarteListener(i, c));
            main1.add(l);
        }
        if(jeu.joueurActuelle()==0 && jeu.etape()==0){
            main1.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        }
        this.add(main1);

        JPanel paquetCentre = new JPanel();
        for (int i = 0; i < 6; i++) {
            JLabel l = new JLabel(jeu.getPiles()[i].topDeck().toString());
            l.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            l.setPreferredSize(dimlabel);
            l.addMouseListener(new JoueurCarteListener(i, c));
            paquetCentre.add(l);
        }
        if(jeu.etape()==2 || jeu.etape()==3){
            paquetCentre.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        }
        this.add(paquetCentre);
        
        JPanel carteJouee = new JPanel();
        
        JLabel joue1 = new JLabel();
        joue1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        joue1.setPreferredSize(dimlabel);
        carteJouee.add(joue1);
        if(jeu.etape() == 1 || jeu.etape()==2){
           joue1.setText("carte");
        } else{
           joue1.setText("Vide"); 
        }
        JLabel joue2 = new JLabel();
        joue2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        joue2.setPreferredSize(dimlabel);
        if(jeu.etape() == 2){
           joue2.setText("carte");
        } else{
           joue2.setText("Vide"); 
        }
        carteJouee.add(joue2);
        
        this.add(carteJouee);

        JPanel main2 = new JPanel();
        for (int i = 0; i < jeu.getMains()[1].getnbCarte(); i++) {
            JLabel l = new JLabel(jeu.getMains()[1].getMain()[i].toString());
            l.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            l.setPreferredSize(dimlabel);
            if(jeu.etape()==1)
                l.addMouseListener(new JoueurCarteListener(i, c));
            main2.add(l);
        }
        if(jeu.joueurActuelle()==1 && jeu.etape()==1){
            main2.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        }
        this.add(main2);
        this.revalidate();
    }

}
