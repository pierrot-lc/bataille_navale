package bataille_navale.init;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import bataille_navale.observer.Observateur;

public class InitVue extends JFrame implements Observateur {
	private static final long serialVersionUID = 2155653895059605420L;
	private JPanel container = new JPanel();
	
	public InitVue(JPanel container) {
		super();
		this.setTitle("Paramètres de la partie");
		this.setSize(320, 320);
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
	}

	public void update() {
		this.repaint();
	}
}
