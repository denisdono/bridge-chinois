package Modele;

public class Carte {
	private int valeur; // valmeur de la carte de 2 a 14 (11=valet; 12=dame; 13=roi; 14=as)
	private Couleur couleur;
	Carte(int valeur, Couleur couleur){
		this.couleur=couleur;
		this.valeur=valeur;
	}
	public Couleur getCouleur() {
		return couleur;
	}
	public int getValeur() {
		return valeur;
	}

	public String toString() {
		String str = "Carte : " ;
		str = str+" "+getValeur()+" "+getCouleur();
		return str;
		
	}
}

