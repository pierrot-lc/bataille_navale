package bataille_navale.partie;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bataille_navale.affichage.Bouton;
import bataille_navale.affichage.BoutonPanel;
import bataille_navale.contenu.Pos;
import bataille_navale.fonctionnement.Joueur;

public class PartiePanel extends JPanel {
	private static final long serialVersionUID = 5267980910497056707L;
	//Variables
	private BoutonPanel panCarteAffichee, panOwnCarte;
	private Joueur joueur;
	private int nbrBat[][];
	
	private JLabel warning = new JLabel();
	private JLabel lblBateaux = new JLabel();
	private JLabel lblJoueur;
	private JLabel lblNbrBat[];
	
	private JPanel panNorth = new JPanel();
	private JPanel panWest = new JPanel();
	private JPanel panAnimation = null;
	
	private JButton drawOwnCarte = new JButton("Afficher ma carte");
	
	private boolean cartePossedee = false;//Si la carte affichée est celle du joueur ou celle qu'il doit mitrailler
	private int coup = -1;
	
	private LocalControleur control;
	
	//Constructeurs
	public PartiePanel(Joueur joueur) {
		this.joueur = joueur;
		this.nbrBat = joueur.getCarteAffichee().getNbrBat();
		control = new LocalControleur(joueur);
		
		initPanel();
	}
	public PartiePanel(Joueur joueur, JPanel panAnimation) {
		this.joueur = joueur;
		this.nbrBat = joueur.getCarteAffichee().getNbrBat();
		control = new LocalControleur(joueur);
		
		this.panAnimation = panAnimation;
		
		initPanel();
	}
	
	private void initPanel() {
		this.removeAll();
		panCarteAffichee = new BoutonPanel(Bouton.transformerCarte(joueur.getCarteAffichee()));
		panOwnCarte = new BoutonPanel(Bouton.transformerCarte(joueur.getCartePossedee()));
		
		//panNorth
		panNorth.setPreferredSize(new Dimension(600, 100));
		lblJoueur = new JLabel(joueur.getName());
		panNorth.add(lblJoueur);
		panNorth.add(warning);
		
		//panWest
		panWest.setPreferredSize(new Dimension(100, 500));
		
		String strBateaux = "";
		if(cartePossedee)//Le label dépends de si on regarde notre carte ou pas
			strBateaux = "Mes bateaux";
		else
			strBateaux = "Bateaux à détruire";
		lblBateaux.setText(strBateaux);
		panWest.add(lblBateaux);
		
		lblNbrBat = new JLabel[nbrBat.length];
		for(int i = 0; i < nbrBat.length; i++) {
			String str;
			if(nbrBat[i][0] == 1)//Si une case, phrase au singulier
				str = nbrBat[i][0] + " case : " + nbrBat[i][1] + "/" + nbrBat[i][2];
			else
				str = nbrBat[i][0] + " cases : " + nbrBat[i][1] + "/" + nbrBat[i][2];
			
			lblNbrBat[i] = new JLabel(str);
			lblNbrBat[i].setPreferredSize(new Dimension(75, 30));
			panWest.add(lblNbrBat[i]);
		}

		panWest.add(drawOwnCarte);//Bouton pour voir sa propre carte
		
		if(panAnimation != null)
			panWest.add(panAnimation);
		
		//Colle tout les panels
		this.setLayout(new BorderLayout());
		this.add(panNorth, BorderLayout.NORTH);
		this.add(panCarteAffichee, BorderLayout.CENTER);
		this.add(panWest, BorderLayout.WEST);
		
		//Listeners
		drawOwnCarte.addActionListener(new OwnCarteListener());
	}
		
	public void paintComponent(Graphics g) {//Actualise l'affichage
		for(int i = 0; i < nbrBat.length; i++) {//Actualise les labels
			String str;
			if(nbrBat[i][0] == 1)//Si une case, phrase au singulier
				str = nbrBat[i][0] + " case : " + nbrBat[i][1] + "/" + nbrBat[i][2];
			else
				str = nbrBat[i][0] + " cases : " + nbrBat[i][1] + "/" + nbrBat[i][2];
			
			if(!lblNbrBat[i].getText().equals(str))
				lblNbrBat[i].setText(str);
		}
		
		if(cartePossedee) {//Le label dépends de si on regarde notre carte ou pas
			lblBateaux.setText("Mes bateaux");
			drawOwnCarte.setText("OK");
			panOwnCarte.checkPanel(joueur.getCartePossedee());
			this.remove(panCarteAffichee);
			this.add(panOwnCarte, BorderLayout.CENTER);
		}
		else {
			lblBateaux.setText("Bateaux à détruire");
			drawOwnCarte.setText("Ma carte");
			panCarteAffichee.checkPanel(joueur.getCarteAffichee());
			this.remove(panOwnCarte);
			this.add(panCarteAffichee, BorderLayout.CENTER);
		}
		
		this.revalidate();
	}

	public void setListener(boolean b) {
		if(b)
			addListener(panCarteAffichee.getTabBouton());
		else
			delListener(panCarteAffichee.getTabBouton());
	}
	private void addListener(Bouton tabBouton[][]) {
		for(int i = 0; i < tabBouton.length; i++)
			for(int j = 0; j < tabBouton[i].length; j++)
				tabBouton[i][j].addActionListener(new BoutonListener());
	}
	private void delListener(Bouton tabBouton[][]) {
		for(int i = 0; i < tabBouton.length; i++) {
			for(int j = 0; j < tabBouton[i].length; j++) {
				ActionListener al[] = tabBouton[i][j].getActionListeners();//Récupère les listeners du bouton
				for(int k = 0; k < al.length; k++)
					tabBouton[i][j].removeActionListener(al[k]);//Les enlèves les un après les autres
			}
		}
	}
	
	public void initCoup() {
		coup = -1;
	}
	
	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}
	public Joueur getJoueur() {
		return this.joueur;
	}
	public int getCoup() {
		return this.coup;
	}
	
	//Listeners
	class OwnCarteListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if(!cartePossedee) {//Affiche la carte possédée
				nbrBat = joueur.getCartePossedee().getNbrBat();
				cartePossedee = true;
			}
			else {//Affiche la vraie carte à mitrailler
				nbrBat = joueur.getCarteAffichee().getNbrBat();
				cartePossedee = false;
			}
			repaint();
		}
	}
	
	class BoutonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if(cartePossedee)//Si on est en mode de vue de sa propre carte on s'arrête là
				return;
			if(!(event.getSource() instanceof Bouton))
				return;
			
			Bouton bouton = (Bouton)event.getSource();
			Pos pos = bouton.getCase().getPos();
			
			coup = control.tir(pos);
			switch(coup) {
			case 0://Case mer touchée
				warning.setText("Raté !");
				repaint();
				break;
			case 1://Bateau coulé
				warning.setText("Coulé !");
				repaint();
				break;
			case 2://Bateau touché
				warning.setText("Touché !");
				repaint();
				break;
			case 3://Case bateau plouf déjà connue
				break;
			case 4://Case mer déjà connue
				break;
			case 5://Case bateauPloufant déjà connue
				break;
			case -1://Erreur
				warning.setText("Erreur");
				break;
			}
		}
	}
}
