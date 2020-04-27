package Tests;

import java.util.Iterator;

import Modele.Carte;
import Modele.Deck;

public class TestDeck {
	Deck deck;
	int nbPioche;// compte le nombre de carte piochée;
	
	public TestDeck(Deck deck){
		this.deck = deck;
		nbPioche = 0;
	}
	
	public void testAffichageDeck(Deck deck) {//Affiche les cartes du deck
		System.out.println(deck);
	}
	
	public void testPioche(Deck deck) {//vide completement la pioche + affichage de la carte piochée
		Carte carte;//carte qui va etre piochée
		 
		Iterator it= deck.getCartes().iterator();
		
		while(it.hasNext()) {
			carte = deck.piocher();
			System.out.println("Carte piochée : "+carte);
			nbPioche++;
		}
		
		
	}
	
	public static void main(String[] args) {
		//creation deck 1er constructeur
		Deck deckAff = new Deck();
		
		System.out.println("////Test de l'affichage du deck////");
		System.out.println();
		//test affichage des 52 cartes
		TestDeck test = new TestDeck(deckAff);
		test.testAffichageDeck(deckAff);
		
		//test de la pioche : 
		//Entrée : deck de 52 cartes
		//Sortie : deck de 0 cartes
		System.out.println("////Test de la pioche////");
		//assertEquals(test.deck.getCartes().size(),52);
		System.out.println();
		System.out.println("Taille du deck : "+test.deck.getCartes().size());//doit etre égale a 52
		
		test.testPioche(deckAff);
		//assertEquals(test.deck.getCartes().size(),0);
		System.out.println();
		System.out.println("Taille du deck : "+test.deck.getCartes().size());//doit etre egal a 0
		
		
		
		
	}
	
	
	
}
