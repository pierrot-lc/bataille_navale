package bataille_navale.partie;

import bataille_navale.BatailleNavale;
import bataille_navale.Fct;
import bataille_navale.affichage.Fenetre;
import bataille_navale.contenu.Carte;
import bataille_navale.contenu.Pos;
import bataille_navale.fonctionnement.Joueur;
import bataille_navale.fonctionnement.Regle;
import reseau.Client;
import reseau.FluxPartie;
import reseau.Server;

public class PartieMulti extends Partie {
	private Fenetre fen = BatailleNavale.fen;
	private Joueur joueur;
	
	private FluxPartie flux;
	
	private PartiePanelReseau panJ;
	private PartiePanelOpposant panO;
	
	private String nameAdversaire = "";
	
	public static boolean gagne = false;
	
	public PartieMulti() {
		
	}
	
	public void initPartie(FluxPartie flux) {
		this.flux = flux;
		
		initNbrBat(joueur.getCartePossedee().getNbrBat());//Réinitialise les tableaux
		
		Carte carteJoueur = joueur.getCartePossedee();
		Carte carteOpposant = new Carte(carteJoueur.getWidth(), carteJoueur.getHeight());//Copie la carte du joueur mais en vide
		carteOpposant.setNbrBat(copierNbrBat(carteJoueur.getNbrBat()));//Copie le tableau (à cause des pointeurs)
		
		joueur.setCarteAffichee(carteOpposant);//Récupère la carte de l'autre joueur
		flux.setCarteOpposant(carteOpposant);
		
		panJ = new PartiePanelReseau(joueur);
		panO = new PartiePanelOpposant(joueur);
		
		flux.setPanO(panO);
		
		if(flux instanceof Server)
			flux.setPlaying(true);
		else
			flux.setPlaying(false);
	}
	
	public void isReady(){
		flux.finPlacement();
		while(flux.getPret() < 2)
			Fct.pause();
	}
	
	
	@Override
	public void start() {
		if(joueur == null) {
			System.out.println("Joueur non initialisé !");
			return;
		}
		if(joueur.getCarteAffichee() == null) {
			System.out.println("Les cartes ne sont pas initialisées");
			return;
		}
		
		if(flux instanceof Client)
			fen.setPanel(panO);
		
		boolean gagne = false;
		do {
			if(flux instanceof Server) {
				gagne = tour();
				if(gagne)
					break;
				tourAdverse();	
			}
			else {
				tourAdverse();
				gagne = tour();
			}
		} while(!gagne);
		fen.dispose();
	}
	
	private boolean tour() {
		boolean tour = true;
		
		fen.setPanel(panJ);
		
		do {
			panJ.setListener(true);
			Pos posCoup = null;
			while(posCoup == null) {
				posCoup = panJ.getPosCoup();
				Fct.pause();
			}
			
			int coup = flux.envoyerPos(posCoup);
			panJ.initPosCoup();
			panJ.repaint();
			
			if(coup == 0){
				tour = false;
				flux.finDeTour();
			}
			
			gagne = Regle.termine(joueur.getCarteAffichee());
			if(gagne) {
				tour = false;
				flux.partieGagnee();
			}
		} while(tour);
		Fct.pauseLongue();
		
		return gagne;
	}
	
	private void tourAdverse() {
		fen.setPanel(panO);
		while(!flux.isPlaying())
			Fct.pause();
		
		Fct.pauseLongue();
	}
	
	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}
	public void setNameAdvsersaire(String str) {
		nameAdversaire = str;
	}
}
