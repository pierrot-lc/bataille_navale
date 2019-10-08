package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import bataille_navale.init.InitModel;

public class Server extends FluxPartie implements Runnable{
	private ServerSocket ss;
	private InitModel model;
	
	private boolean paramEnvoyes = false;
	
	public Server(InitModel model) {
		this.model = model;
		try {
			ss = new ServerSocket(model.getPort(), 1);
			System.out.println("Serveur créé sur le port " + model.getPort());			
		} catch (IOException e) {e.printStackTrace();}
		Thread t = new Thread(this);
		t.start();
	}

	
	public void sendParam() {
		System.out.println("Envois des parametres");

		pwOut.println("Message fake pour finir le thread MR du client");//Erreur message perdu dans les méandres d'Internet
		pwOut.flush();

		pwOut.println("nom");
		pwOut.flush();
		pwOut.println(model.getNomJ1());
		pwOut.flush();
		
		pwOut.println("carte");
		pwOut.flush();
		pwOut.println(model.getWidthCarte());
		pwOut.flush();
		pwOut.println(model.getHeightCarte());
		pwOut.flush();
		
		pwOut.println("bateaux");
		pwOut.flush();
		pwOut.println(model.getNbrBat().length);
		for(int i = 0; i < model.getNbrBat().length; i++)
			for(int j = 0; j < (model.getNbrBat())[i].length; j++) {
				pwOut.println((model.getNbrBat())[i][j]);
				pwOut.flush();
			}
		
		pwOut.println("FIN");
		pwOut.flush();
		
		try {
			model.setNomJ2(brIn.readLine());
		} catch (IOException e) {e.printStackTrace();}
		
		System.out.println("Parametres envoyes");
		paramEnvoyes = true;
	}
	
	@Override
	public void run() {
		try {
			Socket socket = ss.accept();
			System.out.println("Client connecté !");
			pwOut = new PrintWriter(socket.getOutputStream());
			brIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			MR = new MsgReader(brIn, this);
		} catch (IOException e) {e.printStackTrace();}
		
	}
	
	public boolean isParamEnvoyes() {
		return paramEnvoyes;
	}
	
}
