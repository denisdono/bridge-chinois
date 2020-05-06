package Vue;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Patterns.Observateur;

public class ReglesWindow extends JFrame implements Observateur{
	JPanel hisPanel;
    
    public ReglesWindow(){
        this.setTitle("Historique");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int)screenSize.getWidth()/3*2, (int)screenSize.getHeight()/3*2);
        hisPanel = new JPanel();
		hisPanel.setLayout(new BoxLayout(hisPanel,BoxLayout.PAGE_AXIS));
		
        miseAJour();
    }

	@Override
	public void miseAJour() {		
        JLabel titre = new JLabel("R�gles");
        titre.setFont(new Font("Calibri", Font.PLAIN, 24));
        hisPanel.add(titre);
        JTextArea regles = new JTextArea();
        regles.setText("\tLe bridge chinois est un jeu de cartes � 2 joueurs.\r\n\t" + 
        		"On utilise un jeu de 52 cartes. L'ordre des cartes et des couleurs est le m�me qu'au bridge (as, roi, dame, valet, dix, ...deux, et pique, coeur, carreau, tr�fle).\r\n\t" + 
        		"Le donneur distribue 11 cartes � chacun des 2 joueurs, puis distribue les cartes restantes en 6 piles de 5 cartes, face cach�e. Puis il d�couvre les 6 cartes qui sont au sommet de chacune des 6 piles.\n\t Si toutes les cartes visibles ont une valeur inf�rieure � 10, la manche est jou�e sans atout. Sinon, l'atout est fix� par la carte d�couverte la plus forte.\r\n\t" + 
        		"\r\n\t" + 
        		"Le but du jeu est de r�aliser un nombre de plis le plus grand possible (en particulier, plus que l'adversaire).\n\t Chaque pli va consister en 2 cartes, chacune pos�e sur le tapis par l'un des joueurs, choisie parmi les cartes qu'il a en main.\n\t Le pli est gagn� et ramass� (faces cach�es, sans possibilit� de le consulter par la suite) par le joueur\r\n\t" + 
        		"     qui a pos� la carte la plus forte, si les deux cartes sont de m�me couleur,\r\n\t" + 
        		"     qui a coup� (jou� un atout) si l'une des deux cartes est un atout,\r\n\t" + 
        		"     qui a pos� la premi�re carte, si les deux cartes sont de couleur diff�rente et qu'aucune n'est un atout (on dit que le second joueur s'est d�fauss�).\r\n\t" + 
        		"\r\n\t" + 
        		"Le donneur a initialement la main.\r\n\t" + 
        		"Le joueur qui gagne le pli prend (ou garde) la main.\r\n\t" + 
        		"Le joueur qui a la main choisit une des cartes de sa main, et la pose sur le tapis. L'autre joueur, s'il le peut, doit fournir : jouer une carte de la m�me couleur.\n\t S'il ne peut pas fournir, il a le choix entre couper ou se d�fausser. Donc, on n'est pas oblig� de couper.\r\n\t" + 
        		"Si la manche se joue sans atout, le second joueur ne peut bien s�r que se d�fausser s'il ne peut fournir... dur !\r\n\t" + 
        		"Le joueur qui a gagn� le pli le ramasse. Puis il choisit l'une des cartes d�couvertes (s'il en reste... voir plus loin) et la place dans sa main.\n\t Il d�couvre ensuite la carte cach�e qu'il a ainsi fait appara�tre en sommet de pile (si la pile n'est pas vide).\n\t L'autre joueur choisit � son tour une carte d�couverte (il y en a au moins une, puisqu'il y a un nombre pair de cartes dans les piles sur la table : c'est un invariant de l'it�ration).\n\t Puis il d�couvre la carte cach�e qu'il a ainsi fait appara�tre en sommet de pile (si la pile n'est pas vide).\r\n\t" + 
        		"Dans la premi�re phase de la manche, les 2 joueurs ont ainsi toujours 11 cartes en main (encore un invariant).\r\n\t" + 
        		"\r\n\t" + 
        		"Lorsque les piles sont �puis�es, la manche continue selon les m�mes r�gles, en supprimant la phase de choix/d�couverte, bien s�r.\r\n\t" + 
        		"Cette seconde phase s'arr�te, puisque le nombre de cartes que les joueurs ont en main d�cro�t strictement � chaque lev�e. La manche se termine lorsque les 2 joueurs n'ont plus de carte en main.\n\t S'il reste des cartes � l'un et pas � l'autre, c'est que l'un ou l'autre a trich�, ou qu'il y a une erreur dans le programme.\r\n\t" + 
        		"\r\n\t" + 
        		"� la fin de la manche, on compte les plis de chacun (ou d'un seul des deux, les plus f�t�s voient pourquoi) et chacun se voit attribuer par l'arbitre le nombre de points correspondant.\r\n\t" + 
        		"En g�n�ral, l'arbitre est l'un des deux joueurs, l'autre surveille simplement les comptes.\r\n\t" + 
        		"Au cours des manches successives, chaque joueur donne tour � tour.\r\n\t" + 
        		"La partie se joue en un nombre de points ou en un nombre de manches qui a �t� fix� � l'avance (par exemple 100 points, ou 12 manches).\n\t Dans le cas o� le nombre de manches est fix�, il faut choisir un nombre pair, car le donneur a un certain avantage. ");
        regles.setEditable(false);
        hisPanel.add(regles);
        JScrollPane scrollPane = new JScrollPane(hisPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        this.add(scrollPane);
	}
	public void montrer() {
        this.setVisible(true);
    }
}
