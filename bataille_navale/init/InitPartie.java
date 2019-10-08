package bataille_navale.init;

import bataille_navale.Fct;

public class InitPartie {
	private InitControleur control = new InitControleur();
	private ModePanel panMode = new ModePanel();
	private ParamPanel panParam = new ParamPanel();
	private InitVue vue = new InitVue(panMode);
	
	private InitModel model;
	
	public InitPartie() {
		
	}
	
	public void go() {
		while(panMode.isRunning()) {
			Fct.pause();
			if(panMode.wantSetParam()) {
				vue.setPanel(panParam);
				while(panParam.isRunning())
					Fct.pause();
				
				panParam.setRunning();//Pour le prochain appel du panel
				panMode.setParam(false);//Réinitialise
				vue.setPanel(panMode);//Colle l'ancien panel
			}
		}
		vue.dispose();
		initModel();
	}
	
	private void initModel() {//Initialise le model à partir des valeurs des panels
		control.setMode(panMode.getMode());
		if(panMode.getMode().equals("Local")) {
			control.setNomJ1(panMode.getNameJ1());
			control.setNomJ2(panMode.getNameJ2());
		}
		else
			control.setNomJ1(panMode.getNameJ());
		
		control.setNbrBat(panParam.getNbrBat());
		control.setHeightCarte(panParam.getHeightCarte());
		control.setWidthCarte(panParam.getWidthCarte());
		
		control.setHote(panMode.isHote());
		control.setIp(panMode.getIp());
		control.setPort(panMode.getPort());
		
		model = control.getModel();
	}
	
	public InitModel getModel() {
		return this.model;
	}
}
