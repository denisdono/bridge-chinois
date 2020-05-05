package Controleur;

import Modele.*;

class JoueurHumain extends Joueur {
	JoueurHumain(int n, Jeu p) {
		super(n, p);
	}

	@Override
	boolean jeu(int i) {
		if (jeu.etape()<2) {// poser une carte			
			if (jeu.peutJouer(i, num)) {// vérifie qu'il peut jouer cette carte
				jeu.jouer(i,num);
				return true;
			} else {
				return false;
			}
		}
		else {// piocher une carte
			if (jeu.peutPiocher(i)) {// vérifie qu'il peut piocher
				jeu.jouer(i,num);
				return true;
			} else {
				return false;
			}
		}
	}
}