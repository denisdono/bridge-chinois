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
        this.setTitle("Règles");
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
        regles.setText("\n\tLe bridge chinois est un jeu de cartes à 2 joueurs.\r\n\t" + 
        		"On utilise un jeu de 52 cartes. L'ordre des cartes et des couleurs est le même qu'au bridge (as, roi, dame, valet, dix, ...deux, et pique, coeur, carreau, trèfle).\r\n\t" + 
        		"Les joueurs recoivent 11 cartes chacun, les autres cartes sont répartient en 6 piles de 5 cartes, dont la carte du sommet et face visible.\n\t Si toutes les cartes visibles ont une valeur inférieure à 10, la manche est jouée sans atout. Sinon, l'atout est fixé par la carte découverte la plus forte.\r\n\t" + 
        		"\r\n\t" + 
        		"Le but du jeu est de réaliser le plus de pli possible.\n\t Chaque pli va consister en 2 cartes, chacune posée par un joueur, choisie parmi les cartes qu'il a en main.\n\t Chaque pli est gagné et ramassé par le joueur :\r\n\t" + 
        		"    - qui a posé la carte la plus forte, si les deux cartes sont de même couleur,\r\n\t" + 
        		"    - qui a coupé (joué un atout) si l'une des deux cartes est un atout,\r\n\t" + 
        		"    - qui a posé la premiére carte, si les deux cartes sont de couleur différente et qu'aucune n'est un atout (on dit que le second joueur s'est défaussé).\r\n\t" + 
        		"\r\n\t" + 
        		"Le donneur a initialement la main.\r\n\t" + 
        		"Le joueur qui gagne le pli prend (ou garde) la main.\r\n\t" + 
        		"Le joueur qui a la main choisit une des cartes de sa main, et la pose sur le tapis. L'autre joueur, s'il le peut, doit fournir : jouer une carte de la même couleur.\n\t S'il ne peut pas fournir, il a le choix entre couper ou se défausser. Donc, on n'est pas obligé de couper.\r\n\t" + 
        		"Si la manche se joue sans atout, le second joueur ne peut bien sûr que se défausser.\r\n\t" + 
        		"Le joueur qui a gagné le pli le ramasse. Puis il choisit l'une des cartes découvertes (s'il en reste) et la place dans sa main.\n\t L'autre joueur choisit à son tour une carte découverte.\r\n\t" + 
        		"Dans la première phase de la manche, les 2 joueurs ont ainsi toujours 11 cartes en main.\r\n\t" + 
        		"\r\n\t" + 
        		"Lorsque les piles sont épuisées, la manche continue selon les mêmes règles, mais le nombre de carte en main diminue.\r\n\t" + 
        		"La manche se termine l'orsqu'il ne reste plus de carte en main.\r\n\t" + 
        		"\r\n\t" + 
        		"à la fin de la manche, on compte les plis et chacun se voit attribuer le nombre de points correspondant (1 point par pli).\r\n\t" + 
        		"La partie se joue en un nombre de points ou en un nombre de manches qui a été fixé à l'avance (par exemple 100 points, ou 12 manches).\n\tDans le cas oû le nombre de manches est fixé, il faut choisir un nombre pair, car le donneur a un certain avantage. ");
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
