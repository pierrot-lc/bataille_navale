package bataille_navale.contenu.etats.bateau;

import bataille_navale.contenu.Pos;
import bataille_navale.contenu.etats.Contenu;

public abstract class BateauCorps extends Contenu {
	private String name = "BateauCorps";
	private Pos pos[];
	private int nbrCases;
	private int id;
	
	//Contructeurs
	public BateauCorps(int id, Pos pos) {
		this.pos = new Pos[1];
		this.pos[0] = new Pos(pos.getX(), pos.getY());
		this.nbrCases = 1;
		this.id = id;
	}
	public BateauCorps(int id, Pos pos[]) {
		this.nbrCases = pos.length;
		this.pos = pos;
		this.id = id;
	}
	public BateauCorps() {
		
	}
	
	//Mutateurs
	public void setName(String name) {
		this.name = name;
	}
	public void setPos(Pos pos[]) {
		this.pos = pos;
		this.nbrCases = this.pos.length;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	//Accesseurs
	public int getNbrCases() {
		return this.nbrCases;
	}
	public String getName() {
		return this.name;
	}
	public Pos[] getPos() {
		return this.pos;
	}
	public int getId() {
		return this.id;
	}
}
