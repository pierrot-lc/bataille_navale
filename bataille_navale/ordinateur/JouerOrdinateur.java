package bataille_navale.ordinateur;

import java.util.Random;

import bataille_navale.contenu.Carte;
import bataille_navale.contenu.Pos;
import bataille_navale.contenu.etats.Contenu;
import bataille_navale.contenu.etats.Mer;
import bataille_navale.contenu.etats.Plouf;
import bataille_navale.contenu.etats.bateau.BateauPloufant;

public class JouerOrdinateur {//Classe fait que de méthodes statics qui permettent de jouer intelligement
	private static Random rand = new Random();
	
	public static Pos jouer(Carte carte) {//Renvoie la pos sur laquelle tirer
		Pos pos = gererPloufant(carte);//Un bateau ploufant ?
		if(pos != null)//Si il y a bien un coup selectionner
			return pos;
		
		pos = chooseRandomCase(carte);//Choisi une case sur deux jusqu'à épuisement des cases disponibles
		if(pos != null)
			return pos;
		
		pos = randomCase(carte);//Alors joue juste en mode random total
		return pos;
	}
	
	private static Pos randomCase(Carte carte) {//Renvoie une case jouable au hasard
		boolean valide = false;
		int maxX = carte.getWidth();
		int maxY = carte.getHeight();
		Pos pos = new Pos();
		
		while(!valide) {
			System.out.println("Cherche une case complétement aléatoire");
			int x = rand.nextInt(maxX);
			int y = rand.nextInt(maxY);
			
			pos = new Pos(x, y);
			if(carte.getCase(pos).getContenu() instanceof Mer)
				valide = true;
		}
		
		return pos;
	}
	
	private static Pos chooseRandomCase(Carte carte) {//Choisi une pos de manière à tirer une case sur deux à chaque fois
		boolean coupValide = false;
		Pos posValide = new Pos();
		int maxX = carte.getWidth();
		int maxY = carte.getHeight();
		
		if(!isAvailable(carte))
			return null;
		
		while(!coupValide) {//Le quadrillage de zone se fait de manière à jouer un coup sur deux avec une combo paire-paire et impaire-impaire
			System.out.println("Cherche une case pour quadrillé la zone");
			int x = rand.nextInt(maxX);
			int y = -1;
			switch(x%2) {
			case 0://Chiffre pair
				while(y%2 != 0)
					y = rand.nextInt(maxY);
				break;
			case 1://Chiffre impair
				while(y%2 != 1)
					y = rand.nextInt(maxY);
				break;
			}
			
			Pos posTemp = new Pos(x, y);
			if(carte.getCase(posTemp).getContenu() instanceof Mer) {
				posValide = posTemp;
				coupValide = true;
			}
		}
		
		return posValide;
	}
	
	private static boolean isAvailable(Carte carte) {//Check si il y a bien possibilité de joué encore une case sur deux
		for(int i = 0; i < carte.getWidth(); i++) {
			for(int j = 0; j < carte.getHeight(); j++) {
				if(i%2 == 0 && j%2 == 0) {
					if(carte.getCase(new Pos(i, j)).getContenu() instanceof Mer)
						return true;
				}
				if(i%2 == 1 && j%2 == 1) {
					if(carte.getCase(new Pos(i, j)).getContenu() instanceof Mer)
						return true;
				}
			}
		}
		System.out.println("Plus de cases pour quadrillé la zone !");
		return false;//Si à aucun moment aucune case ne remplie les critères alors renvoie faux
	}
	
	private static Pos gererPloufant(Carte carte) {//Retourne la pos du coup à joué
		Pos pos = isPloufant(carte);
		if(pos == null)
			return null;//Pas de pos possible
		
		Pos memoirePlouf = null;
		boolean mauvaisAlignement = false;
		
		boolean coupValable = false;
		Pos posValable = new Pos();
		
		while(!coupValable) {
			System.out.println("Cherche une case dans les environs du bateau ploufant");
			int orientation = -1;
			int alignement = chooseAlignement(carte, pos);
			switch(alignement) {
			case 0://Ni l'un ni l'autre (Une seule case touchée)
				orientation = randomOrientation();
				break;
			case 1://Horizontal
				while(orientation%2 != 1)//Impaire == horizontal
					orientation = randomOrientation();
				
				if(mauvaisAlignement)
					while(orientation%2!=0)
						orientation = randomOrientation();
				break;
			case 2://Vertical
				while(orientation%2 != 0)//Paire == vertical
					orientation = randomOrientation();
				
				if(mauvaisAlignement)//Inverse l'alignement pour ne pas chercher dans le mauvais
					while(orientation%2!=1)
						orientation = randomOrientation();
				break;
			}
			System.out.print("Orientation du tir : " + orientation);
			Pos posTemp = new Pos(pos.getX(), pos.getY());
			boolean continuer = true;
			while(continuer) {//Tente de trouver un coup valable et continue pour avancer dans la carte
				switch(orientation) {
				case 0://HAUT
					posTemp.setY(posTemp.getY() - 1);
					break;
				case 1://DROITE
					posTemp.setX(posTemp.getX() + 1);
					break;
				case 2://BAS
					posTemp.setY(posTemp.getY() + 1);
					break;
				case 3://GAUCHE
					posTemp.setX(posTemp.getX() - 1);
					break;
				}
				
				if(posTemp.getX() < 0 || posTemp.getX() >= carte.getWidth()
						|| posTemp.getY() < 0 || posTemp.getY() >= carte.getHeight()) {//Dépassement de la carte
					continuer = false;//Recommence avec un autre alignement/orientation
					if(memoirePlouf == null)
						memoirePlouf = new Pos(posTemp.getX(), posTemp.getY());
					else if(memoirePlouf.getX() != posTemp.getX() || memoirePlouf.getY() != posTemp.getY())
						//Si on a de nouveau rencontré une case inutile en plus d'une première case inutile alors ce n'est pas le bon alignement
						mauvaisAlignement = true;
					
					System.out.println(", dépassement de carte, reprise de la recherche");
				}
				else {//Si pas de dépassement on peut regarder le contenu de la case (sinon elle existe pas !)
					Contenu content = carte.getCase(posTemp).getContenu();
					if(content instanceof Plouf) {//Case plouf inutile et séparation du bateau ploufant donc impossible
						continuer = false;//Recommence de la même façon la recherche
						if(memoirePlouf == null)
							memoirePlouf = new Pos(posTemp.getX(), posTemp.getY());
						else if(memoirePlouf.getX() != posTemp.getX() || memoirePlouf.getY() != posTemp.getY())
							//Si on a de nouveau rencontré une case inutile en plus d'une première case inutile alors ce n'est pas le bon alignement
							mauvaisAlignement = true;
						
						System.out.println(", plouf rencontré, reprise de la recherche");
					}
					if(content instanceof Mer) {//Case intéressante !
						posValable = posTemp;
						continuer = false;
						coupValable = true;//Sort de la boucle principale
					}
				}
			}//Fin du while(continuer)
		}//Fin du while(!coupValable)
		return posValable;
	}
	
	private static Pos isPloufant(Carte carte) {//Cherche des pos de bateau ploufant
		Pos pos = new Pos();
		Contenu content;
		
		for(int i = 0; i < carte.getWidth(); i++) {
			for(int j = 0; j < carte.getHeight(); j++) {
				pos.setX(i);
				pos.setY(j);
				content = carte.getCase(pos).getContenu();
				
				if(content instanceof BateauPloufant)
					return pos;
			}
		}
		
		return null;//Pas de pos existantes
	}
	
	private static int chooseAlignement(Carte carte, Pos pos) {//Détermine l'alignement possible du bateau en fonction de l'alignement des bateaux ploufants
		Contenu content;
		Pos posTemp;
		boolean vertical = false;
		boolean horizontal = false;
		
		
		//Test l'alignement horizontal
		if(pos.getX() - 1 > 0) {//Pour ne pas dépasser la carte
			posTemp = new Pos(pos.getX() - 1, pos.getY());//Recule d'une case
			content = carte.getCase(posTemp).getContenu();
			if(content instanceof BateauPloufant)
				horizontal = true;
		}
		if(pos.getX() + 1 < carte.getWidth()) {
			posTemp = new Pos(pos.getX() + 1, pos.getY());//Avance d'une case
			content = carte.getCase(posTemp).getContenu();
			if(content instanceof BateauPloufant)
				horizontal = true;
		}
			
		//Test l'alignement vertical
		if(pos.getY() - 1 > 0) {
			posTemp = new Pos(pos.getX(), pos.getY() - 1);//Recule d'une case
			content = carte.getCase(posTemp).getContenu();
			if(content instanceof BateauPloufant)
				vertical = true;
		}
		if(pos.getY() + 1 < carte.getHeight()) {
			posTemp = new Pos(pos.getX(), pos.getY() + 1);//Avance d'une case
			content = carte.getCase(posTemp).getContenu();
			if(content instanceof BateauPloufant)
				vertical = true;
		}
		
		if(horizontal && vertical)
			return 0;//Alignement inconnu
		else if(horizontal)
			return 1;//Horizontal
		else if(vertical)
			return 2;//Vertical
		
		return 0;//Pas d'alignement spécifique
	}
	
	private static int randomOrientation() {
		int orientation = rand.nextInt(4);//Compris entre 0 et 3
		return orientation;//0 = HAUT; 1 = DROITE; 2 = BAS; 3 = GAUCHE
	}
}
