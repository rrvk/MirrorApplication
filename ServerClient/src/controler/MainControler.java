package controler;

import gui.MainGui;
import server.Server;
import client.Client;

public class MainControler {
	MainGui gui;
	Server ser=null;
	Client clin=null;
	public void setGui(MainGui gui){
		this.gui=gui;
	}
	
	public Client getClient(){
		return clin;
	}
	
	public Server getServer(){
		return ser;
	}
	
	public String searchOrCreate(String ip, Integer poort){
		Client cl = new Client();
		if (cl.ping(ip,poort)){
			clin = cl;
			cl.setIpAndPoort(ip, poort);
			Thread t = new Thread(cl);
			t.start();
			return "Server Online";
			// Jeej de server is online ga wat spul doen.
		}
		else{
			System.out.println("mew server niet online eigen maken");
			// he bah de server is ofline. 
			// Dan maar een eigen server aanmaken
			ser = new Server(poort);
			Thread t = new Thread(ser);
			t.start();
			return "Eigen Server opzetten";
		}
	}
}
