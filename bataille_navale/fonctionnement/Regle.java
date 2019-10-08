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
			if(bat.getNbrCases() > 1) {//Si le bateau fais plus d'une 1 case il faut vérifier si les autres cases sont déjà touchées ou pas
				Pos posBat[] = bat.getPos();
				
				for(int i = 0; i < posBat.length; i++) {//Parcours toutes les cases du bateau
					Contenu content = carte.getCase(posBat[i]).getContenu();//Récupère le contenu de la case de la pos actuelle
					if((content instanceof Bateau) && (posBat[i].getX() != pos.getX() || posBat[i].getY() != pos.getY())) {//Pour ne pas comparé avec notre propre bateau
						//Si l'une des deux seulement est égale (normal puisqu'il est aligné sur un axe)
						//Si les deux sont valides alors c'est que on est sur la case même du tir donc pas de comparaison
						return 2;//Bateau touché !
					}
				}
			}
			
			return 1;//Si le bateau ne fais pas plus d'une case ou que les autres vérifications ne sont pas valides il est coulé
		}
		else if(cont instanceof BateauPloufant)
			return 5;//Bateau ploufant deja checkée !
		else if(cont instanceof Mer)
			return 0;//Case vide
		else if(cont instanceof BateauPlouf)
			return 3;//Case bateau plouf déjà checkée !
		else if(cont instanceof Plouf)
			return 4;//Case mer déjà checkée !
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
		
		int nbrBat[][] = carte.getNbrBat();//Ensuite réduis de 1 dans le tableau
		for(int i = 0; i < nbrBat.length; i++) {
			if(nbrBat[i][0] == pos.length)//Taille du bateau
				nbrBat[i][1] -= 1;
		}
		System.out.println("Un bateau a été coulé !");
	}
	
	public static void setCase(int coup, Pos p, Carte carte) {
		Contenu content = carte.getCase(p).getContenu();//Contenu de la carte à la pos de la case
		Case c = carte.getCase(p);
		
		switch(coup) {
		case 0://Case touchée : mer
			c.setContenu(new Plouf());
			carte.setCase(c.getPos(), c.getContenu());
			break;
		case 1://Case touchée : Bateau qui maintenant est coulé
			if(content instanceof BateauCorps) {//Sécurité
				BateauCorps bat = (BateauCorps)content;				
				Regle.batPloufer(carte, bat);//Puis test si le bateau est completement ploufer ou pas (et change son etat si oui)
			}
			break;
		case 2://Case touchée : Bateau qui n'est pas coulé encore
			if(content instanceof BateauCorps) {
				BateauCorps bat = (BateauCorps)content;
				Pos pos[] = bat.getPos();
				int id = bat.getId();
				
				c.setContenu(new BateauPloufant(id, pos));//Copie du bateau mais en version ploufant
				carte.setCase(c.getPos(), c.getContenu());//Change l'état de la case
			}
			break;
		case 3://Case touchée : case bateau plouf déjà checkée !
			break;
		case 4://Case touchée : case plouf déjà checkée !
			break;
		case 5://Case touchée : case bateauPloufant déjà checkée !
			break;
		}
	}
	
	public static boolean termine(Carte carte) {
		for(int i = 0; i < carte.getWidth(); i++) {
			for(int j = 0; j < carte.getHeight(); j++) {
				Case c = carte.getCase(new Pos(i, j));
				if(c.getContenu() instanceof Bateau)//Bateau non découvert
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
