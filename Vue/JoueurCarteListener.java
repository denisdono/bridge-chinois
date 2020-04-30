/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author dodee
 */
public class JoueurCarteListener extends MouseAdapter{
        int indice;
	CollecteurEvenements control;

	JoueurCarteListener(int indice, CollecteurEvenements c) {
		this.indice = indice;
		this.control = c;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		control.clicSouris(indice);
                System.out.println("indice carte "+indice);
		
	}
}
