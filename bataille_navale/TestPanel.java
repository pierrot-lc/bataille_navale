package bataille_navale;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class TestPanel extends JPanel {
private static final long serialVersionUID = -3461014340377842595L;
	
	private String mode = "Ordinateur";
	
	private JLabel lblMode = new JLabel("Mode");
	private JLabel lblJ1 = new JLabel("Nom joueur 1");
	private JLabel lblJ2 = new JLabel("Nom joueur 2");
	private JLabel lblJ = new JLabel("Nom joueur");
	
	private JRadioButton jr1 = new JRadioButton("Local");
	private JRadioButton jr2 = new JRadioButton("Ordinateur");
	private JRadioButton jr3 = new JRadioButton("En ligne");	
	
	private JTextField nomJ1 = new JTextField();
	private JTextField nomJ2 = new JTextField();
	private JTextField nomJ = new JTextField();
	
	private JButton paramButton = new JButton("Paramètres");
	private JButton okButton = new JButton("OK");
	
	private JPanel panRadio = new JPanel();
	private JPanel panNom = new JPanel();
	private JPanel panBouton = new JPanel();
	private JPanel panNomJ1 = new JPanel();
	private JPanel panNomJ2 = new JPanel();	
	
	private boolean running = true;
	private boolean setParam = false;
	
	public TestPanel() {
		super();
		this.setSize(new Dimension(320, 320));
		initPanel();
	}
	
	private void initPanel() {
		ButtonGroup bg = new ButtonGroup();
		bg.add(jr1);
		bg.add(jr2);
		bg.add(jr3);
		
		panRadio.add(lblMode);
		panRadio.add(jr1);
		panRadio.add(jr2);
		panRadio.add(jr3);
		
		panNom.setPreferredSize(new Dimension(this.getWidth(), 100));
		nomJ1.setPreferredSize(new Dimension(100, 20));
		nomJ2.setPreferredSize(new Dimension(100, 20));
		nomJ.setPreferredSize(new Dimension(100, 20));
		
		panNomJ1.add(lblJ1);
		panNomJ1.add(nomJ1);
		panNomJ2.add(lblJ2);
		panNomJ2.add(nomJ2);
		
		actualisePanNom();
		
		panBouton.add(paramButton);
		panBouton.add(okButton);
		
		//Ajout des panels sur le panel principal
		this.add(panRadio);
		this.add(panNom);
		this.add(panBouton);
		
		//Les listeners
		jr1.addActionListener(new RadioListener());
		jr2.addActionListener(new RadioListener());			
		jr3.addActionListener(new RadioListener());
		paramButton.addActionListener(new BoutonParamListener());
		okButton.addActionListener(new BoutonOkListener());
						
		//Initialisation des radioButtons
		jr1.setSelected(true);
		System.out.println("Fin de l'initialisation du panel");
	}
	
	private void actualisePanNom() {
		panNom.removeAll();
		
		if(mode.equals("Local")) {
			panNom.add(panNomJ1);
			panNom.add(panNomJ2);
		}
		else {
			panNom.add(lblJ);
			panNom.add(nomJ);
		}
	}
	
	public void paintComponent(Graphics g) {//Affichage du panel
		actualisePanNom();
		
		this.add(panRadio);
		this.add(panNom);
		this.add(panBouton);
	}
	
	//Accesseurs
	public String getNameJ1() {
		return nomJ1.getText();
	}
	public String getNameJ2() {
		return nomJ2.getText();
	}
	public String getNameJ() {
		return nomJ.getText();
	}
	public String getMode() {
		return this.mode;
	}
	
	//Mutateurs
	public boolean isRunning() {
		return this.running;
	}
	public boolean wantSetParam() {
		return this.setParam;
	}
	public void setParam(boolean bool) {
		this.setParam = bool;
	}
	
	//Listeners
	class RadioListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JRadioButton radio = (JRadioButton)event.getSource();
			mode = radio.getText();
			repaint();
		}
	}
	class BoutonParamListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			setParam = true;
		}
	}
	class BoutonOkListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {		
			running = false;//Plus besoin de ce panel !
		}
	}
}
