/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

/**
 *
 * @author dodee
 */
public enum Couleur {
    Pique(3),
    Coeur(2),
    Carreaux(1),
    Trefle(0),
	Neutre(-1);// utiliser pour l'absence d'atout
    
    private int val;
    Couleur(int val){
        this.val = val;
    }

    public int getVal() {//retourne la valeur de la couleur
        return val;
    }
    
}
