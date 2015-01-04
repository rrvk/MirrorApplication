package server;

import gui.MainGui;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;

public class ServerSendToClients implements Runnable{
	private String commando="";
	// deze worden gebruikt voor de beweging van het scherm
	private int x;
	private int y;
	
	public ServerSendToClients(String commando){
		this.commando=commando;
	}
	
	public void setXandY(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	@Override
	public void run() {
		switch (commando) {
		case "Move":
			for(Iterator<Map.Entry<Integer, Socket>> it = Server.clients.entrySet().iterator(); it.hasNext(); ) {
				Map.Entry<Integer, Socket> entry = it.next();
				PrintWriter pout;
				try {
					pout = new PrintWriter(entry.getValue().getOutputStream(), true);
					pout.println("Move Coordinaten START");
					pout.println(x);
					pout.println(y);
				} catch (IOException e) {
					MainGui.getTxtAreaLog().append("Error in de coordniaten zenden");
				}
	    	}
			break;

		default:
			break;
		}
		
	}

}
