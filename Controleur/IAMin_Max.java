package Controleur;


import Modele.Carte;
import Modele.Couleur;
import Modele.Jeu;
import Modele.MemeCarte;
import Modele.arbreMinMax;
import Modele.Deck;
import Modele.Coup;
import Modele.Historique;

public class IAMin_Max extends Joueur {
	MemeCarte contexte;//contient tout le contexte de la partie
	arbreMinMax arbre=new arbreMinMax();//notre arbre de chois possible
	int etape;//la dernier etape a la quelle on a jouer
	
	public IAMin_Max(int n,Jeu j) {
		//cree et initialise notre IA
		super(n,j);
		int []CarteMain=new int[12];
		Carte carte = new Carte(13,Couleur.Neutre);
		contexte=new MemeCarte(12,jeu.getAtout());
		for (int i=0;i<jeu.getMains()[num].getnbCarte();i++) {
			//met a jour la position des carte qu'on a en main
			contexte.Carte_Main(jeu.getMains()[num].voirCarte(i));
			CarteMain[i]=contexte.CarteAInt(jeu.getMains()[num].voirCarte(i));
		}
		//cree notre noeud de depart
		arbre.arbreMinMax(1,contexte,jeu.j_dom(),jeu.etape(),num,12,carte,CarteMain,0);
		creeArbre(arbre,arbre.getetage()+etageSup());//indique la profondeur voulu pour l'arbre
		int tmp;
		tmp=comptefils(arbre);
		etape=0;
		System.out.println("il y a actuellement "+tmp+" de fils crée");
	}
	
	void testTransition() {
		//verifie que la carte cree est correctement transposer en entier pui remis en carte
		Carte c;
		int tmp;
		Couleur couleur = null;
		for(int j =0;j<4 ;j++ ) { // creations de toutes les cartes
			switch(j) {
				case 0: couleur = Couleur.Trefle;
						break;
				case 1: couleur = Couleur.Carreaux;
						break;
				case 2: couleur = Couleur.Coeur;
						break;
				case 3: couleur = Couleur.Pique;
					break;		
						
			}
			for(int i = 2 ;i<15;i++ ) { // Les cartes vont du 2 au 14 pour les cartes de 2 a 10 les chiffres parlent d'eux meme ensuite 11=valet, 12=dame, 13=roi et 14=as
				c = new Carte(i,couleur);
				System.out.println("carte cree "+c);
				tmp=contexte.CarteAInt(c);
				System.out.println("carte vaut "+tmp);
				c=contexte.IntACarte(tmp);
				System.out.println("carte recuperer "+c);
				System.out.println(" ");
			}
		}
	}
	
	
	int etageSup() {
		//simule le nombre de fils qui sera cr�e dans la partie
		int e=0;
		int inconnue=arbre.getnbInconnue();
		if(inconnue>20) {
			e=3;
		}
		else if(inconnue>17){
			e=4;
		}
		else if(inconnue>13) {
			e=5;
		}
		else if(inconnue>10) {
			e=6;
		}
		else if(inconnue>5) {
			e=8;
		}
		else {
			e=10;
		}
		return e;
	}
	
	public int IAjeu() {
		int i=0;
		int j;
		Carte Ajouer;
		arbreMinMax fils[];
		//int tmp[];
		creeArbre(arbre,arbre.getetage()+3);//indique la profondeur voulu pour l'arbre
		/*tmp=comptefils(arbre);
		System.out.println("il y a actuellement "+tmp[0]+" de fils crée");
		System.out.println("il en sont a l'etape "+tmp[1]);*/
		switch (jeu.etape()) {
		case 0:
		case 1:
			arbreMinMax tmp;
			deplace_arbre();
			j=arbre.maxtauxvictoir();
			fils=arbre.getfils();
			tmp=fils[j];
			Ajouer=tmp.getCarteAction();
			i=indiceCarteMain(Ajouer);
			etape=jeu.etape();
			break;
		case 2:
		case 3:
			deplace_arbre();
			i=meilleurPioche();
			etape=jeu.etape();
			break;
		}
		return i;
	}
	
	
	
	int meilleurPioche() {
		//System.out.println("recherche meilleur pioche");
		Deck pioche[];
		pioche=jeu.getPiles();
		arbreMinMax tmp;
		arbreMinMax fils[];
		fils=arbre.getfils();
		int ind=-1;
		float meilleur=-1;
		for (int j=0;j<arbre.getnbfils();j++) {
			tmp=fils[j];
			for (int i=0;i<6;i++) {
				//System.out.println("regarde si la pioche est piochable");
					if(jeu.peutPiocher(i)) {
						if(pioche[i].topDeck().getCouleur()==tmp.getCarteAction().getCouleur() && pioche[i].topDeck().getValeur()==tmp.getCarteAction().getValeur()) {
							//System.out.println("la pioche est piochable");
							if(tmp.gettauxvictoir()>meilleur) {
								meilleur=tmp.gettauxvictoir();
								ind=i;
						}
					}
				}
			}
		}
		return ind;
	}
	
	public int indiceCarteMain(Carte c) {
		int i=0;
		boolean trouver=false;
		while(i<jeu.getMains()[num].getnbCarte() && !trouver) {
			if(jeu.getMains()[num].voirCarte(i).getCouleur()==c.getCouleur() && jeu.getMains()[num].voirCarte(i).getValeur()==c.getValeur()) {
				trouver =true;
			}
			else {
				i++;
			}
		}
		return i;
	}
	
	public int comptefils(arbreMinMax arbre) {
		//ser a conaitre le nombre de fils actuellement crée
		int res;
		res=0;
		int nbfildirect;
		arbreMinMax fils[];
		if(arbre.getnbfils()==0) {
			res=arbre.getetape();
		}
		else {
			nbfildirect=arbre.getnbfils();
			res=nbfildirect;
			fils=arbre.getfils();
			for(int i=0;i<nbfildirect;i++) {
				res=res+comptefils(fils[i]);

			}
		}
		return res;
	}
	
	public boolean creeArbre(arbreMinMax arbre,int etage_max) {
		//cree les successeure d'un noueud
		float vict=0;
		boolean fin=false;
		if(arbre.getnbfils()==0) {
			vict=1-arbre.Successeur();//cree les successeur du noeud
			arbre.changetauxvictoir(vict);
		}
		if((arbre.getnbcarteMain()>0 || arbre.getnbcarteAdv()>0) && etage_max>arbre.getetage()) {
			//lance la creation des successeur si l'abre na pas atteint sa profondeur viser
			int nbsuc=arbre.getnbfils();
			arbreMinMax []suc=arbre.getfils();
			for(int i=0;i<nbsuc;i++) {
				creeArbre(suc[i],etage_max);
				vict=vict+suc[i].gettauxvictoir();
			}
			vict=vict/nbsuc;
			arbre.changetauxvictoir(vict);
			//notre de taux de victoire est la moyenne des taux de victoire des successeur directe
		}
	if((arbre.getnbcarteMain()==0 && arbre.getnbcarteAdv()==0)) {
		fin=true;
	}
	return fin;
	}
	
	public void deplace_arbre() {
		//trouve la carte jouer a l'etape qui nous interesse
		Carte jouer=null;
		boolean finpioche=jeu.pilesvide();
		while(jeu.etape()!=arbre.getetape() || finpioche){
			//notre arbre n'est pas sur l'etape en cour on le met a jour
			switch(arbre.getetape()) {
			case 0:
				//System.out.println("carte poser premier ");
				if(finpioche && arbre.getdom()!=jeu.j_dom() && jeu.j_dom()!=num) {
					jouer=jeu.getc_doma();
				}
				else {
					jouer=jeu.getC_dom();
				}
				etape=1;
				break;
			case 1:
				//System.out.println("carte poser second ");
				if(finpioche && arbre.getdom()!=jeu.j_dom() && jeu.j_dom()!=num) {
					jouer=jeu.getc_suba();
				}
				else {
					jouer=jeu.getC_sub();
				}
				if(jeu.pilesvide()) {
					etape=0;
				}
				else {
					etape=2;
				}
				break;
			case 2:
				//System.out.println("carte piocher premier ");
				jouer=jeu.getCartepioche1();
				etape=3;
				break;
			case 3:
				//System.out.println("carte piocher second ");
				jouer=jeu.getCartepioche2();
				etape=0;
				break;
			}
			System.out.println("on  va avancer avec la carte "+jouer);
			finpioche=false;
			avancer_arbre(jouer);
			/*System.out.println(" ");
			System.out.println("joueur dominant est "+jeu.j_dom());
			System.out.println("joueur dominant de l'arbre est "+arbre.getdom());
			System.out.println(" ");
			System.out.println(" ");
			System.out.println("etape est "+jeu.etape());
			System.out.println("etape de l'arbre est "+arbre.getetape());*/
		}
	}
		
		
		public void avancer_arbre(Carte c) {
			//avance l'arbre selont les carte qui ont ete jouer si necessaire
		System.out.println("carte jouer"+c);
		System.out.println("carteAction"+arbre.getCarteAction());
		int i=0;
		arbreMinMax fils[]=arbre.getfils();
		boolean trouver=false;
		while(!trouver) {
			//System.out.println("carte regarder "+fils[i].getCarteAction());
			if(fils[i].getCarteAction().getCouleur()==c.getCouleur() && fils[i].getCarteAction().getValeur()==c.getValeur()) {
				trouver=true;
				System.out.println("dans la boucle carte "+fils[i].getCarteAction());
				arbre=fils[i];
			}
			i++;	
		}
	}
}
