package Modele;


import Patterns.Observable;
import java.util.Stack;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;


public class Jeu extends Observable {
	boolean enCours;// partie en cour
	boolean piochage;// y'as t'il des cartes a piocher ?
	Deck [] piles;// pile présente sur la table
	Hand main1;//main du joueur 1
	Hand main2;//main du joueur 2
	int joueurdominant;// quel joueur à la main (premier a jouer/piocher)
	Couleur atout;// l'atout de la manche
	

	public Jeu() {
		enCours = true;
		piochage= true;
		joueurdominant=1;
		Deck paquet = new Deck(); // creation d'un paquet de carte (deja melanger)
		piles = new Deck[6];// creation d'un tableau de piles pour les six paquets sur la table
		Stack<Carte> p =new Stack<Carte>();// variable temporaire
		main1=new Hand();
		main2=new Hand();
		for (int i=0;i<11;i++) { // remplissage des mains des joueurs
			main1.ajoutCarte(paquet.piocher());
			main2.ajoutCarte(paquet.piocher());
		}
		for (int i=0;i<6;i++) {// boucle sur les six piles
			for (int j=0;j<5;j++) {// boucle pour piocher les 5 cartes
				p.push(paquet.piocher());//pioche
			}
			piles[i]=new Deck(p);// enregistrement de la pile dans le tableau
			p.clear();//reinitialisation de la pile temporaire
		}
	}

	public void jouer() {
		if (enCours) {
			// tour de jeu
			
			// joueurdominant pose une carte
			
			// le second joueur pose une carte en conséquences (limiter par raport a la cartes)
			
			// calcul de qui remporte le plis
			
			// celui qui gagne devien joueur dominant
			
			//s'il reste des cartes a piocher le joueur dominant pioche suivi du second
			
			metAJour();
		}
	}
	
	
	public void save(String s){
		FileOutputStream save;
		try {
			save = new FileOutputStream(new File(s));
	    BufferedOutputStream bsave = new BufferedOutputStream(save);
	    //bsave.write(1);
	    // donnÃ©e a sauvegarder
		bsave.close();
		} catch (IOException e) {	
			System.err.println("Impossible de sauvegarder dans " + s);
			System.err.println(e.toString());
			System.exit(1);
		}
	}
	public void load(String s) {
		 try {
		      FileInputStream save = new FileInputStream(new File(s));
		      BufferedInputStream bsave = new BufferedInputStream(save);
		      //bsave.read();
		      // donnÃ©e a lire
		      bsave.close();
		  } catch (IOException e) {
		      e.printStackTrace();
		    }
	}
	
	// a re travailler



	public boolean enCours() {
		return enCours;
	}
	
	public boolean peutJouer(Carte c1, Carte c2, Hand main) {
		if (c1.getCouleur()==c2.getCouleur()) {
			return true;}
		else {
			for (int i=0;i<main.getnbCarte();i++) {
				if (main.voirCarte(i).getCouleur()==c1.getCouleur()) {
					return false;
				}
			}
			return true;
		}
		
	}
}
