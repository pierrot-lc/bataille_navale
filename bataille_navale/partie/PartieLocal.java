package bataille_navale.partie;

import bataille_navale.BatailleNavale;
import bataille_navale.Fct;
import bataille_navale.affichage.Fenetre;
import bataille_navale.fonctionnement.Joueur;
import bataille_navale.fonctionnement.Regle;

public class PartieLocal extends Partie {
	private Joueur joueur1;
	private Joueur joueur2;
	private Fenetre fen;
	
	private PartiePanel pan1, pan2;
	
	public PartieLocal() {
		this.fen = BatailleNavale.fen;
	}
	
	public void initPartie() {
		initNbrBat(joueur1.getCartePossedee().getNbrBat());//R�initialise les tableaux
		initNbrBat(joueur2.getCartePossedee().getNbrBat());
		
		joueur1.setCarteCachee(joueur2.getCartePossedee());//Associe la carte de chaque joueurs � l'autre
		joueur2.setCarteCachee(joueur1.getCartePossedee());
		
		pan1 = new PartiePanel(joueur1);
		pan2 = new PartiePanel(joueur2);
	}
	
	public void start() {
		if(joueur1 == null || joueur2 == null) {//Par s�curit�
			System.out.println("Les joueurs ne sont pas initialis�s");
			return;
		}
		if(joueur1.getCarteCachee() == null || joueur2.getCarteCachee() == null) {
			System.out.println("Les cartes ne sont pas initialis�es");
			return;
		}
		
		
		boolean continuer = true, gagner = false;		
		while(continuer) {
			gagner = tour(joueur1, pan1);//Tour au joueur 1
			if(gagner) {
				continuer = false;
				break;
			}
			
			gagner = tour(joueur2, pan2);//Tour au joueur 2
			if(gagner)
				continuer = false;
		}
		fen.dispose();
	}
	
	private boolean tour(Joueur joueur, PartiePanel panel) {
		boolean gagner = false, tour = true;
		
		fen.setPanel(panel);
		do {
			panel.setListener(true);//Active le listener
			while(panel.getCoup() == -1) {//Tant qu'il n'a pas jou� on attends
				Fct.pause();
			}
			panel.setListener(false);//D�sactive le listener
			
			if(panel.getCoup() == 0)//Si le joueur a fait un coup dans le vide
				tour = false;
			panel.initCoup();//R�initialise la valeur de son coup
			
			gagner = Regle.termine(joueur.getCarteCachee());//Test si le joueur a gagn�
			if(gagner) {
				System.out.println("Gagn� !");
				Fct.pauseLongue();
				return true;//Retourne alors vrai (tour fini et il a gagn�)
			}
		} while(tour); //Tant qu'il n'a pas fini son tour on continue
		
		
		Fct.pauseLongue();
		return false;//Le joueur a fini son tour et n'a pas gagn�
	}
	
	
	//Accesseurs
	public void setJoueur1(Joueur joueur) {
		this.joueur1 = joueur;
	}
	public void setJoueur2(Joueur joueur) {
		this.joueur2 = joueur;
	}
	
	//Mutateurs
	public Joueur getJoueur1() {
		return this.joueur1;
	}
	public Joueur getJoueur2() {
		return this.joueur2;
	}	
}
