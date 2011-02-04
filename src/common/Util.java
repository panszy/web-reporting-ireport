package common;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Util {
	
	@SuppressWarnings("unchecked")
	public static ArrayList<String[]> sendQuery(String host, int port, int queryId,String[] parameters) throws Exception {		
		Socket s = null;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
				
		ArrayList<Object> message = new ArrayList<Object>();
		message.add(queryId);
		message.add(parameters);
		long awal = System.currentTimeMillis();
		try {
			s = new Socket(host, port);

			oos = new ObjectOutputStream(s.getOutputStream());			
			oos.writeObject(message);
			oos.flush();

			ois = new ObjectInputStream(s.getInputStream());
			return (ArrayList<String[]>) ois.readObject();
		} finally {
			System.out.println(System.currentTimeMillis() - awal + " ms");
			if(ois != null)
				ois.close();
			if(oos != null)
				oos.close();
			if(s != null)
				s.close();
		}
	}

}
