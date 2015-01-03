package server;

import gui.MainGui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HandelClient implements Runnable {
	Socket client;
	Integer clientNumber;
	public HandelClient(Socket client, Integer clientNumber){
		this.client=client;
		this.clientNumber=clientNumber;
	}
	@Override
	public void run() {
		try {
			PrintWriter pout = new PrintWriter(client.getOutputStream(), true);;
			BufferedReader is = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String line = is.readLine();
			String send = "Ik begrijp je niet";
			if (line.equals("Hallo iemand hier?")){
				send = "Yep iemand is hier.";
			}
			pout.println(send);
			client.close();
			is.close();
			pout.close();
			Server.removeItemFromClients(clientNumber);
			MainGui.getTxtAreaLog().append("Cliënt("+clientNumber+") gesloten\n");
		}
		catch( IOException e){
			System.out.println(e.getMessage());
		}
	}
}
