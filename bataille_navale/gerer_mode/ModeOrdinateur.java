package bataille_navale.gerer_mode;

import bataille_navale.BatailleNavale;
import bataille_navale.Fct;
import bataille_navale.affichage.Fenetre;
import bataille_navale.contenu.Carte;
import bataille_navale.fonctionnement.Joueur;
import bataille_navale.init.InitModel;
import bataille_navale.ordinateur.PlacerBatOrdinateur;
import bataille_navale.partie.PartieOrdinateur;
import bataille_navale.placeur.PlaceurPanel;

public class ModeOrdinateur extends Mode {
	private Fenetre fen = BatailleNavale.fen;
	private PartieOrdinateur partie;
	private Joueur joueur, ordinateur;
	
	public ModeOrdinateur(InitModel model) {
		partie = new PartieOrdinateur();
		initialiserPartie(model);
	}
	
	private void initialiserPartie(InitModel model) {//G�re l'initialisation standart d'une partie
		joueur = new Joueur(model.getNomJ1());
		ordinateur = new Joueur("Ordinateur");
		
		Carte carteJ = new Carte(model.getWidthCarte(), model.getWidthCarte());
		Carte carteO = new Carte(model.getWidthCarte(), model.getHeightCarte());
		
		carteJ.setNbrBat(copierNbrBat(model.getNbrBat()));
		carteO.setNbrBat(copierNbrBat(model.getNbrBat()));
		
		joueur.setCartePossedee(carteJ);
		ordinateur.setCartePossedee(carteO);
	}
	
	public void placer() {
		placerBateaux(joueur);//Ouvre les fen�tres pour placer les bateaux � chaques joueurs
		placerBateauxOrdinateur(ordinateur);
		
		partie.setJoueur(joueur);//Assigne chaque joueur � la partie
		partie.setOrdinateur(ordinateur);
	}
	
	private void placerBateaux(Joueur joueur) {//Ouvre les fen�tres ad�quates pour placer les bateaux
		PlaceurPanel pan = new PlaceurPanel(joueur);
		fen.setPanel(pan);
		while(pan.isRunning())
			Fct.pause();
	}
	
	private void placerBateauxOrdinateur(Joueur joueur) {//Place les bateaux de l'ordinateur
		int nbrBat[][] = joueur.getCartePossedee().getNbrBat();
		
		for(int i = 0; i < nbrBat.length; i++) {//Parcours toutes les entr�es du tableau
			for(int j = 0; j < nbrBat[i][1]; j++) {//Pour chaque nbr de bateaux
				PlacerBatOrdinateur.placerBateau(joueur.getCartePossedee(), nbrBat[i][0]);//Place un bateau de la taille s�lectionn�e sur la carte s�lectionn�e
			}
		}
	}
	
	//Accesseur � la partie
	public PartieOrdinateur getPartie() {
		return this.partie;
	}
}
