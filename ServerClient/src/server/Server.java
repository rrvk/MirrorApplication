package server;

import gui.MainGui;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Server implements Runnable{
	private Integer poort;
	
	public static Map<Integer, String> clients= new HashMap<Integer, String>();
	public static Integer count=0;
	
	public static void removeItemFromClients(Integer key){
		for(Iterator<Map.Entry<Integer, String>> it = clients.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry<Integer, String> entry = it.next();
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
				clients.put(count, client.getInetAddress().toString());
				System.out.println(count);
				System.out.println(client.getInetAddress().toString());
				System.out.println();
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
