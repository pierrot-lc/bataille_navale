package bataille_navale.partie;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import bataille_navale.affichage.Bouton;
import bataille_navale.affichage.BoutonPanel;
import bataille_navale.fonctionnement.Joueur;

public class PartiePanelOpposant extends JPanel {
	private static final long serialVersionUID = -818240102716280909L;
	
	private BoutonPanel panCarte;
	private Joueur joueur;
	private int nbrBat[][];
	
	private JLabel warning = new JLabel();
	private JLabel lblBateaux = new JLabel();
	private JLabel lblJoueur;
	private JLabel lblNbrBat[];
	
	private JPanel panNorth = new JPanel();
	private JPanel panWest = new JPanel();
	private JPanel panAnimation = null;
	
	//Constructeurs
	public PartiePanelOpposant(Joueur joueur) {
		this.joueur = joueur;
		this.nbrBat = joueur.getCartePossedee().getNbrBat();
		
		initPanel();
	}
	public PartiePanelOpposant(Joueur joueur, JPanel panAnimation) {
		this.joueur = joueur;
		this.nbrBat = joueur.getCartePossedee().getNbrBat();
		
		this.panAnimation = panAnimation;
		
		initPanel();
	}
	
	private void initPanel() {
		this.removeAll();
		panCarte = new BoutonPanel(Bouton.transformerCarte(joueur.getCartePossedee()));
		
		//panNorth
		panNorth.setPreferredSize(new Dimension(600, 100));
		lblJoueur = new JLabel(joueur.getName());
		panNorth.add(lblJoueur);
		panNorth.add(warning);
		
		//panWest
		panWest.setPreferredSize(new Dimension(100, 500));
		
		String strBateaux = "Mes bateaux";
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
		
		if(panAnimation != null)
			panWest.add(panAnimation);
		
		//Colle tout les panels
		this.setLayout(new BorderLayout());
		this.add(panNorth, BorderLayout.NORTH);
		this.add(panCarte, BorderLayout.CENTER);
		this.add(panWest, BorderLayout.WEST);
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
		
		panCarte.checkPanel(joueur.getCartePossedee());
		
		this.revalidate();
	}
	
	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}
	public Joueur getJoueur() {
		return this.joueur;
	}
}
