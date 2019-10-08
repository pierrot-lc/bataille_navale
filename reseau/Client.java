package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import bataille_navale.init.InitControleur;
import bataille_navale.init.InitModel;

public class Client extends FluxPartie {
	private Socket socket = null;
	private int port = 0;
	private String IP = "";
	
	public Client(String IP, int port) {
		this.port = port;
		this.IP = IP;
		
		try {
			this.socket = new Socket(this.IP, this.port);
			System.out.println("Connexion réussie");
			pwOut = new PrintWriter(socket.getOutputStream());
			brIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			MR = new MsgReader(brIn, this);
			
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public InitModel recupParam(String nom){
		MR.interruptThread();
		String message = "";
		int nbrBat[][];
		
		InitControleur control = new InitControleur();
		
		System.out.println("Demande des parametres de partie");
		pwOut.println("Parametres");
		pwOut.flush();
		
		System.out.println("En attente de la reception des parametres");
		try {
			System.out.println("Début de la reception des parametres");
			do {
				message = brIn.readLine();
				if(message.equalsIgnoreCase("nom")) {
					message = brIn.readLine();
					control.setNomJ1(message);
					System.out.println("Nom du joueur recu");
				}
				else if(message.equalsIgnoreCase("carte")){
					message = brIn.readLine();
					control.setWidthCarte(Integer.parseInt(message));
					message = brIn.readLine();
					control.setHeightCarte(Integer.parseInt(message));
					message = "";
					System.out.println("Taille de la carte recue");
				}
				else if(message.equalsIgnoreCase("bateaux")){
					message = brIn.readLine();
					nbrBat = new int[Integer.parseInt(message)][3];
					for(int i = 0; i < nbrBat.length; i++)
						for(int j = 0; j < nbrBat[i].length; j++)
							nbrBat[i][j] = Integer.parseInt(brIn.readLine());
					message = "";
					control.setNbrBat(nbrBat);
					System.out.println("NbrBat recus");
				}
			} while(!message.equalsIgnoreCase("FIN"));
			pwOut.println(nom);
			pwOut.flush();
			System.out.println("Fin de la reception des parametres - tuut tuut tuut");
		} catch (IOException e) {e.printStackTrace();}
		MR.startThread();
		return control.getModel();
	}
}
