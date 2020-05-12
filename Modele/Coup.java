package Modele;

import java.io.Serializable;
import java.util.Arrays;

public class Coup implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int joueur;//numero du joueur qui a joué
	public boolean changerjoueur;// doit on changer de joueur
	boolean enCours;// partie en cour
	boolean finmanche;// a t'on fini la manche ?
	boolean piochage;// y'as t'il des cartes a piocher ?
	boolean parManche;// la fin de partie et décidé par nombre de manche (false= on décide par score)
	Deck [] piles;// pile présente sur la table
	Hand main1;//main des joueur
	
	Hand main2;
	int totalfin;// score a obtenir ou nombre de manche a faire avant la fin de partie
	int manche;// le nombre de manche actuelle
	int etape;// etape actuelle d'un tour de jeu
	int joueurdominant;// quel joueur à la main (premier a jouer/piocher)
	Couleur atout;// l'atout de la manche
	Carte c_dom;// carte jouer par le joueur dominant 
	Carte c_sub;// carte jouer par l'autre joueur
	Carte c_0;//carte piochée par le joueur dominant
	Carte c_1;//carte piochée par l'autre joueur
	private String action;//Pose ou Pioche
	int diff;// dificulter de l'ia
	boolean showCarte;// carte visible
	boolean IA;// prï¿½sence d'une IA
	public Coup() {
		
	}
	
	public Coup(int joueur, boolean changerjoueur, boolean enCours, boolean finmanche, boolean piochage,
			boolean parManche, Deck[] piles, Hand h1,Hand h2, int totalfin, int manche, int etape, int joueurdominant,
			Couleur atout, Carte c_dom, Carte c_sub,int diff,boolean show,boolean ia) {
		super();
		this.joueur = joueur;
		this.changerjoueur = changerjoueur;
		this.enCours = enCours;
		this.finmanche = finmanche;
		this.piochage = piochage;
		this.parManche = parManche;
		this.piles = piles;
		this.main1 = h1;
		this.main2 =h2;
	
		this.totalfin = totalfin;
		this.manche = manche;
		this.etape = etape;
		this.joueurdominant = joueurdominant;
		this.atout = atout;
		this.c_dom = c_dom;
		this.c_sub = c_sub;
		
		this.showCarte = show;
		this.diff=diff;
		this.IA=ia;
		
		if(etape ==0 || etape == 1) {
			action = "pose";
		}else {
			action = "pioche";
		}	}


//	@Override
//	public String toString() {
//		return "Coup2 [joueur=" + joueur + ", changerjoueur=" + changerjoueur + ", enCours=" + enCours + ", finmanche="
//				+ finmanche + ", piochage=" + piochage + ", parManche=" + parManche + ", piles="
//				+ Arrays.toString(piles) + ", mains=" + Arrays.toString(mains) + ", totalfin=" + totalfin + ", manche="
//				+ manche + ", etape=" + etape + ", joueurdominant=" + joueurdominant + ", atout=" + atout + ", c_dom="
//				+ c_dom + ", c_sub=" + c_sub + ", action=" + action + "]";
//	}
	
	

	public int getJoueur() {
		return joueur;
	}

	

	
	




	





	@Override
	public String toString() {
		return "Coup [joueur=" + joueur + ", changerjoueur=" + changerjoueur + ", enCours=" + enCours + ", finmanche="
				+ finmanche + ", piochage=" + piochage + ", parManche=" + parManche + ", main1=" + main1 + ", main2="
				+ main2 + ", totalfin=" + totalfin + ", manche=" + manche + ", etape=" + etape + ", joueurdominant="
				+ joueurdominant + ", atout=" + atout + ", c_dom=" + c_dom + ", c_sub=" + c_sub + ", c_0=" + c_0
				+ ", c_1=" + c_1 + ", action=" + action + ", diff=" + diff + ", showCarte=" + showCarte + ", IA=" + IA
				+ "]";
	}

	public void setJoueur(int joueur) {
		this.joueur = joueur;
	}

	public boolean isChangerjoueur() {
		return changerjoueur;
	}

	public void setChangerjoueur(boolean changerjoueur) {
		this.changerjoueur = changerjoueur;
	}

	public boolean isEnCours() {
		return enCours;
	}

	public void setEnCours(boolean enCours) {
		this.enCours = enCours;
	}

	public boolean isFinmanche() {
		return finmanche;
	}

	public void setFinmanche(boolean finmanche) {
		this.finmanche = finmanche;
	}

	public boolean isPiochage() {
		return piochage;
	}

	public void setPiochage(boolean piochage) {
		this.piochage = piochage;
	}

	public boolean isParManche() {
		return parManche;
	}

	public void setParManche(boolean parManche) {
		this.parManche = parManche;
	}

	public Deck[] getPiles() {
		return piles;
	}

	public void setPiles(Deck[] piles) {
		this.piles = piles;
	}

	public Hand getMain1() {
		return main1;
	}
	public Hand getMain2() {
		return main2;
	}


	public void setMains(Hand mains) {
		this.main1 = mains;
	}

	public int getTotalfin() {
		return totalfin;
	}

	public void setTotalfin(int totalfin) {
		this.totalfin = totalfin;
	}

	public int getManche() {
		return manche;
	}

	public void setManche(int manche) {
		this.manche = manche;
	}

	public int getEtape() {
		return etape;
	}

	public void setEtape(int etape) {
		this.etape = etape;
	}

	public int getJoueurdominant() {
		return joueurdominant;
	}

	public void setJoueurdominant(int joueurdominant) {
		this.joueurdominant = joueurdominant;
	}

	public Couleur getAtout() {
		return atout;
	}

	public void setAtout(Couleur atout) {
		this.atout = atout;
	}

	public Carte getC_dom() {
		return c_dom;
	}

	public void setC_dom(Carte c_dom) {
		this.c_dom = c_dom;
	}

	public Carte getC_sub() {
		return c_sub;
	}

	public void setC_sub(Carte c_sub) {
		this.c_sub = c_sub;
	}

	public Carte getC_0() {
		return c_0;
	}

	public void setC_0(Carte c_0) {
		this.c_0 = c_0;
	}

	public Carte getC_1() {
		return c_1;
	}

	public void setC_1(Carte c_1) {
		this.c_1 = c_1;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getDiff() {
		return diff;
	}

	public void setDiff(int diff) {
		this.diff = diff;
	}

	public boolean isShowCarte() {
		return showCarte;
	}

	public void setShowCarte(boolean showCarte) {
		this.showCarte = showCarte;
	}

	public boolean isIA() {
		return IA;
	}

	public void setIA(boolean iA) {
		IA = iA;
	}
	
	
	
	
}