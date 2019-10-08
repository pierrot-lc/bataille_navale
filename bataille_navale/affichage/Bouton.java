package bataille_navale.affichage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import bataille_navale.contenu.Carte;
import bataille_navale.contenu.Case;
import bataille_navale.contenu.Pos;
import bataille_navale.contenu.etats.Contenu;
import bataille_navale.contenu.etats.Etat;
import bataille_navale.contenu.etats.Mer;
import bataille_navale.contenu.etats.Plouf;
import bataille_navale.contenu.etats.bateau.Bateau;
import bataille_navale.contenu.etats.bateau.BateauCorps;
import bataille_navale.contenu.etats.bateau.BateauPlouf;
import bataille_navale.contenu.etats.bateau.BateauPloufant;

public class Bouton extends JButton {
	//Variable commune à tout les boutons
	private static RescaleOp rescaleOp = new RescaleOp(0.7f, 0.0f, null);//Assombrissement de 70%
	
	//Variables
	private static final long serialVersionUID = -3397196338284408262L;
	private BufferedImage img = null;
	private Etat etat = Etat.MER;
	private Case c;
	private AffineTransform tx;
	private AffineTransformOp op;
	private boolean assombrir = false;
	
	//Constructeurs
	public Bouton(Pos pos, Carte carte) {
		super("Bouton");
		c = carte.getCase(pos);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		tx = AffineTransform.getRotateInstance(0, 0, 0);//Initialise les paramètres de rotations
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);//Objet de rotation utilisé pour dessiner l'image
		
		switch(etat) {
		case MER:
			try {
				img = ImageIO.read(new File("MerBleue.png"));
			} catch(IOException e) {
				e.printStackTrace();
			}
			break;
		case PLOUF:
			try {
				img = ImageIO.read(new File("Plouf.png"));
			} catch(IOException e) {
				e.printStackTrace();
			}
			break;
		case BATEAU:
			dessinerBateau(false);
			break;
		case BATEAUPLOUF:
			dessinerBateau(true);
			break;
		case BATEAUPLOUFANT:
			try {
				img = ImageIO.read(new File("BateauPloufant.png"));
			} catch(IOException e) {
				e.printStackTrace();
			}
			break;
		}
		
		if(assombrir)
			img = rescaleOp.filter(img, null);//Assombrie l'image de 70%
		
		g2d.drawImage(op.filter(img, null), 0, 0, this.getWidth(), this.getHeight(), this);//Dessine l'image
	}
	
	//Accesseurs et mutateurs
	public void setEtat(Etat etat) {
		this.etat = etat;
	}
	public void setCase(Case c) {
		this.c = c;
	}
	
	public Etat getEtat() {
		return this.etat;
	}
	public Case getCase() {
		return this.c;
	}
	
	private void dessinerBateau(boolean plouf) {
		BateauCorps content;
		if(!(c.getContenu() instanceof BateauCorps)){
			System.out.println("Erreur d'affichage du bouton ! Instance de la case : " + c.getContenu().getClass());
			return;
		}
		else
			content = (BateauCorps)c.getContenu();
		
		Pos pos[] = content.getPos();
		boolean horizontal;
		
		if(pos.length == 1) {//Bateau à une case
			try {
				if(plouf)
					img = ImageIO.read(new File("BateauPlouf.png"));
				else
					img = ImageIO.read(new File("Bateau.png"));
			} catch(IOException e) {
				e.printStackTrace();
			}
			return;//Fin du dessin pour un bateau à une case
		}
		
		if(pos[0].getX() != pos[1].getX())//Alignement horizontal
			horizontal = true;
		else
			horizontal = false;
		
		int numeroPos = 0;
		for(int i = 0; i < pos.length; i++) {
			if((c.getPos().getX() == pos[i].getX()) && (c.getPos().getY() == pos[i].getY()))
				numeroPos = i;//Récupère l'endroit du bateau sur lequel on travail
		}
		if(numeroPos == 0) {//Début du bateau
			try {
				if(plouf)
					img = ImageIO.read(new File("Debut_bateauPlouf.png"));
				else
					img = ImageIO.read(new File("Debut_bateau.png"));
			} catch(IOException e) {
				e.printStackTrace();
			}	
		}
		else if(numeroPos == pos.length - 1) {//Fin du bateau
			try {
				if(plouf)
					img = ImageIO.read(new File("Fin_bateauPlouf.png"));
				else
					img = ImageIO.read(new File("Fin_bateau.png"));
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		else {//Entre le début et la fin (répétition du milieu)
			try {
				if(plouf)
					img = ImageIO.read(new File("Milieu_bateauPlouf.png"));
				else
					img = ImageIO.read(new File("Milieu_bateau.png"));
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		if(!horizontal) {
			tx = AffineTransform.getRotateInstance(Math.PI/2, img.getWidth()/2, img.getHeight()/2);//Initialise les paramètres de rotations
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		}
	}
	
	//Méthode static
	public static Bouton[][] transformerCarte(Carte carte) {
		int x = carte.getWidth();
		int y = carte.getHeight();
		Bouton tabBouton[][] = new Bouton[x][y];
		
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				Pos pos = new Pos(i, j);
				tabBouton[i][j] = new Bouton(pos, carte);
				
				Contenu c = carte.getCase(pos).getContenu() ;
				if(c instanceof Bateau)
					tabBouton[i][j].setEtat(Etat.BATEAU);
				else if(c instanceof BateauPlouf)
					tabBouton[i][j].setEtat(Etat.BATEAUPLOUF);
				else if(c instanceof BateauPloufant)
					tabBouton[i][j].setEtat(Etat.BATEAUPLOUFANT);
				else if(c instanceof Mer)
					tabBouton[i][j].setEtat(Etat.MER);
				else if(c instanceof Plouf)
					tabBouton[i][j].setEtat(Etat.PLOUF);
			}
		}
		
		return tabBouton;
	}
	
	public void setAssombrir(boolean bool) {
		assombrir = bool;
	}
}
