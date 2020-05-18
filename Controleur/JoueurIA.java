package Controleur;

import Modele.Jeu;

class JoueurIA extends Joueur{
	IARandom ia;
	IASimple ias;
	
	JoueurIA(int n, Jeu p) {
		super (n,p);
		ia =new IARandom(n,p);
		ias=new IASimple (n,p);
	}

	
	boolean tempsEcoule() {
		int i;
		int niv=jeu.niveauIA();
		switch (niv) {
		case 0:
			i=ia.IAJeu();
			break;
		case 1:
			i=ias.IAjeu();
			break;
		default :
			i=-1;//ne seras jamais atteint.
		}
		jeu.jouer(i, num);
		return true;	
		}

	
	
	

	
	}
	
