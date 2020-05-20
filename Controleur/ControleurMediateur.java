package Controleur;

import java.io.IOException;

import Modele.Coup;
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
		changeJoueur();
		for (int i = 0; i < 2; i++)
			if (i==1 && IA) { // s'il y as une IA le jeu la met en tant que joueur numero2
				joueurs[i] = new JoueurIA(i, jeu);
				jeu.activeIA();
			}
			else
				joueurs[i] = new JoueurHumain(i, jeu);
	}

	
	public void annule() {
		//if(!jeu.getIA())
			Coup c = jeu.historique.getPasse().peek();//rajouter try ctch
			jeu.annuler();
			joueurCourant = c.getJoueur();	
			System.out.print("Joueur courant"+joueurCourant);
			
		//}
	}

	public void refait() {
		if(jeu.historique.peutRefaire()) {
			jeu.refaire(joueurCourant);
			changeJoueur();
			//System.out.print("Joueur courant"+joueurCourant);
			jeu.metAJour();
		}else {
			System.out.println("Pas de coup rejouable");
		}

	}
	
	@Override
	public void sauvegarde() {
		jeu.save("laSauvegarde");
	}
	
	@Override
	public void charge() throws ClassNotFoundException {
		try {
			jeu.load("laSauvegarde");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void clicSouris(int i) {
		// Lors d'un clic, on le transmet au joueur courant.
		// Si un coup a effectivement été joué (humain, coup valide), on change de joueur.
		if (joueurs[joueurCourant].jeu(i))
			System.out.println("diff "+jeu.niveauIA());
			if(jeu.niveauIA()==2 && jeu.getIA())
				joueurs[1].avanceMinMAx(jeu.getdern());
			changeJoueur();
		
		//System.out.print("Joueur courant"+joueurCourant);
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
	
	public void abandonner() {
		jeu.giveUp();
		changeJoueur();
	}

	public void recommencer() {
		jeu.start();
		changeJoueur();
	}
	
	public void tictac() {
		if (jeu.enCours()) {
			if (decompte == 0) {
				// Lorsque le temps est écoulé on le transmet au joueur courant.
				// Si un coup a été joué (IA) on change de joueur.
				if (joueurs[joueurCourant].tempsEcoule()) {
					if(jeu.niveauIA()==2 && jeu.getIA())
						joueurs[1].avanceMinMAx(jeu.getdern());
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