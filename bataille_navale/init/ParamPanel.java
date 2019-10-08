package bataille_navale.init;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ParamPanel extends JPanel {
	private static final long serialVersionUID = -2793898286180036449L;
	
	private JLabel lblTaille = new JLabel("Taille de la carte", SwingConstants.CENTER);
	private JLabel lblWidth = new JLabel("Largeur :");
	private JLabel lblHeight = new JLabel("Longueur :");
	private JLabel lblNbrBat = new JLabel("Nombre de bateaux", SwingConstants.CENTER);
	
	private JComboBox<String> comboWidth = new JComboBox<String>();
	private JComboBox<String> comboHeigth = new JComboBox<String>();
	
	private JTextField tfNbrCase = new JTextField();
	private JTextField tfNbrBat = new JTextField();
	
	private JTextArea textArea = new JTextArea(3, 25);
	private JScrollPane scrollPane = new JScrollPane(textArea);
	
	private JButton validateButton = new JButton("OK");
	private JButton okButton = new JButton("OK");
	private JButton reinitButton = new JButton("Réinitialiser");
	private JButton defaultButton = new JButton("Défaut");
	
	private JPanel panWidthCarte = new JPanel();
	private JPanel panHeightCarte = new JPanel();
	private JPanel panNbrBat = new JPanel();
	private JPanel panText = new JPanel();
	
	private boolean running = true;
	
	private RegleCarte regleCarte = new RegleCarte();//Pour effectuer les vérifications
	//et contenir tous les paramètres de la partie
	
	public ParamPanel() {
		this.setSize(new Dimension(320, 320));
		initPanel();
	}
	
	private void initPanel() {
		//Haut du panel, tout ce qui concerne la taille de la carte
		lblTaille.setPreferredSize(new Dimension(this.getWidth(), 20));
		lblWidth.setPreferredSize(new Dimension(this.getWidth()*20/100, 20));
		
		panWidthCarte.add(lblWidth);
		panWidthCarte.add(comboWidth);
		panWidthCarte.setPreferredSize(new Dimension(this.getWidth(), 30));
		
		
		lblHeight.setPreferredSize(new Dimension(this.getWidth()*20/100, 20));
		panHeightCarte.add(lblHeight);
		panHeightCarte.add(comboHeigth);
		panHeightCarte.setPreferredSize(new Dimension(this.getWidth(), 30));
		
		this.add(lblTaille);
		this.add(panHeightCarte);
		this.add(panWidthCarte);
		
		//Bas du panel, selection du nombre de bateaux
		lblNbrBat.setPreferredSize(new Dimension(this.getWidth(), 20));
		
		tfNbrCase.setPreferredSize(new Dimension(30, 30));
		tfNbrBat.setPreferredSize(new Dimension(30, 30));
		
		tfNbrCase.setText("1");
		tfNbrBat.setText("1");
		
		panNbrBat.add(tfNbrBat);
		panNbrBat.add(new JLabel("bateaux de"));
		panNbrBat.add(tfNbrCase);
		panNbrBat.add(new JLabel("cases"));
		panNbrBat.add(validateButton);
		panNbrBat.setPreferredSize(new Dimension(this.getWidth(), 40));
		
		panText.add(scrollPane);
		panText.add(reinitButton);
		panText.add(defaultButton);
		panText.add(okButton);
		panText.setPreferredSize(new Dimension(this.getWidth(), 100));
		
		//Ajout des panels sur le panel principal
		this.add(lblNbrBat);
		this.add(panNbrBat);
		this.add(panText);
		
		//Listeners
		okButton.addActionListener(new BoutonListener());
		validateButton.addActionListener(new ValidateListener());
		reinitButton.addActionListener(new ReinitListener());
		defaultButton.addActionListener(new DefaultListener());
		comboWidth.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int w = Integer.parseInt(comboWidth.getSelectedItem().toString());
				regleCarte.setWidthCarte(w);
			}
		});
		comboHeigth.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int h = Integer.parseInt(comboHeigth.getSelectedItem().toString());
				regleCarte.setWidthCarte(h);
			}
		});
		
		scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);//Désactive la scrollbar horizontale
		textArea.setEditable(false);
		
		//Initialise les combobox
		for(int i = 4; i <= 15; i++) {//Ajoute les tailles aux comboBox
			comboWidth.addItem(String.valueOf(i));
			comboHeigth.addItem(String.valueOf(i));
		}
		
		defaultParam();//Paramètres par défaut
	}
	
	public int getWidthCarte() {
		return regleCarte.getWidthCarte();
	}
	public int getHeightCarte() {
		return regleCarte.getHeightCarte();
	}
	
	public int[][] getNbrBat() {
		regleCarte.finaliserNbrBat();//Initialise le tableau pour qu'il soit utilisable
		return regleCarte.getNbrBat();
	}
	
	public boolean isRunning() {
		return this.running;
	}
	public void setRunning() {
		this.running = true;
	}
	//Privates
	private void defaultParam() {
		regleCarte.defaultParam();//Paramètres de partie par défaut
		int temp[][] = regleCarte.getNbrBat();
		
		comboWidth.setSelectedIndex(6);//10
		comboHeigth.setSelectedIndex(6);//10
		
		textArea.setText(null);
		
		int i = 0;
		while(temp[i][0] > 0) {
			String bat = "bateau";
			if(temp[i][1] > 1)//Cas pluriel de la phrase
				bat = "bateaux";
			textArea.append(temp[i][1] + " " + bat + " de " + temp[i][0] + " cases\n");
			i++;
		}
		textArea.append("Paramètres par défaut\n");
	}
	
	//Listeners
	class ValidateListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if(tfNbrBat.getText().equals("") || tfNbrCase.getText().equals(""))
				return;//Cas où les entrées sont vides
			
			int nbrBat = Integer.parseInt(tfNbrBat.getText());
			int tailleBat = Integer.parseInt(tfNbrCase.getText());
			int result = regleCarte.addBateaux(tailleBat, nbrBat);//Récupère l'action réalisée par RegleCarte
			
			String bat = "bateau";
			if(nbrBat > 1)
				bat = "bateaux";
			switch(result) {
			case 1://Paramètre enregistré
				textArea.append(nbrBat + " " + bat + " de " + tailleBat + " cases\n");
				break;
			case 2://Paramètre modifié(doublon)
				textArea.append(nbrBat + " " + bat + " de " + tailleBat + " cases (remplacement)\n");
				break;
			case -1://Nombre de bateau == 0
				textArea.append("Nombre de bateau invalide\n");
				break;
			case -2://Taille du bateau > taille de la carte
				textArea.append("Trop grand par rapport à la taille de la carte !\n");
				break;
			case -3://Trop de bateaux pour la carte
				textArea.append("Trop de bateaux (plus de 70% de place prise)\n");
				break;
			}			
			tfNbrBat.setText("");
			tfNbrCase.setText("");
		}
	}
	class BoutonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			running = false;
		}
	}
	class DefaultListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			defaultParam();
		}
	}
	class ReinitListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			regleCarte.reinitParam();
			
			comboWidth.setSelectedIndex(0);//4
			comboHeigth.setSelectedIndex(0);//4			
			textArea.setText(null);//Vide
		}
	}
}
