package Controleur;


import java.util.Random;
import Modele.Jeu;

class JoueurIA extends Joueur {
	Random r;
	int niv;


	
	
	JoueurIA(int n, Jeu p) {
		super(n, p);
		r = new Random();
		niv=2;
	}

	@Override
	boolean tempsEcoule() {
		int i;
		i = r.nextInt(jeu.getMains()[num].getnbCarte());
		if (jeu.etape()<2) {
			while (!jeu.peutJouer(i, num)) {
				i = r.nextInt(jeu.getMains()[num].getnbCarte());
			}
		}
		else {
			while (!jeu.peutPiocher(i)) {
				i = r.nextInt(6);
			}
		}
		jeu.jouer(i,num);
		return true;
		}
	}
	
