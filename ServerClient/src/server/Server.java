package server;

import gui.MainGui;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Server implements Runnable{
	private Integer poort;
	
	public static Map<Integer, Socket> clients= new HashMap<Integer, Socket>();
	public static Integer count=0;
	
	public static void removeItemFromClients(Integer key){
		for(Iterator<Map.Entry<Integer, Socket>> it = clients.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry<Integer, Socket> entry = it.next();
			if(entry.getKey()==key) {
				it.remove();
			}
    	}
	}
	
	public Server(Integer poort){
		this.poort= poort;
	}
	
	public void sendCordinates(int x, int y){
		ServerSendToClients send = new ServerSendToClients("Move");
		send.setXandY(x, y);
		Thread t = new Thread(send);
		t.start();
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
				clients.put(count, client);
				HandelClient hC= new HandelClient(client,count);
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
}
