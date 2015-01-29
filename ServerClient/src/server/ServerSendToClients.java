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
	private int h;
	private int w;
	private int state;
	private String mirrorText;
	
	public void setCommando(String commando){
		this.commando=commando;
	}
	
	public void setXandY(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	public void setSize(int h, int w) {
		this.h = h;
		this.w = w;		
	}

	@SuppressWarnings("unchecked")
	public void send() {
		switch (commando) {
		case "Move":
			JSONObject objCoordinaten = new JSONObject();
			objCoordinaten.put("name", "Coordinaten");
			objCoordinaten.put("x", x);
			objCoordinaten.put("y", y);
			
			try {
				sendToAll(objCoordinaten);
			} catch (IOException e) {
				MainGui.getTxtAreaLog().append("Error in de coordniaten zenden\n");
			}
			break;
		case "Size":
			JSONObject objSize = new JSONObject();
			objSize.put("name", "Size");
			objSize.put("h", h);
			objSize.put("w", w);
			
			try {
				sendToAll(objSize);
			} catch (IOException e) {
				MainGui.getTxtAreaLog().append("Error in het veranderen van de groote\n");
			}
			break;
		case "State":
			JSONObject objState = new JSONObject();
			objState.put("name", "State");
			objState.put("state", state);
			
			try {
				sendToAll(objState);
			} catch (IOException e) {
				MainGui.getTxtAreaLog().append("Error in het veranderen van de groote\n");
			}
			break;
		case "Mirror_String":
			JSONObject objMirrorString = new JSONObject();
			objMirrorString.put("name", "mirrorTekst");
			objMirrorString.put("tekst", mirrorText);
			
			try {
				sendToAll(objMirrorString);
			} catch (IOException e) {
				MainGui.getTxtAreaLog().append("Error in het zenden van mirrortekst \n");
			}
			break;
		default:
			break;
		}
	}
	
	private void sendToAll(JSONObject obj) throws IOException{
		for(Iterator<Map.Entry<Integer, ClientInfo>> it = Server.clients.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry<Integer, ClientInfo> entry = it.next();
			// dit is voor het versturen van het JSONObject
			ObjectOutputStream oos = entry.getValue().getOOS();
			
			// verzenden
			oos.reset();
			oos.writeObject(obj);
	        oos.flush();
    	}
	}

	public void setState(int newState) {
		state = newState;
	}

	public void setMirrorField(String mirrorTekst) {
		this.mirrorText =mirrorTekst;
	}
}
