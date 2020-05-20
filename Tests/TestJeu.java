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
		jeu.getMains()[0].addScoreM(100);
		System.out.println("Score J1 : "+jeu.getMains()[0].getnbScoreM());
		jeu.getMains()[1].addScoreM(0);
		System.out.println("Score J2 : "+jeu.getMains()[1].getnbScoreM());
//		assertEquals(jeu.vainqueurPartie(),0);//J1 gagne
		if(jeu.vainqueurPartie()==0) {
			System.out.println("J1 gagne");
		}else if(jeu.vainqueurPartie()==1) {
			System.out.println("J2 gagne");
		}else {
			System.out.println("Egalite");
		}
		
		jeu.getMains()[0].addScoreM(0);
		System.out.println("Score J1 : "+jeu.getMains()[0].getnbScoreM());
		jeu.getMains()[1].addScoreM(101);
		System.out.println("Score J2 : "+jeu.getMains()[1].getnbScoreM());
//		assertEquals(jeu.vainqueurPartie(),1);//J2 gagne
		if(jeu.vainqueurPartie()==0) {
			System.out.println("J1 gagne");
		}else if(jeu.vainqueurPartie()==1) {
			System.out.println("J2 gagne");
		}else {
			System.out.println("Egalite");
		}
		
		jeu.getMains()[0].addScoreM(1);
		System.out.println("Score J1 : "+jeu.getMains()[0].getnbScoreM());
		jeu.getMains()[1].addScoreM(0);
		System.out.println("Score J2 : "+jeu.getMains()[1].getnbScoreM());
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
	public void testJouer() {
		System.out.println("/// TEST JOUER ///");
		Hand mainTest = new Hand();
		Hand mainTest2 = new Hand();
		Carte [] cartes = new Carte[11];
		Carte [] cartes2 = new Carte[11];
		System.out.println("On initialise les mains");

		for (int i = 0; i < 11; i++) {
			cartes[i] = new Carte(i+2,Couleur.Pique);
			mainTest.ajoutCarte(cartes[i]);
		}
		
		for (int i = 0; i < 11; i++) {
			cartes2[i] = new Carte(i+2,Couleur.Coeur);
			mainTest2.ajoutCarte(cartes2[i]);
		}
		System.out.println("Main OK");
		jeu.getMains()[0] = mainTest;
		jeu.getMains()[1] = mainTest2;
		System.out.print("Main j1 : ");

		jeu.getMains()[0].afficherMain();
		System.out.println();
		System.out.print("Main j2 : ");

		jeu.getMains()[1].afficherMain();
		System.out.println("J1 joue une carte ");
		jeu.jouer(0, 0);
		System.out.println("");
		System.out.println("Carte jouée :"+jeu.getC_dom()+" (attendu 2 Pique)");
		assertEquals(10,jeu.getMains()[0].getnbCarte());
		
		System.out.println("J2 joue une carte ");
		jeu.jouer(0, 1);
		System.out.println("Carte jouée :"+jeu.getC_sub()+" (attendu 2 Coeur)");
		assertEquals(10,jeu.getMains()[1].getnbCarte());
		assertEquals(2,jeu.etape());
		
		
		System.out.println("J1 pioche ");

		jeu.jouer(0, 0);
		assertEquals(11, jeu.getMains()[0].getnbCarte());
		
		System.out.println("J2 pioche ");
		jeu.jouer(0, 1);
		assertEquals(11, jeu.getMains()[1].getnbCarte());
		assertEquals(0,jeu.etape());
		System.out.println("Fin manche ");

	}

	

}
