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
		return true;
		}
	}
	
