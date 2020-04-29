package Modele;



public class Hand{
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
	
	public Carte voirCarte(int i) {// permet de regarder la carte d'indice i
		return main[i];
	}
	
	public Carte poserCarte(int i) {// permet de poser une carte de la main (défini par l'indice)
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
	
	public int getnbCarte() {// obtenir le nombre de carte du joueur 
		return nbcarte;
	}
	
	public int getnbPlis() {// obtenir le nombre de plis du joueur
		return plis;
	}
	
	public void addPlis() {// incrémente les plis
		plis++ ;
	}
	
	public void resetPlis() {// remet les plis a 0
		plis=0 ;
	}
	
	public int getnbScore() {// obtenir le score du joueur
		return score;
	}
	

}