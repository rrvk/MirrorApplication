package server;

import gui.MainGui;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Server implements Runnable{
	private Integer poort;
	private ServerSendToClients send = new ServerSendToClients();
	
	public static Map<Integer, ClientInfo> clients= new HashMap<Integer, ClientInfo>();
	public static Integer count=0;
	
	public static void removeItemFromClients(Integer key){
		for(Iterator<Map.Entry<Integer, ClientInfo>> it = clients.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry<Integer, ClientInfo> entry = it.next();
			if(entry.getKey()==key) {
				it.remove();
			}
    	}
	}
	
	public Server(Integer poort){
		this.poort= poort;
	}

	@Override
	public void run() {
		try {
			//New server opzetten
			@SuppressWarnings("resource")
			ServerSocket server= new ServerSocket(poort);
			// controleren of check is true
			while (true){
				//accepteren van client
				Socket client = server.accept();
				MainGui.getTxtAreaLog().append("Nieuwe Cliënt("+count+")\n");
				ClientInfo clientinfo = new ClientInfo(client);
				clients.put(count, clientinfo);
				HandelClient hC= new HandelClient(clientinfo,count);
				count++;
				//niewe thread starten voor afhandelen client
				Thread t = new Thread(hC);
				t.start();
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void sendCordinates(int x, int y){
		send.setCommando("Move");
		send.send(x,y);
	}
	
	public void sendSize(int h, int w){
		send.setCommando("Size");
		send.send(h,w);
	}

	public void sendState(int newState) {
		send.setCommando("State");
		send.send(newState);
		
	}

	public void closeClients() throws IOException {
		for(Iterator<Map.Entry<Integer, ClientInfo>> it = clients.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry<Integer, ClientInfo> entry = it.next();
			entry.getValue().getClient().close();
			it.remove();
    	}
		
	}

	public void sendMirrorField(String mirrorTekst) {
		send.setCommando("Mirror_String");
		send.send(mirrorTekst);		
	}

	public void sendScreenToClient(MainGui gui) {
		// TODO spul naar verschillende cielt sturen met pijltjes
		sendCordinates(gui.getFrame().getX(), gui.getFrame().getY());
		sendSize(gui.getFrame().getHeight(), gui.getFrame().getWidth());
		sendState(gui.getFrame().getState());
		sendMirrorField(gui.getTxtMirrorField().getText());
		send.setCommando("ScreenToClient");
		send.send("ClientX");
	}

	public void sendMode(Integer mode) {
		send.setCommando("Mode");
		send.send(mode);
		
	}
}
