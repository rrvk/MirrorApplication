package server;

import gui.MainGui;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;

public class ServerSendToClients{
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

	@SuppressWarnings("unchecked")
	public void send() {
		switch (commando) {
		case "Move":
			for(Iterator<Map.Entry<Integer, ClientInfo>> it = Server.clients.entrySet().iterator(); it.hasNext(); ) {
				Map.Entry<Integer, ClientInfo> entry = it.next();
				//PrintWriter pout;
				try {
					// dit is voor het versturen van het JSONObject
					ObjectOutputStream oos = entry.getValue().getOOS();
					
					// JSONObject
					JSONObject objCoordinaten = new JSONObject();
					objCoordinaten.put("name", "Coordinaten");
					objCoordinaten.put("x", x);
					objCoordinaten.put("y", y);
					
					// verzenden
					oos.reset();
					oos.writeObject(objCoordinaten);
			        oos.flush();
					
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
