package bataille_navale.gerer_mode;

import bataille_navale.BatailleNavale;
import bataille_navale.Fct;
import bataille_navale.contenu.Carte;
import bataille_navale.fonctionnement.Joueur;
import bataille_navale.init.InitModel;
import bataille_navale.partie.PartieMulti;
import bataille_navale.placeur.PlaceurPanel;
import reseau.Client;
import reseau.FluxPartie;
import reseau.Server;

public class ModeReseau extends Mode {
	private PartieMulti partie;
	private Joueur joueur;
	private FluxPartie flux = null;
	
	public ModeReseau(InitModel model, Server serv) {
		if(model.isHote()) {
			while(!serv.isParamEnvoyes())
				Fct.pause();
			flux = serv;
		}
		else {
			Client client = new Client(model.getIp(), model.getPort());
			flux = client;
			InitModel modelHote = client.recupParam(model.getNomJ1());
			
			model.setNbrBat(modelHote.getNbrBat());
			model.setHeightCarte(modelHote.getHeightCarte());
			model.setWidthCarte(modelHote.getWidthCarte());
			model.setNomJ2(modelHote.getNomJ1());
		}
		partie = new PartieMulti();
		initialiserPartie(model);
	}
	
	private void initialiserPartie(InitModel model) {
		joueur = new Joueur(model.getNomJ1());
		Carte carteJ = new Carte(model.getWidthCarte(), model.getHeightCarte());
		carteJ.setNbrBat(copierNbrBat(model.getNbrBat()));
		joueur.setCartePossedee(carteJ);
		
		partie.setJoueur(joueur);
		partie.setNameAdvsersaire(model.getNomJ2());
		
		flux.setCarte(carteJ);
	}
	
	public void placer() {
		PlaceurPanel pan = new PlaceurPanel(joueur);
		BatailleNavale.fen.setPanel(pan);
		while(pan.isRunning())
			Fct.pause();
	}
	
	public PartieMulti getPartie() {
		return partie;
	}
	public FluxPartie getFluxPartie() {
		return flux;
	}
}
