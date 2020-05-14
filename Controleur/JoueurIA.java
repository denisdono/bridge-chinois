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
		boolean rep=false;
		switch (niv) {
		case 0:
			rep=ia.IAJeu();
			break;
		case 1:
			rep=ias.IAjeu();
			break;
		default :
			break;
		}
		return rep;
				
		}

	
	
	

	
	}
	
