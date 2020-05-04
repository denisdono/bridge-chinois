/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

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
        @Override
        public void mouseEntered(MouseEvent e){
            //((JLabel)e.getSource()).setPreferredSize(new Dimension(60,90));
            ((JLabel)e.getSource()).setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        }
        @Override
        public void mouseExited(MouseEvent e){
            //((JLabel)e.getSource()).setPreferredSize(new Dimension(50,80));

            ((JLabel)e.getSource()).setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            
        }
}
