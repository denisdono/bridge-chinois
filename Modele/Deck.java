package Modele;

import java.io.Serializable;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

public class Deck implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Stack<Carte> cartes;
	
	public Deck(){
		cartes = new Stack<Carte>();
		
	}
	
	public void remplirPaquet() {
		Couleur couleur = null;
		for(int j =0;j<4 ;j++ ) { // creations de toutes les cartes
			switch(j) {
				case 0: couleur = Couleur.Trefle;
						break;
				case 1: couleur = Couleur.Carreaux;
						break;
				case 2: couleur = Couleur.Coeur;
						break;
				case 3: couleur = Couleur.Pique;
					break;		
						
			}
			for(int i = 2 ;i<15;i++ ) { // Les cartes vont du 2 au 14 pour les cartes de 2 a 10 les chiffres parlent d'eux meme ensuite 11=valet, 12=dame, 13=roi et 14=as
				Carte carte = new Carte(i,couleur);
				cartes.push(carte);
			}
		}
		Collections.shuffle(cartes); //melange de la pile 
	}
	
	public Deck(Stack<Carte> cartes){ // création d'un deck avec des cartes deja choisis
		this.cartes=cartes;
	}
	
	public Deck(Deck d) {
		this.cartes = new Stack<Carte>();
		this.cartes.addAll(d.getCartes());
	}
	
	public boolean estVide() { //voir si le deck est vide
		return cartes.empty();
	}
	public Carte topDeck() { //regarder la carte du dessus de la pile
		return cartes.peek();
	}
	public Carte piocher() { //recuperer et enlever la carte du dessus de la pile
		Carte carte = null;
		try {			
			carte = cartes.pop();
		}catch(EmptyStackException e) {
			System.out.println("La pioche est vide");
		}
		return carte;
			
		
	}
	
	public void recupCartePile(Carte c) {// permet de mettre une carte dans la pile
		cartes.push(c);
	}
	
	public void videPaquet() {// vide la pile
		cartes.clear();
	}
	
	public Stack<Carte> getCartes() { //recuperer le tas de carte
		return cartes;
	}
	
	public String toString() {
		String str = "\nDeck : ";
		Iterator it = cartes.iterator();
		while(it.hasNext()) {
			str = str+it.next()+" ";
		}
		return str;
	}

	public int getNbCartes() {
		// TODO Auto-generated method stub
		return cartes.size();
	}
        
	
}