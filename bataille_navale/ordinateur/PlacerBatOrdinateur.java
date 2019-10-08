package bataille_navale.ordinateur;

import java.util.Random;

import bataille_navale.contenu.Carte;
import bataille_navale.contenu.Pos;
import bataille_navale.contenu.etats.Contenu;
import bataille_navale.contenu.etats.Mer;
import bataille_navale.contenu.etats.bateau.Bateau;

public class PlacerBatOrdinateur {
	private static Random rand = new Random();
	
	public static void placerBateau(Carte carte, int nbrCase) {
		boolean fini = false;
		Pos pos;
		int orientation;
		do {
			System.out.println("Cherche des positions valides");
			pos = randomCase(carte);
			orientation = randomOrientation();
			if(isValide(carte, pos, orientation, nbrCase))
				fini = true;
		} while(!fini);
		
		placer(carte, pos, orientation, nbrCase);//Place le bateau
	}
	
	private static boolean isValide(Carte carte, Pos pos, int orientation, int nbrCase) {
		Pos posTemp;
		for(int i = 0; i < nbrCase; i++) {
			posTemp = new Pos(pos.getX(), pos.getY());
			
			switch(orientation) {
			case 0://HAUT
				posTemp.setY(pos.getY() - i);
				break;
			case 1://DROITE
				posTemp.setX(pos.getX() + i);
				break;
			case 2://BAS
				posTemp.setY(pos.getY() + i);
				break;
			case 3://GAUCHE
				posTemp.setX(pos.getX() - i);
				break;
			}
			if(posTemp.getX() >= carte.getWidth() || posTemp.getY() >= carte.getHeight() || posTemp.getX() < 0 || posTemp.getY() < 0) {//Si la pos dépasse la carte c'est pas bon
				System.out.println("Dépassement de carte (Case : " + posTemp.toString() + " orientation : " + orientation + ")");
				return false;
			}
			Contenu content = carte.getCase(posTemp).getContenu();
			if(!(content instanceof Mer)) {//Si la case n'est pas vide alors c'est pas bon
				System.out.println("Case contenant autre chose que de la mer (Case : " + posTemp.toString() + " orientation : " + orientation + ")");
				return false;
			}
				
		}
		return true;
	}
	
	private static void placer(Carte carte, Pos pos, int orientation, int nbrCase) {
		Pos tabPos[] = new Pos[nbrCase];
		for(int i = 0; i < tabPos.length; i++) {
			Pos posTemp = new Pos(pos.getX(), pos.getY());
			switch(orientation) {
			case 0://HAUT
				posTemp.setY(pos.getY() - i);
				break;
			case 1://DROITE
				posTemp.setX(pos.getX() + i);
				break;
			case 2://BAS
				posTemp.setY(pos.getY() + i);
				break;
			case 3://GAUCHE
				posTemp.setX(pos.getX() - i);
				break;
			}
			tabPos[i] = new Pos(posTemp.getX(), posTemp.getY());
		}
		
		//Trie d'abord le tableau pour avoir le devant à gauche ou en haut et la fin à droite ou en bas
		for(int i = 0; i < tabPos.length; i++) {
			for(int j = 0; j < tabPos.length - 1; j++) {
				if(tabPos[j].getX() > tabPos[j + 1].getX()) {
					int temp = tabPos[j].getX();
					tabPos[j].setX(tabPos[j + 1].getX());
					tabPos[j + 1].setX(temp);
				}
				else if(tabPos[j].getY() > tabPos[j + 1].getY()) {
					int temp = tabPos[j].getY();
					tabPos[j].setY(tabPos[j + 1].getY());
					tabPos[j + 1].setY(temp);				}
			}
		}
		
		Bateau bat = new Bateau(nbrCase, tabPos);
		for(int i = 0; i < tabPos.length; i++) {//Place les bateaux
			carte.setCase(tabPos[i], bat);
			System.out.println("Bateau à la pos : " + tabPos[i].toString());
		}
	}
	
	private static Pos randomCase(Carte carte) {
		Pos pos = new Pos();
		int xMax = carte.getWidth();
		int yMax = carte.getHeight();
		
		pos.setX(rand.nextInt(xMax));
		pos.setY(rand.nextInt(yMax));
		return pos;
	}
	
	private static int randomOrientation() {
		int orientation = rand.nextInt(4);//Compris entre 0 et 3
		return orientation;//0 = HAUT; 1 = DROITE; 2 = BAS; 3 = GAUCHE
	}
}
