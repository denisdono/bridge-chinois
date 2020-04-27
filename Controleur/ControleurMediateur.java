package Controleur;

import Modele.Jeu;
import Vue.CollecteurEvenements;

public class ControleurMediateur implements CollecteurEvenements {
	Jeu jeu;
	Joueur[] joueurs;
	int joueurCourant;
	final int lenteurAttente = 50;
	int decompte;

	public ControleurMediateur(Jeu j, boolean[] IA) {
		jeu = j;
		joueurs = new Joueur[IA.length];
		for (int i = 0; i < joueurs.length; i++)
			if (IA[i])
				joueurs[i] = new JoueurIA(i, jeu);
			else
				joueurs[i] = new JoueurHumain(i, jeu);
	}

	@Override
	public void clicSouris(int l, int c) {
		// Lors d'un clic, on le transmet au joueur courant.
		// Si un coup a effectivement été joué (humain, coup valide), on change de joueur.
		if (joueurs[joueurCourant].jeu(l, c))
			changeJoueur();
	}

	void changeJoueur() {
		joueurCourant = (joueurCourant + 1) % joueurs.length;
		decompte = lenteurAttente;
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
					System.out.println("On vous attend, joueur " + joueurs[joueurCourant].num());
					decompte = lenteurAttente;
				}
			} else {
				decompte--;
			}
		}
	}
}
