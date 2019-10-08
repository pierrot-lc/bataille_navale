package animations_crabe;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GererAnimation {
	private JPanel panel;
	private Thread t = null;
	
	private String mode = "";
	
	public GererAnimation(JPanel panel) {
		this.panel = panel;
	}
	
	public void setAnimation(String str) {
		mode = str;
		
		if(t != null)
			t.interrupt();
		t = null;
		
		t = new Thread(new Animation(str));
		
		if(t != null)
			t.start();
	}
	
	public String getAnimation() {
		return mode;
	}
	
	class Animation implements Runnable {
		private JLabel tabLbl[];
		
		private boolean running = true;
		private String mode;
		
		public Animation(String mode) {
			this.mode = mode;
		}
		
		@Override
		public void run() {
			init();
			
			while(running) {
				try {
					if(mode.equals("Normal"))
						modeNormal();
					else if(mode.equals("Thuglife"))
						modeThuglife();
					else {
						for(int i = 0; i < tabLbl.length; i++) {
							panel.removeAll();
							panel.add(tabLbl[i]);
							panel.revalidate();
							panel.repaint();
							Thread.sleep(500);
						}
					}
				} catch(InterruptedException e) {running = false;}
			}
		}
		
		private void modeNormal() {
			try {
				for(int i  = 0; i < tabLbl.length; i++) {
					panel.removeAll();
					panel.add(tabLbl[i]);
					panel.revalidate();
					panel.repaint();
					
					if(i == 0)
						Thread.sleep(2000);
					else
						Thread.sleep(200);
				}
			} catch (InterruptedException e) {running = false;}
		}
		private void modeThuglife() {
			try {
				for(int i  = 0; i < tabLbl.length; i++) {
					panel.removeAll();
					panel.add(tabLbl[i]);
					panel.revalidate();
					panel.repaint();
					
					if(i == 3)
						Thread.sleep(3000);
					else
						Thread.sleep(100);
				}
			} catch (InterruptedException e) {running = false;}
		}
		
		private void init() {
			if(mode.equals("Waiting")) {
				tabLbl = new JLabel[2];
				tabLbl[0] = new JLabel(new ImageIcon("Crabes/crabe_waiting.gif"));
				tabLbl[1] = new JLabel(new ImageIcon("Crabes/crabe_waiting2.gif"));
			}
			else if(mode.equals("Angry_1")) {
				tabLbl = new JLabel[2];
				tabLbl[0] = new JLabel(new ImageIcon("Crabes/crabe_angry_1.gif"));
				tabLbl[1] = new JLabel(new ImageIcon("Crabes/crabe_angry_12.gif"));
			}
			else if(mode.equals("Angry_2")) {
				tabLbl = new JLabel[2];
				tabLbl[0] = new JLabel(new ImageIcon("Crabes/crabe_angry_2.gif"));
				tabLbl[1] = new JLabel(new ImageIcon("Crabes/crabe_angry_22.gif"));
			}
			else if(mode.equals("Happy_1")) {
				tabLbl = new JLabel[2];
				tabLbl[0] = new JLabel(new ImageIcon("Crabes/crabe_happy_1.gif"));
				tabLbl[1] = new JLabel(new ImageIcon("Crabes/crabe_happy_12.gif"));
			}
			else if(mode.equals("Happy_2")) {
				tabLbl = new JLabel[4];
				tabLbl[0] = new JLabel(new ImageIcon("Crabes/crabe_happy_2.gif"));
				tabLbl[1] = new JLabel(new ImageIcon("Crabes/crabe_happy_22.gif"));
				tabLbl[2] = new JLabel(new ImageIcon("Crabes/crabe_happy_2.gif"));
				tabLbl[3] = new JLabel(new ImageIcon("Crabes/crabe_happy_23.gif"));
			}
			else if(mode.equals("Héhéhé")) {
				tabLbl = new JLabel[2];
				tabLbl[0] = new JLabel(new ImageIcon("Crabes/crabe_normal.gif"));
				tabLbl[1] = new JLabel(new ImageIcon("Crabes/crabe_hehe.gif"));
			}
			else if(mode.equals("Sad")) {
				tabLbl = new JLabel[2];
				tabLbl[0] = new JLabel(new ImageIcon("Crabes/crabe_sad.gif"));
				tabLbl[1] = new JLabel(new ImageIcon("Crabes/crabe_sad2.gif"));
			}
			else if(mode.equals("Thuglife")) {
				tabLbl = new JLabel[4];
				tabLbl[0] = new JLabel(new ImageIcon("Crabes/crabe_thuglife1.gif"));
				tabLbl[1] = new JLabel(new ImageIcon("Crabes/crabe_thuglife2.gif"));
				tabLbl[2] = new JLabel(new ImageIcon("Crabes/crabe_thuglife3.gif"));
				tabLbl[3] = new JLabel(new ImageIcon("Crabes/crabe_thuglife4.gif"));
			}
			else {
				tabLbl = new JLabel[4];
				tabLbl[0] = new JLabel(new ImageIcon("Crabes/crabe_normal.gif"));
				tabLbl[1] = new JLabel(new ImageIcon("Crabes/crabe_normal2.gif"));
				tabLbl[2] = new JLabel(new ImageIcon("Crabes/crabe_normal.gif"));
				tabLbl[3] = new JLabel(new ImageIcon("Crabes/crabe_normal2.gif"));
			}
		}
	}
}
