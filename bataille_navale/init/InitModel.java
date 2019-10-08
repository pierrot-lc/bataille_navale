package bataille_navale.init;

public class InitModel {
	private String mode;
	private String nomJ1;
	private String nomJ2;
	private int widthCarte, heightCarte;
	private int nbrBat[][];
	
	private boolean hote = true;
	private String ip = null;
	private int port = 0;
	
	public InitModel() {
		
	}
	
	//Accesseurs
	public String getMode() {
		return this.mode;
	}
	public String getNomJ1() {
		return this.nomJ1;
	}
	public String getNomJ2() {
		return this.nomJ2;
	}
	public int getWidthCarte() {
		return this.widthCarte;
	}
	public int getHeightCarte() {
		return this.heightCarte;
	}
	public int[][] getNbrBat() {
		return this.nbrBat;
	}
	
	//Mutateurs
	public void setMode(String mode) {
		this.mode = mode;
	}
	public void setNomJ1(String nom) {
		this.nomJ1 = nom;
	}
	public void setNomJ2(String nom) {
		this.nomJ2 = nom;
	}
	public void setWidthCarte(int nbr) {
		this.widthCarte = nbr;
	}
	public void setHeightCarte(int nbr) {
		this.heightCarte = nbr;
	}
	public void setNbrBat(int tab[][]) {
		this.nbrBat = tab;
	}
	
	//Partie réseau
	public boolean isHote() {
		return hote;
	}
	public String getIp() {
		return ip;
	}
	public int getPort() {
		return port;
	}
	
	public void setHote(boolean b) {
		hote = b;
	}
	public void setIp(String str) {
		ip = str;
	}
	public void setPort(int port) {
		this.port = port;
	}
}
