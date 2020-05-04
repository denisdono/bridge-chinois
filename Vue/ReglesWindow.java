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
        JLabel titre = new JLabel("Règles");
        titre.setFont(new Font("Calibri", Font.PLAIN, 24));
        hisPanel.add(titre);
        JTextArea regles = new JTextArea();
        regles.setText("Le bridge chinois est un jeu de cartes à 2 joueurs.\r\n" + 
        		"On utilise un jeu de 52 cartes. L'ordre des cartes et des couleurs est le même qu'au bridge (as, roi, dame, valet, dix, ...deux, et pique, coeur, carreau, trèfle).\r\n" + 
        		"Le donneur distribue 11 cartes à chacun des 2 joueurs, puis distribue les cartes restantes en 6 piles de 5 cartes, face cachée. Puis il découvre les 6 cartes qui sont au sommet de chacune des 6 piles.\n Si toutes les cartes visibles ont une valeur inférieure à 10, la manche est jouée sans atout. Sinon, l'atout est fixé par la carte découverte la plus forte.\r\n" + 
        		"\r\n" + 
        		"Le but du jeu est de réaliser un nombre de plis le plus grand possible (en particulier, plus que l'adversaire).\n Chaque pli va consister en 2 cartes, chacune posée sur le tapis par l'un des joueurs, choisie parmi les cartes qu'il a en main.\n Le pli est gagné et ramassé (faces cachées, sans possibilité de le consulter par la suite) par le joueur\r\n" + 
        		"     qui a posé la carte la plus forte, si les deux cartes sont de même couleur,\r\n" + 
        		"     qui a coupé (joué un atout) si l'une des deux cartes est un atout,\r\n" + 
        		"     qui a posé la première carte, si les deux cartes sont de couleur différente et qu'aucune n'est un atout (on dit que le second joueur s'est défaussé).\r\n" + 
        		"\r\n" + 
        		"Le donneur a initialement la main.\r\n" + 
        		"Le joueur qui gagne le pli prend (ou garde) la main.\r\n" + 
        		"Le joueur qui a la main choisit une des cartes de sa main, et la pose sur le tapis. L'autre joueur, s'il le peut, doit fournir : jouer une carte de la même couleur.\n S'il ne peut pas fournir, il a le choix entre couper ou se défausser. Donc, on n'est pas obligé de couper.\r\n" + 
        		"Si la manche se joue sans atout, le second joueur ne peut bien sûr que se défausser s'il ne peut fournir... dur !\r\n" + 
        		"Le joueur qui a gagné le pli le ramasse. Puis il choisit l'une des cartes découvertes (s'il en reste... voir plus loin) et la place dans sa main.\n Il découvre ensuite la carte cachée qu'il a ainsi fait apparaître en sommet de pile (si la pile n'est pas vide).\n L'autre joueur choisit à son tour une carte découverte (il y en a au moins une, puisqu'il y a un nombre pair de cartes dans les piles sur la table : c'est un invariant de l'itération).\n Puis il découvre la carte cachée qu'il a ainsi fait apparaître en sommet de pile (si la pile n'est pas vide).\r\n" + 
        		"Dans la première phase de la manche, les 2 joueurs ont ainsi toujours 11 cartes en main (encore un invariant).\r\n" + 
        		"\r\n" + 
        		"Lorsque les piles sont épuisées, la manche continue selon les mêmes règles, en supprimant la phase de choix/découverte, bien sûr.\r\n" + 
        		"Cette seconde phase s'arrête, puisque le nombre de cartes que les joueurs ont en main décroît strictement à chaque levée. La manche se termine lorsque les 2 joueurs n'ont plus de carte en main.\n S'il reste des cartes à l'un et pas à l'autre, c'est que l'un ou l'autre a triché, ou qu'il y a une erreur dans le programme.\r\n" + 
        		"\r\n" + 
        		"À la fin de la manche, on compte les plis de chacun (ou d'un seul des deux, les plus fûtés voient pourquoi) et chacun se voit attribuer par l'arbitre le nombre de points correspondant.\r\n" + 
        		"En général, l'arbitre est l'un des deux joueurs, l'autre surveille simplement les comptes.\r\n" + 
        		"Au cours des manches successives, chaque joueur donne tour à tour.\r\n" + 
        		"La partie se joue en un nombre de points ou en un nombre de manches qui a été fixé à l'avance (par exemple 100 points, ou 12 manches).\n Dans le cas où le nombre de manches est fixé, il faut choisir un nombre pair, car le donneur a un certain avantage. ");
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
