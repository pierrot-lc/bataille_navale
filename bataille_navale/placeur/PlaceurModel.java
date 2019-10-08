package bataille_navale.placeur;

import bataille_navale.contenu.Carte;
import bataille_navale.fonctionnement.Joueur;

public class PlaceurModel {
	private Carte carte;
	public PlaceurModel(Joueur joueur) {
		carte = joueur.getCartePossedee();
	}
	
	public Carte getCarte() {
		return this.carte;
	}
}
