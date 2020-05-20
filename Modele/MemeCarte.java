package Modele;

public class MemeCarte{
		int [] CarteVue;//un tableu indiquant les information disponible sur chaque carte
		int [] MainAdv;//information sur la carte adv
		int nbCadv;//nombre de carte adverssaire
		boolean [] colAdv;//tableaux indiquant si l'adverssaire a une couleure 
		//en main ou nonl'indice cooresond a la valeur de la couleur
		Couleur atout;
	
	public MemeCarte(int nbc,Couleur a){
		nbCadv=nbc;
		atout=a;
		//nbc est le nombre de carte de l'adverssair
		//x est le nombre de cartes de l'adverssaire
		CarteVue=new int [52];
		for(int x=0;x<52;x++) {
			CarteVue[x]=-1;
		}
		//-1 la carte n'a jamais ete vue
		//0 la carte est dans la main de l'adversaire
		//1 la carte est en sommet de pile
		//2 on a la carte en main
		//3 la carte a deja ete jouer
		MainAdv=new int[11];
		for(int c=0;c<11;c++) {
			MainAdv[c]=-1;
		}
		//MainAdv sert a connaitre les cartes en main de l'adverssert
		//-1 carte inconnue 
		//-2 pas de carte
		//sinon notre le nombre est la crte de notre adversset
		colAdv=new boolean[4];
		for (int k=0;k<4;k++){
			//on pars du rincipe que l'adverssaire n'a aucune 
			//couleur et peut don jouer se qu'il veut
			colAdv[k]=false;
		}
		
	}

	public int CarteAInt (Carte c)	{
		//convertie une carte en un nombre unique obtenue avec 
		//val de couleure de la carte * 13 +val de la carte-2
		int valCarte=c.getValeur()-2;
		int ValCol=c.getCouleur().getVal();
		if(ValCol*13 +valCarte<0) {
			System.out.println("couleur carte "+c.getCouleur());
			System.out.println("carte "+c.toString());
		}
		return ValCol*13 +valCarte;
	}
	
	public Carte IntACarte (int c) {
		//renvoie la carte correspondant a un int
		Carte res;
		int valCarte=c%13+2;//calcule la valeur de la carte
		int ValCol=c/13;//calcul la couleur de la carte
		Couleur couleur=Couleur.Neutre;
		switch(ValCol) {
		//trouve la couleur de la carte
		case 0: couleur = Couleur.Trefle;
				break;
		case 1: couleur = Couleur.Carreaux;
				break;
		case 2: couleur = Couleur.Coeur;
				break;
		case 3: couleur = Couleur.Pique;
			break;			
	}
		res=new Carte(valCarte,couleur);
		return res;
	}
	
	public Couleur getAtout() {
		return atout;
	}
	
	public void Carte_Sommet_Pile(Carte c) {
		//maintien les information sur les cartes vues dans la partie
		//par le joueur
		int pos=CarteAInt (c);
		CarteVue[pos]=1;
	}
	
	public void Carte_Main(Carte c) {
		//maintien les information sur les cartes u dans la partie
		int pos=CarteAInt (c);
		CarteVue[pos]=2;
	}
	
	
	
	public void Pioche_Carte_Main_Adv(Carte c) {
		//maintien les information sur les cartes vues dans la partie
		//par le joueur
		boolean trouver=false;
		int i=0;
		int pos=CarteAInt(c);
		CarteVue[pos]=0;
		while(i<11 && !trouver) {
			//ajoute la carte a la main de l'adverssaire 
			if(MainAdv[i]==-2) {
				//on met a jour une carte de la main deja poser
				MainAdv[i]=pos;
				trouver=true;
			}
			i++;
		}
		nbCadv++;
		colAdv[c.getCouleur().getVal()]=true;
	}
	
	public void Carte_Poser(int pos) {
		//maintien les information sur les cartes vues dans la partie
		//par le joueur
		CarteVue[pos]=3;
	}
	
	
	public void poseCarteAdv(int carte,Carte cartejouer,boolean premier) {
		//met a jour les info quand l'adverssaire poseune carte
		int i=0;
		boolean trouver=false;
		while(i<11 && !trouver) {
			//trouve la carte dans la main du joueuret la met a jourS 
			if(MainAdv[i]==carte) {
				MainAdv[i]=-2;
				trouver=true;
			}
			i++;
		}
		if(!trouver) {
			//on a pas trouver la carte on ne la connaissit pas
			//on retire une carte inconnue
			i=0;
			while(i<11 && !trouver) {
				if(MainAdv[i]==-1) {
					MainAdv[i]=-2;
					trouver=true;
				}
				i++;
			}
		}
		nbCadv--;//met a jour le nombre de carte de l'adverssaire
		Carte_Poser(carte);//indique que la carte est poser
		MetAJourColAdv();
	}
	
	
	public void MetAJourColAdv() {
		//met a jour les information sur les couleur de la main adversse d'apres se qu'on sait
		int ValCol;
		for(int k=0;k<4;k++) {
			//reinitialise tout les information sur les couleur de la main adversse
			colAdv[k]=false;
		}
		for(int i=0;i<11;i++) {
			//indique les couleur que l'on ssait que notre adverssaire possedent
			if(MainAdv[i]>=0){
				ValCol=MainAdv[i]/13;
				colAdv[ValCol]=true;
			}
		}
	}
	
	
	public int AdvGagne(Carte c) {
		//renvoi 0 si l'adverssaire ne peut pas nous batre
		//renvoi -1 si on connait pas tout les carte de l'adverssaire
		int res=0;//est le nombre de carte qui bats notre carte
		int i=0;
		boolean inconnue=false;//compte les carte inconnue de notre adverssaire
		while(i<nbCadv); {
			if (carte_gagnante(c, IntACarte(MainAdv[i]))==2) {
				//la carte passer est battue
				res++;
			}
			else if(MainAdv[i]==-1) {
				//il existe une carte de l'adverssaire que l'on connait pas
				inconnue=true;
			}
			i++;
		}
		if(inconnue && res==0) {
			//on ne connais pas tout les carte de l'adverssaire
			res=-1;
		}
		return res;
	}
	
	public boolean estInbatable(Carte c) {
		//indique si la carte c est invincible donc
		//toutes les carte la battant sont soit dans notre main 
		//soit deja jouer
		boolean res=true;//dis si nous somme invaincue
		int carte;//sert a connaitre le chiffre de chaque carte qui nous bat
		int j=c.getValeur();
		int col=c.getCouleur().getVal();
		while(j<15 && res) {
			//on teste si des carte de meme couleur peuve nous batre
			//se qui est sufisant dans le cas sans atout et 
			//si notre carte est un atout
			carte=j+1+13*col;
			if(carte_gagnante(c, IntACarte(carte))==2 && CarteVue[carte]<2) {
				//la carte est vaincue a se tour CarteVue[carte]<1 signifi que
				//la carte est soit dans la main de l'adversaire soit que
				//l'on ne c'est pas ou elle est 
				res=false;
			}
			j++;
		}
		if (!(c.getCouleur()==atout) && atout!=Couleur.Neutre && res) {
			//si la carte c n'est pas une carte atout, qu'il y a un atout
			//et que notre carte n'est pas encor vaincue
			int i=1;//sert tester tout les valeur des carte de couleur de l'atout
			while(i<14 && res) {
				carte=i+13*atout.getVal();
				if(CarteVue[carte]<1) {
					//la carte est un atou et pas nous elle nous obligatoirement 
					//il suffit de tester si elle est diponible pour l'adverssaire
					res=false;
				}
				i++;
			}
		}	
		
		return res;
	}
	
	
	public void AdvPoseInc() {
		//l'adverssaire a pose une carte inconnue on met a jour 
		//les information sur sa main utile pour arbreMinMax
		int i=0;
		boolean trouver=false;
		while(i<11 && !trouver) {
			if(MainAdv[i]==-1) {
				MainAdv[i]=-2;
				trouver=true;
			}
			i++;
		}
		nbCadv--;
	}
	
	public void AdvPiocheInc() {
		//l'adverssaire a pioche une carte inconnue on met a jour 
		//les information sur sa main utile pour arbreMinMax
		int i=0;
		boolean trouver=false;
		while(i<11 && !trouver) {
			if(MainAdv[i]==-2) {
				MainAdv[i]=-1;
				trouver=true;
			}
			i++;
		}
		nbCadv++;
	}
		
	public int getnbCadv() {
		return nbCadv;
	}
	
	public int[] getMainAdv() {
		return MainAdv;
	}
	
	
	public int getnbCpiochable() {
		//renvoi le nombre de cate qui peuvent etre en sommet de pile
		int pioche=0;
		for(int i=0;i<52;i++) {
			if(CarteVue[i]==-1 ||CarteVue[i]==1) {
				pioche++;
			}
		}
		pioche=pioche-getnbCadvInonnue();
		return pioche;
	}
	
	public int getnbCInconnue() {
		//si retour =0 on connait tout les carte  du jeu
		//sinon on renvoi le nombre de carte inconnue
		int inc=0;
		for(int i=0;i<52;i++) {
			if(CarteVue[i]==-1) {
				inc++;
			}
		}
		return inc;
	}
	
	public boolean possedecolAdv(Couleur col) {
		return colAdv[col.getVal()];
	}
	
	public int CartePosableAdv(Carte c_pos,boolean premier) {
		//renvoie le nombre de carte que l'adverssaire 
		//peut jouer selon la carte donner en argument
		//avec les carte que l'on connait de sa main
		int posable=0;
		Carte c;
		for(int i=0;i<11;i++) {
			if (MainAdv[i]>=0) {
				c=IntACarte(MainAdv[i]);
				if (SuposerJouable(c_pos ,c,premier,colAdv[c_pos.getCouleur().getVal()])) {
					//la carte est posable
					posable ++;
				}
			}
		}
		return posable;
	}
	
	public boolean SuposerJouable(Carte c_pos ,Carte c,boolean premier,boolean posedcol) {
		//c_pos la carte poser par en premier null si aucune 
		//c la carte a poser
		//premier vrai si on joue en premier
		//posedcol indique si on possede la couleur
		boolean jouable=true;
		//dit si la carte peut etre jouer
		if (premier) {
			//si on est le premier a poser on peut jouer nimporte quelle carte
			jouable=true;
		}
		else {
			//sinon il faut jouer la meme couleure si on peut
			if (c_pos.getCouleur()==c.getCouleur()) {
				//on joue la meme couleure
				jouable=true;
			}
			else {
				//on joue une couleure diffÃ©rente
				//on sit que l'adverssaire possende la couleur necessaire
					if(posedcol) {
						jouable=false;
					}
				}
			}
		return jouable;
	}
	
	
	public int CarteColeDispo(Couleur col) {
		//renvoie le nombre de carte de la couleur 
		//donner qui a une position inconnue dans le jeu
		int nbretsant=0;
		for (int i=0;i<52;i++) {
			if (CarteVue[i]==-1) {
				if (col.getVal()==IntACarte(i).getCouleur().getVal()) {
					nbretsant++;
				}
			}
		}
		
		return nbretsant;
	}
	
	
	public int[] getCarteVue() {
		return CarteVue;
	}
	
	
	public int getnbCadvConnue() {
		int connue=0;
		for(int i=0;i<11;i++) {
			if(MainAdv[i]>=0) {
				connue++;
			}
		}
		return connue;
	}
	
	public int getnbCadvInonnue() {
		//renvoi le nombre de carte inconnue de l'adverssaire
		int inconnue=0;
		for(int i=0;i<11;i++) {
			if(MainAdv[i]==-1) {
				inconnue++;
			}
		}
		return inconnue;
	}
	
	
	public int carte_gagnante(Carte dom,Carte sub) {
        //gagnant donne le numÃƒÂ©ros du joueure gagnant
        int gagnant=-1;
            if (dom.getCouleur()==sub.getCouleur()){
                //si les deux carte sont de mÃƒÂªme couleure la plus forte l'emporte
                if(dom.getValeur()>sub.getValeur()){
                    gagnant=1;
                }
                else {
                    gagnant=2;
                }
            }
            else {
                    //le premier joueure n'a pas d'atout et les deux joueure on une couleure diffÃƒÂ©rente
                    if (sub.getCouleur()==atout) {
                        gagnant=2;
                    }
                    else {
                        gagnant=1;
                    }
            }
            return gagnant;


        }
	
	
	
}