package bataille_navale.jdialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import animations_crabe.GererAnimation;

public class DialogFinPartie extends JDialog {
	private static final long serialVersionUID = 6379275710979788381L;
	
	private JPanel panCenter = new JPanel();
	private JPanel panAnimation = new JPanel();
	private GererAnimation animation;
	
	private JButton button = new JButton("OK");
	
	private boolean isWin;
	
	public DialogFinPartie(JFrame parent, boolean model, boolean isWin) {
		super(parent, "Fin de la partie !", model);
		this.setSize(270, 200);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		this.isWin = isWin;
		
		init();
		
		this.setVisible(true);
	}
	
	private void init() {
		panAnimation.setPreferredSize(new Dimension(150, 150));
		animation = new GererAnimation(panAnimation);
		
		panCenter.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lbl = new JLabel();
		if(isWin) {
			lbl.setText("Vous avez gagné !");
			panCenter.add(lbl);
			animation.setAnimation("Thuglife");
		}
		else {
			lbl.setText("Vous avez perdu !");
			panCenter.add(lbl);
			animation.setAnimation("Sad");
		}
		
		button.setPreferredSize(new Dimension(60, 30));		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		panCenter.add(button);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.add(panAnimation, BorderLayout.WEST);
		this.add(panCenter, BorderLayout.CENTER);
	}
}
