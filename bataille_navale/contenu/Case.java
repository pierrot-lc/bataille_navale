package bataille_navale.contenu;

import bataille_navale.contenu.etats.Contenu;
import bataille_navale.contenu.etats.Mer;

public class Case {
	//Variables
	private Pos pos;
	private Contenu contenu = new Mer();
	
	//Constructeurs
	public Case() {
		pos = new Pos(0, 0);
	}
	public Case(int x, int y) {
		pos = new Pos(x, y);
	}
	public Case(Pos pos) {
		this.pos = pos;
	}
	
	//Accesseurs et mutateurs
	public void setContenu(Contenu cont) {
		this.contenu = cont;
	}
	public void setPos(Pos pos) {
		this.pos = pos;
	}
	
	public Contenu getContenu() {
		return this.contenu;
	}
	public Pos getPos() {
		return this.pos;
	}
}
