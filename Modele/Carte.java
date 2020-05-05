package Modele;

public class Carte {
	private int valeur; // valmeur de la carte de 2 a 14 (11=valet; 12=dame; 13=roi; 14=as)
	private Couleur couleur;
	public Carte(int valeur, Couleur couleur){
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
		String str = "" ;
		str = str+" "+getValeur()+" "+getCouleur();
		return str;
		
	}
        public String getResourceName(){
            String name = "";
            System.out.println(couleur.toString());
            switch(couleur.toString()){
                case "Pique": name+="Spades "; break;
                case "Carreaux": name+="Diamond ";break;
                case "Coeur": name+="Hearts "; break;
                case "Trefle": name+="Clubs "; break;
                default : System.out.println("Mauvaise couleure"); break;
            }
            
            return name+getValeur()+".png";
        }
}

