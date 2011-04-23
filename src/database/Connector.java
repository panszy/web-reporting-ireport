package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import exception.DaoException;

public class Connector {	
	private static Connector connector;
	private Connection connSample;
	private Connection connAdmin;

	public static Connector getInstance() throws NamingException, SQLException {
		if (connector == null) {
			connector = new Connector();
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)
			  envCtx.lookup("jdbc/SAMPLE");
			connector.connSample = ds.getConnection();
			
			ds = (DataSource)
			  envCtx.lookup("jdbc/DBSIMPD");
			connector.connAdmin = ds.getConnection();
		}
		return connector;
	}

	public Connection getConnection() throws DaoException,NamingException, SQLException {
		if(connSample.isClosed()){
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)
			  envCtx.lookup("jdbc/SAMPLE");

			connector.connSample = ds.getConnection();
		}
		return connSample;
	}
	
	public Connection getConnectionAdmin() throws DaoException,NamingException, SQLException {
		if(connAdmin.isClosed()){
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)
			  envCtx.lookup("jdbc/DBSIMPD");

			connector.connAdmin = ds.getConnection();
		}
		return connAdmin;
	}
}
