package Modele;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.*;

public class Historique implements Serializable {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//il y a les pioches et les poses peut etre les séparer
	private Stack<Coup> passe,futur;//Liste des coups qui ont été joué
	private boolean auPasse; // sert a determiner si on peut rejouer un coup
	public Historique() {
		passe=new Stack<Coup>();
		futur = new Stack<Coup>();
		auPasse = false;
	}
	
	public Historique(Stack<Coup> p,Stack<Coup> f) {
		passe=p;
		futur = f;
	}
	
	
	public boolean peutAnnuler() {
		return !passe.isEmpty();
	}

	public boolean peutRefaire() {//futur a vide quand on joue un nouveau coup
		return !futur.isEmpty();
	}
	
	public void ajouterCoup(Coup c) {
		passe.push(c);
	}
	
	public Coup defaire() {
		Coup c;
		if(peutAnnuler()) {
			c = passe.pop();
			futur.push(c);
		}else {
			c = null;
		}
		return c;
	}
	
	public Coup refaire() {
		Coup c;
		if(peutRefaire()) {
			c = futur.pop();
			passe.push(c);
		}else {
			c = null;
		}
		return c;
	}

	public Stack<Coup> getPasse() {
		return passe;
	}

	public void setPasse(Stack<Coup> passe) {
		this.passe = passe;
	}

	public Stack<Coup> getFutur() {
		return futur;
	}

	public void setFutur(Stack<Coup> futur) {
		this.futur = futur;
	}

	public boolean isAuPasse() {
		return auPasse;
	}

	public void setAuPasse(boolean auPasse) {
		this.auPasse = auPasse;
	}

	public void affiherPasse() {
		// TODO Auto-generated method stub
		for(int i=0;i<passe.size();i++) {
			//System.out.println("Passé :"+passe.get(i).toString());
		}
	}
	
	public void afficherFutur() {
		// TODO Auto-generated method stub
		for(int i=0;i<futur.size();i++)
			System.out.println("Futur "+futur.get(i).toString());
	}
	
	
	

}