package Controleur;

import java.util.Random;

import Modele.Jeu;

public class IARandom extends Joueur {
	Random r;
	
	
	IARandom(int n, Jeu p) {
		super(n, p);
		r = new Random();
	}
	
	
	boolean IAJeu() {
		int i;
		if (jeu.etape()<2) {
			i = r.nextInt(jeu.getMains()[num].getnbCarte());
			while (!jeu.peutJouer(i, num)) {
				i = r.nextInt(jeu.getMains()[num].getnbCarte());
			}
		}
		else {
			i = r.nextInt(6);
			while (!jeu.peutPiocher(i)) {
				i = r.nextInt(6);
			}
		}
		jeu.jouer(i,num);
		return true;
	}
	
}
