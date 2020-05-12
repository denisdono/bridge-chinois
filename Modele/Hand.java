package Modele;

import java.io.Serializable;

// ici se trouve les donn�e du joueur (sa main, ses plis, son score...)

public class Hand implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Carte[] main;//main du joueur 
	private int nbcarte;// nombre de carte du joueur
	private int plis;// nombre de plis du joueur (dans une manche)
	private int score;// score du joueur dans la partie
	
	public Hand() { //initialisation a 0
		main =new Carte[11];
		plis=0;
		score=0;
		nbcarte=0;
	}
	
	public Hand(Hand h) {
		main = h.getMain().clone();
		
		plis = h.plis;
		score =h.score;
		nbcarte = h.nbcarte;
	}
	
	public void trierMain() {// permet de trier les carte d'une main pour les ordonner
		Carte c;
		int k,l,m;
		for (int i=0; i<nbcarte;i++) {//parcoure toute les cartes
			c=main[i];
			k=i;
			for (int j=i+1;j<nbcarte;j++) {// parcour toute les carte apr�s la carte actuelle
				l=c.getCouleur().getVal();
				m=main[j].getCouleur().getVal();
				if (l==2) l=4;// rend momentan�ment le coeur sup�rieure a pique
				if (m==2) m=4;
				if (l>m) {//v�rifie si la couleur de la carte et plus faible
					c=main[j];
					k=j;
				}
				else if (c.getCouleur().getVal()==main[j].getCouleur().getVal()) {//v�rifie si la carte est de meme couleur 
					if (c.getValeur()>main[j].getValeur()) {// verifie si la carte et plus faible
						c=main[j];
						k=j;
					}
				}
			}
			if (k!=i) {// si il existe une carte plus faible r�ordonne
				main[k]=main[i];
				main[i]=c;
			}
		}
	}
	
	
	public Carte voirCarte(int i) {// permet de regarder la carte d'indice i
		return main[i];
	}
	
	public Carte poserCarte(int i) {// permet de poser une carte de la main (d�fini par l'indice)
		Carte c=main[i];
		nbcarte--;
		for(int j=i;j<nbcarte;j++) {// remet les carte au debut du tableau
			main[j]=main[j+1];
		}
		return c;// retourn la carte a poser
	}
	
	public void ajoutCarte(Carte c) {// permet d'ajouter une carte a la main
		if (nbcarte==11) {
			System.err.print("erreur main pleine");
			return;
		}
		else {
		main[nbcarte]=c;
		nbcarte++;
		}
	}
	
	public void videMain() {// permet de vider la mains
		nbcarte=0;
	}
	
	public int getnbCarte() {// obtenir le nombre de carte du joueur 
		return nbcarte;
	}
	
	public int getnbPlis() {// obtenir le nombre de plis du joueur
		return plis;
	}
	
	public void addPlis() {// incr�mente les plis
		plis++ ;
	}
	
	public void maxPlis(){// met le nombre de plis maximal
		plis=26;
	}
	
	public void resetPlis() {// remet les plis a 0
		plis=0 ;
	}
	
	public int getnbScore() {// obtenir le score du joueur
		return score;
	}
	
	public void resetScore() {// remet le score a 0
		score=0;
	}
	
	public void addScore(int p) {// permet d'ajouter p au score
		score=score+p;
	}

        public Carte[] getMain() {// permet de renvoyer la main(donn�e du joueur)
            return main;
        }
       
        public void afficherMain() {
        	for(int i=0;i<main.length;i++) {
        		System.out.print(main[i]+" ");
        	}
        }
        
        public String laMain() {
        	String str ="";
        	for(int i=0;i<main.length;i++) {
        		str = str+main[i]+" ";
        	}
        	return str;
        }

		public void setPlis(int i) {
			// TODO Auto-generated method stub
			plis =i;
		}

}