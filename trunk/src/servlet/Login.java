package servlet;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.naming.NamingException;
import database.Connector;
import web.UserSession;
import web.User;

public class Login extends HttpServlet {
    public static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        UserSession userSession = UserSession.Factory.getUserSession(request);

        if (!userSession.isLoggedIn()) {

            try {
                Connection conn2 = Connector.getInstance().getConnection();
                PreparedStatement pstmt = conn2
                        .prepareStatement(User.Factory.STMT_INSERT_USER_AUDIT);
                // UserSession userSession =
                // UserSession.Factory.getUserSession(request);

                pstmt.setString(1, userSession.getUser().getUsername());
                pstmt.setString(2, request.getRemoteAddr());
                pstmt.setString(3, userSession.getUser().getUsername()
                        + " Login");
                pstmt.executeUpdate();
                pstmt.close();
                conn2.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            } catch (NamingException ne) {
                ne.printStackTrace();
            } catch (Exception e) {
				e.printStackTrace();
			}

            request.getRequestDispatcher("/login.jsp").forward(request,
                    response);
        } else {

            try {
                Connection conn2 = Connector.getInstance().getConnection();
                PreparedStatement pstmt = conn2
                        .prepareStatement(User.Factory.STMT_INSERT_USER_AUDIT);
                // UserSession userSession =
                // UserSession.Factory.getUserSession(request);
                pstmt.setString(1, userSession.getUser().getUsername());
                pstmt.setString(2, request.getRemoteAddr());
                pstmt.setString(3, userSession.getUser().getUsername()
                        + " Login");
                pstmt.executeUpdate();
                pstmt.close();
                conn2.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            } catch (NamingException ne) {
                ne.printStackTrace();
            } catch (Exception e) {
				e.printStackTrace();
			}
            request.getRequestDispatcher("/pages/index.jsp").forward(request,
                    response);
        }

    }

}
