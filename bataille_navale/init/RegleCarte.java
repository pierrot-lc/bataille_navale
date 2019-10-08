package bataille_navale.init;

public class RegleCarte {
	private int nbrBat[][];
	private int widthCarte;
	private int heightCarte;
	
	public RegleCarte() {
		nbrBat = new int[15][3];
		defaultParam();
	}
	
	public int addBateaux(int taille, int nbr) {//Taille des bateaux et leurs nombre
		if(taille == 0)
			return -1;//Erreur
		
		if(taille > widthCarte || taille > heightCarte) {
			return -2;//Bateau trop grand pour la taille de la carte
		}
		
		int nbrTotCase = widthCarte*heightCarte, nbrCaseBat = 0;
		double result;
		for(int i = 0; i < nbrBat.length; i++) {//Compte le nombre de cases prises pour tout les bateaux
			nbrCaseBat += nbrBat[i][0]*nbrBat[i][1];
		}
		nbrCaseBat += taille*nbr;//Ajoute les futurs bateaux
		
		result = (double)nbrCaseBat/(double)nbrTotCase;//Ratio pour savoir si il reste beaucoup de place ou pas
		if(result > 0.7) {//Si les bateaux prennent plus de 70% de place sur la carte alors le placement échoue
			return -3;//Trop de bateaux sur la carte pour en rajouter plus
		}
		
		for(int i = 0; i < nbrBat.length; i++) {
			if(nbrBat[i][0] == taille) {//Recherche dans le tableau si les types de bateaux ne sont pas déjà existants
				nbrBat[i][1] = nbr;
				nbrBat[i][2] = nbr;//Si oui alors remplace le nombre de bateaux par le nouveau
				System.out.println("Doublon ! (Remplacement de l'ancienne valeur)");
				return 2;//Modification d'une ancienne valeur
			}
		}
		
		//Assigne une nouvelle entrée dans le tableau
		int i = 0;
		while(nbrBat[i][0] > 0 && i < nbrBat.length)//Cherche la dernière entrée vide
			i++;
		
		nbrBat[i][0] = taille;//Nbr de case
		nbrBat[i][1] = nbr;//Nbr de bateaux pour ce nbr de case
		nbrBat[i][2] = nbr;
		return 1;//Nouvelle entrée assignée sans soucis
	}
	
	public void defaultParam() {
		widthCarte = 10;
		heightCarte = 10;
		
		for(int i = 0; i < nbrBat.length; i++) {//Initialise les entrées
			nbrBat[i][0] = 0;
			nbrBat[i][1] = 0;
			nbrBat[i][2] = 0;
		}
		
		int j = 2;//Commence par des bateaux de 2 cases
		for(int i = 0; i < 4; i++) {
			nbrBat[i][0] = j;//Taille des bateaux
			nbrBat[i][1] = 1;//Nombre de bateaux de cette taille là
			nbrBat[i][2] = 1;//Nombre maximal de bateaux (utilisé après)
			if(j == 3) {
				nbrBat[i][1] = 2;//Deux bateaux à trois cases par défaut
				nbrBat[i][2] = 2;
			}
			j++;
		}
	}
	public void reinitParam() {
		widthCarte = 4;
		heightCarte = 4;
		
		for(int i = 0; i < nbrBat.length; i++) {//Initialise les entrées
			nbrBat[i][0] = 0;
			nbrBat[i][1] = 0;
			nbrBat[i][2] = 0;
		}
	}
	
	public void finaliserNbrBat() {
		int nbrBatTemp[][] = new int[nbrBat.length][3];
		
		for(int i = 0; i < nbrBat.length; i++) {//Copie le tableau dans un tableau temporaire
			nbrBatTemp[i][0] = nbrBat[i][0];
			nbrBatTemp[i][1] = nbrBat[i][1];
			nbrBatTemp[i][2] = nbrBat[i][2];
		}
		
		for(int i = 0; i < nbrBatTemp.length; i++) {//Trie dans l'ordre décroissant par rapport au nombre de bateaux
			for(int j = 0; j < nbrBatTemp.length - 1; j++) {
				if(nbrBatTemp[j][1] < nbrBatTemp[j+1][1]) {
					int temp1 = nbrBatTemp[j][0];//Le nombre de case
					int temp2 = nbrBatTemp[j][1];//Le nombre de bateau
					
					nbrBatTemp[j][0] = nbrBatTemp[j+1][0];
					nbrBatTemp[j][1] = nbrBatTemp[j+1][1];
					
					nbrBatTemp[j+1][0] = temp1;
					nbrBatTemp[j+1][1] = temp2;
				}
			}
		}
		
		int t = 0;
		do {//S'arrête au premier bateau vide
			t++;
			if(t >= nbrBat.length)
				break;
		} while(nbrBatTemp[t][1] > 0);
		
		nbrBat = new int[t][3];//Coupe toutes les entrées vide en ne faisant un tableau qu'avec les entrées pleines
		for(int i = 0; i < nbrBat.length; i++) {//Copie le tableau
			nbrBat[i][0] = nbrBatTemp[i][0];
			nbrBat[i][1] = nbrBatTemp[i][1];
		}
		
		for(int i = 0; i < nbrBat.length; i++) {//Trie dans l'ordre croissant
			for(int j = 0; j < nbrBat.length - 1; j++) {
				if(nbrBat[j][0] > nbrBat[j+1][0]) {
					int temp1 = nbrBat[j][0];//Le nombre de case
					int temp2 = nbrBat[j][1];//Le nombre de bateau
					
					nbrBat[j][0] = nbrBat[j+1][0];
					nbrBat[j][1] = nbrBat[j+1][1];
					
					nbrBat[j+1][0] = temp1;
					nbrBat[j+1][1] = temp2;
				}
			}
		}
		for(int i = 0; i < nbrBat.length; i++) {
			nbrBat[i][2] = nbrBat[i][1];//Copie le nombre de bateaux sur une troisième case pour sauvegarder le nombre maximal de bateau
		}
	}
	
	//Accesseurs
	public int[][] getNbrBat() {
		return this.nbrBat;
	}
	public int getWidthCarte() {
		return this.widthCarte;
	}
	public int getHeightCarte() {
		return this.heightCarte;
	}
	
	public void setWidthCarte(int w) {
		this.widthCarte = w;
	}
	public void setHeightCarte(int h) {
		this.heightCarte = h;
	}
}
