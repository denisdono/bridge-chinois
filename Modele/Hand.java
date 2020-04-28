package Modele;



public class Hand{
	private Carte[] main;//main du joueur 
	private int nbcarte;
	private int plis;
	private int score;
	
	public Hand() {
		main =new Carte[11];
		plis=0;
		score=0;
		nbcarte=0;
	}
	
	public Carte voirCarte(int i) {
		return main[i];
	}
	
	public Carte poserCarte(int i) {
		Carte c=main[i];
		for(int j=i;j<nbcarte-1;j++) {
			main[j]=main[j+1];
		}
		return c;
	}
	
	public void ajoutCarte(Carte c) {
		if (nbcarte==11) {
			System.err.print("erreur main pleine");
			return;
		}
		else {
		main[nbcarte]=c;
		}
	}
	
	public int getnbCarte() {
		return nbcarte;
	}
	
	public int getnbPlis() {
		return plis;
	}
	
	public int getnbScore() {
		return score;
	}
	

}