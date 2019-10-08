package bataille_navale.init;

public class InitControleur {
	InitModel model;
	
	public InitControleur() {
		model = new InitModel();
	}
	
	public void setMode(String mode) {
		if(mode.equals("Local") || mode.equals("Ordinateur") || mode.equals("En ligne"))
			model.setMode(mode);
	}
	public void setNomJ1(String str) {
		if(!str.equals(null))
			model.setNomJ1(str);
	}
	public void setNomJ2(String str) {
		if(!str.equals(null))
			model.setNomJ2(str);
	}
	public void setNbrBat(int tab[][]) {
		model.setNbrBat(tab);
	}
	public void setWidthCarte(int nbr) {
		if(nbr > 0)
			model.setWidthCarte(nbr);
	}
	public void setHeightCarte(int nbr) {
		if(nbr > 0)
			model.setHeightCarte(nbr);
	}
	
	//Partie réseau
	public void setPort(String str) {
		if(!str.equals(""))
			model.setPort(Integer.parseInt(str));
	}
	public void setIp(String str) {
		model.setIp(str);
	}
	public void setHote(boolean b) {
		model.setHote(b);
	}
		
	
	public InitModel getModel() {
		return this.model;
	}
}