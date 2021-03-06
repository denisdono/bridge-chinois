/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author dodee
 */
public class FenetreAvertissement extends JFrame {
    private String msg;
    private Dimension d;
    public FenetreAvertissement(String msg, Dimension d){
        this.d = d;
        this.msg = msg;
         JPanel pan = new JPanel();
        //pan.setLayout(new GridLayout(2,1));
        JLabel msgLabel = new JLabel(msg);
        msgLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pan.add(msgLabel);
        JButton btOk = new JButton("OK");
        btOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
        pan.add(btOk);
        add(pan);
        init();
    }
    
    public FenetreAvertissement(String msg, Dimension d, Plateau p){
        this.d = d;
        this.msg = msg;
         JPanel pan = new JPanel();
         this.setAlwaysOnTop(true);
        //pan.setLayout(new GridLayout(2,1));
        JLabel msgLabel = new JLabel(msg);
        msgLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pan.add(msgLabel);
        JButton btOk = new JButton("OK");
        btOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                JoueurCarteListener.active=true;
                p.miseAJour();
            }
        });
        pan.add(btOk);
        add(pan);
        init();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    
    private void init(){
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - d.width) / 2 - 60);
        int y = (int) ((dimension.getHeight() - d.height) / 2 - 60);
        setLocation(x, y);
        this.setTitle("Avertissement"); // definitions de la fenetre
        this.setSize(d);
        this.setIconImage(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("info.png")).getImage());
        //setLocationRelativeTo(null);
        setVisible(true);
    }
}
