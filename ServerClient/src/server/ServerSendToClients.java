package server;

import gui.MainGui;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;

public class ServerSendToClients{
	private String commando="";
	
	public void setCommando(String commando){
		this.commando=commando;
	}
	
	/**
	 * this is for sending a string to the client
	 * comatile with Mirror_String
	 * @param str
	 */
	@SuppressWarnings("unchecked")
	public void send(String str){
		switch (commando) {
			case "Mirror_String":
				JSONObject objMirrorString = new JSONObject();
				objMirrorString.put("name", "mirrorTekst");
				objMirrorString.put("tekst", str);
				
				try {
					sendToAll(objMirrorString);
				} catch (IOException e) {
					MainGui.getTxtAreaLog().append("Error in het zenden van mirrortekst \n");
				}
				break;
			case "ScreenToClient":
				JSONObject objScreenToClient = new JSONObject();
				objScreenToClient.put("name", "ScreenToClient");
				// TODO dingen te doen met die string welke client etc
				
				try {
					sendToAll(objScreenToClient);
				} catch (IOException e) {
					MainGui.getTxtAreaLog().append("Error in het zenden van screen \n");
				}
				break;
				
				
		}
	}
	
	/**
	 * This is for sending 1 integer to the client
	 * compatible with State,Mode
	 * @param i
	 */
	@SuppressWarnings("unchecked")
	public void send(int i){
		switch (commando) {
			case "State":
				JSONObject objState = new JSONObject();
				objState.put("name", "State");
				objState.put("state", i);
				
				try {
					sendToAll(objState);
				} catch (IOException e) {
					MainGui.getTxtAreaLog().append("Error in het veranderen van de groote\n");
				}
				break;
			case "Mode":
				JSONObject objMode = new JSONObject();
				objMode.put("name", "Mode");
				objMode.put("mode", i);
				
				try {
					sendToAll(objMode);
				} catch (IOException e) {
					MainGui.getTxtAreaLog().append("Error in het veranderen van de mode\n");
				}
				break;
		}
	}
	
	/**
	 * this is to send 2 integers to the client
	 * compatible with Move and Size
	 * @param x/h
	 * @param y/w
	 */
	@SuppressWarnings("unchecked")
	public void send(int x, int y){
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
				objSize.put("h", x);
				objSize.put("w", y);
				
				try {
					sendToAll(objSize);
				} catch (IOException e) {
					MainGui.getTxtAreaLog().append("Error in het veranderen van de groote\n");
				}
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
}
