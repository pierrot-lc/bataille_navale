package bataille_navale.partie;

import bataille_navale.contenu.Pos;
import bataille_navale.fonctionnement.Joueur;
import bataille_navale.fonctionnement.Regle;

public class LocalControleur {
	private LocalModel model;
	
	public LocalControleur(Joueur joueur) {
		model = new LocalModel(joueur);
	}
	
	public int tir(Pos pos) {
		int coup = Regle.tir(model.getCarte(), pos);//Détermine si le joueur doit encore jouer ou pas		
		Regle.setCase(coup, pos, model.getCarte());//Change la case en fonction du coup !
		model.getJoueur().setCarteCachee(model.getCarte());//Actualise la carte affichée en actualisant la carte cachée du joueur
		
		System.out.println("Coup : " + coup + "\nValeur de la case [" + pos.getX() + ", "+ pos.getY() +
				"] : " + model.getCarte().getCase(pos).getContenu().getName() + "\n");
		
		return coup;
	}
}
