package server;

import gui.MainGui;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.json.simple.JSONObject;

public class HandelClient implements Runnable {
	ClientInfo clientInfo;
	Integer clientNumber;
	public HandelClient(ClientInfo clientInfo, Integer clientNumber){
		this.clientInfo=clientInfo;
		this.clientNumber=clientNumber;
	}
	@Override
	public void run() {
		try {
			// eerst moeten we wachten totdat we iets krijgen
			InputStream is = clientInfo.getClient().getInputStream();  
			ObjectInputStream ois = new ObjectInputStream(is);  
			
			JSONObject objOntvangt = (JSONObject)ois.readObject();  
			if (objOntvangt!=null){
				if (objOntvangt.get("name").equals("ping")){
					if (objOntvangt.get("testString").equals("Hallo iemand hier?")){
						sendPing("Yep iemand is hier.");
						MainGui.getTxtAreaLog().append("Cliënt("+clientNumber+") heeft goed gepingt en is gesloten\n");
					}
					else{
						// hmm dit begrijpen we niet 
						sendPing("Ik begrijp je niet");
						MainGui.getTxtAreaLog().append("Cliënt("+clientNumber+") heeft verkeerd gepingt en is gesloten\n");
					}
				}		
				else{
					MainGui.getTxtAreaLog().append("Cliënt("+clientNumber+") geeft iets raars terug dus sluiten\n");
				}
			}
			ois.close();
			is.close();
			clientInfo.getClient().close();
			Server.removeItemFromClients(clientNumber);
		}
		catch( IOException e){
			if (e.getMessage().contains("Connection reset")){
				MainGui.getTxtAreaLog().append("Cliënt("+clientNumber+") is gesloten \n");
				Server.removeItemFromClients(clientNumber);
			}
			else{
				System.out.println(e.getMessage());
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void sendPing(String testString) throws IOException{
		ObjectOutputStream oos = clientInfo.getOOS();
		JSONObject objVerzend = new JSONObject();
		objVerzend.put("name", "ping");
		objVerzend.put("testString", testString);
		oos.reset();
		oos.writeObject(objVerzend);
		oos.flush();
		// het is verzonden nu mag het worden gesloten
		oos.close();
	}
}
