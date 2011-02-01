package listener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener extends Thread {
	
	int port = 9876;
	
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
