package Tests;

//import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;
import java.util.Iterator;

//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.Before;
import Modele.Carte;
import Modele.Deck;

public class TestsDeck {
	Deck pioche;
	
	@Before
	public void init() {
		pioche = new Deck();
		pioche.remplirPaquet();
	}
	
	@Test
	public void testConstructeur() {
		System.out.println("\n//////Test Constructeur avec une pioche existante//////\n");
		//test du constructeur à partir d'un deck existant
		for(int i=0;i<10;i++) {//on enleve 10 cartes
			pioche.piocher();
		}
		
		Deck pioche2 = new Deck(pioche.getCartes());//creation du 2e deck
		assertEquals(pioche2.getCartes(),pioche.getCartes());
		System.out.println("OK");
	}
	
	@Test
	public void testPioche() {
		System.out.println("\n//////Test Pioche//////");
		//test de la pioche:
		
		//pioche de 10 cartes
				System.out.println("On pioche 10 cartes");
				Carte carte;//carte qui va etre piochée
				for(int i=0;i<10;i++) {//on enleve 10 cartes
					carte = pioche.piocher();			
					System.out.println("Carte piochée : "+carte);
					
				}
				System.out.println();
				System.out.println("Taille du deck : "+pioche.getCartes().size()+ " (attendu 42)");
				assertFalse(pioche.estVide());
				
				//on vide la pioche
				Iterator it= pioche.getCartes().iterator();
				
				 
				while(it.hasNext()) {
					carte = pioche.piocher();			
					System.out.println("Carte piochée : "+carte);
				}
				System.out.println("Taille du deck : "+pioche.getCartes().size()+ " (attendu 0)");
				assertTrue(pioche.estVide());//on verifie que la pioche a bien 0 carte
				System.out.println("OK");
			}
	
	
	
	
	
	
	@Test
	public void testVide() {
		System.out.println("//////Test pioche deck vide//////\n");
		//Test pioche lorsque deck vide
		System.out.println("Taille du deck : "+pioche.getCartes().size());//doit etre égale a 52
		assertEquals(52,pioche.getCartes().size());
		
		Carte carte;//carte qui va etre piochée
		 
		Iterator it= pioche.getCartes().iterator();
		System.out.println("--On vide la pioche");
		while(it.hasNext()) {//on vide la pioche et on afiche la carte piochée
			carte = pioche.piocher();			
		}
		System.out.println("Taille du deck : "+pioche.getCartes().size());
		assertTrue(pioche.estVide());
		System.out.println("--On essaye de piocher une carte :");
		
		pioche.piocher();
		System.out.println("OK");
	}
	
	
				
				
	}


