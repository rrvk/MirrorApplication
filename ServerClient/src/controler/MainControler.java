package controler;

import gui.MainGui;
import server.Server;
import client.Client;

public class MainControler {
	private MainGui gui;
	private Server ser=null;
	private Client clin=null;
	public final static int modeMiror=1;
	public final static int modeExtend=2;
	private int keyCommand=0;
	/* aan de mode kun je zien wat de applicatie doet.
	 * 0=niks
	 * 1=mirror
	 * 2=het doorgeven aan andere computer
	 */
	private int mode=0;
	
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
			// this is the client controler deze zorgt voor de client om zijn spul aftehandelen
			ClientControler con = new ClientControler();
			con.setGui(gui);
			cl.setControler(con);
			clin = cl;
			cl.setIpAndPoort(ip, poort);
			Thread t = new Thread(cl);
			t.start();
			cl.getModeFromServer();
			return "Server Online";
			// Jeej de server is online ga wat spul doen.
		}
		else{
			// mode standaart op mirrot
			setMode(modeMiror);
			System.out.println("mew server niet online eigen maken");
			// he bah de server is ofline. 
			// Dan maar een eigen server aanmaken
			ser = new Server(poort);
			ser.setMode(getMode());
			Thread t = new Thread(ser);
			t.start();
			return "Eigen Server opzetten";
		}
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public void setKeyCommand(int i) {
		keyCommand = i;		
	}

	public int getKeyCommand() {
		return keyCommand;
	}
}
