package Vue;/*
 * Morpion pédagogique

 * Copyright (C) 2016 Guillaume Huard

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

 * Contact: Guillaume.Huard@imag.fr
 *          Laboratoire LIG
 *          700 avenue centrale
 *          Domaine universitaire
 *          38401 Saint Martin d'Hères
 */

import Modele.Jeu;
import java.awt.*;

import javax.swing.*;

public class InterfaceGraphique implements Runnable {

    Jeu j;
    CollecteurEvenements control;
    menuBar menuBar;

    InterfaceGraphique(Jeu jeu, CollecteurEvenements c) {
        j = jeu;
        control = c;
        
    }

    public static void demarrer(Jeu j, CollecteurEvenements control) {
        SwingUtilities.invokeLater(new InterfaceGraphique(j, control));
    }

    @Override
    public void run() {
        //Creation de la fenetre globale
        JFrame frame = new JFrame("Bridge Chinois");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension d = new Dimension(screenSize.width / 25, screenSize.height / 10);
        Menu m = new Menu(j, d);
        Plateau pl = new Plateau(j, control, m, d,frame);
        menuBar = new menuBar(control,frame, j, pl);
        frame.add(pl);
        frame.add(m);

        frame.setJMenuBar(menuBar);

        Timer chrono = new Timer(16, new AdaptateurTemps(control));
        chrono.start();
        frame.setIconImage(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("Back Blue 1.png")).getImage());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

    }
}
