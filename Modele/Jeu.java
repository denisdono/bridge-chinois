package Modele;


import Patterns.Observable;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
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
	List<Carte> main1;//main du joueur 1
	List<Carte> main2;//main du joueur 2
	int joueurdominant;// quel joueur à la main (premier a jouer/piocher)
	

	public Jeu() {
		enCours = true;
		piochage= true;
		joueurdominant=1;
		Deck paquet = new Deck(); // creation d'un paquet de carte (deja melanger)
		piles = new Deck[6];// creation d'un tableau de piles pour les six paquets sur la table
		Stack<Carte> p =new Stack<Carte>();// variable temporaire
		main1=new ArrayList <Carte>();
		main2=new ArrayList <Carte>();
		for (int i=0;i<11;i++) { // remplissage des mains des joueurs
			main1.add(paquet.piocher());
			main2.add(paquet.piocher());
		}
		for (int i=0;i<6;i++) {// boucle sur les six piles
			for (int j=0;j<5;j++) {// boucle pour piocher les 5 cartes
				p.push(paquet.piocher());//pioche
			}
			piles[i]=new Deck(p);// enregistrement de la pile dans le tableau
			p.clear();//reinitialisation de la pile temporaire
		}
	}

	public void jouer(int l, int c) {
		if (enCours) {
			// tour de jeu
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

}
