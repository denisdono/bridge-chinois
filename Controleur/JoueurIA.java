package Controleur;


import java.util.Random;
import Modele.Jeu;

class JoueurIA extends Joueur {
	Random r;
	int niv;// niveau de l'ia 


	
	
	JoueurIA(int n, Jeu p) {
		super(n, p);
		r = new Random();
		niv=2;
	}

	@Override
	boolean tempsEcoule() {
		int i;
		if (jeu.etape()<2) {// poser une carte
			i = r.nextInt(jeu.getMains()[num].getnbCarte());
			while (!jeu.peutJouer(i, num)) {// vérifie que la carte peut etre jouer
				i = r.nextInt(jeu.getMains()[num].getnbCarte());
			}
		}
		else { // piocher
			i = r.nextInt(6);
			while (!jeu.peutPiocher(i)) {// vérifie qu'on peut piocher
				i = r.nextInt(6);
			}
		}
		jeu.jouer(i,num);
		return true;
		}
	}
	
