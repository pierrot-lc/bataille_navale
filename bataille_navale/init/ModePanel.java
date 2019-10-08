 package bataille_navale.init;

import java.awt.BorderLayout;
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

public class ModePanel extends JPanel {
	private static final long serialVersionUID = -3461014340377842595L;
	
	private String mode = "Ordinateur";
	
	private JLabel lblMode = new JLabel("Mode :");
	private JLabel lblJ1 = new JLabel("Nom joueur 1");
	private JLabel lblJ2 = new JLabel("Nom joueur 2");
	private JLabel lblJ = new JLabel("Nom joueur");
	private JLabel lblPort = new JLabel("Port :");
	private JLabel lblIp = new JLabel("IP :");
	
	private ButtonGroup bg = new ButtonGroup();
	private JRadioButton jr2 = new JRadioButton("Local");
	private JRadioButton jr1 = new JRadioButton("Ordinateur");
	private JRadioButton jr3 = new JRadioButton("En ligne");
	
	private ButtonGroup bgr = new ButtonGroup();
	private JRadioButton jr1r = new JRadioButton("Hôte");
	private JRadioButton jr2r = new JRadioButton("Client");
	
	private JTextField nomJ1 = new JTextField();
	private JTextField nomJ2 = new JTextField();
	private JTextField nomJ = new JTextField();
	private JTextField jtfPort = new JTextField();
	private JTextField jtfIp = new JTextField();
	
	private JButton paramButton = new JButton("Paramètres");
	private JButton okButton = new JButton("OK");
	
	private JPanel panRadio = new JPanel();
	private JPanel panNom = new JPanel();
	private JPanel panBouton = new JPanel();
	private JPanel panNomJ1 = new JPanel();
	private JPanel panNomJ2 = new JPanel();	
	private JPanel panReseau = new JPanel();
	private JPanel panRadButReseau = new JPanel();
	private JPanel pan = new JPanel();
	
	private boolean running = true;
	private boolean setParam = false;
	private boolean hote = true;
	
	public ModePanel() {
		super();
		this.setSize(new Dimension(320, 320));
		initPanel();
	}
	
	private void initPanel() {
		bg.add(jr1);
		bg.add(jr2);
		bg.add(jr3);
		
		bgr.add(jr1r);
		bgr.add(jr2r);
		
		panRadio.add(lblMode);
		panRadio.add(jr1);
		panRadio.add(jr2);
		panRadio.add(jr3);
		
		panNom.setPreferredSize(new Dimension(this.getWidth(), 100));
		nomJ1.setPreferredSize(new Dimension(100, 20));
		nomJ2.setPreferredSize(new Dimension(100, 20));
		nomJ.setPreferredSize(new Dimension(100, 20));
		jtfPort.setPreferredSize(new Dimension(100, 20));
		
		panNomJ1.add(lblJ1);
		panNomJ1.add(nomJ1);
		panNomJ2.add(lblJ2);
		panNomJ2.add(nomJ2);
		
		actualisePanNom();
		
		panBouton.add(paramButton);
		panBouton.add(okButton);
		
		//Initialisation du panel mode en ligne
		panReseau.setLayout(new BorderLayout());
		panReseau.setPreferredSize(new Dimension(300, 70));
		panRadButReseau.add(jr1r);
		panRadButReseau.add(jr2r);
		jtfPort.setPreferredSize(new Dimension(100, 20));
		jtfPort.setText("25565");
		jtfIp.setPreferredSize(new Dimension(100, 20));
		jtfIp.setText("localhost");
		
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
		jr1r.addActionListener(new RadioReseauListener());
		jr2r.addActionListener(new RadioReseauListener());
						
		//Initialisation des radioButtons
		jr1.setSelected(true);
		jr1r.setSelected(true);
	}
	
	private void actualisePanNom() {
		panNom.removeAll();
		if(mode.equals("Local")) {
			panNom.add(panNomJ1);
			panNom.add(panNomJ2);
		}
		else if(mode.equals("Ordinateur")){
			panNom.add(lblJ);
			panNom.add(nomJ);
		}
		else if(mode.equals("En ligne")) {
			if(hote) {
				panNom.add(lblJ);
				panNom.add(nomJ);
				
				panReseau.removeAll();
				panReseau.add(panRadButReseau, BorderLayout.NORTH);
				pan.removeAll();			
				pan.add(lblPort);
				pan.add(jtfPort);
				panReseau.add(pan);
				panNom.add(panReseau);
			}
			else {
				panNom.add(lblJ);
				panNom.add(nomJ);
				
				panReseau.removeAll();
				panReseau.add(panRadButReseau, BorderLayout.NORTH);
				pan.removeAll();			
				pan.add(lblPort);
				pan.add(jtfPort);
				pan.add(lblIp);
				pan.add(jtfIp);
				panReseau.add(pan);
				panNom.add(panReseau);
			}
		}
		
		panNom.revalidate();
		panNom.repaint();
	}
	
	public void paintComponent(Graphics g) {//Affichage du panel
		actualisePanNom();
		this.revalidate();
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
	public boolean isHote() {
		return hote;
	}
	public String getIp() {
		return jtfIp.getText();
	}
	public String getPort() {
		return jtfPort.getText();
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
			if(mode.equals("En ligne")) {
				if(jtfPort.getText().equals(""))
					return;
				if(!hote && jtfIp.getText().equals(""))
					return;
			}
			running = false;//Plus besoin de ce panel !
		}
	}
	class RadioReseauListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JRadioButton radioReseau = (JRadioButton)event.getSource();
			String modeR = radioReseau.getText();
			if(modeR.equals("Hôte"))
				hote = true;
			else
				hote = false;
			repaint();
		}
	}
}
