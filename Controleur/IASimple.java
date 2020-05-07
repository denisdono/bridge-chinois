package Controleur;

import Modele.Couleur;
import Modele.Deck;
import Modele.Jeu;
import Modele.Carte;


 public class IASimple extends Joueur {
	 int inutile;
	 
	 IASimple(int n,Jeu p){
		super(n,p);
		inutile=-1;
	 }

	
	
	
	
	public boolean IAjeu() {
		boolean rep;
		switch (jeu.etape() ){
		case 0:
			//si on pose en premier 
			//on va poser notre carte la plus proche de la moyenne des valeur de carte de notre main 
			//en ne posant des carte atout que si on a pas le choix
			rep=poser_premier_simple();
			break;
		case 1:
			//si on pose en deuxieme
			rep=pose_second_simple();
			//on va prendre soit la plus faible carte posable si on ne peut pas gagner 
			//soit poser la plus faible carte gagnante que l'on a
			break;
		case 2:
		case 3:
			rep=piocher();
			//nous allons piocher la plus puissante carte atout que nous trouverons 
			//si il n'y a pas de carte atout on va piocher la plus forte carte toute couleur confondue 
			//trouver
			break;
		default :
			rep=false;
				break;
		}
	return rep;
	}
	
	boolean poser_premier_simple() {
		int indice =-1;//l'indice de la carte qu'on va jouer
		int somme=0;//somme de toutes les valeurs de carte qu'on a en main
		boolean uniq_atout=true;//pour savoir si on a que des atout en main
		int moyenne;//la valeur moyenne de notre main
		int dif;//la difference entre une carte et la moyenne
		int meme_dif=-1;//pour se rappeler de la différence de la carte choisi a la moyenne
		int k=0;
		int div=0;//le nombre de carte qui sera utiliser pour la moyenne des carte de la main
		while (k<jeu.getMains()[num].getnbCarte() && uniq_atout) {
			if(jeu.getMains()[num].voirCarte(k).getCouleur()!=jeu.getAtout()) {
				uniq_atout=false;
			}
			k++;
		}
		for (int i=0;i<jeu.getMains()[num].getnbCarte();i++) {
			if (uniq_atout) {
				//toutes les cartes sont des atout on les prend toutes pour fair la moyenne de la main
				somme=jeu.getMains()[num].voirCarte(i).getValeur()+somme;
				div++;
			}
			else if(jeu.getMains()[num].voirCarte(i).getCouleur()!=jeu.getAtout()) {
				//on a pas que des carte atout en main on ne prend pas en compte 
				//les carte qui sont des atout por fair la moyenne
				somme=jeu.getMains()[num].voirCarte(i).getValeur()+somme;
				div++;
			}
		}
		moyenne=somme/div;
		for (int i=0;i<jeu.getMains()[num].getnbCarte();i++) {
			if(indice==-1) {
				//on pas encore choisi de carte a poser
				if (uniq_atout || jeu.getMains()[num].voirCarte(i).getCouleur()!=jeu.getAtout()) {
					//on a que des atout en main on ne différenci les carte que par leur valeur
					//ou on a une carte qui n'est pas un atout et la choisi
					dif=jeu.getMains()[num].voirCarte(i).getValeur()-moyenne;
					meme_dif=dif;
					indice=i;
				}
			}
			else {
				//on a deja selectionner une carte
				dif=jeu.getMains()[num].voirCarte(i).getValeur()-moyenne;
				if (dif>0 && dif<meme_dif) {
					//notre carte est plus proche de la moyenne de la main que la precedente carte choisi
					if(uniq_atout || jeu.getMains()[num].voirCarte(i).getCouleur()!=jeu.getAtout()) {
						//on a que des atout en main on ne différenci les carte que par leur valeur
						//ou on a une carte qui n'est pas un atout et la choisi
						meme_dif=dif;
						indice=i;
					}
				}
			}
		}
		jeu.jouer(indice, num);
		return true;
		}
	
	public boolean pose_second_simple() {
		int indice=0;//la position de notre carte a jouer
		Couleur col=jeu.getMains()[num].voirCarte(0).getCouleur();//la couleur de la carte a jouer
		int val=jeu.getMains()[num].voirCarte(0).getValeur();//la valeur de notre carte a jouer
		boolean gagner=c_gagnante(jeu.getC_dom(),jeu.getMains()[num].voirCarte(0))==2;
		boolean jouable=jeu.peutJouer(0, num);//sert a savoir si a deja trouver une carte jouable
		for (int i=1;i<jeu.getMains()[num].getnbCarte();i++) {//pour toutes les cartes de la main 
			if (jeu.peutJouer(i, num)) {
				//si la carte est jouable
				if(!jouable) {
				// on vient de trouver la premiere carte jouable
					val=jeu.getMains()[num].voirCarte(i).getValeur();
					col=jeu.getMains()[num].voirCarte(i).getCouleur();
					indice=i;
					gagner=c_gagnante(jeu.getC_dom(),jeu.getMains()[num].voirCarte(i))==2;
					jouable=true;
				}
				else {
					//on avait deja une carte jouable
					if(c_gagnante(jeu.getC_dom(),jeu.getMains()[num].voirCarte(i))==1) {
						//notre carte a tester est perdante
						if(!gagner) {
							//si on a pas encors de carte gagnante
							if(val>=jeu.getMains()[num].voirCarte(i).getValeur()) {
								//si la nouvelle carte a tester estplus faible que notre carte a jouer
								if(col==jeu.getAtout()) {
									//si notre carte a jouer actuellement est un atout on 
									//choisi une carte plus faible quel que soit sa couleure
									val=jeu.getMains()[num].voirCarte(i).getValeur();
									col=jeu.getMains()[num].voirCarte(i).getCouleur();
									indice=i;
								}
								else if (jeu.getMains()[num].voirCarte(i).getCouleur()!=jeu.getAtout()) {
									//si la carte a jouer actuelle n'est pas un atout 
									//on prend une carte qui n'est pas un atout plus faible
									val=jeu.getMains()[num].voirCarte(i).getValeur();
									col=jeu.getMains()[num].voirCarte(i).getCouleur();
									indice=i;
								}
							}
						}
					}
					else {
					//on a une carte gagnante
						if (!gagner) {
							//c'est notre tout première carte gagnante 
							//donc c'est la carte quel'on va jouer
							gagner=true;
							val=jeu.getMains()[num].voirCarte(i).getValeur();
							col=jeu.getMains()[num].voirCarte(i).getCouleur();
							indice=i;
						}
						else {
						//on va changer de carte gagnante que si la carte est gagnante
						//on change de carte a jouer que pour une carte plus faible
							if(val>jeu.getMains()[num].voirCarte(i).getValeur()){
								val=jeu.getMains()[num].voirCarte(i).getValeur();
								col=jeu.getMains()[num].voirCarte(i).getCouleur();
								indice=i;
							}
						}
					}
				}
			}
		}
	jeu.jouer(indice,num );
	return true;
	}
	
	
	public boolean piocher() {
		int indice=-1;//indice de quelle pioche poser
		Couleur col=Couleur.Neutre;//la couleur de notre carte a piocher
		int val=-1;//la valeur de notre carte a piocher
		boolean piocher=false;//on a deja une carte piochable
		boolean est_atout=false;
		Deck[] pile=jeu.getPiles();//recupere tout les piles
		for(int i=0;i<6;i++) {
			if (jeu.peutPiocher(i)) {
				if(!piocher) {
				//on pas encor de carte a piocher on prend la premiere disponible
					indice=i;
					col=pile[i].topDeck().getCouleur();
					val=pile[i].topDeck().getValeur();
					est_atout=col==jeu.getAtout();
					piocher=true;
				}
				else {
				//on a deja une carte piocher on va essayer de trouver une meilleur carte a piocher
					if(val<pile[i].topDeck().getValeur()) {
						//la carte que l'on regarde est plus forte que la carte que l'on a choisi de piocher
						if(pile[i].topDeck().getCouleur()==jeu.getAtout()) {
							//la carte a piocher que l'on regarde est un atout plus forte 
							//que la notre choix actuelle de carte a piocher
							indice=i;
							col=pile[i].topDeck().getCouleur();
							val=pile[i].topDeck().getValeur();
							est_atout=true;
						}
						else if(!est_atout) {
							//la carte que l'on regarde n'est pas un atout mais elle est plus forte
							//que la carte qu'on choisi de piocher qui n'est pas un atout
							indice=i;
							col=pile[i].topDeck().getCouleur();
							val=pile[i].topDeck().getValeur();
						}
					}
					else if(val==pile[i].topDeck().getValeur()) {
						//la carte que l'on regarde est de la meme valeure que celle qu'on a choisi de piocher
						if (pile[i].topDeck().getCouleur()==jeu.getAtout()) {
							//la carte que l'on regarde est un atout
							//on ne peut donc pas avoir d'atout on la prend
							indice=i;
							col=pile[i].topDeck().getCouleur();
							val=pile[i].topDeck().getValeur();
							est_atout=true;
						}
					}
					else if(val>pile[i].topDeck().getValeur() && !est_atout && pile[i].topDeck().getCouleur()==jeu.getAtout()) {
						//on a choisi une carte plus forte que que celle qu'on regarde mais 
						//elle n'est pas un atout contrairement a celle que l'on regarde qui est un atout
						indice=i;
						col=pile[i].topDeck().getCouleur();
						val=pile[i].topDeck().getValeur();
						est_atout=true;
					}
				}
			}
		}
	jeu.jouer(indice, num);
	return true;
	}
	
	
	public int c_gagnante(Carte c1,Carte c2) {
        //gagnant donne le numÃ©ros du joueure gagnant
        int gagnant=-1;
            if (c1.getCouleur()==c2.getCouleur()){
                //si les deux carte sont de mÃªme couleure la plus forte l'emporte
                if(c1.getValeur()>c2.getValeur()){
                    gagnant=1;
                }
                else {
                    gagnant=2;
                }
            }
            else {
                    //le premier joueure n'a pas d'atout et les deux joueure on une couleure diffÃ©rente
                    if (c2.getCouleur()==jeu.getAtout()) {
                        gagnant=2;
                    }
                    else {
                        gagnant=1;
                    }
            }
            return gagnant;


        }
}
