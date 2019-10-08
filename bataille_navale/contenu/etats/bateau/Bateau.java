package bataille_navale.contenu.etats.bateau;

import bataille_navale.contenu.Pos;

public class Bateau extends BateauCorps {
	
	public Bateau(int id, Pos pos) {
		super(id, pos);
		this.setName("Bateau");
	}
	public Bateau(int id, Pos pos[]) {
		super(id, pos);
		this.setName("Bateau");
	}
}