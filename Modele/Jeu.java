package Modele;


import Patterns.Observable;
import java.util.Stack;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;


public class Jeu extends Observable {
	boolean changerjoueur;// doit on changer de joueur
	boolean enCours;// partie en cour
	boolean finmanche;// a t'on fini la manche ?
	boolean piochage;// y'as t'il des cartes a piocher ?
	boolean parManche;// la fin de partie et d�cid� par nombre de manche (false= on d�cide par score)
	Deck [] piles;// pile pr�sente sur la table
	Hand [] mains;//main des joueur
	int totalfin;// sore a obtenir ou nombre de manche a faire avant la fin de partie
	int manche;// le nombre de manche actuelle
	int etape;// etape actuelle d'un tour de jeu
	int joueurdominant;// quel joueur � la main (premier a jouer/piocher)
	Couleur atout;// l'atout de la manche
	Carte c_dom;// carte jouer par le joueur dominant 
	Carte c_sub;// carte jouer par l'autre joueur
	

	public Jeu() {
		enCours = true;
		changerjoueur=false;
		joueurdominant=0;
		piles = new Deck[6];// creation d'un tableau de piles pour les six paquets sur la table
		mains=new Hand[2];
		mains[0]=new Hand();
		mains[1]=new Hand();
		nouvelleManche();
	}

	public void nouvelleManche() {
		finmanche=false;
		piochage= true;
		etape=0;
		mains[0].resetPlis();
		mains[1].resetPlis();
		Deck paquet = new Deck(); // creation d'un paquet de carte (deja melanger)
		Stack<Carte> p =new Stack<Carte>();// variable temporaire
		for (int i=0;i<11;i++) { // remplissage des mains des joueurs
			mains[0].ajoutCarte(paquet.piocher());
			mains[1].ajoutCarte(paquet.piocher());
		}
		for (int i=0;i<6;i++) {// boucle sur les six piles
			for (int j=0;j<5;j++) {// boucle pour piocher les 5 cartes
				p.push(paquet.piocher());//pioche
			}
			piles[i]=new Deck(p);// enregistrement de la pile dans le tableau
			p.clear();//reinitialisation de la pile temporaire
		}
		trouve_atout();
	}
	
	public void jouer(int i,int n) {
		if (enCours) {
			if (!finmanche) {
				// tour de jeu
				switch(etape) {
				case 0:
				// joueurdominant pose une carte
					c_dom=mains[n].poserCarte(i);
					metAJour();
					etape++;
					break;
				case 1:
					// le second joueur pose une carte en cons�quences (limiter par raport a la cartes)
					c_sub=mains[n].poserCarte(i);
					metAJour();
					// calcul de qui remporte le plis
					int j;
					j=carte_gagnante();
					if (j==2) {// celui qui gagne devien joueur dominant
						joueurdominant = (joueurdominant + 1) % 2;
						changerjoueur=true;
					}
					mains[joueurdominant].addPlis();// incr�mente le nombre de plis du vainqueur
					metAJour();
					etape++;
					break;
				case 2:
					if (piochage) {//test s'il reste des carte a piocher
						//s'il reste des cartes a piocher le joueur dominant pioche
						mains[n].ajoutCarte(piles[i].piocher());
						metAJour();
						etape++;
					}
					else {
						etape=0;
					}
					break;
				case 3:
					
					mains[n].ajoutCarte(piles[i].piocher());
					metAJour();
					piochage=!pilesvide();
					etape=0;
					break;
				}
				if (mains[0].getnbCarte()==0 && mains[1].getnbCarte()==0) {
					finmanche=true;
					if(parManche) {
						enCours=(manche!=totalfin);
						int j=vainqueurManche();
						if (j!=-1) {
							mains[j].addScore(1);
						}
					}
					else{
						mains[0].addScore(mains[0].getnbPlis());
						mains[1].addScore(mains[1].getnbPlis());
						enCours=(mains[0].getnbScore()<totalfin && mains[1].getnbScore()<totalfin);
					}
					if (enCours) {
						nouvelleManche();
						manche++;
					}
					
				}		
			}	
		}
			
	}
	

	
	public int carte_gagnante() {
        //gagnant donne le num�ros du joueure gagnant
        int gagnant=-1;
            if (c_dom.getCouleur()==c_sub.getCouleur()){
                //si les deux carte sont de m�me couleure la plus forte l'emporte
                if(c_dom.getValeur()>c_sub.getValeur()){
                    gagnant=1;
                }
                else {
                    gagnant=2;
                }
            }
            else {
                if (c_dom.getCouleur()==atout) {
                    //le premier joueur gagne si il y a 1 atout ,les deux joueure on une couleure diff�rente
                    gagnant=1;
                }
                else {
                    //le premier joueure n'a pas d'atout et les deux joueure on une couleure diff�rente
                    if (c_sub.getCouleur()==atout) {
                        gagnant=2;
                    }
                    else {
                        gagnant=1;
                    }
                }
            }
            return gagnant;


        }
	
	private void trouve_atout() {
        int val_max=9;
        Couleur col=Couleur.Neutre;
        for (int i=0;i<6;i++) {
            if (piles[i].topDeck().getValeur()>val_max) {
                val_max=piles[i].topDeck().getValeur();
                col=piles[i].topDeck().getCouleur();
            }
            else if(piles[i].topDeck().getValeur()==val_max && piles[i].topDeck().getCouleur().getVal()>col.getVal()) {
                col= piles[i].topDeck().getCouleur();
            }
        }
        atout=col;
       
    }
	
	private int vainqueurManche() {
		if (mains[0].getnbPlis()>mains[1].getnbPlis()) {
			return 0;
		}
		if (mains[0].getnbPlis()<mains[1].getnbPlis()) {
			return 1;
		}
		return -1;
	}
	
	public int vainqueurPartie() {
		if (mains[0].getnbScore()>mains[1].getnbScore()) {
			return 0;
		}
		if (mains[0].getnbScore()<mains[1].getnbScore()) {
			return 1;
		}
		return -1;
	}
	
	public void save(String s){
		FileOutputStream save;
		try {
			save = new FileOutputStream(new File(s));
	    BufferedOutputStream bsave = new BufferedOutputStream(save);
	    //bsave.write(1);
	    // donnée a sauvegarder
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
		      // donnée a lire
		      bsave.close();
		  } catch (IOException e) {
		      e.printStackTrace();
		    }
	}
	
	public int etape() {
		return etape;
	}	
	
	public int j_dom() {
		return joueurdominant;
	}
	
	public void ch_joueur() {
		changerjoueur=false;
	}
	
	public boolean new_dom() {
		return changerjoueur;
	}

	public boolean enCours() {
		return enCours;
	}
	
	private boolean pilesvide() {
		boolean temp = true;
		for (int i=0;i<6;i++) {
			if (piles[i].estVide()) {
				temp=false;
			}
		}
		return temp;
	}
	
	public boolean peutPiocher(int i) {//verifie que la pile n'est pas vide
		return !(piles[i].estVide());
	}
	
	public boolean peutJouer(int k ,int j) {
		if (j==joueurdominant) {
			return true;
		}
		else {
			Carte c=mains[j].voirCarte(k);
			if (c_dom.getCouleur()==c.getCouleur()) {
				return true;
			}
			else {
				for (int i=0;i<mains[j].getnbCarte();i++) {
					if (mains[j].voirCarte(i).getCouleur()==c.getCouleur()) {
						return false;
					}
				}
				return true;
			}
		}
		
	}
}
