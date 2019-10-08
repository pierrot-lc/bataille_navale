package bataille_navale.partie;

public abstract class Partie {
	public abstract void start();

	protected void initNbrBat(int tab[][]) {
		for(int i = 0; i < tab.length; i++) {
			tab[i][1] = tab[i][2];
		}
	}
	
	protected int[][] copierNbrBat(int tab[][]) {
		int tabCopie[][] = new int[tab.length][tab[0].length];
		
		for(int i = 0; i < tab.length; i++) {
			for(int j = 0; j < tab[i].length; j++) {
				tabCopie[i][j] = tab[i][j];
			}
		}
		
		return tabCopie;
	}
}
