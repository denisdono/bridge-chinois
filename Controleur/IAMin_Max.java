package Controleur;


import Modele.Carte;
import Modele.Couleur;
import Modele.Jeu;
import Modele.MemeCarte;
import Modele.arbreMinMax;

public class IAMin_Max extends Joueur {
	MemeCarte contexte;//contient tout le contexte de la partie
	arbreMinMax arbre;//notre arbre de chois possible
	
	
	public IAMin_Max(int n,Jeu j) {
		//cree et initialise notre IA
		super(n,j);
		int [] nbp=new int[6];
		int [] cp=new int[6];
		int []CarteMain=new int[12];
		Carte carte = new Carte(13,Couleur.Neutre);
		contexte=new MemeCarte(j,12);
		for (int i=0;i<jeu.getMains()[num].getnbCarte();i++) {
			//met a jour la position des carte qu'on a en main
			contexte.Carte_Main(jeu.getMains()[num].voirCarte(i));
			CarteMain[i]=contexte.CarteAInt(jeu.getMains()[num].voirCarte(i));
		}
		for(int p=0;p<6;p++) {
			//met a jour la position des carte sur la sommet de pile
			contexte.Carte_Sommet_Pile(jeu.getPiles()[p].topDeck());
			nbp[p]=5;
			cp[p]=contexte.CarteAInt(jeu.getPiles()[p].topDeck());
		}
		//cree notre noeud de depart
		arbre=new arbreMinMax();
		arbre.arbreMinMax(1,contexte,jeu.j_dom(),jeu.etape(),num,12,nbp,cp,carte,CarteMain,0);
	}
	
	public boolean IAJeu() {
		creeArbre(arbre,arbre.getetage()+3);
		System.out.println("creation de l'arbre terminer sur 5 etage plus que l'estimation de gain a faire");
		return true;
	}
	
	public boolean creeArbre(arbreMinMax arbre,int etage_max) {
		boolean fin=false;
		arbre.Successeur();//cree les successeur du noeud
		if((arbre.getnbcarteMain()>0 || arbre.getnbcarteAdv()>0) && etage_max>arbre.getetage()) {
			//lance la creation des successeur si l'abre na pas atteint s
			int nbsuc=arbre.getnbfils();
			arbreMinMax []suc=arbre.getfils();
			for(int i=0;i<nbsuc;i++) {
				creeArbre(suc[i],etage_max);
			}
		}
	if((arbre.getnbcarteMain()==0 && arbre.getnbcarteAdv()==0)) {
		fin=true;
	}
	return fin;
	}
}
