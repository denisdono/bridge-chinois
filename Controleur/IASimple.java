package Controleur;

import Modele.Couleur;
import Modele.Deck;
import Modele.Jeu;
import Modele.Carte;


 public class IASimple extends Joueur {
	 int valCar;//notre plus puissante carte de carreaux
	 int valCoeur;//notre plus puissante carte de coeur
	 int valPiq;//notre plus puissante carte de pique
	 int valTre;//notre plus puissante carte de trefle
	 Couleur maxQ;//la couleur dont on a le plus de carte
	 
	 IASimple(int n,Jeu p){
		super(n,p);
		valCar=0;
		valCoeur=0;
		valPiq=0;
		valTre=0;
	 }

	
	
	
	
	public int IAjeu() {
		int rep;
		switch (jeu.etape() ){
		case 0:
			//si on pose en premier 
			//on va poser notre carte la plus proche de la moyenne des valeur de carte de notre main 
			//en ne posant des carte atout que si on a pas le choix
			if(jeu.getAtout()!=Couleur.Neutre) {
				rep=poser_premier_simple();
			}
			else {
				rep=poser_premier_simpleSansAtout();
			}
			break;
		case 1:
			//si on pose en deuxieme
			rep=pose_second_simple();
			//on va prendre soit la plus faible carte posable si on ne peut pas gagner 
			//soit poser la plus faible carte gagnante que l'on a
			break;
		case 2:
		case 3:
			if(jeu.getAtout()!=Couleur.Neutre) {
				rep=piocher();
			}
			else {
				rep=piocherSansAtout();
			}
			//nous allons piocher la plus puissante carte atout que nous trouverons 
			//si il n'y a pas de carte atout on va piocher la plus forte carte toute couleur confondue 
			//trouver
			break;
			default :rep=-1;//ne seras jamais atteint
			break;
		}
		return rep;
	}
	
	int poser_premier_simple() {
		int indice =-1;//l'indice de la carte qu'on va jouer
		int somme=0;//somme de toutes les valeurs de carte qu'on a en main
		boolean uniq_atout=true;//pour savoir si on a que des atout en main
		int moyenne;//la valeur moyenne de notre main
		int dif;//la difference entre une carte et la moyenne
		int meme_dif=-1;//pour se rappeler de la diff�rence de la carte choisi a la moyenne
		int k=0;
		int div=0;//le nombre de carte qui sera utiliser pour la moyenne des carte de la main
		while (k<jeu.getMains()[num].getnbCarte() && uniq_atout) {
			//regarde si on a uniquement des carte atout
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
					//on a que des atout en main on ne diff�renci les carte que par leur valeur
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
						//on a que des atout en main on ne diff�renci les carte que par leur valeur
						//ou on a une carte qui n'est pas un atout et la choisi
						meme_dif=dif;
						indice=i;
					}
				}
			}
		}
		return indice;
		}
	
	public int pose_second_simple() {
		int indice=0;//la position de notre carte a jouer
		Couleur col=jeu.getMains()[num].voirCarte(0).getCouleur();//la couleur de la carte a jouer
		int val=jeu.getMains()[num].voirCarte(0).getValeur();//la valeur de notre carte a jouer
		boolean gagner=jeu.carte_gagnante(jeu.getC_dom(),jeu.getMains()[num].voirCarte(0))==2;
		boolean jouable=jeu.peutJouer(0, num);//sert a savoir si a deja trouver une carte jouable
		for (int i=1;i<jeu.getMains()[num].getnbCarte();i++) {//pour toutes les cartes de la main 
			if (jeu.peutJouer(i, num)) {
				//si la carte est jouable
				if(!jouable) {
				// on vient de trouver la premiere carte jouable
					val=jeu.getMains()[num].voirCarte(i).getValeur();
					col=jeu.getMains()[num].voirCarte(i).getCouleur();
					indice=i;
					gagner=jeu.carte_gagnante(jeu.getC_dom(),jeu.getMains()[num].voirCarte(i))==2;
					jouable=true;
				}
				else {
					//on avait deja une carte jouable
					if(jeu.carte_gagnante(jeu.getC_dom(),jeu.getMains()[num].voirCarte(i))==1) {
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
							//c'est notre tout premi�re carte gagnante 
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
	return indice;
	}
	
	
	public int piocher() {
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
	return indice;
	}
	
	

	
	int poser_premier_simpleSansAtout() {
		ModifVal();
		int indice =-1;//l'indice de la carte qu'on va jouer
		int somme=0;//somme de toutes les valeurs de carte qu'on a en main
		int moyenne;//la valeur moyenne de notre main
		int dif;//la difference entre une carte et la moyenne
		int meme_dif=-1;//pour se rappeler de la diff�rence de la carte choisi a la moyenne

		int div=0;//le nombre de carte qui sera utiliser pour la moyenne des carte de la main
		for (int i=0;i<jeu.getMains()[num].getnbCarte();i++) {
			if (maxQ==jeu.getMains()[num].voirCarte(i).getCouleur()) {
				//on fait la moyenne des carte de la couleur qu'on a en plus grande quantiter
				somme=jeu.getMains()[num].voirCarte(i).getValeur()+somme;
				div++;
			}
		}
		moyenne=somme/div;
		for (int i=0;i<jeu.getMains()[num].getnbCarte();i++) {
			if(indice==-1) {
				//on pas encore choisi de carte a poser
				if (jeu.getMains()[num].voirCarte(i).getCouleur()==maxQ) {
					//on prens la premi�re cate qui est de la couleur que l'on veut poser
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
					if(jeu.getMains()[num].voirCarte(i).getCouleur()==maxQ) {
						//on trouve une carte de la couleur choisi plus adapter
						meme_dif=dif;
						indice=i;
					}
				}
			}
		}
		return indice;
		}
		
	
	public int piocherSansAtout() {
		ModifVal();
		int indice=-1;//indice de quelle pioche poser
		int val=-1;//la valeur de notre carte a piocher
		boolean piocher=false;//on a deja une carte piochable
		Deck[] pile=jeu.getPiles();//recupere tout les piles
		boolean colbloquer=false;//si il nous manque une couleur 
		//et qu'on a croiser une carte asser puissante de catte couleur 
		//on ne peut plus piocher de carte de couleur non prioritaire
		for(int i=0;i<6;i++) {
			if (jeu.peutPiocher(i)) {
				if(!piocher) {
				//on pas encor de carte a piocher on prend la premiere disponible
					indice=i;
					val=pile[i].topDeck().getValeur();
					piocher=true;
					if((valTre==0 && pile[i].topDeck().getCouleur()==Couleur.Trefle) 
							||(valCar==0 && pile[i].topDeck().getCouleur()==Couleur.Carreaux) 
							||(valCoeur==0 && pile[i].topDeck().getCouleur()==Couleur.Coeur) 
							|| ((valPiq==0 && pile[i].topDeck().getCouleur()==Couleur.Pique))) {
						colbloquer=true;
					}
				}
				else {
				//on a deja une carte piocher on va essayer de trouver une meilleur carte a piocher
					if (((valTre==0 && pile[i].topDeck().getCouleur()==Couleur.Trefle) 
							||(valCar==0 && pile[i].topDeck().getCouleur()==Couleur.Carreaux) 
							||(valCoeur==0 && pile[i].topDeck().getCouleur()==Couleur.Coeur) 
							|| ((valPiq==0 && pile[i].topDeck().getCouleur()==Couleur.Pique))) 
							&& pile[i].topDeck().getValeur()>7){
						//nous avons pas de carte d'une des quatre couleur et 
						//la carte que nous regardon est de catte couleur 
						if(!colbloquer){
							// on a pas encor choisi de carte a couleur prioritaire
							indice=i;
							val=pile[i].topDeck().getValeur();
							colbloquer=true;
							
						}
						else if(val<pile[i].topDeck().getValeur()) {
							//on prend la carte de couleur prioritaire que si elle est 
							//plus forte que la carte choisi precedament
							indice=i;
							val=pile[i].topDeck().getValeur();
						}
						
					}
					else if(val<pile[i].topDeck().getValeur() && !colbloquer) {
						//la carte que l'on regarde est plus forte que la carte que l'on a choisi de piocher							//la carte a piocher que l'on regarde est un atout plus forte 
						//que la notre choix actuelle de carte a piocher
							indice=i;
							val=pile[i].topDeck().getValeur();
					}
				}
			}
		}
	return indice;
	}
	
	void ModifVal(){
		int nbCar=0;
		int nbCoeur=0;
		int nbPiq=0;
		int nbTre=0;
		//met a jour la plus puissante carte de chaque couleure
		for (int i=0;i<jeu.getMains()[num].getnbCarte();i++) {
			if(jeu.getMains()[num].voirCarte(i).getCouleur()==Couleur.Trefle) {
				//nous avons une carte de trefle et v�rifions si c'est la plus puissante
				valTre=Math.max(valTre,jeu.getMains()[num].voirCarte(i).getValeur());
				nbTre++;
			}
			else if(jeu.getMains()[num].voirCarte(i).getCouleur()==Couleur.Carreaux) {
				//nous avons une carte de Carreaux et v�rifions si c'est la plus puissante
				valCar=Math.max(valCar,jeu.getMains()[num].voirCarte(i).getValeur());
				nbCar++;
			}
			else if(jeu.getMains()[num].voirCarte(i).getCouleur()==Couleur.Coeur) {
				//nous avons une carte de Coeur et v�rifions si c'est la plus puissante
				valCoeur=Math.max(valCoeur,jeu.getMains()[num].voirCarte(i).getValeur());
				nbCoeur++;
			}
			else {
				//nous avons une carte de Pique et v�rifions si c'est la plus puissante
				valPiq=Math.max(valPiq,jeu.getMains()[num].voirCarte(i).getValeur());
				nbPiq++;
			}
		}
		int maxC=Math.max(Math.max(nbCar,nbCoeur),Math.max(nbPiq,nbTre));
		//le nombre de carte max toutcouleur confondue
		if(nbCar==maxC) {
			//on plus de carreaux que d'autre couleur
			maxQ=Couleur.Carreaux;
		}
		else if(nbTre==maxC) {
			//on plus de tefle que d'autre couleur
			maxQ=Couleur.Trefle;
		}
		else if(nbCoeur==maxC) {
			//on plus de coeur que d'autre couleur
			maxQ=Couleur.Coeur;
		}
		else {
			//on plus de pique que d'autre couleur
			maxQ=Couleur.Pique;
		}
	}
}
