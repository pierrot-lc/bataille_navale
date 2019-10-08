package bataille_navale.contenu;

import bataille_navale.contenu.etats.Contenu;
import bataille_navale.contenu.etats.Mer;
import bataille_navale.contenu.etats.Plouf;
import bataille_navale.contenu.etats.bateau.Bateau;
import bataille_navale.contenu.etats.bateau.BateauCorps;
import bataille_navale.contenu.etats.bateau.BateauPloufant;

public class Carte {
	//Variables
	private int width = 0;
	private int height = 0;
	private Case carte[][];
	private int nbrBateaux = 0;//Compte le nombre de bateaux
 	private int nbrBat[][];
 	
	//Constructeurs
	public Carte(int x, int y) {
		this.width = x;
		this.height = y;
		initCarte();
	}
	
	//Méthodes de classe
	private void initCarte() {
		carte = new Case[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				carte[i][j] = new Case(new Pos(i, j));
			}
		}
	}
	public void placerBateau(Pos[] pos) {//Place un bateau sur la pos sélectionnée
		nbrBateaux++;//Augmente de 1 et devient l'identifiant du bateau
		Bateau bat = new Bateau(nbrBateaux, pos);//Créer le bateau
		
		for(int i = 0; i < pos.length; i++) {
			this.getCase(pos[i]).setContenu(bat);
		}
	}
	public void enleverBateau(BateauCorps bat) {
		Pos pos[] = bat.getPos();
		for(int i = 0; i < pos.length; i++) {
			this.setCase(pos[i], new Mer());
		}
	}
	
	//Méthodes statiques
	public static void randomCarte(Carte carte) {
		int id = 1;//Identifiant du bateau
		for(int i = 0; i < carte.height; i++) {
			for(int j = 0; j < carte.width; j++) {
				int rdm = (int)(Math.random() + 0.3);
				Contenu cont;
				if(rdm == 0)
					cont = new Mer();
				else {
					cont = new Bateau(id, new Pos(i, j));
					id++;
				}
				carte.setCase(new Pos(i, j), cont);
			}
		}
	}
	public static void cacherBateau(Carte carteComplete, Carte carteACachee) {//Cache tout les bateaux d'une carte
		for(int i = 0; i < carteComplete.height; i++) {
			for(int j = 0; j < carteComplete.width; j++) {
				Pos pos = new Pos(i, j);
				Case c = carteComplete.getCase(pos);
				if(c.getContenu() instanceof Bateau) {//Remplace tout les bateaux par de la mer
					carteACachee.setCase(pos, new Mer());
				}
				else {
					carteACachee.setCase(pos, c.getContenu());
				}
			}
		}
		
		carteACachee.setNbrBat(carteComplete.getNbrBat());
	}
	public static void afficherContenu(Carte carte) {
		int x = carte.getWidth();
		int y = carte.getHeight();
		
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				String str = carte.getCase(new Pos(i, j)).getContenu().getName();
				System.out.println("Contenu de la case [" + i + "; " + j + "] : " + str);
			}
		}
	}
	
	public static void modifierCarte(Carte carte, int coup, Pos pos) {
		Contenu c = null;
		switch(coup) {
		case 0://Mer
			c = new Plouf();
			break;
		case 2://Touché
			c = new BateauPloufant();
			break;
		}
		carte.getCase(pos).setContenu(c);
	}
	
	//Accesseurs
	public void setWidth(int x) {
		this.width = x;
	}
	public void setHeight(int y) {
		this.height = y;
	}
	public void setCase(Pos pos, Contenu cont) {
		carte[pos.getX()][pos.getY()].setContenu(cont);
	}
	public void setNbrBat(int tab[][]) {
		this.nbrBat = tab;
	}
	
	//Mutateurs
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	public Case getCase(Pos pos) {
		return this.carte[pos.getX()][pos.getY()];
	}
	public int[][] getNbrBat() {
		return this.nbrBat;
	}
}
