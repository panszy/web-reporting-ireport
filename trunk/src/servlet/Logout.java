package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import database.Connector;
import web.User;
import web.UserSession;

public class Logout extends HttpServlet {
    public static final long serialVersionUID = 1L;
    static Logger logger = Logger.getLogger(Logout.class);

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        UserSession userSession = UserSession.Factory.getUserSession(request);
        // userSession.logout();
        try {
            Connection conn2 = Connector.getInstance().getConnection();
            PreparedStatement pstmt = conn2
                    .prepareStatement(User.Factory.STMT_INSERT_USER_AUDIT);
            // UserSession userSession =
            // UserSession.Factory.getUserSession(request);
            pstmt.setString(1, userSession.getUser().getUsername());
            pstmt.setString(2, request.getRemoteAddr());
            pstmt.setString(3, userSession.getUser().getUsername() + " Logout");
            pstmt.executeUpdate();
            pstmt.close();       
            Connector.putConnection(conn2);
            userSession.logout();
            request.getRequestDispatcher("/logout.jsp").forward(request, response);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (NamingException ne) {
            ne.printStackTrace();
        }
        catch(NullPointerException e)
        {
            request.getRequestDispatcher("/logout.jsp").forward(request, response);
        }
       // request.getRequestDispatcher("/logout.jsp").forward(request, response);
 catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        UserSession userSession = UserSession.Factory.getUserSession(request);
        userSession.logout();
        try {
            Connection conn2 = Connector.getInstance().getConnection();
            PreparedStatement pstmt = conn2
                    .prepareStatement(User.Factory.STMT_INSERT_USER_AUDIT);
            // UserSession userSession =
            // UserSession.Factory.getUserSession(request);
            pstmt.setString(1, userSession.getUser().getUsername());
            pstmt.setString(2, request.getRemoteAddr());
            pstmt.setString(3, userSession.getUser().getUsername() + " Logout");
            pstmt.executeUpdate();
            pstmt.close();    
            Connector.putConnection(conn2);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (NamingException ne) {
            ne.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}
        request.getRequestDispatcher("/logout.jsp").forward(request, response);
    }
}
