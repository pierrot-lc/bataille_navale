package bataille_navale.placeur;

import bataille_navale.contenu.Carte;
import bataille_navale.fonctionnement.Joueur;

public class Placeur {
	private Joueur joueur = new Joueur();

	public Placeur() {
		
	}
	
	//Mutateurs
	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}
	
	//Accesseurs
	public Carte getCarte() {
		return this.joueur.getCartePossedee();
	}
	public Joueur getJoueur() {
		return this.joueur;
	}
}
