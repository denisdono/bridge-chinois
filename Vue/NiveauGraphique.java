package Vue;

import Modele.Jeu;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

public class NiveauGraphique extends JComponent implements Observateur {
	Jeu jeu;
	int largeurCase, hauteurCase;

	public NiveauGraphique(Jeu j) {
		jeu = j;
		jeu.ajouteObservateur(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D drawable = (Graphics2D) g;
        int lignes = jeu.hauteur();
        int colonnes = jeu.largeur();
        largeurCase = largeur() / colonnes;
        hauteurCase = hauteur() / lignes;

        g.clearRect(0, 0, largeur(), hauteur());
        if (!jeu.enCours())
            g.drawString("Fin", 20, hauteur()/2);
        // Grille
        for (int i=1; i<lignes;i++) {
            g.drawLine(0, i*hauteurCase, largeur(), i*hauteurCase);
        }
        for (int j=1; j<colonnes;j++) {
            g.drawLine(j*largeurCase, 0, j*largeurCase, hauteur());
        }
        // Coups
        for (int i=0; i<lignes; i++)
            for (int j=0; j<colonnes; j++)
                switch (jeu.valeur(i, j)) {
                    case -1:
                        g.drawOval(j*largeurCase, i*hauteurCase, largeurCase, hauteurCase);
                        break;
                    default:
                        g.drawLine(j*largeurCase, i*hauteurCase, (j+1)*largeurCase, (i+1)*hauteurCase);
                        g.drawLine(j*largeurCase, (i+1)*hauteurCase, (j+1)*largeurCase, i*hauteurCase);
                        break;
                }
	}

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