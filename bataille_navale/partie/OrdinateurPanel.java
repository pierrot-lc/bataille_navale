package bataille_navale.partie;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import bataille_navale.affichage.Bouton;
import bataille_navale.affichage.BoutonPanel;
import bataille_navale.fonctionnement.Joueur;

public class OrdinateurPanel extends JPanel {
	private static final long serialVersionUID = 2802504575887370155L;
	
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
		
		private boolean cartePossedee = false;//Si la carte affichée est celle du joueur ou celle qu'il doit mitrailler
		private int coup = -1;
		
		//Constructeurs
		public OrdinateurPanel(Joueur ordinateur) {
			this.joueur = ordinateur;
			this.nbrBat = joueur.getCarteAffichee().getNbrBat();
			
			initPanel();
		}
		public OrdinateurPanel(Joueur ordinateur, JPanel panAnimation) {
			this.joueur = ordinateur;
			this.nbrBat = joueur.getCarteAffichee().getNbrBat();
			
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
			
			if(panAnimation != null)
				panWest.add(panAnimation);
			
			//Colle tout les panels
			this.setLayout(new BorderLayout());
			this.add(panNorth, BorderLayout.NORTH);
			this.add(panCarteAffichee, BorderLayout.CENTER);
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
			
			if(cartePossedee) {//Le label dépends de si on regarde notre carte ou pas
				lblBateaux.setText("Mes bateaux");
				panOwnCarte.checkPanel(joueur.getCartePossedee());
				this.remove(panCarteAffichee);
				this.add(panOwnCarte, BorderLayout.CENTER);
			}
			else {
				lblBateaux.setText("Bateaux à détruire");
				panCarteAffichee.checkPanel(joueur.getCarteAffichee());
				this.remove(panOwnCarte);
				this.add(panCarteAffichee, BorderLayout.CENTER);
			}
			
			this.revalidate();
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
}
