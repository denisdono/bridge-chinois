package Modele;

import java.util.Collections;
import java.util.Stack;

public class Deck {
	private Stack<Carte> cartes;
	
	Deck(){
		Couleur couleur = null;
		for(int j =0;j<4 ;j++ ) {
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
			for(int i = 2 ;i<15;i++ ) {
				Carte carte = new Carte(i,couleur);
				cartes.push(carte);
			}
		}
		Collections.shuffle(cartes);
	}
	Deck(Stack<Carte> cartes){
		this.cartes=cartes;
	}
	
	public boolean estVide() {
		return cartes.empty();
	}
	public Carte topDeck() {
		return cartes.peek();
	}
	public Carte piocher() {
		return cartes.pop();
	}
	
}
