package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientInfo {
	private Socket client;
	private ObjectOutputStream oos;
	
	public ClientInfo(Socket client, ObjectOutputStream oos){
		this.client=client;
		this.oos=oos;
	}
	
	public ClientInfo(Socket client) throws IOException{
		this.client=client;
		this.oos=new ObjectOutputStream(client.getOutputStream());
	}
	
	public Socket getClient(){
		return client;
	}
	
	public ObjectOutputStream getOOS(){
		return oos;
	}
}
