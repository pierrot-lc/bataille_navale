package bataille_navale.partie;

import bataille_navale.contenu.Carte;
import bataille_navale.fonctionnement.Joueur;

public class LocalModel {
	private Carte carte;
	private Joueur joueur;
	
	public LocalModel(Joueur joueur) {
		this.carte = joueur.getCarteCachee();
		this.joueur = joueur;
	}
	
	public Carte getCarte() {
		return this.carte;
	}
	public Joueur getJoueur() {
		return this.joueur;
	}
}
