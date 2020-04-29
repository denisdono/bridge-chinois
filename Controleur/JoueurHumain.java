package Controleur;

import Modele.*;

class JoueurHumain extends Joueur {
	JoueurHumain(int n, Jeu p) {
		super(n, p);
	}

	@Override
	boolean jeu() {
		if (jeu.etape()<2) {
			int i=0;/// WARNING A CHANGER ///
			// A adapter selon le jeu,
			// Un coup peut être constitué de plusieurs passages par cette fonction, ex :
			// - selection d'un pièce + surlignage des coups possibles
			// - selection de la destination
			// Autrement dit une machine à état peut aussi être gérée par un objet de cette
			// classe. Dans le cas du morpion, un clic suffit.
			if (jeu.peutJouer(i, num)) {
				jeu.jouer(i,num);
				return true;
			} else {
				return false;
			}
		}
		else {
			int i=0;/// WARNING A CHANGER ///
			if (jeu.peutPiocher(i)) {
				jeu.jouer(i,num);
				return true;
			} else {
				return false;
			}
		}
	}
}