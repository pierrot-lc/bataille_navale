package bataille_navale.gerer_mode;

import bataille_navale.BatailleNavale;
import bataille_navale.Fct;
import bataille_navale.contenu.Carte;
import bataille_navale.fonctionnement.Joueur;
import bataille_navale.init.InitModel;
import bataille_navale.partie.PartieLocal;
import bataille_navale.placeur.PlaceurPanel;

public class ModeLocal extends Mode {
	private Joueur joueur1, joueur2;
	private PartieLocal partie;
	
	public ModeLocal(InitModel modelInit) {
		partie = new PartieLocal();
		initialiserJoueurs(modelInit);
	}
	
	private void initialiserJoueurs(InitModel modelInit) {
		joueur1 = new Joueur(modelInit.getNomJ1());//Créé les joueurs
		joueur2 = new Joueur(modelInit.getNomJ2());
		
		Carte carte1 = new Carte(modelInit.getWidthCarte(), modelInit.getHeightCarte());//Créé les cartes
		Carte carte2 = new Carte(modelInit.getWidthCarte(), modelInit.getHeightCarte());
		
		carte1.setNbrBat(copierNbrBat(modelInit.getNbrBat()));//Colle le tableau à la carte
		carte2.setNbrBat(copierNbrBat(modelInit.getNbrBat()));//En créé un nouveau (sinon problème de pointeur par la suite)
		
		joueur1.setCartePossedee(carte1);//Assigne les cartes
		joueur2.setCartePossedee(carte2);
	}
	
	public void placer() {//Gère l'initialisation standart d'une partie en local
		placerBateaux(joueur1);//Ouvre les fenêtres pour placer les bateaux à chaques joueurs
		placerBateaux(joueur2);
		
		partie.setJoueur1(joueur1);//Assigne chaque joueur à la partie
		partie.setJoueur2(joueur2);
	}
	
	private void placerBateaux(Joueur joueur) {//Ouvre les fenêtres adéquates pour placer les bateaux
		PlaceurPanel pan = new PlaceurPanel(joueur);
		BatailleNavale.fen.setPanel(pan);
		while(pan.isRunning())
			Fct.pause();
	}
	
	//Accesseur à la partie
	public PartieLocal getPartie() {
		return this.partie;
	}
}
