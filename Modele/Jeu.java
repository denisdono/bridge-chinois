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
	Couleur atout;// l'atout de la manche
	Carte c_dom;// carte jouer par le joueur dominant 
	Carte c_sub;// carte jouer par l'autre joueur
	int diff;// dificulter de l'ia
	boolean showCarte;// carte visible
	boolean IA;// prï¿½sence d'une IA
	

	public Jeu() {
		IA=false;
		start();
	}
		
	public void start() {
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
			
			
//			selCarte = Integer.parseInt(br.readLine());
//			if (selCarte < 0 || selCarte > 2) {
//				selCarte = 0;
//			}
//			selFond = Integer.parseInt(br.readLine());
//			if (selFond < 0 || selFond > 2) {
//				selFond = 0;
//			}
			br.close();
		} catch (IOException e) {// si le fichier n'est pas trouvÃ© on en crÃ©e un avec les configurations de base
			try {
				File confile = new File("config");
				if (confile.createNewFile()) {
					System.out.println("CrÃ©ation d'un fichier config.");
					FileWriter myWriter = new FileWriter("config");
					myWriter.write("0\n0\n100\n0\n0\n0\n");
					myWriter.close();
					diff = 0;
					parManche = false;
					totalfin = 100;
					showCarte=true;
//					selCarte = 0;
//					selFond = 0;
				}
			} catch (IOException e1) {// la crÃ©ation du fichier a echouÃ©
				System.out.println("impossible de crÃ©er un fichier.");
			}
		}
//		parManche=false;//////// condition par dÃ©fault
//		totalfin=100;///////////
		enCours = true;
		changerjoueur=false;
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
	
	public void nouvelleManche() {
		finmanche=false;
		piochage= true;
		etape=0;
		mains[0].resetPlis();
		mains[1].resetPlis();
		Deck paquet = new Deck(); // creation d'un paquet de carte (deja melanger)
		paquet.remplirPaquet();
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
	}
	
	public void jouer(int i,int n) {
		if (enCours) {
			//tant que la condition de victoire par nombre de plis gagner ou de mancher gagner n'est pas atteinte
			if (!finmanche) {
				// tour de jeu
				switch(etape) {
				case 0:
				// joueurdominant pose une carte
					c_dom=mains[n].poserCarte(i);
					etape++;
					metAJour();
					break;
				case 1:
					// le second joueur pose une carte en consÃƒÂ©quences (limiter par raport a la cartes)
					c_sub=mains[n].poserCarte(i);
					metAJour();
					// calcul de qui remporte le plis
					int j;
					jcarte_gagnante(c_dom,c_sub);
					if (j==2) {// celui qui gagne devien joueur dominant
						joueurdominant = (joueurdominant + 1) % 2;
						changerjoueur=true;
					}
					mains[joueurdominant].addPlis();// incrÃƒÂ©mente le nombre de plis du vainqueur
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
						mains[n].ajoutCarte(piles[i].piocher());
						mains[n].trierMain();
						etape++;
						metAJour();
					
					break;
				case 3:
					//le deuxiÃƒÂ¨me joueure pioche
					mains[n].ajoutCarte(piles[i].piocher());
					mains[n].trierMain();
					piochage=!pilesvide();//teste si il reste des carte a piocher
					etape=0;//fini un tour de jeu
                                        metAJour();
					break;
				}
				if (mains[0].getnbCarte()==0 && mains[1].getnbCarte()==0) {
					//teste si la manche est fini
					finmanche=true;
					if(parManche) {
						//si on compte par nombre de manche
						enCours=(manche!=totalfin);//on vÃƒÂ©rifie si on fini la partie
						int j=vainqueurManche();
						if (j!=-1) {
							//si il n'y a pas ÃƒÂ©galiter on incrÃƒÂ©mente le score du vaiqueur
							mains[j].addScore(1);
						}
					}
					else{
						//sinon est par nombre de plis gagner
						//on ajoute le nombre de plis gagner par chaque jooueur
						mains[0].addScore(mains[0].getnbPlis());
						mains[1].addScore(mains[1].getnbPlis());
						enCours=(mains[0].getnbScore()<totalfin && mains[1].getnbScore()<totalfin);//on vÃƒÂ©rifie si on fini la partie
					}
					if (enCours) {
						//si la partie n'est pas fini on lance une nouvelle manche
						nouvelleManche();
						manche++;
					}
					metAJour();
				}		
			}	
		}
			
	}
	
	public void giveUp() {
		finmanche=true;
		changerjoueur=true;
		mains[0].videMain();
		mains[1].videMain();
		for (int i=0;i<6;i++) {// boucle sur les six piles
			piles[i].videPaquet();
		}
		if(parManche) {
			//si on compte par nombre de manche
			enCours=(manche!=totalfin);//on vÃƒÂ©rifie si on fini la partie
			if (IA && joueurActuelle()==1) {
				//si c'est une ia c'est l'autre joueur qui as abandonner
				mains[1].addScore(1);
				joueurdominant=1;
			}
			else {//sinon on concidère que c'est le joueur actuelle qui abandonne
				mains[(joueurActuelle()+1)%2].addScore(1);
				joueurdominant=(joueurActuelle()+1)%2;
			}
		}
		else{
			if (IA && joueurActuelle()==1) {
				//si c'est une ia c'est l'autre joueur qui as abandonner
				mains[1].addScore(26);
				joueurdominant=1;
			}
			else {//sinon on concidère que c'est le joueur actuelle qui abandonne
				mains[(joueurActuelle()+1)%2].addScore(26);
				joueurdominant=(joueurActuelle()+1)%2;
			}
			enCours=(mains[0].getnbScore()<totalfin && mains[1].getnbScore()<totalfin);//on vÃƒÂ©rifie si on fini la partie
		}
		if (enCours) {
			//si la partie n'est pas fini on lance une nouvelle manche
			nouvelleManche();
			manche++;
		}
		metAJour();
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
		if (mains[0].getnbScore()>mains[1].getnbScore()) {
			//le joueur 1 a gagner la partie
			return 0;
		}
		if (mains[0].getnbScore()<mains[1].getnbScore()) {
			//le joueur 2 a gagner la partie
			return 1;
		}
		return -1;
		//egaliter
	}
	
	public void save(String s){
		FileOutputStream save;
		try {
			save = new FileOutputStream(new File(s));
	    BufferedOutputStream bsave = new BufferedOutputStream(save);
	    //bsave.write(1);
	    // donnÃƒÂƒÃ‚Â©e a sauvegarder
		bsave.close();
		} catch (IOException e) {	
			System.err.println("Impossible de sauvegarder dans " + s);
			System.err.println(e.toString());
			System.exit(1);
		}
	}
	
	public void load(String s) {
		 try {
		      FileInputStream save = new FileInputStream(new File(s));
		      BufferedInputStream bsave = new BufferedInputStream(save);
		      //bsave.read();
		      // donnÃƒÂƒÃ‚Â©e a lire
		      bsave.close();
		  } catch (IOException e) {
		      e.printStackTrace();
		    }
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
	
	private boolean pilesvide() {
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
        
}
