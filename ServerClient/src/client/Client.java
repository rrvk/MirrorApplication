package client;

import gui.MainGui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.json.simple.JSONObject;

public class Client implements Runnable{
	String ip;
	Integer poort;
	Socket client;
	// als deze true is dan word deze client gesloten
	private boolean exit=false;
	int x;
	int y;
	
	public Client(){
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		x = gd.getDisplayMode().getWidth();
		y = gd.getDisplayMode().getHeight();
	}
	
	public void setIpAndPoort(String ip, Integer poort){
		this.ip=ip;
		this.poort=poort;
	}
	
	public void exitClient() throws IOException{
		exit = true;
	}
	
	/**
	 * Kijkt of de server online is. 
	 * en of die het goede antwoord terug stuurt
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean ping(String ip, Integer poort){
		// 10 keer pingen om te kijken of de server ook bestaat
		for (int i = 0; i < 10; i++) {
			try{
				Socket client = new Socket(ip,poort);
				
				// dit is voor het versturen van de ping
				OutputStream os = client.getOutputStream();  
				ObjectOutputStream oos = new ObjectOutputStream(os);
				
				// dit is het JSONObject dat verzonden moet worden
				JSONObject objVerzend = new JSONObject();
		        objVerzend.put("name", "ping");
		        objVerzend.put("testString", "Hallo iemand hier?");
		        oos.writeObject(objVerzend);
		        oos.flush();
		        			
				// nu moeten we wachten totdat er antwoord komt
				InputStream is = client.getInputStream();  
				ObjectInputStream ois = new ObjectInputStream(is);  
				JSONObject objOntvang = (JSONObject)ois.readObject();  
				// TODO time out van 10 seconden
				if (objOntvang!=null){
					if (objOntvang.get("name").equals("ping")){
		        		if (objOntvang.get("testString").equals("Yep iemand is hier.")){
		        			// mooi zo goede antwoord client sluiten en een goedkeuring terug sturen
		        			client.close();
							ois.close();
							is.close();
							return true;
		        		}
		        		else{
		        			// hmm wel wat terug gekregen maar niet het goede
		        			client.close();
							ois.close();
							is.close();
							return false;
		        		}
					}
				}
				client.close();
				ois.close();
				is.close();
			}
			catch(Exception ex){}
		}
		return false;
	}
	
	@Override
	public void run() {
		try {
			client = new Socket(ip,poort);
			// dit om te kijken wat er allemaal wordt ontvangen van de server, dus wat we moeten aanpassen
			InputStream is = client.getInputStream();  
			ObjectInputStream ois = new ObjectInputStream(is);

			JSONObject objOntvang = null;
			while ((objOntvang = (JSONObject)ois.readObject()) != null && exit==false) {
				if (objOntvang!=null){
					switch (objOntvang.get("name").toString()) {
					case "Coordinaten":
						if (objOntvang.containsKey("x") && objOntvang.containsKey("y")){
							Integer x = Integer.parseInt(objOntvang.get("x").toString());
							Integer y = Integer.parseInt(objOntvang.get("y").toString());
							if ((x>=0 && x<=this.x) && (y>=0 && y<=this.y)){
								MainGui.changeLocationFrame(x,y);
							}
							else if(x<0 && y<0){
								MainGui.changeLocationFrame(0,0);
							}
						}
						break;
					case "Size":
						if (objOntvang.containsKey("h") && objOntvang.containsKey("w")){
							Integer h = Integer.parseInt(objOntvang.get("h").toString());
							Integer w = Integer.parseInt(objOntvang.get("w").toString());
							if ((h>0 && h<this.y) && (w>0 && w<this.x)){
								MainGui.changeFrameSize(h, w);
							}
							else if(h>this.y || w>this.x){
								MainGui.changeFrameSize(this.y, this.x);
							}
						}
						break;
					case "State":
						if (objOntvang.containsKey("state")){
							Integer state = Integer.parseInt(objOntvang.get("state").toString());
								MainGui.changeFrameState(state);
						}
						break;
					default:
						break;
					}
				}
			}
			client.close();
		} catch (EOFException e2){
			MainGui.getTxtAreaLog().append("De server is gesloten\n");
		} catch (IOException e1) {
			MainGui.getTxtAreaLog().append("ERROR Server werkt niet meer mee, verbinding gesloten\n");
		} catch (ClassNotFoundException e) {
			MainGui.getTxtAreaLog().append("ERROR iets raars binnengekregen wat we niet begrijpen, verbinding gesloten\n");
		} 
	}
}
