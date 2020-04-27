package Modele;

public class Carte {
	private int valeur;
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

