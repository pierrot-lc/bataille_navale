package bataille_navale.partie;

import java.awt.Dimension;

import javax.swing.JPanel;

import animations_crabe.GererAnimation;
import bataille_navale.BatailleNavale;
import bataille_navale.Fct;
import bataille_navale.affichage.Fenetre;
import bataille_navale.contenu.Carte;
import bataille_navale.contenu.Case;
import bataille_navale.contenu.Pos;
import bataille_navale.fonctionnement.Joueur;
import bataille_navale.fonctionnement.Regle;
import bataille_navale.jdialog.DialogFinPartie;
import bataille_navale.ordinateur.JouerOrdinateur;

public class PartieOrdinateur extends Partie {
	private Joueur joueur, ordinateur;
	private Fenetre fen = BatailleNavale.fen;
	private PartiePanel panJ;
	private OrdinateurPanel panO;
	
	private GererAnimation animationJ, animationO;
	private JPanel panAnimationJ = new JPanel();
	private JPanel panAnimationO = new JPanel();
	
	public PartieOrdinateur() {
		
	}
	
	public void initPartie() {
		initNbrBat(joueur.getCartePossedee().getNbrBat());//Réinitialise les tableaux
		initNbrBat(ordinateur.getCartePossedee().getNbrBat());
		
		joueur.setCarteCachee(ordinateur.getCartePossedee());//Récupère la carte de l'autre joueur
		ordinateur.setCarteCachee(joueur.getCartePossedee());//Pour actualiser leurs carteCachee qui correspondent alors à la cartePossedee
		
		panAnimationJ.setPreferredSize(new Dimension(100, 100));
		panAnimationO.setPreferredSize(new Dimension(100, 100));
		animationJ = new GererAnimation(panAnimationJ);
		animationO = new GererAnimation(panAnimationO);
		
		panJ = new PartiePanel(joueur, panAnimationJ);
		panO = new OrdinateurPanel(ordinateur, panAnimationO);
	}
	
	public void start() {
		if(joueur == null || ordinateur == null) {
			System.out.println("Joueur ou ordinateur non initialisés !");
		}
		if(joueur.getCarteCachee() == null || ordinateur.getCarteCachee() == null) {
			System.out.println("Les cartes ne sont pas initialisées");
			return;
		}
		
		boolean continuer = true, gagner = false;
		while(continuer) {
			gagner = tour(joueur, panJ);//Tour au joueur 1
			if(gagner) {
				continuer = false;
				break;
			}
			
			gagner = tourOrdinateur();//Tour à l'ordinateur
			if(gagner)
				continuer = false;
		}
		fen.dispose();
	}
	
	private boolean tourOrdinateur() {
		System.out.println("Tour de l'ordinateur");
		
		boolean tour = true;
		boolean gagne = false;
		
		if(!animationJ.getAnimation().equals(""))
			animationO.setAnimation(animationJ.getAnimation());
		else
			animationO.setAnimation("Normal");
		
		fen.setPanel(panO);
		
		Fct.pauseLongue();
		while(tour) {
			int coup = tirOrdinateur();//Fait un tir sur l'ordinteur
			gagne = Regle.termine(ordinateur.getCarteCachee());
			
			if(coup == 1)//L'ordinateur a coulé un bateau du joueur
				animationO.setAnimation("Angry_2");
			else if(coup == 2)//L'ordinateur a touché un bateau
				animationO.setAnimation("Angry_1");
			
			if(coup == 0)
				tour = false;//Fin du tour
			if(gagne)
				tour = false;
			panO.repaint();
			Fct.pauseLongue();
		}
		if(gagne) {
			new DialogFinPartie(null, true, false);
			Fct.pauseLongue();
			System.out.println("L'ordinateur a gagné !");
		}
		return gagne;
	}
	
	private int tirOrdinateur() {
		Pos posTir = JouerOrdinateur.jouer(ordinateur.getCarteAffichee());
		Carte carte = ordinateur.getCarteCachee();
		Case c = carte.getCase(posTir);
		int coup;
		
		coup = Regle.tir(carte, posTir);//Acualise la case touchée et détermine si le joueur doit encore jouer ou pas		
		Regle.setCase(coup, c.getPos(), carte);//Change la case en fonction du coup !
		ordinateur.setCarteCachee(carte);//Actualise la carte affichée actualisant la carte cachée et les boutons
		System.out.println("Coup : " + coup + "\nValeur de la case [" + c.getPos().getX() + ", "+ c.getPos().getY() +
				"] : " + c.getContenu().getName() + "\n");
		
		return coup;
	}
	
	private boolean tour(Joueur joueur, PartiePanel panel) {
		System.out.println("Début du tour de " + joueur.getName());
		boolean gagner = false, tour = true;
		
		if(!animationO.getAnimation().equals(""))
			animationJ.setAnimation(animationO.getAnimation());
		else
			animationJ.setAnimation("Normal");
		
		fen.setPanel(panel);
		do {
			panel.setListener(true);//Active le listener
			
			long tempsPrecedent = System.currentTimeMillis();
			long tempsActuel = System.currentTimeMillis();
			while(panel.getCoup() == -1) {//Tant qu'il n'a pas joué on attends
				Fct.pause();
				
				tempsActuel = System.currentTimeMillis();
				if((tempsActuel - tempsPrecedent)/1000 > 10 && !animationJ.getAnimation().equals("Waiting"))//Si l'utilisateur n'a pas joué au bout de 10 secs
					animationJ.setAnimation("Waiting");
			}
			
			panel.setListener(false);//Désactive le listener
			
			if(panel.getCoup() == 1)//Le joueur a coulé un bateau
				animationJ.setAnimation("Happy_2");
			else if(panel.getCoup() == 2)
				animationJ.setAnimation("Happy_1");
			
			if(panel.getCoup() == 0) {//Si le joueur a fait un coup dans le vide
				tour = false;
				animationJ.setAnimation("Normal");
			}
			panel.initCoup();//Réinitialise la valeur de son coup
			
			gagner = Regle.termine(joueur.getCarteCachee());//Test si le joueur a gagné
			if(gagner) {
				new DialogFinPartie(null, true, true);
				System.out.println("Gagné !");
				Fct.pauseLongue();
				return true;//Retourne alors vrai (tour fini et il a gagné)
			}
		} while(tour); //Tant qu'il n'a pas fini son tour on continue
		
		
		Fct.pauseLongue();//Petite pause avant de finir le tour
		return false;//Le joueur a fini son tour et n'a pas gagné
	}
	
	//Accesseurs
	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}
	public void setOrdinateur(Joueur ordinateur) {
		this.ordinateur = ordinateur;
	}
	
	//Mutateurs
	public Joueur getJoueur() {
		return this.joueur;
	}
	public Joueur getOrdinateur() {
		return this.ordinateur;
	}
}
