package Vue;


import Modele.Jeu;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class NiveauGraphique extends JPanel implements Observateur {
	Jeu jeu;
	int largeurCase=1, hauteurCase=1;

	public NiveauGraphique(Jeu j) {
		jeu = j;
		jeu.ajouteObservateur(this);
                JPanel main1 = new JPanel(new FlowLayout());
                for(int i =0;i<11;i++){
                    JLabel l = new JLabel("emplac carte");
                    main1.add(l);
                }
                this.add(main1);
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
           
        public void setTaille(Dimension screenSize) {
            this.setPreferredSize(new Dimension((int)(screenSize.getWidth()/2-250), (int)screenSize.getHeight()/2)); 
        }
}