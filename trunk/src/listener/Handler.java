package listener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import database.QueryExecutor;

public class Handler implements Runnable {
	
	private Socket s;	
	
	public Handler (Socket s){
		this.s = s;
	}

	public void run() {
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		try {
			// format input: ArrayList<Object> : first Object is queryId=int, second is parameters-String[]
			ois = new ObjectInputStream(s.getInputStream());
			@SuppressWarnings("unchecked")
			ArrayList<Object> res = (ArrayList<Object>) ois.readObject();
			QueryExecutor query = new QueryExecutor((Integer)(res.get(0)));
			ArrayList<String[]> tempResult = query.executeQuery((String[])res.get(1));
			// format output: ArrayList<String[]>			
			oos = new ObjectOutputStream(s.getOutputStream());			
			oos.writeObject(tempResult);
			oos.flush();
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			try {
				if(ois != null)
					ois.close();
				if(oos != null)
					oos.close();
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
