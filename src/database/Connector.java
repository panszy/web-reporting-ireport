package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class Connector {
	private ArrayList<Connection> connection = new ArrayList<Connection>();
	private static Connector conn;
	private int maxConnection;

	public Connector(String user, String password, String url, int port,
			int initalConnection, int maxConnection) throws Exception {
		this.maxConnection = maxConnection;
		if (conn == null)
			conn = new Connector(user, password, url, port, initalConnection,
					maxConnection);
		for (int i = 0; i < initalConnection; i++) {
			putConnection(createNewConnection());
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
			Class.forName("com.ibm.db2.jcc.DB2DataSource");
			Connection db2Conn = DriverManager.getConnection("jdbc:db2:schema", "username", "password"); 
			// TODO: provide the real schema, username, and password
			return db2Conn;
		}
	}
}
