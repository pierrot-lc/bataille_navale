package bataille_navale;

import bataille_navale.affichage.Fenetre;
import bataille_navale.gerer_mode.ModeLocal;
import bataille_navale.gerer_mode.ModeOrdinateur;
import bataille_navale.gerer_mode.ModeReseau;
import bataille_navale.init.InitModel;
import bataille_navale.init.InitPartie;
import bataille_navale.partie.PartieLocal;
import bataille_navale.partie.PartieMulti;
import bataille_navale.partie.PartieOrdinateur;
import reseau.Server;

public class BatailleNavale {
	public static Fenetre fen = new Fenetre();

	public BatailleNavale() {
		System.out.println("Nouvelle bataille navale crée !");
	}
	
	public void go() {
		InitPartie init = new InitPartie();
		init.go();
		InitModel model = init.getModel();
		
		if(model.getMode().equals("Local")) {
			ModeLocal local = new ModeLocal(model);//Initialise les objets joueurs
			local.placer();//Lance le placement des bateaux des joueurs
			
			PartieLocal partie = local.getPartie();//Récupère la partie contenant les deux joueurs
			partie.initPartie();//Initialisation
			partie.start();//Lancement de la partie
		}
		else if(model.getMode().equals("Ordinateur")) {
			ModeOrdinateur modeO = new ModeOrdinateur(model);
			modeO.placer();
			
			PartieOrdinateur partie = modeO.getPartie();
			partie.initPartie();
			partie.start();
		}
		else if(model.getMode().equals("En ligne")) {
			ModeReseau modeR = null;
			if(model.isHote()){
				Server server = new Server(model);
				modeR = new ModeReseau(model, server);
				modeR.placer();
			}
			else {
				modeR = new ModeReseau(model, null);
				modeR.placer();
			}
			PartieMulti partie = modeR.getPartie();
			partie.initPartie(modeR.getFluxPartie());
			partie.isReady();
			partie.start();
		}
	}
}
