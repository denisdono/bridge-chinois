
package Vue;

public interface CollecteurEvenements {
	void clicSouris(int i);
	void tictac();
	void nouvellePartie();
	void recommencer();
	
	void abandonner();
	void annule();
	void refait();
	void sauvegarde();
	void charge() throws ClassNotFoundException;
}
