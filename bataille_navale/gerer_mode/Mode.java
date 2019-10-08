package bataille_navale.gerer_mode;

import bataille_navale.partie.Partie;

public abstract class Mode {
	public abstract Partie getPartie();
	
	protected int[][] copierNbrBat(int nbrBat[][]) {
		int tab[][] = new int[nbrBat.length][3];
		for(int i = 0; i < nbrBat.length; i++) {
			tab[i][0] = nbrBat[i][0];
			tab[i][1] = nbrBat[i][1];
			tab[i][2] = nbrBat[i][2];
		}
		
		return tab;
	}
}
