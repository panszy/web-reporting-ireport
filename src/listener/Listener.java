package listener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import database.Connector;

public class Listener extends Thread {
	
	int port = 9876;
	
	static {
		try {
			new Connector("visitek-117", "poyomuna190582", "localhost", 50000, 1, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		try {
			ServerSocket ss = new ServerSocket(port);
			while(true){
				Socket s = ss.accept();
				Thread t = new Thread(new Handler(s));
				t.start();	
			}
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
}
