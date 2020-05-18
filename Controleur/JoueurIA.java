package Controleur;

import Modele.Jeu;

class JoueurIA extends Joueur{
	int niv;
	IARandom ia;
	IASimple ias;
	
	JoueurIA(int n, Jeu p) {
		super (n,p);
		niv=p.niveauIA();
		switch (niv) {
		case 0:
			ia =new IARandom(n,p);
			break;
		case 1:
			ias=new IASimple (n,p);
			break;
		default :
			break;
		}
	}

	
	boolean tempsEcoule() {
		int i;
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
	
