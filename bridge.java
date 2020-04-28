import Controleur.ControleurMediateur;
import Modele.Jeu;
import Vue.CollecteurEvenements;
import Vue.InterfaceGraphique;

import java.security.InvalidParameterException;
import java.util.Iterator;

public class bridge {
	public static void main(String[] args) {
		boolean IA;


		// Le choix des joueurs est passé en arguments de la ligne de commande
		// Par défaut on prend IA
		// Avec des arguments on peut choisir humain ou IA comme adversaire
			String nature;
			if (0 < args.length)
				nature = args[0];
			else
				nature = "IA";
			switch (nature) {
				case "humain":
					IA = false;
					break;
				case "IA":
					IA = true;
					break;
				default:
					throw new InvalidParameterException(nature);
			}
		

		Jeu j = new Jeu();
		CollecteurEvenements control = new ControleurMediateur(j, IA);
		InterfaceGraphique.demarrer(j, control);
	}
}
