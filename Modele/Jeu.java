package Modele;

/*
 * gauffre empoison�e
 * Copyright (C) 2020 Grondin Denis

 * Ce programme est libre, vous pouvez le redistribuer et/ou le
 * modifier selon les termes de la Licence Publique Générale GNU publiée par la
 * Free Software Foundation (version 2 ou bien toute autre version ultérieure
 * choisie par vous).

 * Ce programme est distribué car potentiellement utile, mais SANS
 * AUCUNE GARANTIE, ni explicite ni implicite, y compris les garanties de
 * commercialisation ou d'adaptation dans un but spécifique. Reportez-vous à la
 * Licence Publique Générale GNU pour plus de détails.

 * Vous devez avoir reçu une copie de la Licence Publique Générale
 * GNU en même temps que ce programme ; si ce n'est pas le cas, écrivez à la Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * États-Unis.

 * Contact: denisg3105@gmail.com
 *          515 rue des r�sidences
 *          Domaine universitaire
 *          38401 Saint Martin d'Hères
 */

import Patterns.Observable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;


public class Jeu extends Observable {
	boolean enCours;
	boolean maj;
	int[][] plateau;
	int coup;
	int ligne;
	int colonne;
	

	public Jeu(int n,int m) {
		// initialisation
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
	    bsave.write(1);
	    // donn�e a sauvegarder
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
		      ligne=bsave.read();
		      // donn�e a lire
		      bsave.close();
		  } catch (IOException e) {
		      e.printStackTrace();
		    }
	}
	
	// a re travailler
	public boolean libre(int i, int j) {
		return valeur(i, j) == -1;
	}

	public int valeur(int i, int j) {
		return plateau[i][j];
	}

	public boolean enCours() {
		return enCours;
	}

	public int largeur() {
		return colonne;
	}

	public int hauteur() {
		return ligne;
	}
}
