package Modele;

public class Carte {
	private int valeur; // valmeur de la carte de 2 a 14 (11=valet; 12=dame; 13=roi; 14=as)
	private Couleur couleur;
	public Carte(int valeur, Couleur couleur){
		this.couleur=couleur;
		this.valeur=valeur;
	}
	public Couleur getCouleur() {// permet de récuperer la couleur de la carte
		return couleur;
	}
	public int getValeur() {// permet de récuperer la valeur de la carte
		return valeur;
	}

	public String toString() {// permet d'écrire la carte
		String str = "" ;
		str = str+" "+getValeur()+" "+getCouleur();
		return str;
		
	}
}

