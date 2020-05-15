package Modele;

public class arbreMinMax {
	float tauxvictoir;//condiendra notre esstimation de gain optenue par la somme de la probabiliter que 
	//chacune de nos action face un coup gagnan sur tout les successeur calculer
	float prob;//probabiliter que le noeud se produise 
	//(surtout probabiliter que l'adverssert est cette carte)
	MemeCarte context;//tout les information sur l'etat des carte aux loeud actuelle
	int dom;//numeros du joueur dominant aux actuelle
	int nbfils;//nombre de fils du noeud
	arbreMinMax[] fils;//liste des fils
	int etape;//indique l'atape courante du noeud
	int [] nbpile;//nombre de carterestant sur la pile i
	int [] cpile;//carte sur le sommet de la pile i
	int num;//numero du joueur
	int nbcarteMain;//nombre de nos carte
	int [] carteMain;//les carte de notre main en entier
	boolean ActionInconnue ;//si vrai l'action en cour doit prendre en comptequ'une 
	//carte inconnue peut etre utiliser
	Carte carteAction;//la carte que l'on a jouer pour arriver ici
	int etage;//indique a quelle etage de l'arbre on est
	
	
	public void arbreMinMax(float p,MemeCarte c,int d,int e,int n,int nbcm,int[]nbp,int[]cp,Carte ca,int []cm,int hauteur) {
		etage=hauteur;
		tauxvictoir=0;
		prob=p;
		dom=d;
		carteAction=ca;
		context=new MemeCarte(c.jeu,c.nbCadv);
		carteMain=new int[12];
		for (int u=0;u<12;u++) {
			//copie notre main
			carteMain[u]=cm[u];
		}
		//copie les valeur de MemeCarte de l'etage precedent
		
		for (int i=0;i<52;i++) {
			//copie les information sur les position des cartes
			context.CarteVue[i]=c.CarteVue[i];
		}
		for(int j=0;j<12;j++) {
			//copie la main de l'adverssaire
			context.MainAdv[j]=c.MainAdv[j];
		}
		for (int k=0;k<4;k++) {
			//copie des information sur les couleur posseder par l'adverssert
			context.colAdv[k]=c.colAdv[k];
		}
		nbpile=new int[6];
		cpile=new int[6];
		for(int pl=0;pl<6;pl++) {
			//copie des information sur les pile
			nbpile[pl]=nbp[pl];
			cpile[pl]=cp[pl];
		}
		nbfils=0;
		fils=null;
		etape=e;
		num=n;
		nbcarteMain=nbcm;
		ActionInconnue=false;
	}
	
	
	public void Successeur() {
		//cree les successeure du noeud en cour
		float vict;
		switch (etape){
		case 0:
			//premier a poser;
			nombreDeSucPremierPos();//trouve le nombre de successseur
			if(dom==num) {
				creeSucPoseJoueurPremier();
			}
			else {
				creeSucPoseAdvPremier();
			}
			break;
		case 1:
			//deuxieme a poser
			nombreSucPosSecond();
			if(dom!=num) {
				vict=creeSucPoseJoueurSecond();
			}
			else {
				vict=creeSucPoseAdvSecond();
			}
			break;
		case 2:
			//piocher joueur dominant
			nbSucPioche();
			creeSucPioche();
			break;
		case 3:
			//piocher second joueur
			nbSucPioche();
			creeSucPioche();
			break;
		default :
			break;
		}
	}
	
	public void creeSucPoseJoueurPremier() {
		//cree les successeur lorsque l'on est le premier a jouer
		fils=new arbreMinMax[nbfils];
		int numeroSuc=0;
		for (int i=0;i<12;i++) {
			if(carteMain[i]>=0) {
				fils[numeroSuc]=new arbreMinMax();
				fils[numeroSuc].arbreMinMax(1,context,dom,etape+1,num,nbcarteMain,nbpile,cpile,context.IntACarte(carteMain[i]),carteMain,etage+1);
				fils[numeroSuc].PoseMainJoueur(i);
				numeroSuc++;
			}
		}
		
	}
	
	public void creeSucPoseJoueurSecond() {
		//cree les successeur du noeud dans le cas ou le joueur joue en premier
		fils=new arbreMinMax[nbfils];
		int gagnant;
		int nextDom=dom;
		int numeroSuc=0;
		int nextetape;
		Carte c;
		if(peutPiocher()) {
			nextetape=2;
		}
		else {
			nextetape=0;
		}
		for (int i=0;i<12;i++) {
			if(carteMain[i]>=0) {
				if(context.SuposerJouable(carteAction,context.IntACarte(carteMain[i]),false,Carte_col_Joueur(carteAction.getCouleur())!=0)) {
					gagnant=context.jeu.carte_gagnante(carteAction,context.IntACarte(carteMain[i]));
					if(gagnant==2) {
						//le joueur dominant n'a pas gagner le plis
						nextDom=(nextDom+1)%2;
					}
					fils[numeroSuc]=new arbreMinMax();
					fils[numeroSuc].arbreMinMax(1,context,nextDom,nextetape,num,nbcarteMain,nbpile,cpile,context.IntACarte(carteMain[i]),carteMain,etage+1);
					fils[numeroSuc].PoseMainJoueur(i);
					numeroSuc++;
				}
			}
		}
	}
	
	public void creeSucPoseAdvSecond() {
		//cree les successeur lorsque l'adversssaire joue second
		fils=new arbreMinMax[nbfils];
		int gagnant;
		int nextDom=dom;
		int numeroSuc=0;
		int[] carteAdv=context.getMainAdv();
		int nextetape;
		if(peutPiocher()) {
			nextetape=2;
		}
		else {
			nextetape=0;
		}
		for (int i=0;i<12;i++) {
			//on liste tout les carte connue de notre adverssaire
			if(carteAdv[i]>=0) {
				//les carte connue de l'adverssaire
				if(context.SuposerJouable(carteAction,context.IntACarte(carteAdv[i]),false,context.possedecolAdv(carteAction.getCouleur()))) {
					//la carte est jouable
					gagnant=context.jeu.carte_gagnante(carteAction,context.IntACarte(carteAdv[i]));
					if(gagnant==2) {
						//le joueur dominant n'a pas gagner le plis
						nextDom=(nextDom+1)%2;
					}
					fils[numeroSuc]=new arbreMinMax();
					fils[numeroSuc].arbreMinMax(1,context,nextDom,nextetape,num,nbcarteMain,nbpile,cpile,context.IntACarte(carteAdv[i]),carteMain,etage+1);
					fils[numeroSuc].context.poseCarteAdv(carteAdv[i]);
					numeroSuc++;
				}
			}
		}
		if(ActionInconnue) {
			//l'adverssaire a des carte inconnue on liste tout les carte joueble
			float prob;
			if(context.possedecolAdv(carteAction.getCouleur())) {
				prob=context.CarteColeDispo(carteAction.getCouleur());
			}
			else {
				prob=context.getnbCadvInonnue()/context.getnbCInconnue();
			}
			int cartevue[]=context.getCarteVue();
			for (int j=0;j<52;j++) {
				if(cartevue[j]==-1) {
					if(context.SuposerJouable(carteAction,context.IntACarte(carteAdv[j]),false,context.possedecolAdv(carteAction.getCouleur()))) {
						//si l'adverssaire peux jouer la couleur deja oser on pren en compte que les carte inconnue de la bonne couleur
						gagnant=context.jeu.carte_gagnante(carteAction,context.IntACarte(j));
						if(gagnant==2) {
							//le joueur dominant n'a pas gagner le plis
							nextDom=(nextDom+1)%2;
						}
						fils[numeroSuc]=new arbreMinMax();
						fils[numeroSuc].arbreMinMax(prob,context,nextDom,nextetape,num,nbcarteMain,nbpile,cpile,context.IntACarte(j),carteMain,etage+1);
						fils[numeroSuc].context.poseCarteAdv(j);
						numeroSuc++;
				}
			}
		}
		}
	}
	
	public void creeSucPioche() {
		fils=new arbreMinMax[nbfils];
		//cree les successeur de l'arbre MinMax quand on pioche
		int nextetape;
		int numeroSuc=0;
		if(etape==2) {
			nextetape=etape+1;
		}
		else {
			nextetape=0;
		}
		for(int i=0;i<6;i++) {
			if(nbpile[i]>0) {
				//si la pioche est pleine
				if(cpile[i]!=-1) {
					fils[numeroSuc]=new arbreMinMax();
					fils[numeroSuc].arbreMinMax(1,context,dom,nextetape,num,nbcarteMain,nbpile,cpile,context.IntACarte(cpile[i]),carteMain,etage+1);
					if(dom==(etape%2)+1) {
						//c'est nous qui piochons
						fils[numeroSuc].PiocheMainJoueur(i,cpile[i]);
						//mise a jour du contexte dans le fils
					}
					else {
						//l'adverssaire pioche
						fils[numeroSuc].context.Pioche_Carte_Main_Adv(context.IntACarte(cpile[i]));
					}
					numeroSuc++;
				}
			}
		}
		if(ActionInconnue) {
			//on peut piocher toute les carte a une position inconnue
			float prob=getPileInc()/context.getnbCInconnue();
			int cartevue[]=context.getCarteVue();
			for (int j=0;j<52;j++) {
				if(cartevue[j]==-1) {
					fils[numeroSuc]=new arbreMinMax();
					fils[numeroSuc].arbreMinMax(prob,context,dom,nextetape,num,nbcarteMain,nbpile,cpile,context.IntACarte(j),carteMain,etage+1);
					if(dom==(etape%2)+1) {
						//c'est nous qui piochons
						fils[numeroSuc].PiocheMainJoueur(j,cpile[j]);
						//mise a jour du contexte dans le fils
					}
					else {
						//l'adverssaire pioche
						fils[numeroSuc].context.Pioche_Carte_Main_Adv(context.IntACarte(j));
					}
					numeroSuc++;
				}
			}
		}
	}
	
	public void creeSucPoseAdvPremier() {
		//cree les successeur lorsque l'adversssairejoue premier
		fils=new arbreMinMax[nbfils];
		int numeroSuc=0;
		int[] carteAdv=context.getMainAdv();
		for (int i=0;i<12;i++) {
			//on liste tout les carte connue de notre adverssaire
			if(carteAdv[i]>=0) {
				fils[numeroSuc]=new arbreMinMax();
				fils[numeroSuc].arbreMinMax(1,context,dom,etape+1,num,nbcarteMain,nbpile,cpile,context.IntACarte(carteAdv[i]),carteMain,etage+1);
				fils[numeroSuc].context.poseCarteAdv(carteAdv[i]);
				numeroSuc++;
			}
		}
		if(ActionInconnue) {
			//l'adverssaire a des carte inconnue on liste tout les carte joueble
			float prob=context.getnbCadvInonnue()/context.getnbCInconnue();
			int cartevue[]=context.getCarteVue();
			for (int j=0;j<52;j++) {
				if(cartevue[j]==-1) {
					fils[numeroSuc]=new arbreMinMax();
					fils[numeroSuc].arbreMinMax(prob,context,dom,etape+1,num,nbcarteMain,nbpile,cpile,context.IntACarte(j),carteMain,etage+1);
					fils[numeroSuc].context.poseCarteAdv(j);
					numeroSuc++;
				}
			}
			
		}
	}
	
	
	public void nombreSucPosSecond() {
		//nombre de successeur dans l'abre si on pose en second
		//en indiquant si il y a des carte inconnue a prendre en compte
		if (dom==num) {
			//on joue en premier le deuxieme joueur pose second
			//ici c'est le tour de l'IA
			if(context.getnbCadvInonnue()==0 ) {
				//on connait tout les carte adversse
				ActionInconnue=false;
				nbfils=context.CartePosableAdv(carteAction ,false);
			}
			else {
				ActionInconnue=true;
				//on ne connait pas aux moin une carte adversse
				if(context.possedecolAdv(carteAction.getCouleur())) {
					//l'adverssaire possedent la couleur il doit jouer 
					//une carte de cette couleur il peut donc jouer tour les carte 
					//de cette couleur dans sa main ou a une position inconnue
					nbfils=context.CartePosableAdv(carteAction,false);
					nbfils=nbfils+context.CarteColeDispo(carteAction.getCouleur());
				}
				else {
					//on peut pas affirmer que l'adverssair possede cette couleur 
					//on suppose qu'il peut jouer tout les carte inconnue
					nbfils=context.getnbCInconnue()+context.getnbCadvConnue();
				}
			}
		}
		else {
			//on joue en second
			if(Carte_col_Joueur(carteAction.getCouleur())==0) {
				//on pas de carte de la couleur deja poser
				nbfils=nbcarteMain;
			}
			else {
				//on a des carte de la couleur deja poser
				nbfils=Carte_col_Joueur(carteAction.getCouleur());
			}
			ActionInconnue=false;
		}
	}
	
	
	
	public void nombreDeSucPremierPos() {
		//nombre de successeur dans l'abre si on pose en premier
		//en indiquant si il y a des carte inconnue a prendre en compte
		if (dom==num) {
			//on joue en premier
			nbfils=nbcarteMain;
			ActionInconnue=false;
		}
		else {
			//on joue en second l'autre joueur pose en premier
			if(context.getnbCadvInonnue()==0) {
				//on connait tout les carte adversse
				nbfils=context.getnbCadv();
				ActionInconnue=false;
			}
			else {
				//on ne connait pas aux moin une carte adversse
				nbfils=context.getnbCInconnue()+context.getnbCadvConnue();
				//nombre de carte inconnue +nombre de carte adverssaire connue 
				ActionInconnue=true;
			}
		}
	}
	
	
	public void nbSucPioche() {
		//nombre de successeur dans l'abre si on pioche
		//en indiquant si il y a des carte inconnue a prendre en compte
			if(getPileInc()==0) {
				nbfils=getPileDispo();
				ActionInconnue=false;
			}
			else {
				nbfils=context.getnbCInconnue()+getPileDispo();
				ActionInconnue=true;
			}
	}
	
	public int getPileInc() {
		//compte le nombre de pile avec des carte inconnue en sommet de pile
		int inc=0;
		for(int i=0;i<6;i++) {
			if(nbpile[i]>0) {
				if(cpile[i]==-1) {
					inc++;
				}
			}
		}
		return inc;
	}
	
	public int getnbcarteMain(){
		return nbcarteMain;
	}
	
	public int getnbcarteAdv(){
		return context.getnbCadv();
	}
	
	public int getetage(){
		return etage;
	}
	
	public int getnbfils(){
		return nbfils;
	}
	
	public arbreMinMax[] getfils(){
		return fils;
	}
	
	public int getPileDispo() {
		//compte le nombre de pile avec des carte a piocher
		int dispo=0;
		for(int i=0;i<6;i++) {
			if(nbpile[i]>0) {
				dispo++;
			}
		}
		return dispo;
	}	
	
	
	public int Carte_col_Joueur(Couleur col) {
		//renvoi le nombre de carte de la couleur demander
		int nbcol=0;
		Carte c;
		for(int i=0;i<12;i++) {
			if(carteMain[i]>=0) {
				c=context.IntACarte(carteMain[i]);
				if (c.getCouleur()==col) {
					nbcol++;
				}
			}
		}
		return nbcol;
	}
	
	public void PoseMainJoueur(int i) {
		//met a jour la main du joueur en cas de pose
		nbcarteMain--;
		carteMain[i]=-1;
	}
	
	
	public void PiocheMainJoueur(int i,int carte) {
		//le joueur pioche et met a jour les information
		int j=0;
		boolean trouver=false;
		while(!trouver) {
			if (carteMain[j]<0) {
				carteMain[j]=carte;
				trouver=true;
			}
			j++;
		}
		context.Carte_Main(context.IntACarte(carte));
		//precise la position de la carte
		nbpile[i]=nbpile[i]-1;
		cpile[i]=-1;
		nbcarteMain++;
	}
	
	public boolean peutPiocher() {
		boolean pioche=false;
		for (int i=0;i<6;i++) {
			if (nbpile[i]>0) {
				pioche=true;
			}
		}
		return pioche;
	}
	
}
