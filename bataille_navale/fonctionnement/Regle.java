package bataille_navale.fonctionnement;

import bataille_navale.contenu.Carte;
import bataille_navale.contenu.Case;
import bataille_navale.contenu.Pos;
import bataille_navale.contenu.etats.Contenu;
import bataille_navale.contenu.etats.Mer;
import bataille_navale.contenu.etats.Plouf;
import bataille_navale.contenu.etats.bateau.Bateau;
import bataille_navale.contenu.etats.bateau.BateauCorps;
import bataille_navale.contenu.etats.bateau.BateauPlouf;
import bataille_navale.contenu.etats.bateau.BateauPloufant;

public class Regle {
	//--------Methodes-----------//
	public static int tir(Carte carte, Pos pos){//Pos vaut la pos du tir
		Contenu cont = carte.getCase(pos).getContenu();
		
		if(cont instanceof Bateau) {
			Bateau bat = (Bateau)cont;
			if(bat.getNbrCases() > 1) {//Si le bateau fais plus d'une 1 case il faut v�rifier si les autres cases sont d�j� touch�es ou pas
				Pos posBat[] = bat.getPos();
				
				for(int i = 0; i < posBat.length; i++) {//Parcours toutes les cases du bateau
					Contenu content = carte.getCase(posBat[i]).getContenu();//R�cup�re le contenu de la case de la pos actuelle
					if((content instanceof Bateau) && (posBat[i].getX() != pos.getX() || posBat[i].getY() != pos.getY())) {//Pour ne pas compar� avec notre propre bateau
						//Si l'une des deux seulement est �gale (normal puisqu'il est align� sur un axe)
						//Si les deux sont valides alors c'est que on est sur la case m�me du tir donc pas de comparaison
						return 2;//Bateau touch� !
					}
				}
			}
			
			return 1;//Si le bateau ne fais pas plus d'une case ou que les autres v�rifications ne sont pas valides il est coul�
		}
		else if(cont instanceof BateauPloufant)
			return 5;//Bateau ploufant deja check�e !
		else if(cont instanceof Mer)
			return 0;//Case vide
		else if(cont instanceof BateauPlouf)
			return 3;//Case bateau plouf d�j� check�e !
		else if(cont instanceof Plouf)
			return 4;//Case mer d�j� check�e !
		else
			return -1;//Code erreur
	}
	
	public static void batPloufer(Carte carte, BateauCorps bat) {
		Pos pos[] = bat.getPos();
		//On le coule completement !
		int id = bat.getId();
		BateauPlouf batPlouf = new BateauPlouf(id, pos);
		for(int i = 0; i < pos.length; i++)
			carte.getCase(pos[i]).setContenu(batPlouf);//Transforme toute les cases des pos en BateauPlouf
		
		int nbrBat[][] = carte.getNbrBat();//Ensuite r�duis de 1 dans le tableau
		for(int i = 0; i < nbrBat.length; i++) {
			if(nbrBat[i][0] == pos.length)//Taille du bateau
				nbrBat[i][1] -= 1;
		}
		System.out.println("Un bateau a �t� coul� !");
	}
	
	public static void setCase(int coup, Pos p, Carte carte) {
		Contenu content = carte.getCase(p).getContenu();//Contenu de la carte � la pos de la case
		Case c = carte.getCase(p);
		
		switch(coup) {
		case 0://Case touch�e : mer
			c.setContenu(new Plouf());
			carte.setCase(c.getPos(), c.getContenu());
			break;
		case 1://Case touch�e : Bateau qui maintenant est coul�
			if(content instanceof BateauCorps) {//S�curit�
				BateauCorps bat = (BateauCorps)content;				
				Regle.batPloufer(carte, bat);//Puis test si le bateau est completement ploufer ou pas (et change son etat si oui)
			}
			break;
		case 2://Case touch�e : Bateau qui n'est pas coul� encore
			if(content instanceof BateauCorps) {
				BateauCorps bat = (BateauCorps)content;
				Pos pos[] = bat.getPos();
				int id = bat.getId();
				
				c.setContenu(new BateauPloufant(id, pos));//Copie du bateau mais en version ploufant
				carte.setCase(c.getPos(), c.getContenu());//Change l'�tat de la case
			}
			break;
		case 3://Case touch�e : case bateau plouf d�j� check�e !
			break;
		case 4://Case touch�e : case plouf d�j� check�e !
			break;
		case 5://Case touch�e : case bateauPloufant d�j� check�e !
			break;
		}
	}
	
	public static boolean termine(Carte carte) {
		for(int i = 0; i < carte.getWidth(); i++) {
			for(int j = 0; j < carte.getHeight(); j++) {
				Case c = carte.getCase(new Pos(i, j));
				if(c.getContenu() instanceof Bateau)//Bateau non d�couvert
					return false;
			}
		}
		int nbrBat[][] = carte.getNbrBat();
		for(int i = 0; i < nbrBat.length; i++)
			if(nbrBat[i][1] > 0)
				return false;
		
		return true;
	}
}
