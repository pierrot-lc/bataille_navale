package bataille_navale.placeur;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import bataille_navale.affichage.Bouton;
import bataille_navale.affichage.BoutonPanel;
import bataille_navale.contenu.Case;
import bataille_navale.contenu.Pos;
import bataille_navale.fonctionnement.Joueur;

public class PlaceurPanel extends JPanel {
	private static final long serialVersionUID = -7147488050639280455L;
	
	private BoutonPanel panBouton;
	
	private JLabel warning = new JLabel();
	private JLabel lblJoueur;
	private JLabel lblNbrBat[];
	
	private JButton creer = new JButton("Placer un bateau");
	private JButton ok = new JButton("OK");
	
	private JPanel panNorth = new JPanel();
	private JPanel panWest = new JPanel();
	
	private Joueur joueur;
	private int nbrBat[][];
	
	private PlaceurControleur control;
	
	private CarteListener carteListener = new CarteListener();
	private boolean selectionBateau = false;
	private ArrayList<Pos> posArray = new ArrayList<Pos>();
	
	private boolean running = true;
	
	public PlaceurPanel(Joueur joueur) {
		this.joueur = joueur;
		this.nbrBat = joueur.getCartePossedee().getNbrBat();
		this.control = new PlaceurControleur(this.joueur);
		initPanel();
	}
	
	private void initPanel() {
		this.removeAll();
		panBouton = new BoutonPanel(Bouton.transformerCarte(joueur.getCartePossedee()));
		
		//panNorth
		panNorth.setPreferredSize(new Dimension(600, 100));
		lblJoueur = new JLabel(joueur.getName());
		panNorth.add(lblJoueur);
		panNorth.add(warning);
		
		//panWest
		panWest.setPreferredSize(new Dimension(100, 500));
		panWest.add(new JLabel("Bateaux à placer"));
		lblNbrBat = new JLabel[nbrBat.length];
		for(int i = 0; i < nbrBat.length; i++) {
			String str;
			if(nbrBat[i][0] == 1)//Si une case, phrase au singulier
				str = nbrBat[i][0] + " case : " + nbrBat[i][1] + "/" + nbrBat[i][2];
			else
				str = nbrBat[i][0] + " cases : " + nbrBat[i][1] + "/" + nbrBat[i][2];
			
			lblNbrBat[i] = new JLabel(str);
			lblNbrBat[i].setPreferredSize(new Dimension(75, 30));
			panWest.add(lblNbrBat[i]);
		}
		
		boolean enable = false;
	    for(int i = 0; i < nbrBat.length; i++) {
	    	if(nbrBat[i][1] > 0)
	    		enable = true;//Active le bouton tant que tout les bateaux ne sont pas placés
	    }
	    creer.setEnabled(enable);//Bouton désactiver si on ne peut plus placer de bateaux
	    ok.setEnabled(!enable);//Bouton désactiver tant que tout les bateaux ne sont pas placés
		panWest.add(creer);
		panWest.add(ok);
	    
		//Colle tout les panels
		this.setLayout(new BorderLayout());
		this.add(panNorth, BorderLayout.NORTH);
		this.add(panBouton, BorderLayout.CENTER);
		this.add(panWest, BorderLayout.WEST);
		
		//Listeners
		addListener(panBouton.getTabBouton());
		creer.addActionListener(new BoutonListener());
		ok.addActionListener(new OkListener());
	}
	
	private void redessinerWest() {
		for(int i = 0; i < nbrBat.length; i++) {
			String str;
			if(nbrBat[i][0] == 1)//Si une case, phrase au singulier
				str = nbrBat[i][0] + " case : " + nbrBat[i][1] + "/" + nbrBat[i][2];
			else
				str = nbrBat[i][0] + " cases : " + nbrBat[i][1] + "/" + nbrBat[i][2];
			
			if(!lblNbrBat[i].getText().equals(str))
				lblNbrBat[i].setText(str);
		}
		
		boolean enable = false;
	    for(int i = 0; i < nbrBat.length; i++) {
	    	if(nbrBat[i][1] > 0)
	    		enable = true;//Active le bouton tant que tout les bateaux ne sont pas placés
	    }
	    creer.setEnabled(enable);//Bouton désactiver si on ne peut plus placer de bateaux
	    ok.setEnabled(!enable);//Bouton désactiver tant que tout les bateaux ne sont pas placés
	}
	public void paintComponent(Graphics g) {  
	    redessinerWest();
	    panBouton.checkPanel(control.getModel().getCarte());//Actualise le panel
	    this.revalidate();
	}
	
	//Accesseurs
	public Joueur getJoueur() {
		return this.joueur;
	}
	public int[][] getNbrBat() {
		return this.nbrBat;
	}
	
	//Etat
	public boolean isRunning() {
		return this.running;
	}
	
	//Méthodes private
	private void addListener(Bouton tabBouton[][]) {
		for(int i = 0; i < tabBouton.length; i++)
			for(int j = 0; j < tabBouton[i].length; j++) {
				tabBouton[i][j].addMouseListener(carteListener);
				tabBouton[i][j].addActionListener(carteListener);
			}
	}
	
	//Listeners
	class BoutonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JButton button = (JButton)event.getSource();
			if(!selectionBateau) {//Si on est pas en mode de selection de bateau alors on passe dans ce mode
				button.setText("Valider");
				selectionBateau = true;
				return;//On s'arrête là
			}
			
			//Validation d'un bateau
			Pos pos[] = transformerArray(posArray);
			
			int result = control.placerBateau(pos);//Vérifie et place le bateau si possible
			switch(result) {
			case -1:
				warning.setText("Cases du bateau non alignés");
				break;
			case -2:
				warning.setText("Positions non valides");
				break;
			case -3:
				warning.setText("Une des cases déjà occupée");
				break;
			case -4:
				warning.setText("Pas de bateaux disponibles pour ce nombre de cases");
				break;
			case 1:
				warning.setText("Bateau placé");
				break;
			}
			repaint();
			
			//Réinitialisations
			selectionBateau = false;
			button.setText("Placer un bateau");
			posArray = new ArrayList<Pos>();
			Bouton tabBouton[][] = panBouton.getTabBouton();
			for(int i = 0; i < tabBouton.length; i++)//Enlève tout les assombrissements des cases
				for(int j = 0; j < tabBouton[i].length; j++)
					tabBouton[i][j].setAssombrir(false);
		}
		
		private Pos[] transformerArray(ArrayList<Pos> array) {
			Pos pos[] = new Pos[array.size()];
			for(int i = 0; i < array.size(); i++) {
				pos[i] = array.get(i);
			}
			return pos;
		}
	}
	
	class CarteListener implements MouseListener, ActionListener {
		public void actionPerformed(ActionEvent event) {//Pour mieux récupéré un clic gauche(MouseListener est trop lent)
			//Si on est en mode selection de case et que ce n'est pas un clic droit
			if(selectionBateau) {
				Case c = ((Bouton)event.getSource()).getCase();
				
				boolean doublon = false;
				for(int i = 0; i < posArray.size(); i++) {
					if(posArray.get(i) == c.getPos())//Test si il y a un doublon ou pas
						doublon = true;
				}
				if(!doublon) {//Si ce n'est pas un doublon on peut ajouter la pos dans le tableau
					posArray.add(c.getPos());
					Bouton tabBouton[][] = panBouton.getTabBouton();
					tabBouton[c.getPos().getX()][c.getPos().getY()].setAssombrir(true);
				}
			}
		}
		
		public void mouseClicked(MouseEvent event) {//Utile uniquement pour détecter le clic droit
			if(SwingUtilities.isRightMouseButton(event)) {//Clic droit ?
				Case c = ((Bouton)event.getSource()).getCase();
				Pos pos = c.getPos();
				boolean result = control.delBateau(pos);
				if(result) {
					warning.setText("Bateau retiré");
					
					//Réinitialisations
					selectionBateau = false;
					creer.setText("Placer un bateau");
					posArray = new ArrayList<Pos>();
					Bouton tabBouton[][] = panBouton.getTabBouton();
					for(int i = 0; i < tabBouton.length; i++)//Enlève tout les assombrissements des cases
						for(int j = 0; j < tabBouton[i].length; j++)
							tabBouton[i][j].setAssombrir(false);
					
					repaint();
					return;
				}
			}
		}

		public void mouseEntered(MouseEvent e) {
			
		}

		public void mouseExited(MouseEvent e) {
			
		}

		public void mousePressed(MouseEvent e) {
			
		}

		public void mouseReleased(MouseEvent e) {
			
		}
	}
	
	class OkListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			running = false;
		}
	}
}
