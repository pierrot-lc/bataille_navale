package bataille_navale.affichage;

import java.awt.GridLayout;

import javax.swing.JPanel;

import bataille_navale.contenu.Carte;
import bataille_navale.contenu.Pos;
import bataille_navale.contenu.etats.Contenu;
import bataille_navale.contenu.etats.Etat;
import bataille_navale.contenu.etats.Mer;
import bataille_navale.contenu.etats.Plouf;
import bataille_navale.contenu.etats.bateau.Bateau;
import bataille_navale.contenu.etats.bateau.BateauPlouf;
import bataille_navale.contenu.etats.bateau.BateauPloufant;

public class BoutonPanel extends JPanel {
	private static final long serialVersionUID = -7147488050639280455L;
	private Bouton tabBouton[][];
	private int x, y;
	
	public BoutonPanel(Bouton tabBouton[][]) {
		super();
		this.tabBouton = tabBouton;
		this.x = tabBouton.length;
		this.y = tabBouton[0].length;
		
		initPanel();
	}
	
	public void initPanel() {
		this.setLayout(new GridLayout(x, y));
		
		for(int i = 0; i < y; i++) {
			for(int j = 0; j < x; j++) {
				this.add(tabBouton[j][i]);//Parcours le tableau de droite à gauche
			}
		}
	}
	
	public void checkPanel(Carte carte) {//Actualise le panel selon la carte
		for(int i = 0; i < tabBouton.length; i++) {
			for(int j = 0; j < tabBouton[0].length; j++) {
				Pos pos = new Pos(i, j);
				Contenu contentCarte = carte.getCase(pos).getContenu();
				Etat etat = tabBouton[i][j].getEtat();
				
				if(contentCarte instanceof Mer) {
					if(!(etat.equals(Etat.MER)))
						tabBouton[i][j].setEtat(Etat.MER);
				}
				else if(contentCarte instanceof Bateau) {
					if(!(etat.equals(Etat.BATEAU)))
						tabBouton[i][j].setEtat(Etat.BATEAU);
				}
				else if(contentCarte instanceof BateauPlouf) {
					if(!(etat.equals(Etat.BATEAUPLOUF)))
						tabBouton[i][j].setEtat(Etat.BATEAUPLOUF);
				}
				else if(contentCarte instanceof BateauPloufant) {
					if(!(etat.equals(Etat.BATEAUPLOUFANT)))
						tabBouton[i][j].setEtat(Etat.BATEAUPLOUFANT);
				}
				else if(contentCarte instanceof Plouf) {
					if(!(etat.equals(Etat.PLOUF)))
						tabBouton[i][j].setEtat(Etat.PLOUF);
				}
			}
		}
	}
	
	//Mutateurs	
	public void setTabBouton(Bouton tabBouton[][]) {
		this.tabBouton = tabBouton;
		this.x = tabBouton.length;
		this.y = tabBouton[0].length;
	}
	
	//Accesseurs
	public Bouton[][] getTabBouton() {
		return this.tabBouton;
	}
}
