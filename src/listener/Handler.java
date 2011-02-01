package listener;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import database.QueryExecutor;

public class Handler implements Runnable {
	
	private Socket s;
	private String data = "";
	private String result = "";
	
	public Handler (Socket s){
		this.s = s;
	}

	public void run() {
		DataInputStream dis = null;
		DataOutputStream dos = null;
		try {			
			dis = new DataInputStream(s.getInputStream());
			byte[] buffer = new byte[1024];  
			int length = dis.read(buffer);
			while(length == 1024){					
				for(byte b : buffer)
					data += (char)b;
			}
			// format input: idquery,paramter...<delimiter:ascii of 33>			
			int indexOfComma = data.indexOf(",");
			String idQuery = data.substring(0,indexOfComma);
			StringTokenizer st = new StringTokenizer(data.substring(indexOfComma),""+(char)33);
			Object[] o = new Object[st.countTokens()];
			for(int i = 0; i < o.length ; i++)
				o[i] = st.nextToken();
			QueryExecutor query = new QueryExecutor(Integer.parseInt(idQuery));
			ArrayList<String[]> tempResult = query.executeQuery(o);
			// format output: columnn...<delimiter:ascii of 33>, row<delimiter:ascii of 44>
			for(int i = 0; i < tempResult.size() ; i++)
				for (int j = 0; j < tempResult.get(i).length ; j++){
					if (j < tempResult.get(i).length - 1)
						result+=tempResult.get(i)[j]+(char)33;
					else
						result+=tempResult.get(i)[j]+(char)44;
				}				
			dos = new DataOutputStream(s.getOutputStream());			
			dos.write(result.getBytes());
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			try {
				if(dis != null)
					dis.close();
				if(dos != null)
					dos.close();
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
