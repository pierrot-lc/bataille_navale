package bataille_navale.placeur;

import bataille_navale.contenu.Carte;
import bataille_navale.contenu.Pos;
import bataille_navale.contenu.etats.Contenu;
import bataille_navale.contenu.etats.bateau.BateauCorps;
import bataille_navale.fonctionnement.Joueur;

public class PlaceurControleur {
	private PlaceurModel model;
	
	public PlaceurControleur(Joueur joueur) {
		model = new PlaceurModel(joueur);
	}
	
	public int placerBateau(Pos pos[]) {
		boolean result = false;
		
		result = PlaceurRegles.isAligne(pos);
		if(!result)
			return -1;//Les cases ne sont pas alignés
		
		result = PlaceurRegles.isValide(pos);
		if(!result)
			return -2;//Positions non valides
		
		result = PlaceurRegles.isTaken(model.getCarte(), pos);
		if(result)//Si la place est prise
			return -3;//Une des cases est déjà occupée par un bateau
		
		result = PlaceurRegles.isCapable(model.getCarte().getNbrBat(), pos);
		if(!result)
			return -4;//Pas de bateaux disponibles pour ce nombre de cases
		
		model.getCarte().placerBateau(pos);
		actualiserNbrBat(pos.length, -1);//Actualise le tableau
		return 1;//Bateau placé !
	}
	
	public boolean delBateau(Pos pos) {
		Carte carte = model.getCarte();
		Contenu content = carte.getCase(pos).getContenu();
		if(!(content instanceof BateauCorps))
			return false;//Pas de bateau sur la case cliquée
		
		BateauCorps bat = (BateauCorps)content;
		carte.enleverBateau(bat);
		actualiserNbrBat(bat.getPos().length, 1);//Actualise le tableau
		return true;//Bateau enlevé
	}
	private void actualiserNbrBat(int taille, int ajout) {
		int nbrBat[][] = model.getCarte().getNbrBat();
		for(int i = 0; i < nbrBat.length; i++) {
			if(nbrBat[i][0] == taille)
				nbrBat[i][1] = nbrBat[i][1] + ajout;//Actualise la valeur du tableau
		}
	}
	
	public PlaceurModel getModel() {
		return this.model;
	}
}
