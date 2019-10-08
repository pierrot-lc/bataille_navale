package bataille_navale;

import bataille_navale.contenu.Pos;

public class Fct {
	public static void pause() {
		try {//Pour ne pas faire surchauffé le PC
			Thread.sleep(5);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void pauseLongue() {
		try {
			Thread.sleep(500);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void lireTabPos(Pos pos[]) {
		for(Pos p : pos) {
			System.out.println(p.toString());
		}
	}
}
