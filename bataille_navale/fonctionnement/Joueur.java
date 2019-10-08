package bataille_navale.fonctionnement;

import bataille_navale.contenu.Carte;

public class Joueur {
	private String name = "Joueur";
	private Carte carteCachee;
	private Carte carteAffichee;
	private Carte cartePossedee; 
	
	public Joueur() {
	}
	public Joueur(String name) {
		this.name = name;
	}
	
	//Mutateurs
	public void setName(String name) {
		this.name = name;
	}
	public void setCarteCachee(Carte carte) {//Actualise la carteCachee et la carteAffichee
		this.carteCachee = carte;
		
		if(carteAffichee == null)//Si c'est la première utilisation de la fonction la carte est null
			carteAffichee = new Carte(carteCachee.getWidth(), carteCachee.getHeight());
		
		Carte.cacherBateau(carteCachee, carteAffichee);
	}
	public void setCartePossedee(Carte carte) {
		this.cartePossedee = carte;
	}
	public void setCarteAffichee(Carte carte) {
		this.carteAffichee = carte;
	}
	
	//Accesseurs
	public String getName() {
		return this.name;
	}
	public Carte getCarteCachee() {
		return this.carteCachee;
	}
	public Carte getCarteAffichee() {
		return this.carteAffichee;
	}
	public Carte getCartePossedee() {
		return this.cartePossedee;
	}
}
