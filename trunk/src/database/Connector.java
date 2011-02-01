package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Properties;

public class Connector {
	private ArrayList<Connection> connection = new ArrayList<Connection>();
	private static Connector conn;
	private int maxConnection;
	private String username;
	private String password;
	private String host;
	private int port;

	public Connector(String username, String password, String host, int port,
			int initalConnection, int maxConnection) throws Exception {
		this.maxConnection = maxConnection;
		this.username = username;
		this.password = password;
		this.host = host;
		this.port = port;
		if (conn == null) {
			conn = new Connector(username, password, host, port,
					initalConnection, maxConnection);
			for (int i = 0; i < initalConnection; i++) {
				putConnection(createNewConnection());
			}
		}
	}

	public static Connector getInstance() {
		return conn;
	}

	public synchronized Connection getConnection() throws Exception {
		if (connection.size() == 0)
			putConnection(createNewConnection());
		return connection.get(connection.size() - 1);
	}

	public synchronized void putConnection(Connection conn) throws Exception {
		connection.add(conn);
	}

	private Connection createNewConnection() throws Exception {
		if (maxConnection == connection.size())
			throw new Exception("Exceeding maximum connection");
		else {
			String databaseURL = "jdbc:derby:net://"+this.host+":"+Integer.toString(this.port)+"/sample";
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			Properties properties = new Properties();
			properties.put("user", this.username);
			properties.put("password", this.password);
			properties.put("retreiveMessagesFromServerOnGetMessage", "true");
			Connection conn = DriverManager.getConnection(databaseURL, properties);
			return conn;
		}
	}
}
