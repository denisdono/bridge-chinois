package Controleur;

import Modele.Jeu;
import Vue.CollecteurEvenements;

public class ControleurMediateur implements CollecteurEvenements {
	Jeu jeu;
	Joueur[] joueurs;
	int joueurCourant;
	final int lenteurAttente = 50;
	int decompte;

	public ControleurMediateur(Jeu j, boolean IA) {
		jeu = j;
		joueurs = new Joueur[2];
		for (int i = 0; i < 2; i++)
			if (i==1 && IA) // s'il y as une IA le jeu la met en tant que joueur numero2
				joueurs[i] = new JoueurIA(i, jeu);
			else
				joueurs[i] = new JoueurHumain(i, jeu);
	}

	@Override
	public void clicSouris(int i) {
		// Lors d'un clic, on le transmet au joueur courant.
		// Si un coup a effectivement été joué (humain, coup valide), on change de joueur.
		if (joueurs[joueurCourant].jeu(i))
			changeJoueur();
	}

	void changeJoueur() {
		if (jeu.new_dom()){// s'il y as eu un changement du joueur dominant on remet le bon joueur
			joueurCourant=jeu.j_dom();
			jeu.ch_joueur();//remet la variable du nouveau joueur dominant a false
		}
		else {// sinon on fais continuer en alternance
			joueurCourant = (joueurCourant + 1) % joueurs.length;
			decompte = lenteurAttente;
		}
	}

	public void recommencer() {
		jeu.start();
	}
	
	public void tictac() {
		if (jeu.enCours()) {
			if (decompte == 0) {
				// Lorsque le temps est écoulé on le transmet au joueur courant.
				// Si un coup a été joué (IA) on change de joueur.
				if (joueurs[joueurCourant].tempsEcoule()) {
					changeJoueur();
				} else {
				// Sinon on indique au joueur qui ne réagit pas au temps (humain) qu'on l'attend.
					//System.out.println("On vous attend, joueur " + joueurs[joueurCourant].num());
					decompte = lenteurAttente;
				}
			} else {
				decompte--;
			}
		}
	}
}