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

public class ReglesWindow extends JFrame {

    public ReglesWindow() {
        this.setTitle("Règles");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) (screenSize.getWidth() / 3 * 2.5), (int) screenSize.getHeight() / 3 * 2);
        JPanel reglePane = new JPanel();
        reglePane.setLayout(new BoxLayout(reglePane, BoxLayout.PAGE_AXIS));

        JLabel regles = new JLabel();
        regles.setText("<html><h1 style=\"text-decoration:underline;\">Règles</h1><h2>Principe</h2> Le bridge chinois est un jeu de cartes à 2 joueurs.<br>"
                + "On utilise un jeu de 52 cartes. L'ordre des cartes et des couleurs est le même qu'au bridge (as, roi, dame, valet, dix, ...deux, et pique, coeur, carreau, trèfle).<br>"
                + "Les joueurs recoivent 11 cartes chacun, les autres cartes sont réparties en 6 piles de 5 cartes, avec la première de chaque piles face visible.<br>  L'atout est fixé par la carte découverte la plus forte. Dans le cas où aucune de ces cartes n'est supérieure ou égale à 10, la manche se joue sant atout.<br>"
                + "<h2>But du jeu</h2>Le but du jeu est de réaliser le plus de plis possible.<br> Chaque pli est constitué de 2 cartes, chacune posée par un joueur, choisie parmi les cartes qu'il a en main.<br> Le pli est gagné par le joueur qui:<br>"
                + "<ul><li> a posé la carte la plus forte, si les deux cartes sont de même couleur,</li>"
                + "<li>a coupé (joué un atout) si l'une des deux cartes est un atout,</li>"
                + "<li>a posé la première carte, si les deux cartes sont de couleur différente et qu'aucune n'est un atout (on dit que le second joueur s'est défaussé).</li></ul>"
                + "<h2>Déroulement</h2>Le donneur a initialement la main.<br>"
                + "Le joueur qui gagne le pli prend (ou garde) la main.<br>"
                + "Le joueur qui a la main choisit une de ses cartes et la pose sur le tapis. L'autre joueur, s'il le peut, doit fournir : jouer une carte de la même couleur.<br> S'il ne peut pas fournir, il a le choix entre couper ou se défausser. Donc, on n'est pas obligé de couper.<br>"
                + "Si la manche se joue sans atout, le second joueur ne peut bien sûr que se défausser.<br>"
                + "Puis, le joueur qui a gagné le pli choisit l'une des cartes découvertes (s'il en reste) et la place dans sa main.<br> L'autre joueur choisit à son tour une carte découverte.<br><br>"
                + "Dans la première phase de la manche, les 2 joueurs ont ainsi toujours 11 cartes en main.<br>"
                + "Lorsque les piles sont épuisées, la manche continue selon les mêmes règles, mais le nombre de cartes en main diminue.<br>"
                + "La manche se termine lorsqu'il ne reste plus de cartes.<br>"
                + "<h2>Comptage des points</h2>À la fin de la manche, on compte les plis et chacun se voit attribuer le nombre de points correspondant (1 point par pli).<br>"
                + "La partie se joue en un nombre de points ou en un nombre de manches qui a été fixé à l'avance (par exemple 100 points, ou 12 manches).<br>Dans le cas oû le nombre de manches est fixé, il est préférable de choisir un nombre pair, car le donneur a un certain avantage.<br><br><br><html> ");
        reglePane.add(regles);
        JScrollPane scrollPane = new JScrollPane(reglePane);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        this.add(scrollPane);
        setLocationRelativeTo(null);
    }

    public void montrer() {
        this.setVisible(true);
    }
}
