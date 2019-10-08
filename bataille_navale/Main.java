package bataille_navale;

import bataille_navale.affichage.Fenetre;

public class Main {
	public static Fenetre fen = new Fenetre();
	
	public static void main(String[] args) {
		BatailleNavale bataille = new BatailleNavale();
		
		while(true)
			bataille.go();
	}
}
