package reseau;

import java.io.BufferedReader;
import java.io.IOException;

public class MsgReader implements Runnable{

	private BufferedReader brIn;
	private FluxPartie flux;
	private Thread t;
	
	public MsgReader(BufferedReader _brIn, FluxPartie flux){
		this.brIn = _brIn;
		this.flux = flux;
		t = new Thread(this);
		t.start();
	}
	
	private void testMessage(String message) {
		if(message == null)
			return;
		if(message.equalsIgnoreCase("Parametres")) {
			if(flux instanceof Server) {
				System.out.println("Demande de parametres recu !");
				Server server = (Server)flux;
				server.sendParam();
			}
		}
		else if(message.equalsIgnoreCase("tir")) {
			System.out.println("Demande de tir recue");
			flux.recevoirPos();
		}
		else if(message.equalsIgnoreCase("FinDeTour")) {
			flux.setPlaying(true);
		}
		else if(message.equalsIgnoreCase("FinPlacement")){
			System.out.println("incrementation de la variable pret");
			flux.incrementPret();
		}
		else if(message.equalsIgnoreCase("PartieGagnee")) {
			flux.finirPartie();
		}
		else 
			System.out.println("Client : " + message);
	}
	
	@Override
	public void run(){
		System.out.println("Thread lancé");
		boolean running = true;
		String message = "";
		while(running) {
			try {
				message = brIn.readLine();
				Thread.sleep(10);
				System.out.println("Msg reader : " + message);
				testMessage(message);
			} catch (IOException e) {e.printStackTrace();}
			catch(InterruptedException e) {running = false;}
		}
		System.out.println("Msg reader : " + message);
		System.out.println("Thread fini");
	}
	public void interruptThread() {
		t.interrupt();
	}
	public void startThread() {
		t = new Thread(this);
		t.start();
	}
}
