package bataille_navale.affichage;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import bataille_navale.observer.Observateur;

public class Fenetre extends JFrame implements Observateur {
	private static final long serialVersionUID = -7762060900169360122L;
	private JPanel container = new JPanel();
	
	public Fenetre() {
		super();
		this.setTitle("Bataille Navale");
		this.setSize(600, 600);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public Fenetre(JPanel container) {
		super();
		this.setTitle("Bataille Navale");
		this.setSize(600, 600);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    this.container = container;
	    this.setContentPane(this.container);
	    this.setVisible(true);
	}
	
	public void paintComponent(Graphics g) {//Actualise le container
		container.repaint();
		this.revalidate();
	}
	
	public void setPanel(JPanel panel) {
		this.container = panel;
	    this.setContentPane(this.container);
	    this.repaint();
	    this.revalidate();
	    
	    this.setVisible(true);
	}

	public void update() {
		this.repaint();
	}
}
