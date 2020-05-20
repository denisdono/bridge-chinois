package Controleur;

import Modele.Jeu;
import Modele.Carte;

class JoueurIA extends Joueur{
	IARandom ia;
	IASimple ias;
	IAMin_Max iam;
	
	JoueurIA(int n, Jeu p) {
		super (n,p);
		ia =new IARandom(n,p);
		ias=new IASimple (n,p);
		iam=new IAMin_Max(n,p);
	}

	
	boolean tempsEcoule() {
		int i;
		int niv=2;
		switch (niv) {
		case 0:
			i=ia.IAJeu();
			break;
		case 1:
			i=ias.IAjeu();
			break;
		case 2:
			i=iam.IAjeu();
			break;
		default :
			i=-1;//ne seras jamais atteint.
		}
		jeu.jouer(i, num);
		return true;	
		}
	
	void avanceMinMAx(Carte c) {
		iam.avancer_arbre(c);
	}

	
	
	

	
	}
	
