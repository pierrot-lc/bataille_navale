package bataille_navale.placeur;

import bataille_navale.contenu.Carte;
import bataille_navale.contenu.Pos;
import bataille_navale.contenu.etats.Contenu;
import bataille_navale.contenu.etats.bateau.Bateau;

public class PlaceurRegles {
	public static boolean isAligne(Pos pos[]) {
		if(pos.length < 1)
			return false;//Tableau vide
		
		Pos p = pos[0];
		
		//Test l'alignement des pos
		for(int i = 0; i < pos.length; i++) {
			Pos test = pos[i];
			if((p.getX() != test.getX()) && (p.getY() != test.getY()))//Test les alignements
				return false;
		}
		
		return true;//Toutes les vérifications sont bonnes
	}
	public static boolean isValide(Pos pos[]) {//Test si les positions sont cohérentes
		if(pos.length < 1)//Si le tableau est null
			return false;
		
		//Test les ecarts entre les pos
		trierPos(pos);//Trie d'abord les positions
		for(int i = 0; i < pos.length - 1; i++) {
			Pos test = pos[i + 1];
			int x = pos[i].getX();//Actualise pour les prochains test d'écart
			int y = pos[i].getY();
			int ecart = test.getX() - x;
			
			if(ecart < -1 || ecart > 1)//Ecart entre les pos trop grand
				return false;
			ecart = test.getY() - y;
			if(ecart < -1 || ecart > 1)
				return false;
		}
		return true;//Bateau valide !
	}
	
	public static boolean isTaken(Carte carte, Pos pos[]) {
		for(int i = 0; i < pos.length; i++) {
			Contenu content = carte.getCase(pos[i]).getContenu();
			if(content instanceof Bateau)//Si la case est déjà occupée par un bateau
				return true;//Place occupée !
		}
		return false;//Place non occupée
	}
	
	public static boolean isCapable(int nbrBat[][], Pos pos[]) {
		int tailleBateau = pos.length;
		int repere = -1;
		for(int i = 0; i < nbrBat.length; i++) {
			if(nbrBat[i][0] == tailleBateau)
				repere = i;
		}
		
		if(repere == -1)
			return false;//Le bateau ne fais parti d'aucune des tailles autorisées
		
		if(nbrBat[repere][1] == 0)
			return false;//Plus de bateaux disponibles pour cette taille là
		
		return true;//Le bateau est d'une bonne taille
	}
	
	//Privates
	private static void trierPos(Pos pos[]) {//Trie le tableau de pos dans l'ordre croissant
		
		for(int i = 0; i < pos.length + 1; i++) {
			for(int j = 0; j < pos.length - 1; j++) {
				Pos p;
				if(pos[j].getX() > pos[j + 1].getX()) {
					p = pos[j + 1];
					pos[j + 1] = pos[j];
					pos[j] = p;
				}
				if(pos[j].getY() > pos[j + 1].getY()) {
					p = pos[j + 1];
					pos[j + 1] = pos[j];
					pos[j] = p;
				}
			}
		}
	}
}
