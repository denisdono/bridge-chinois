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
        public static boolean active = true;
        private int indice;
	private CollecteurEvenements control;
        private Dimension dim;

	JoueurCarteListener(int indice, CollecteurEvenements c, Dimension d) {
		this.indice = indice;
		this.control = c;
                this.dim = d;
	}

	@Override
	public void mousePressed(MouseEvent e) {
                if(active)
                    control.clicSouris(indice);
		
	}
        @Override
        public void mouseEntered(MouseEvent e){
            if(active)
        	((JLabel)e.getSource()).setSize(new Dimension(((JLabel)e.getSource()).getSize().width,((JLabel)e.getSource()).getSize().height+15));
            
        }
        
        
        @Override
        public void mouseExited(MouseEvent e){
                if(active)
                    ((JLabel)e.getSource()).setSize(dim);
        }
}
