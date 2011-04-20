package listener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.http.*;
import javax.servlet.*;

import web.User;
import web.UserSession;

import database.Connector;
import exception.DaoException;

public class Listener extends Thread implements ServletContextListener,
		HttpSessionListener {

	int port = 9876;

	public void run() {
		try {
			ServerSocket ss = new ServerSocket(port);
			while (true) {
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
		UserSession userSession = UserSession.Factory.getUserSession(arg0.getSession());
		PreparedStatement pstmt = null;
            try {
				Connection conn2 = Connector.getInstance().getConnection();
				pstmt = conn2
				        .prepareStatement(User.Factory.STMT_INSERT_USER_AUDIT);
				// UserSession userSession =
				// UserSession.Factory.getUserSession(request);
				pstmt.setString(1, userSession.getUser().getUsername());
				pstmt.setString(2, (String)arg0.getSession().getAttribute("RemoteAddress"));
				pstmt.setString(3, "Session timeout");
				pstmt.executeUpdate();
				pstmt.close();       

				userSession.logout();
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if(pstmt!=null)
						pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void contextInitialized(ServletContextEvent arg0) {
		Listener listener = new Listener();
		listener.start();

	}
}
