package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JPanel;

import bataille_navale.BatailleNavale;
import bataille_navale.Fct;
import bataille_navale.contenu.Carte;
import bataille_navale.contenu.Pos;
import bataille_navale.contenu.etats.Contenu;
import bataille_navale.contenu.etats.bateau.BateauCorps;
import bataille_navale.fonctionnement.Regle;
import bataille_navale.jdialog.DialogFinPartie;
import bataille_navale.partie.PartieMulti;

public abstract class FluxPartie {
	protected PrintWriter pwOut;
	protected BufferedReader brIn;
	protected MsgReader MR;
	protected int pret = 0;
	
	protected boolean playing, ready;
	protected Carte carte, carteOpposant;
	private JPanel panO;
	
	public int envoyerPos(Pos pos) {
		MR.interruptThread();
		System.out.println("Envois de la pos " + pos.toString());
		pwOut.println("tir");
		pwOut.flush();
		
		pwOut.println(pos.getX());
		pwOut.flush();
		pwOut.println(pos.getY());
		pwOut.flush();
		System.out.println("Fin de l'envois, attente de la récupèration de la valeur du tir");
		
		String message = "";
		try {
			do {
				message = brIn.readLine();
			} while(!message.equalsIgnoreCase("coup"));
			
			int coup = Integer.parseInt(brIn.readLine());
			System.out.println("Valeur du coup : " + coup);
			if(coup == 1)
				recevoirPosBatCoule();
			else
				Carte.modifierCarte(carteOpposant, coup, pos);
			MR.startThread();
			return coup;
		} catch (IOException e) {e.printStackTrace();}
		MR.startThread();
		return -1;
	}
	
	public void recevoirPos() {
		pwOut.println("fake");
		pwOut.flush();
		try {
			System.out.println("Attente de reception des coordonnes du tir");
			int x = Integer.parseInt(brIn.readLine());
			int y = Integer.parseInt(brIn.readLine());
			
			Pos pos = new Pos(x, y);
			System.out.println("Coordonnees reçues" + pos.toString());
			
			int coup = Regle.tir(carte, pos);
			System.out.println("Valeur du coup : " + coup);
			Regle.setCase(coup, pos, carte);//Change la case en fonction du coup !
			panO.repaint();
			
			pwOut.println("coup");
			pwOut.flush();
			pwOut.println(coup);
			pwOut.flush();
			if(coup == 1)
				envoyePosBatCoule(pos);
			
		} catch (IOException e) {e.printStackTrace();}
	}
	
	private void envoyePosBatCoule(Pos pos) {
		Contenu c = carte.getCase(pos).getContenu();
		if(!(c instanceof BateauCorps))
			return;
		
		System.out.println("Envois des coordonnees du bateau");
		
		BateauCorps bat = (BateauCorps)c;
		int nbrCase = bat.getNbrCases();
		
		Pos posBat[] = bat.getPos();
		pwOut.println(nbrCase);
		pwOut.flush();
		for(int i = 0; i < posBat.length; i++) {
			Pos posTemp = posBat[i];
			pwOut.println(posTemp.getX());
			pwOut.flush();
			pwOut.println(posTemp.getY());
			pwOut.flush();
		}
	}
	private void recevoirPosBatCoule() {
		System.out.println("Début de reception des coordonnees du bateau coulé");
		try {
			int nbrCase = Integer.parseInt(brIn.readLine());
			System.out.println("Taille du bateau reçue : " + nbrCase);
			Pos posBat[] = new Pos[nbrCase];
			
			for(int i = 0; i < posBat.length; i++) {
				int x = Integer.parseInt(brIn.readLine());
				int y = Integer.parseInt(brIn.readLine());
				posBat[i] = new Pos(x, y);
				System.out.println("Case " + i + " reçue" + posBat[i].toString());
			}
			carteOpposant.placerBateau(posBat);
			Regle.setCase(1, posBat[0], carteOpposant);//Change la case en fonction du coup !
		} catch (NumberFormatException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
	}
	
	public void finirPartie() {
		PartieMulti.gagne = true;
		Fct.pauseLongue();
		new DialogFinPartie(null, true, false);
		BatailleNavale.fen.dispose();
	}
	
	public void partieGagnee() {
		pwOut.println("PartieGagnee");
		pwOut.flush();
		new DialogFinPartie(null, true, true);
	}
	
	public void finPlacement() {
		this.pret++;
		pwOut.println("FinPlacement");
		pwOut.flush();
	}
	
	public void finDeTour(){
		pwOut.println("FinDeTour");
		pwOut.flush();
		playing = false;
	}
	
	public void incrementPret(){
		this.pret++;
		System.out.println("Pret : " + pret);
	}
	
	public void setCarte(Carte c) {
		carte = c;
	}
	public void setCarteOpposant(Carte c) {
		carteOpposant = c;
	}
	public void setPanO(JPanel pan) {
		panO = pan;
	}
	public void setPlaying(boolean b) {
		playing = b;
	}
	public void setReady(boolean b) {
		ready = b;
	}
	
	public boolean isPlaying() {
		return playing;
	}
	public boolean isReady() {
		return ready;
	}

	public int getPret() {
		return this.pret;
	}
}
