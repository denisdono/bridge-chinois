package Modele;


import Patterns.Observable;
import java.util.Stack;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Jeu extends Observable {
	boolean changerjoueur;// doit on changer de joueur
	boolean enCours;// partie en cour
	boolean finmanche;// a t'on fini la manche ?
	boolean piochage;// y'as t'il des cartes a piocher ?
	boolean parManche;// la fin de partie et dÃƒÂ©cidÃƒÂ© par nombre de manche (false= on dÃƒÂ©cide par score)
	Deck [] piles;// pile prÃƒÂ©sente sur la table
	Hand [] mains;//main des joueur
	int totalfin;// score a obtenir ou nombre de manche a faire avant la fin de partie
	int manche;// le nombre de manche actuelle
	int etape;// etape actuelle d'un tour de jeu
	int joueurdominant;// quel joueur ÃƒÂ  la main (premier a jouer/piocher)
	int joueurdominantpred;
	Couleur atout;// l'atout de la manche
	Carte c_dom;// carte jouer par le joueur dominant 
	Carte c_sub;// carte jouer par l'autre joueur
	Carte c_0;//carte piochée par le joueur dominant
	Carte c_1;//carte piochée par l'autre joueur
	Carte Cartepioche1;
	Carte Cartepioche2;
	Deck paquet;
	int diff;// dificulter de l'ia
	boolean showCarte;// carte visible
	boolean IA;// prï¿½sence d'une IA
	int ind;//sert a determiner quelle carte posé dans la pioche
	public boolean annulation;
        private int carteApiocher=-1;
	int jca;
	boolean debut;
	int selCarte;
	int selFond;
	public Historique historique;

	public Jeu() {
		IA=false;
		start();
	}
		
	public void start() {
		readConfig();
		debut = true;
		historique = new Historique();
		annulation = false;
		
		enCours = true;
		changerjoueur=true;
		joueurdominant=0;
		manche=1;
		piles = new Deck[6];// creation d'un tableau de piles pour les six paquets sur la table
		for (int i=0;i<6;i++) {// boucle sur les six piles
			piles[i]=new Deck();
		}
		mains=new Hand[2];//cree les main des joueur
		mains[0]=new Hand();//cree la main du premier joueur
		mains[1]=new Hand();//cree la main du deuxiÃƒÂ¨me joueur
		nouvelleManche();//initialise une manche
		metAJour();
	}
	
	public void distrubition(Deck p) {
		Deck paquet=new Deck(p);
		for (int i=0;i<11;i++) { // remplissage des mains des joueurs
			mains[0].ajoutCarte(paquet.piocher());
			mains[1].ajoutCarte(paquet.piocher());
		}
		mains[0].trierMain();
		mains[1].trierMain();
		for (int i=0;i<6;i++) {// boucle sur les six piles
			for (int j=0;j<5;j++) {// boucle pour piocher les 5 cartes
				piles[i].recupCartePile(paquet.piocher());//pioche
			}
		}
		trouve_atout();
		
		Coup c1 ;
		Deck [] pi= new Deck[6];
		for (int k=0;k<6;k++) {// boucle sur les six piles
			pi[k]=new Deck(piles[k]);
		}
		Hand h1=new Hand(mains[0]);
		Hand h2=new Hand(mains[1]);
			c1 =  new Coup(0,changerjoueur,enCours,finmanche,piochage,parManche,pi,h1,h2,totalfin,manche,etape,joueurdominant,joueurdominantpred,atout,c_dom,c_sub,diff,showCarte,IA);
			historique.ajouterCoup(c1);
	}
	
	public void restart() {
		changerjoueur=true;
		joueurdominant=(manche+1)%2;
		mains[0].videMain();
		mains[1].videMain();
		for (int i=0;i<6;i++) {// boucle sur les six piles
				piles[i].videPaquet();
			}
		finmanche=false;
		piochage= true;
		etape=0;
		mains[0].resetPlis();
		mains[1].resetPlis();
		distrubition(paquet);
		metAJour();
	}
	
	
	public void nouvelleManche() {
		finmanche=false;
		piochage= true;
		etape=0;
		mains[0].resetPlis();
		mains[1].resetPlis();
		// creation d'un paquet de carte (deja melanger)
		paquet =new Deck();
		paquet.remplirPaquet();
		distrubition(paquet);
	}
	
	public void jouer(int i,int n) {
		annulation=false;
		historique.getFutur().clear();
		if(etape==3)
			joueurdominantpred=joueurdominant;
		//creation par copie pour les coups
		Hand h1=new Hand(mains[0]);
		Hand h2=new Hand(mains[1]);
		Deck [] pi= new Deck[6];
		for (int k=0;k<6;k++) {// boucle sur les six piles
			pi[k]=new Deck(piles[k]);
		}
		//
		Coup c1 ;
		ind=i;
        carteApiocher=i;
        metAJour();
        carteApiocher=-1;
		if (enCours) {
			//tant que la condition de victoire par nombre de plis gagner ou de mancher gagner n'est pas atteinte
			if (!finmanche) {
				// tour de jeu
				switch(etape) {
				case 0:
				// joueurdominant pose une carte
					c_dom=mains[n].poserCarte(i);
					h1=new Hand(mains[0]);
					h2=new Hand(mains[1]);
					for (int k=0;k<6;k++) {// boucle sur les six piles
						pi[k]=new Deck(piles[k]);
					}
					//on passe n en parametre mais il faudrait passer joueur_actuel()
					c1= new Coup(n,changerjoueur,enCours,finmanche,piochage,parManche,pi,h1,h2,totalfin,manche,etape,joueurdominant,joueurdominantpred,atout,c_dom,c_sub,diff,showCarte,IA);
					historique.ajouterCoup(c1);
					etape++;
					metAJour();
					break;
				case 1:
					// le second joueur pose une carte en consÃƒÂ©quences (limiter par raport a la cartes)
					c_sub=mains[n].poserCarte(i);
					for (int k=0;k<6;k++) {// boucle sur les six piles
						pi[k]=new Deck(piles[k]);
					}
					metAJour();
					// calcul de qui remporte le plis
					int j;
					j=carte_gagnante(c_dom,c_sub);
					if (j==2) {// celui qui gagne devien joueur dominant
						joueurdominant = (joueurdominant + 1) % 2;
						changerjoueur=true;
					}
					mains[joueurdominant].addPlis();// incrÃƒÂ©mente le nombre de plis du vainqueur
					h1=new Hand(mains[0]);
					h2=new Hand(mains[1]);
					c1= new Coup(n,changerjoueur,enCours,finmanche,piochage,parManche,pi,h1,h2,totalfin,manche,etape,joueurdominant,joueurdominantpred,atout,c_dom,c_sub,diff,showCarte,IA);
				
					historique.ajouterCoup(c1);
					
					
					if (piochage) {//test s'il reste des carte a piocher
						etape++;
					}
					else {
						etape=0;
					}
					metAJour();
					break;
				case 2:
					
						//s'il reste des cartes a piocher le joueur dominant pioche
						Cartepioche1=piles[i].piocher();
						mains[n].ajoutCarte(Cartepioche1);
						mains[n].trierMain();
						h1=new Hand(mains[0]);
						h2=new Hand(mains[1]);
						for (int k=0;k<6;k++) {// boucle sur les six piles
							pi[k]=new Deck(piles[k]);
						}
						c1= new Coup(n,changerjoueur,enCours,finmanche,piochage,parManche,pi,h1,h2,totalfin,manche,etape,joueurdominant,joueurdominantpred,atout,c_dom,c_sub,diff,showCarte,IA);
						
						historique.ajouterCoup(c1);
						etape++;
						metAJour();
					
						
					break;
				case 3:
					//le deuxiÃƒÂ¨me joueure pioche
					Cartepioche2=piles[i].piocher();
					mains[n].ajoutCarte(Cartepioche2);
					mains[n].trierMain();
					
					for (int k=0;k<6;k++) {// boucle sur les six piles
						pi[k]=new Deck(piles[k]);
					}

					h1=new Hand(mains[0]);
					h2=new Hand(mains[1]);
					c1= new Coup(n,changerjoueur,enCours,finmanche,piochage,parManche,pi,h1,h2,totalfin,manche,etape,joueurdominant,joueurdominantpred,atout,c_dom,c_sub,diff,showCarte,IA);
					historique.ajouterCoup(c1);
					
					piochage=!pilesvide();//teste si il reste des carte a piocher
					etape=0;//fini un tour de jeu
                                        metAJour();
					break;
				}
				if (mains[0].getnbCarte()==0 && mains[1].getnbCarte()==0) {
					//teste si la manche est fini
					finmanche=true;
					int j=vainqueurManche();
					if (j!=-1) {
						//si il n'y a pas ÃƒÂ©galiter on incrÃƒÂ©mente le score du vaiqueur
						mains[j].addScoreM(1);
					}
					//on ajoute le nombre de plis gagner par chaque jooueur
						mains[0].addScoreP(mains[0].getnbPlis());
						mains[1].addScoreP(mains[1].getnbPlis());
					if(parManche) {
						//si on compte par nombre de manche
						enCours=(manche<totalfin);//on vÃƒÂ©rifie si on fini la partie
					}
					else{
						//sinon est par nombre de plis gagner
						enCours=(mains[0].getnbScoreP()<totalfin && mains[1].getnbScoreP()<totalfin);//on vÃƒÂ©rifie si on fini la partie
					}
					if (enCours) {
						//si la partie n'est pas fini on lance une nouvelle manche
						nouvelleManche();
					}
					manche++;
					metAJour();
				}		
			}	
		}
			historique.affiherPasse();
			historique.afficherFutur();
	}
	
	public Carte getCartepioche2() {
		return Cartepioche2;
	}
	
	public Carte getCartepioche1() {
		return Cartepioche1;
	}
	
	
	public void annuler() {
		
		annulation = true;
		
		if(historique.getPasse().size()==1) {
			return;
		}else {
			
		Coup cj;//coup joué
		Coup cpeek;//coup au sommet
		if(IA) {
			cj = historique.defaire();
			cpeek=historique.getPasse().peek();
				if(cj.getJoueur()!=0) {
					
				while(cpeek.getJoueur()!=0 && historique.getPasse().size()!=0 ) {
					cj=historique.defaire();
					if(historique.getPasse().size()>0) {
						cpeek=historique.getPasse().peek();
						
					}
				}
				
				cj = historique.defaire();
				}


		}else {
			
			cj = historique.defaire();//on annule le coup du joueur
		}
		System.out.print("historique avant annule:");
		//historique.affiherPasse();
		
		 cpeek= historique.getPasse().peek();
		System.out.println("main :");
		 //c.main1.afficherMain();
			historique.setAuPasse(true);
			this.changerjoueur = cj.isChangerjoueur();// doit on changer de joueur OK
			this.enCours = cpeek.isEnCours();// partie en cour OK
			this.finmanche = cpeek.isFinmanche();// a t'on fini la manche ?
			this.piochage = cpeek.isPiochage();// y'as t'il des cartes a piocher ?
			this.parManche = cpeek.isParManche();// la fin de partie et décidé par nombre de manche (false= on décide par score)
			this.mains[0] = new Hand(cpeek.getMain1());//pour creer pas par reference :    ^) OK
			this.mains[1] = new Hand(cpeek.getMain2()); // OK
			for(int i=0;i<piles.length;i++) {
				this.piles[i] = new Deck(cpeek.getPiles()[i]);//pb copie OK
			}
			
			this.totalfin = cpeek.getTotalfin();// score a obtenir ou nombre de manche a faire avant la fin de partie PK
			this.manche = cpeek.getManche();// le nombre de manche actuelle OK
			this.etape = cj.getEtape();// etape actuelle d'un tour de jeu OK
			this.joueurdominant =cpeek.getJoueurdominant();// quel joueur à la main (premier a jouer/piocher) OK
			if(etape!=1) {
				
				this.joueurdominantpred=cj.getJoueurdominant();
			}else {
				this.joueurdominantpred=cj.getJoueurdominantpred();

			}
			this.atout = cpeek.getAtout();// l'atout de la manche OK
			this.c_dom = cpeek.getC_dom();// carte jouer par le joueur dominant OK
			this.c_sub = cpeek.getC_sub();// carte jouer par l'autre joueur OK
			
			
			this.diff=cpeek.getDiff();
			this.showCarte = cpeek.isShowCarte();
			this.IA =cj.isIA();
			System.out.println();

			System.out.print("\nhistorique après annule: etape = "+cj.etape+"\n");
			
			metAJour();

			
	
	}

		historique.affiherPasse();
		historique.afficherFutur();
	}
	
	public void refaire() {//reste a corriger bug joueur actuel
		annulation = true;
		System.out.println("Taille geFutur :"+historique.getFutur().size());

		//jca=n;
		Coup cj;//coup joué
		Coup cpeek;
//		 cj = historique.refaire();//on redo le coup du joueur
		 //cpeek=cj;
		if(getIA()) {
			
			 cj = historique.refaire();//on redo 1 coup de l'ia
			
			 cpeek=historique.getFutur().peek();
				if(cj.getJoueur()!=1) {
					
				while(cpeek.getJoueur()!=0 && historique.getFutur().size()>0 ) {
					cj=historique.refaire();
					if(historique.getFutur().size()>0) {
						cpeek=historique.getFutur().peek();
					}
					historique.afficherFutur();
				}
				
//				cj = historique.refaire();
				}

//		
			
		}else {
			 cj = historique.refaire();//on redo 1 coup de l'ia

		}
		
		//Coup cpeek;//coup au sommet
		System.out.print("historique avant refaire:");
		historique.afficherFutur();
		// cpeek= historique.getPasse().peek();
		System.out.println("main :");
		 //c.main1.afficherMain();
			//historique.setAuPasse(true);
			this.changerjoueur = cj.isChangerjoueur();// doit on changer de joueur OK
			this.enCours = cj.isEnCours();// partie en cour OK
			this.finmanche = cj.isFinmanche();// a t'on fini la manche ?
			this.piochage = cj.isPiochage();// y'as t'il des cartes a piocher ?
			this.parManche = cj.isParManche();// la fin de partie et décidé par nombre de manche (false= on décide par score)
			this.mains[0] = new Hand(cj.getMain1());//pour creer pas par reference :    ^) OK
			this.mains[1] = new Hand(cj.getMain2()); // OK
			for(int i=0;i<piles.length;i++) {
				this.piles[i] = new Deck(cj.getPiles()[i]);//pb copie OK
			}
			
			this.totalfin = cj.getTotalfin();// score a obtenir ou nombre de manche a faire avant la fin de partie PK
			this.manche = cj.getManche();// le nombre de manche actuelle OK
			this.etape = (cj.getEtape()+1)%4;// etape actuelle d'un tour de jeu OK
			this.joueurdominant =cj.getJoueurdominant();// quel joueur à la main (premier a jouer/piocher) OK
			this.joueurdominantpred=cj.getJoueurdominantpred();//a prendre en compte que lors du changement de tour
			this.atout = cj.getAtout();// l'atout de la manche OK
			this.c_dom = cj.getC_dom();// carte jouer par le joueur dominant OK
			this.c_sub = cj.getC_sub();// carte jouer par l'autre joueur OK
			
			System.out.println();
			System.out.print("\nhistorique après annule: etape = "+etape+"\n");
			
			historique.affiherPasse();
			historique.afficherFutur();
			System.out.println("Joueur act: "+joueurActuelle());
			metAJour();
			
	}

	
	public void giveUp() {
		if (enCours) {
			changerjoueur=true;
			mains[0].videMain();
			mains[1].videMain();
			finmanche=true;
			etape=0;
			for (int i=0;i<6;i++) {// boucle sur les six piles
				piles[i].videPaquet();
			}
			if (IA && joueurActuelle()==1) {
				//si c'est une ia c'est l'autre joueur qui as abandonner
				mains[1].addScoreM(1);
				mains[0].resetPlis();
				mains[1].maxPlis();
				mains[1].addScoreP(26);
			}
			else {//sinon on concidère que c'est le joueur actuelle qui abandonne
				mains[(joueurActuelle()+1)%2].addScoreM(1);
				mains[(joueurActuelle()+1)%2].addScoreP(26);
				mains[joueurActuelle()].resetPlis();
				mains[(joueurActuelle()+1)%2].maxPlis();
			}
			if(parManche) {
				//si on compte par nombre de manche
				enCours=(manche<totalfin);//on vÃƒÂ©rifie si on fini la partie
			}
			else{
				enCours=(mains[0].getnbScoreP()<totalfin && mains[1].getnbScoreP()<totalfin);//on vÃƒÂ©rifie si on fini la partie
			}
			if (enCours) {
				//si la partie n'est pas fini on lance une nouvelle manche
				nouvelleManche();
			}
			joueurdominant=manche%2;
			manche++;
			metAJour();
			
		}
	}		
	
	public boolean isShowCarte() {
		return showCarte;
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
	
	private void trouve_atout() {
	//donne l'atout de la partie 
        int val_max=10;
	//initialisation de la carte la plus haute trouver un cran en desssou de la carte nÃƒÂ©cessaire pour avoir un atout dans la manche
        Couleur col=Couleur.Neutre;
	//on initialise sur une valeur neutre si on ne trouve pas d'atout cette valeure sera notre atout
        for (int i=0;i<6;i++) {
	//teste sur les six pile de pioche
            if (piles[i].topDeck().getValeur()>val_max) {
		//si on vient trouver une nouvelle plus haute carte 
                val_max=piles[i].topDeck().getValeur();
		    //on memorise le score de cette carte
                col=piles[i].topDeck().getCouleur();
		    //on memorise l'atout de notre carte
            }
            else if(piles[i].topDeck().getValeur()==val_max && piles[i].topDeck().getCouleur().getVal()>col.getVal()) {
		    //si on on trouve une carte de meme score on test si la couleure de cette nouvelle carte est plus fort
                col= piles[i].topDeck().getCouleur();
		    //on memorise le nouvelle atout
            }
        }
        atout=col;
       	//enregistre l'atout de la manche en cour
    }
	
	private int vainqueurManche() {
		//donne le numÃƒÂ©ro du joueure gagnant de la manche
		if (mains[0].getnbPlis()>mains[1].getnbPlis()) {
			//joeure 1 a gagner 
			return 0;
		}
		if (mains[0].getnbPlis()<mains[1].getnbPlis()) {
			//joueur 2 a gagner
			return 1;
		}
		return -1;
		//egaliter
	}
	
	public int vainqueurPartie() {
		//donne le gagnatnt d'une partie(de plusieure manche)
		if(parManche) {
			if (mains[0].getnbScoreM()>mains[1].getnbScoreM()) {
				//le joueur 1 a gagner la partie
				return 0;
			}
			if (mains[0].getnbScoreM()<mains[1].getnbScoreM()) {
				//le joueur 2 a gagner la partie
				return 1;
			}
		}
		else {
			if (mains[0].getnbScoreP()>mains[1].getnbScoreP()) {
				//le joueur 1 a gagner la partie
				return 0;
			}
			if (mains[0].getnbScoreP()<mains[1].getnbScoreP()) {
				//le joueur 2 a gagner la partie
				return 1;
			}
		}
		return -1;
		//egaliter
	}
	
	public void save(String s){
		FileOutputStream save;
		try {
			save = new FileOutputStream(new File(s));
	    BufferedOutputStream bsave = new BufferedOutputStream(save);
	    ObjectOutputStream osave = new ObjectOutputStream(save);
	   // DataOutputStream dsave = new DataOutputStream(save);
	    //bsave.write(1);
	    osave.writeBoolean(changerjoueur);// doit on changer de joueur
	    osave.writeBoolean(enCours);// partie en cour
	    osave.writeBoolean(finmanche);// a t'on fini la manche ?
	    osave.writeBoolean(piochage);// y'as t'il des cartes a piocher ?
	    osave.writeBoolean(parManche);// la fin de partie et décidé par nombre de manche (false= on décide par score)
		osave.writeObject(piles);// pile présente sur la table
		osave.writeObject(mains);//main des joueur
		osave.writeInt(totalfin);// score a obtenir ou nombre de manche a faire avant la fin de partie
		osave.writeInt(manche);// le nombre de manche actuelle
		osave.writeInt(etape);// etape actuelle d'un tour de jeu
		osave.writeInt(joueurdominant);// quel joueur à la main (premier a jouer/piocher)
		osave.writeObject(atout);// l'atout de la manche
		osave.writeObject(c_dom);// carte jouer par le joueur dominant 
		osave.writeObject(c_sub);// carte jouer par l'autre joueur
		osave.writeObject(c_0);//carte piochée par le joueur dominant
		osave.writeObject(c_1);//carte piochée par l'autre joueur
		osave.writeInt(ind);//sert a determiner quelle carte posé dans la pioche
		osave.writeBoolean(annulation);
		osave.writeObject(historique);
		
	    // donnÃ©e a sauvegarder
		osave.close();
		System.out.println("Partie sauvegardée");
		} catch (IOException e) {	
			System.err.println("Impossible de sauvegarder dans " + s);
			System.err.println(e.toString());
			System.exit(1);
		}
	}
	
	public void load(String s) throws ClassNotFoundException, IOException {
		 try {
		      FileInputStream save = new FileInputStream(new File(s));
		      BufferedInputStream bsave = new BufferedInputStream(save);
		      ObjectInputStream osave = new ObjectInputStream(save);
		     
		    changerjoueur =  osave.readBoolean();// doit on changer de joueur
		  	enCours =  osave.readBoolean();;// partie en cour
		  	finmanche = osave.readBoolean();;// a t'on fini la manche ?
		  	piochage =  osave.readBoolean();;// y'as t'il des cartes a piocher ?
		  	parManche =  osave.readBoolean();;// la fin de partie et décidé par nombre de manche (false= on décide par score)
		  	piles = (Deck[])osave.readObject();// pile présente sur la table
		  	mains = (Hand[]) osave.readObject();//main des joueur
		  	totalfin = osave.readInt();// score a obtenir ou nombre de manche a faire avant la fin de partie
		  	manche = osave.readInt();// le nombre de manche actuelle
		  	etape = osave.readInt();// etape actuelle d'un tour de jeu
		  	joueurdominant = osave.readInt();// quel joueur à la main (premier a jouer/piocher)
		  	atout = (Couleur)osave.readObject();// l'atout de la manche
		  	c_dom = (Carte) osave.readObject();// carte jouer par le joueur dominant 
		  	c_sub = (Carte) osave.readObject();// carte jouer par l'autre joueur
		  	c_0 = (Carte) osave.readObject();;//carte piochée par le joueur dominant
		  	c_1 = (Carte) osave.readObject();;//carte piochée par l'autre joueur
		  	ind = osave.readInt();;//sert a determiner quelle carte posé dans la pioche
		  	annulation = osave.readBoolean();
		  	historique = (Historique) osave.readObject();
		      osave.close();
		      metAJour();
				System.out.println("Partie chargée");

		  } catch (IOException e) {
		      e.printStackTrace();
		    }
	}
	
	public int niveauIA() {
		return diff;
	}
	
	public int etape() {
		//retourne l'etape actuelle d'un tour
		return etape;
	}	
	
	public int j_dom() {
		//renvoi le numero du joueure dominant
		return joueurdominant;
	}
	
	public void ch_joueur() {
		//indique qu'il y a u un changement de joueur
		changerjoueur=false;
	}
	
	public boolean new_dom() {
		//indique si il faut changer de joueur courant
		return changerjoueur;
	}

	public boolean enCours() {
		//dit si la partie est en cour
		return enCours;
	}
	
	public int getMancheactuelle() {// renvoie le numï¿½ro de la manche actuelle
		return manche;
	}
	
	public boolean MancheCours() {
		//dit si la manche est en cour
		return !finmanche;
	}
	
	public boolean pilesvide() {
		//teste si les 6 piles de pioche sont vide
		for (int i=0;i<6;i++) {
			if (!piles[i].estVide()) {
				//dÃ¯Â¿Â½s qu'une pile n'est pas vide on renvoi faux
				return false;
			}
		}
		return true;
	}
	
	public boolean peutPiocher(int i) {
		//verifie que la pile n'est pas vide
		return !(piles[i].estVide());
	}
	
	public boolean peutJouer(int k ,int j) {//k=indice de la carte j=numero du joueur
		//dit si la carte peut etre jouer
		if (j==joueurdominant) {
			//si on est le premier a poser on peut jouer nimporte quelle carte
			return true;
		}
		else {
			//sinon il faut jouer la meme couleure si on peut
			Carte c=mains[j].voirCarte(k);
			if (c_dom.getCouleur()==c.getCouleur()) {
				//on joue la meme couleure
				return true;
			}
			else {
				//on joue une couleure diffÃƒÂ©rente
				for (int i=0;i<mains[j].getnbCarte();i++) {
					//on regarde si il existe une carte de meme couleure dans la main que celle qui a ete poser
					if (mains[j].voirCarte(i).getCouleur()==c_dom.getCouleur()) {
						//on a trouver une carte de meme couleure que celle qui a ete poser
						return false;
					}
				}
				//aucune carte de meme couleure que celle qui a ete poser n'a ete trouver
				return true;
			}
		}
		
	}

		public Couleur getAtout() {// permet de renvoyer l'atout
			return atout;
		}
	
	
        public Hand[] getMains() {// permet de renvoyer mains (les donnÃ© des joueurs)
            return mains;
        }

        public Deck[] getPiles() {// permet de renvoyer les piles
            return piles;
        }
        
        public int joueurActuelle() {// permet de renvoyer le joueur courant
        	
        if (etape%2==0) {
            return joueurdominant;
        }
        return (joueurdominant + 1) % 2;
        }

        public Carte getC_dom() {// permet de renvoyer la carte du joueur dominant
            return c_dom;
        }

        public Carte getC_sub() {// permet de renvoyer la carte du joueur non sdominant
            return c_sub;
        }
        
        public void activeIA() {//l'ia est active
        	IA=true;
        }
        
        public void desactiveIA() {// l'ia n'est pas active
        	IA=false;
        }
        
        public boolean getIA() {// renvoie si l'ia est active
        	return IA;
        }
        
        public boolean getShowCarte() {
        	return this.showCarte;
        }
        public int getSelCarte() {
        	return selCarte;
        }
        public int getSelFond() {
        	return selFond;
        }
        
        public int getScore(int j) {
        	if(parManche) {
        		return mains[j].getnbScoreM();
        	}
        	else {
        		return mains[j].getnbScoreP();
        	}
        }

        public int getCarteApiocher() {
            return carteApiocher;
        }
        public void readConfig() {
        	try {
    			BufferedReader br = new BufferedReader(new FileReader("config"));
    			diff = Integer.parseInt(br.readLine());
    			if (diff < 0 || diff > 1) { // a changer pour diff > 2
    				diff = 0;
    			}
    			if(Integer.parseInt(br.readLine())==1) {
    				parManche=true;
    			}
    			else parManche=false;
    			
    			totalfin = Integer.parseInt(br.readLine());
    			if (totalfin<=0) {
    				if (parManche)totalfin=12;
    				else totalfin=100;
    			}
    			
    			if (Integer.parseInt(br.readLine())==0) {
    				showCarte=true;
    			}else {
    				showCarte=false;
    			}
    			
    			
    			
    			selCarte = Integer.parseInt(br.readLine());
    			if (selCarte < 0 || selCarte > 2) {
    				selCarte = 0;
    			}
    			selFond = Integer.parseInt(br.readLine());
    			if (selFond < 0 || selFond > 2) {
    				selFond = 0;
    			}
    			br.close();
    		} catch (IOException e) {// si le fichier n'est pas trouvÃ© on en crÃ©e un avec les configurations de base
    			try {
    				File confile = new File("config");
    				if (confile.createNewFile()) {
    					System.out.println("Création d'un fichier config.");
    					FileWriter myWriter = new FileWriter("config");
    					myWriter.write("0\n0\n100\n0\n0\n0\n");
    					myWriter.close();
    					diff = 0;
    					parManche = false;
    					totalfin = 100;
    					showCarte=true;
    					selCarte = 0;
    					selFond = 0;
    				}
    			} catch (IOException e1) {// la crÃ©ation du fichier a echouÃ©
    				System.out.println("impossible de crÃ©er un fichier.");
    			}
    		}
        	metAJour();
        }
        
        
}


