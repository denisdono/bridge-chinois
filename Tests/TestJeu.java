package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Modele.Carte;
import Modele.Couleur;
import Modele.Deck;
import Modele.Hand;
import Modele.Jeu;

public class TestJeu {
	Jeu jeu;
	
	@Before
	public void init() {
		jeu = new Jeu();
	}
	
	
	
	@Test
	public void testJeu() {
		System.out.println("///Test initialisation Jeu///");
		int nbCartes=0;
		assertTrue(jeu.enCours());
//		assertFalse(jeu.isChangerjoueur());
//		assertEquals(jeu.getJoueurdominant(),0);
		int cartePiocheesJ1 = jeu.getMains()[0].getnbCarte();
		int cartePiocheesJ2 = jeu.getMains()[1].getnbCarte();
		assertEquals(11,cartePiocheesJ1);
		assertEquals(11,cartePiocheesJ2);
		System.out.println("J1 a pioché : "+cartePiocheesJ1+" cartes (attendu 11)");
		System.out.println("J2 a pioché : "+cartePiocheesJ2+" cartes (attendu 11)");
//		System.out.println("Liste cartes de la pioches :");
		for(int i=0;i<6;i++) {
//			System.out.println(jeu.getPiles()[i].toString());
			nbCartes += jeu.getPiles()[i].getNbCartes();			
		}
		System.out.println("Nombre de cartes piochées pour les piles : "+nbCartes+" (attendu 30)");
		assertEquals(30, nbCartes);

	}
	
	@Test
	public void testVainqueurPartie() {
		System.out.println("///test vainqueurPartie()///");
		jeu.getMains()[0].addScore(100);
		System.out.println("Score J1 : "+jeu.getMains()[0].getnbScore());
		jeu.getMains()[1].addScore(0);
		System.out.println("Score J2 : "+jeu.getMains()[1].getnbScore());
		assertEquals(jeu.vainqueurPartie(),0);//J1 gagne
		if(jeu.vainqueurPartie()==0) {
			System.out.println("J1 gagne");
		}else if(jeu.vainqueurPartie()==1) {
			System.out.println("J2 gagne");
		}else {
			System.out.println("Egalite");
		}
		
		jeu.getMains()[0].addScore(0);
		System.out.println("Score J1 : "+jeu.getMains()[0].getnbScore());
		jeu.getMains()[1].addScore(101);
		System.out.println("Score J2 : "+jeu.getMains()[1].getnbScore());
		assertEquals(jeu.vainqueurPartie(),1);//J2 gagne
		if(jeu.vainqueurPartie()==0) {
			System.out.println("J1 gagne");
		}else if(jeu.vainqueurPartie()==1) {
			System.out.println("J2 gagne");
		}else {
			System.out.println("Egalite");
		}
		
		jeu.getMains()[0].addScore(1);
		System.out.println("Score J1 : "+jeu.getMains()[0].getnbScore());
		jeu.getMains()[1].addScore(0);
		System.out.println("Score J2 : "+jeu.getMains()[1].getnbScore());
		assertEquals(jeu.vainqueurPartie(),-1);//egalite
		if(jeu.vainqueurPartie()==0) {
			System.out.println("J1 gagne");
		}else if(jeu.vainqueurPartie()==1) {
			System.out.println("J2 gagne");
		}else {
			System.out.println("Egalite");
		}
	}
	
	
	
	
	@Test
	public void testCarte_gagnante() {
		Carte c1= new Carte(2,Couleur.Carreaux);
		Carte c2= new Carte(3,Couleur.Carreaux);
//		jeu.setC_dom(c1);
//		jeu.setC_sub(c2);
	}
	
	@Test
	public void testTrouve_atout() {
		
	}
	
	@Test
	public void testJouer() {
		
	}

	@Test
	public void testNouvelleManche() {
		
	}
	
	
	@Test
	public void testPilesvide() {
		
		
	}
	@Test
	public void testPeutJouer() {
		
	}

}
