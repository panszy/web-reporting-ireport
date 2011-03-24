package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Properties;

public class Connector {
	private static ArrayList<Connection> connection = new ArrayList<Connection>();
	private static Connector conn;
	private static int maxConnection;
	private static int initialConnection;
	private int currentUsedConnection;
	private static String username;
	private static String password;
	private static String host;
	private static int port;

	public Connector(String username, String password, String host, int port,
			int initialConnection, int maxConnection) throws Exception {
		Connector.maxConnection = maxConnection;
		Connector.username = username;
		Connector.password = password;
		Connector.host = host;
		Connector.port = port;
		Connector.initialConnection = initialConnection;		
	}

	public static Connector getInstance() throws Exception {
		if (conn == null) {
			conn = new Connector(username, password, host, port,
					initialConnection, maxConnection);
			for (int i = 0; i < initialConnection; i++) {
				putConnection(createNewConnection());
			}
		}
		return conn;
	}

	public synchronized Connection getConnection() throws Exception {
		if (connection.size() == 0)
			putConnection(createNewConnection());
		return connection.remove(connection.size() - 1);
	}

	public static synchronized void putConnection(Connection conn) throws Exception {
		connection.add(conn);
	}

	private static Connection createNewConnection() throws Exception {
		if (maxConnection == connection.size())
			throw new Exception("Exceeding maximum connection");
		else {
			String databaseURL = "jdbc:db2://"+host+":"+Integer.toString(port)+"/sample";
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			Properties properties = new Properties();
			properties.put("user", username);
			properties.put("password", password);
			properties.put("retreiveMessagesFromServerOnGetMessage", "true");
			Connection conn = DriverManager.getConnection(databaseURL, properties);
			return conn;
		}
	}
}
