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
	
	public void sendCordinates(int x, int y){
		send.setXandY(x, y);
		send.setCommando("Move");
		send.send();
	}
	
	public void sendSize(int h, int w){
		send.setSize(h, w);
		send.setCommando("Size");
		send.send();
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

	public void sendState(int newState) {
		send.setState(newState);
		send.setCommando("State");
		send.send();
		
	}

	public void closeClients() throws IOException {
		for(Iterator<Map.Entry<Integer, ClientInfo>> it = clients.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry<Integer, ClientInfo> entry = it.next();
			entry.getValue().getClient().close();
			it.remove();
    	}
		
	}
}
