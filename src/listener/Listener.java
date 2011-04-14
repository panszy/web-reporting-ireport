package listener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.http.*;
import javax.servlet.*;


import database.Connector;

public class Listener extends Thread implements ServletContextListener, HttpSessionListener
{
	
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

	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		Listener listener = new Listener();
		listener.start();
		
	}
}
