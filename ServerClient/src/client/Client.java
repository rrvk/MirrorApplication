package client;

import gui.MainGui;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client implements Runnable{
	String ip;
	Integer poort;
	public void setIpAndPoort(String ip, Integer poort){
		this.ip=ip;
		this.poort=poort;
	}
	
	/**
	 * Kijkt of de server online is. 
	 * en of die het goede antwoord terug stuurt
	 * @return
	 */
	public boolean ping(String ip, Integer poort){
		// 10 keer pingen om te kijken of de server ook bestaat
		for (int i = 0; i < 10; i++) {
			try{
				// nieuwe verbinding maken
				Socket client = new Socket(ip,poort);
				// gedeelte voor schrijven naar server
				DataOutputStream os = new DataOutputStream(client.getOutputStream());
				os.writeBytes("Hallo iemand hier?\n");
				os.flush();
				// inlezen van het respons
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				String line = in.readLine();
				os.close();
				if (line!=null){
					// dit moet het respons zijn. 
					if (line.equals("Yep iemand is hier.")){
						client.close();
						in.close();
						return true;
					}
					else{
						client.close();
						return false;
					}
				}
				in.close();
				client.close();
			}
			catch(Exception ex){}
		}
		return false;
	}
	@Override
	public void run() {
		try {
			Socket client = new Socket(ip,poort);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String line = in.readLine();
			while(line!=null){
				if (line.equals("Move Coordinaten START")){
					Integer x = Integer.parseInt(in.readLine());
					Integer y = Integer.parseInt(in.readLine());
					MainGui.changeLocationFrame(x,y);
				}
				line = in.readLine();
			}
			client.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}
}
