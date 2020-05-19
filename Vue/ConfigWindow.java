package Vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
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

import Modele.Jeu;
import Patterns.Observateur;
import javax.swing.ImageIcon;

public class ConfigWindow extends JFrame implements Observateur {
	JPanel hisPanel;
	ButtonGroup diffGroup;
	ButtonGroup condGroup;
	ButtonGroup showGroup;
	ButtonGroup dosGroup;
	ButtonGroup fondGroup;
	Jeu jeu;
	Plateau pl;
	// variables des configurations
	int selDiff;
	int selWin;
	int value;
	int showCarte;
	int selCarte;
	int selFond;

	public ConfigWindow(Jeu jeu, Plateau pl) {
		this.jeu = jeu;
		this.pl = pl;
		// recuperations des infos du fichier config
		try {
			BufferedReader br = new BufferedReader(new FileReader("config"));
			selDiff = Integer.parseInt(br.readLine());
			if (selDiff < 0 || selDiff > 2) {
				selDiff = 0;
			}
			selWin = Integer.parseInt(br.readLine());
			if (selWin < 0 || selWin > 1) {
				selWin = 0;
			}
			value = Integer.parseInt(br.readLine());
			if (value <= 0) {
				if (selWin == 1)
					value = 12;
				else
					value = 100;
			}
			showCarte = Integer.parseInt(br.readLine());
			if (showCarte < 0 || showCarte > 1) {
				showCarte = 0;
			}

			selCarte = Integer.parseInt(br.readLine());
			if (selCarte < 0 || selCarte > 2) {
				selCarte = 0;
			}
			selFond = Integer.parseInt(br.readLine());
			if (selFond < 0 || selFond > 2) {
				selFond = 0;
			}
			br.close();
		} catch (IOException e) {// si le fichier n'est pas trouv� on en cr�e un avec les configurations de
									// base
			try {
				File confile = new File("config");
				if (confile.createNewFile()) {
					System.out.println("Cr�ation d'un fichier config.");
					FileWriter myWriter = new FileWriter("config");
					myWriter.write("0\n0\n100\n0\n0\n0\n");
					myWriter.close();
					selDiff = 0;
					selWin = 0;
					value = 100;
					showCarte = 0;
					selCarte = 0;
					selFond = 0;
				}
			} catch (IOException e1) {// la cr�ation du fichier a echou�
				System.out.println("impossible de cr�er un fichier.");
			}
		}

		this.setTitle("Configuration"); // definitions de la fenetre
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(450, 600);
                this.setIconImage(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("iconeparam.png")).getImage());

		hisPanel = new JPanel();
		hisPanel.setLayout(new BoxLayout(hisPanel, BoxLayout.PAGE_AXIS));
		setLocationRelativeTo(null);
		miseAJour();
	}

	@Override
	public void miseAJour() { // cr�ation es diffenrentes parties de la fenetre
		JLabel titre = new JLabel("Configurations");
		titre.setFont(new Font("Calibri", Font.PLAIN, 24));
		hisPanel.add(titre);
		if(jeu==null || jeu.getIA()) {
			JPanel diff = new JPanel();
			diff.setBorder(BorderFactory.createTitledBorder("Difficulté IA :"));
			diffGroup = new ButtonGroup();
			JRadioButton facile = new JRadioButton("facile");
			JRadioButton moyenne = new JRadioButton("moyenne");
			JRadioButton difficile = new JRadioButton("difficile");
			facile.setActionCommand("0");
			moyenne.setActionCommand("1");
			difficile.setActionCommand("2");
			switch (selDiff) {
			case 0:
				facile.setSelected(true);
				break;
			case 1:
				moyenne.setSelected(true);
				break;
			case 2:
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
		}
		JPanel cond = new JPanel();
		cond.setLayout(new BoxLayout(cond, BoxLayout.PAGE_AXIS));
		cond.setBorder(BorderFactory.createTitledBorder("Condition de victoire :"));
		JPanel radioP = new JPanel();
		JPanel valueP = new JPanel();
		condGroup = new ButtonGroup();
		JRadioButton point = new JRadioButton("Nombre de point max");
		JRadioButton manche = new JRadioButton("Nombre de manches");
		point.setActionCommand("0");
		manche.setActionCommand("1");

		switch (selWin) {
		case 0:
			point.setSelected(true);
			break;
		case 1:
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

		JPanel show = new JPanel();
		show.setBorder(BorderFactory.createTitledBorder("Montrer les cartes de l'adversaire :"));
		showGroup = new ButtonGroup();
		JRadioButton yes = new JRadioButton("oui");
		JRadioButton no = new JRadioButton("non");
		yes.setActionCommand("0");
		no.setActionCommand("1");

		switch (showCarte) {
		case 0:
			yes.setSelected(true);
			break;
		case 1:
			no.setSelected(true);
			break;
		}
		showGroup.add(yes);
		showGroup.add(no);

		show.add(yes);
		show.add(no);
		hisPanel.add(show);

		JPanel dos = new JPanel();
		dos.setBorder(BorderFactory.createTitledBorder("Dos de carte :"));
		dosGroup = new ButtonGroup();
		JRadioButton carte1 = new JRadioButton();
		JLabel imgc1 = new JLabel(new ImageIcon(
                new ImageIcon(ClassLoader.getSystemClassLoader().getResource("Back0.png")).getImage()
                .getScaledInstance(57, 88, Image.SCALE_SMOOTH)));
		//imgc1.setPreferredSize(new Dimension(57,88));
		imgc1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				carte1.setSelected(true);
			}
		});
		
		
		JRadioButton carte2 = new JRadioButton();
		JLabel imgc2 = new JLabel(new ImageIcon(
                new ImageIcon(ClassLoader.getSystemClassLoader().getResource("Back1.png")).getImage()
                .getScaledInstance(57, 88, Image.SCALE_SMOOTH)));
		//imgc2.setPreferredSize(new Dimension(57,88));
		imgc2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				carte2.setSelected(true);
			}
		});
		
		JRadioButton carte3 = new JRadioButton();
		JLabel imgc3 = new JLabel(new ImageIcon(
                new ImageIcon(ClassLoader.getSystemClassLoader().getResource("Back2.png")).getImage()
                .getScaledInstance(57, 88, Image.SCALE_SMOOTH)));
		//imgc3.setPreferredSize(new Dimension(57,88));
		imgc3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				carte3.setSelected(true);
			}
		});
		
		carte1.setActionCommand("0");
		carte2.setActionCommand("1");
		carte3.setActionCommand("2");
		switch (selCarte) {
		case 0:
			carte1.setSelected(true);
			break;
		case 1:
			carte2.setSelected(true);
			break;
		case 2:
			carte3.setSelected(true);
			break;
		}
		dosGroup.add(carte1);
		dosGroup.add(carte2);
		dosGroup.add(carte3);
		dos.add(carte1);
		dos.add(imgc1);
		dos.add(carte2);
		dos.add(imgc2);
		dos.add(carte3);
		dos.add(imgc3);

		hisPanel.add(dos);

		JPanel fond = new JPanel();
		fond.setBorder(BorderFactory.createTitledBorder("Fond de plateau :"));
		fondGroup = new ButtonGroup();
		JRadioButton fond1 = new JRadioButton();
		JLabel imgf1 = new JLabel(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("Background0.jpg")));
		imgf1.setPreferredSize(new Dimension(100,50));
		imgf1.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		imgf1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				fond1.setSelected(true);
			}
		});
		
		JRadioButton fond2 = new JRadioButton();
		JLabel imgf2 = new JLabel(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("Background1.jpg")));
		imgf2.setPreferredSize(new Dimension(100,50));
		imgf2.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		imgf2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				fond2.setSelected(true);
			}
		});
		JRadioButton fond3 = new JRadioButton();
		JLabel imgf3 = new JLabel(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("Background2.jpg")));
		imgf3.setPreferredSize(new Dimension(100,50));
		imgf3.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		imgf3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				fond3.setSelected(true);
			}
		});
		fond1.setActionCommand("0");
		fond2.setActionCommand("1");
		fond3.setActionCommand("2");

		switch (selFond) {
		case 0:
			fond1.setSelected(true);
			break;
		case 1:
			fond2.setSelected(true);
			break;
		case 2:
			fond3.setSelected(true);
			break;
		}

		fondGroup.add(fond1);
		fondGroup.add(fond2);
		fondGroup.add(fond3);
		fond.add(fond1);
		fond.add(imgf1);
		fond.add(fond2);
		fond.add(imgf2);
		fond.add(fond3);
		fond.add(imgf3);

		hisPanel.add(fond);

		JPanel end = new JPanel();
		JButton cancel = new JButton("annuler");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {// ferme sans modifier le fichier de config
				setVisible(false);
				dispose();
			}
		});
		JButton valid = new JButton("valider"); // boutton de validation qui stock toutes les infos selection�s dans
												// un
												// fichier config
		valid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(jeu==null || jeu.getIA()) {
					selDiff = Integer.parseInt(diffGroup.getSelection().getActionCommand());
				}
				selWin = Integer.parseInt(condGroup.getSelection().getActionCommand());
				value = Integer.parseInt(valueField.getValue().toString());
				showCarte = Integer.parseInt(showGroup.getSelection().getActionCommand());
				selCarte = Integer.parseInt(dosGroup.getSelection().getActionCommand());
				selFond = Integer.parseInt(fondGroup.getSelection().getActionCommand());
				try {
					FileWriter myWriter = new FileWriter("config");
					myWriter.write(selDiff + "\n" + selWin + "\n" + value + "\n" + showCarte + "\n" + selCarte + "\n"
							+ selFond + "\n");
					myWriter.close();
				} catch (IOException e1) {
					try {
						File confile = new File("config");
						if (confile.createNewFile()) {
							System.out.println("Création d'un fichier config.");
							FileWriter myWriter = new FileWriter("config");
							myWriter.write(selDiff + "\n" + selWin + "\n" + value + "\n" + showCarte + "\n" + selCarte
									+ "\n" + selFond + "\n");
							myWriter.close();
						}
					} catch (IOException e2) {
						System.out.println("impossible de cr�er un fichier.");
					}
				}
				if (jeu != null) {
					jeu.readConfig();
				}
				if(pl!=null) {
					pl.creerPlateau();
				}
				setVisible(false); // ferme la fenetre
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
