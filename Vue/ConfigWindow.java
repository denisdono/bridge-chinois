package Vue;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import Patterns.Observateur;

public class ConfigWindow extends JFrame implements Observateur {
	JPanel hisPanel;
	ButtonGroup diffGroup;
	ButtonGroup condGroup;

	ButtonGroup dosGroup;
	ButtonGroup fondGroup;
	int selDiff;
	int selWin;
	int value;
	int selCarte;
	int selFond;

	public ConfigWindow() {

		try {
			BufferedReader br = new BufferedReader(new FileReader("config"));
			selDiff = Integer.parseInt(br.readLine());
			if(selDiff<0||selDiff>2) {
				 selDiff=0;
			 }
			selWin = Integer.parseInt(br.readLine());
			if(selWin<0||selWin>1) {
				 selWin=0;
			 }
			value = Integer.parseInt(br.readLine());
			selCarte = Integer.parseInt(br.readLine());
			if(selCarte<0||selCarte>2) {
				 selCarte=0;
			 }
			selFond = Integer.parseInt(br.readLine());
			if(selFond<0||selFond>2) {
				 selFond=0;
			 }
			br.close();
		} catch (IOException e) {
			try {
				File confile = new File("config");
				if (confile.createNewFile()) {
					System.out.println("Cr�ation d'un fichier config.");
					FileWriter myWriter = new FileWriter("config");
					myWriter.write("0\n0\n100\n0\n0\n");
					myWriter.close();
					selDiff = 0;
					selWin = 0;
					value = 100;
					selCarte = 0;
					selFond = 0;
				}
			} catch (IOException e1) {
				System.out.println("impossible de cr�er un fichier.");
			}
		}
		this.setTitle("Configuration");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((int) screenSize.getWidth() / 5, (int) screenSize.getHeight() / 3 * 2);
		hisPanel = new JPanel();
		hisPanel.setLayout(new BoxLayout(hisPanel, BoxLayout.PAGE_AXIS));

		miseAJour();
	}

	@Override
	public void miseAJour() {
		JLabel titre = new JLabel("Configurations");
		titre.setFont(new Font("Calibri", Font.PLAIN, 24));
		hisPanel.add(titre);
		JPanel diff = new JPanel();
		diff.setBorder(BorderFactory.createTitledBorder("Difficult� IA :"));
		diffGroup = new ButtonGroup();
		JRadioButton facile = new JRadioButton("facile");
		JRadioButton moyenne = new JRadioButton("moyenne");
		JRadioButton difficile = new JRadioButton("difficile");
		facile.setActionCommand("0");
		moyenne.setActionCommand("1");
		difficile.setActionCommand("2");
		switch(selDiff) {
		case 0 : 
			facile.setSelected(true);
			break;
		case 1 :
			moyenne.setSelected(true);
			break;
		case 2 :
			difficile.setSelected(true);
			break;
		}
		diffGroup.add(facile);
		diffGroup.add(moyenne);
		diffGroup.add(difficile);
		diff.add(facile);
		diff.add(moyenne);
		diff.add(difficile);

		hisPanel.add(diff);

		JPanel cond = new JPanel();
		cond.setLayout(new BoxLayout(cond,BoxLayout.PAGE_AXIS));
		cond.setBorder(BorderFactory.createTitledBorder("Condition de victoire :"));
		JPanel radioP = new JPanel();
		JPanel valueP = new JPanel();
		condGroup = new ButtonGroup();
		JRadioButton point = new JRadioButton("Nombre de point max");
		JRadioButton manche = new JRadioButton("Nombre de manches");
		point.setActionCommand("0");
		manche.setActionCommand("1");
		
		switch(selWin) {
		case 0 : 
			point.setSelected(true);
			break;
		case 1 :
			manche.setSelected(true);
			break;
		}
		
		
		JFormattedTextField valueField = new JFormattedTextField();
		valueField.setValue(new Integer(value));
		valueField.setColumns(3);
		
		condGroup.add(point);
		condGroup.add(manche);
		
		radioP.add(point);
		radioP.add(manche);
		valueP.add(new JLabel("valeur : "));
		valueP.add(valueField);
		cond.add(radioP);
		cond.add(valueP);

		hisPanel.add(cond);

		JPanel dos = new JPanel();
		dos.setBorder(BorderFactory.createTitledBorder("Dos de carte :"));
		dosGroup = new ButtonGroup();
		JRadioButton carte1 = new JRadioButton("carte1");
		JRadioButton carte2 = new JRadioButton("carte2");
		JRadioButton carte3 = new JRadioButton("carte3");
		carte1.setActionCommand("0");
		carte2.setActionCommand("1");
		carte3.setActionCommand("2");
		switch(selCarte) {
		case 0 : 
			carte1.setSelected(true);
			break;
		case 1 :
			carte2.setSelected(true);
			break;
		case 2 :
			carte3.setSelected(true);
			break;
		}
		dosGroup.add(carte1);
		dosGroup.add(carte2);
		dosGroup.add(carte3);
		dos.add(carte1);
		dos.add(carte2);
		dos.add(carte3);

		hisPanel.add(dos);

		JPanel fond = new JPanel();
		fond.setBorder(BorderFactory.createTitledBorder("Fond de plateau :"));
		fondGroup = new ButtonGroup();
		JRadioButton fond1 = new JRadioButton("fond1");
		JRadioButton fond2 = new JRadioButton("fond2");
		JRadioButton fond3 = new JRadioButton("fond3");
		fond1.setActionCommand("0");
		fond2.setActionCommand("1");
		fond3.setActionCommand("2");
		
		switch(selFond) {
		case 0 : 
			fond1.setSelected(true);
			break;
		case 1 :
			fond2.setSelected(true);
			break;
		case 2 :
			fond3.setSelected(true);
			break;
		}
		
		
		fondGroup.add(fond1);
		fondGroup.add(fond2);
		fondGroup.add(fond3);
		fond.add(fond1);
		fond.add(fond2);
		fond.add(fond3);

		hisPanel.add(fond);

		JPanel end = new JPanel();
		JButton cancel = new JButton("annuler");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		JButton valid = new JButton("valider");
		valid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 selDiff = Integer.parseInt(diffGroup.getSelection().getActionCommand());
				 selWin = Integer.parseInt(condGroup.getSelection().getActionCommand());
				 value = Integer.parseInt(valueField.getValue().toString());
				 selCarte = Integer.parseInt(dosGroup.getSelection().getActionCommand());
				 selFond = Integer.parseInt(fondGroup.getSelection().getActionCommand());
				try {
					FileWriter myWriter = new FileWriter("config");
					myWriter.write(selDiff + "\n" + selWin + "\n" + value + "\n" + selCarte + "\n" + selFond + "\n");
					myWriter.close();
				} catch (IOException e1) {
					try {
						File confile = new File("config");
						if (confile.createNewFile()) {
							System.out.println("Cr�ation d'un fichier config.");
							FileWriter myWriter = new FileWriter("config");
							myWriter.write(
									selDiff + "\n" + selWin + "\n" + value + "\n" + selCarte + "\n" + selFond + "\n");
							myWriter.close();
						}
					} catch (IOException e2) {
						System.out.println("impossible de cr�er un fichier.");
					}
				}
				setVisible(false);
				dispose();
			}
		});

		end.add(cancel);
		end.add(valid);
		hisPanel.add(end);
		JScrollPane scrollPane = new JScrollPane(hisPanel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		this.add(scrollPane);
	}

	public void montrer() {
		this.setVisible(true);
	}

}
