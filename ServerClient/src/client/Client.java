package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
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
				if (line!=null)
					// dit moet het respons zijn. 
					if (line.equals("Yep iemand is hier.")){
						client.close();
						return true;
					}
					else{
						client.close();
						return false;
					}
				in.close();
				client.close();
			}
			catch(Exception ex){}
		}
		return false;
	}
}
