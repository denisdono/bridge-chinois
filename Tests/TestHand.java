package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Modele.*;


public class TestHand {
	Hand mainj;
	Deck pioche;
	@Before
	public void init() {
		mainj = new Hand();
		pioche = new Deck();
		assertEquals(mainj.getnbCarte(),0);//verif main vide
		pioche.remplirPaquet();
		Carte c;
		//initialisation de la main avec 11 cartes
		for(int i=0;i<11;i++) {
			c=pioche.piocher();
			//System.out.println(c);
			mainj.ajoutCarte(c);
		}
		assertEquals(mainj.getnbCarte(),11);// verif main a 11 cartes
		

	}
	
	@Test
	public void testVoirCarte() {
		System.out.println("\n//////Test VoirCarte//////\n");
		
		for(int i=0;i<11;i++) {
			System.out.println(mainj.voirCarte(i));
		}	
		System.out.println("OK\n");

	}
	
	@Test
	public void testAjoutCarte() {
		//on test le cas où l'on veut piocher 12 cartes
		System.out.println("\n//////Test ajoutCarte//////\n");
		System.out.println("Main du joueur initialisée");
		Hand mainAvantAjout = mainj;
		Carte c;
		System.out.println("Le joueur a 11 cartes et il essaye d'en piocher 12 : ");
		c=pioche.piocher();
		mainj.ajoutCarte(c);
		assertEquals(mainj,mainj);
		System.out.print("OK");

	}
	
	@Test
	public void testPoserCarte() {
		
		//on test le cas où on veut poser une carte qui n'est pas dans notre main
		System.out.println("\n//////Test PoserCarte//////\n");
		assertEquals(mainj.getnbCarte(),11);//verif main a 11 cartes
		System.out.println("\nEtat de la main : ");

		for(int i=0;i<11;i++) {
			System.out.println("main : "+mainj.poserCarte(0));			
		}
		assertEquals(mainj.getnbCarte(),0);//verif main vide
		System.out.println("\nEtat de la main après avoir posé toutes les cartes : ");
		for(int i=0;i<11;i++) {
			System.out.print("main : "+mainj.voirCarte(i)+" ");
			System.out.println(mainj.getnbCarte());
		}
		System.out.println("\nOn pose une carte d'indice qui n'existe pas");		
		System.out.println("Carte posée : "+mainj.poserCarte(0));
		assertEquals(null,mainj.poserCarte(0));
	}

}
