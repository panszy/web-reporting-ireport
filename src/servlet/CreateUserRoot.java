package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import java.util.Set;
import java.util.HashSet;
import java.util.regex.*;
import exception.DaoException;
import exception.EmailException;
import common.EncryptUtil;
import database.Connector;
import exception.InvalidPasswordException;
import exception.RoleException;
import exception.UserNameException;
import exception.NIKException;
import web.HttpConstants;
import web.User;
import web.UserSession;

public class CreateUserRoot extends HttpServlet {
    public static final long serialVersionUID = 1L;

    static Logger logger = Logger.getLogger(CreateNewUser.class);

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
     request.getRequestDispatcher("/pages/superuser/user-details.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // Set User Object Values
        User user = new User();
        Connection conn;
        try {
            user.setUsername(request
                    .getParameter(HttpConstants.HTTP_VAR_USERNAME));
            user.setPassword(EncryptUtil.getMd5Hash(request
                    .getParameter(HttpConstants.HTTP_VAR_PASSWORD)));
         
            user.setFullName(request
                    .getParameter(HttpConstants.HTTP_VAR_FULLNAME));
            user.setNik(request.getParameter(HttpConstants.HTTP_VAR_NIK));
            user.setEmailAddress(request
                    .getParameter(HttpConstants.HTTP_VAR_EMAIL_ADDRESS));
            user.setDepartemen(request
                    .getParameter(HttpConstants.HTTP_VAR_DEPARTEMEN));
            user.setDivision(request
                    .getParameter(HttpConstants.HTTP_VAR_DIVISION));
            user.setAddress(request
                    .getParameter(HttpConstants.HTTP_VAR_ADDRESS));
        
        
            Set set = new HashSet();
            if (request.getParameter(HttpConstants.HTTP_VAR_ADMIN_ROLE) != null||request.getParameter("adminori")!=null)
                set.add(0);
            if (request.getParameter(HttpConstants.HTTP_VAR_CGW_ROLE) != null||request.getParameter("cgwori")!=null)
                set.add(1);
            if (request.getParameter(HttpConstants.HTTP_VAR_THRESHOLD_ROLE) != null||request.getParameter("thresholdori")!=null)
                set.add(2);
            if (request.getParameter(HttpConstants.HTTP_VAR_MONITORING_ROLE) != null||request.getParameter("monitoringori")!=null)
                set.add(3);
            user.setRoles(set);
            // Connection conn;
            User userLoad;
            // try{
            // validate password
            if(request.getParameter(HttpConstants.HTTP_VAR_USERNAME).equalsIgnoreCase("marwansyah"))
             throw new UserNameException();
            if (request.getParameter(HttpConstants.HTTP_VAR_USERNAME)== "")
                throw new UserNameException("You Must Fill Username Column!");
            if ((request.getParameter(HttpConstants.HTTP_VAR_PASSWORD))
                    .length() < 8)
                throw new InvalidPasswordException(
                        "Password length must be at least 8 characters");
            Pattern passPattern = Pattern
                    .compile("([0-9]+[a-zA-Z]+[a-zA-Z]*)|([a-zA-Z]+[0-9]+[a-zA-Z]*)");
            Matcher passmatcher = passPattern.matcher(request
                    .getParameter(HttpConstants.HTTP_VAR_PASSWORD));
            boolean passmatch = passmatcher.matches();
            if (!passmatch) {
                throw new InvalidPasswordException(
                        "Password must contains letter and number");
            }
            if (request.getParameter("password").compareTo(
                    request.getParameter("confPassword")) != 0) {
                throw new InvalidPasswordException(
                        "Password and Confirmed Password does not match");
            }
            // Pattern
            // pattern=Pattern.compile("/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i");
            user.setUsername(request
                    .getParameter(HttpConstants.HTTP_VAR_USERNAME));
            user.setPassword(EncryptUtil.getMd5Hash(request
                    .getParameter(HttpConstants.HTTP_VAR_PASSWORD)));
         
            if (request.getParameter(HttpConstants.HTTP_VAR_NIK) == "") {
                throw new NIKException("You must Fill NIK column");
            }

            Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
            Matcher matcher = pattern.matcher(request
                    .getParameter(HttpConstants.HTTP_VAR_EMAIL_ADDRESS));
            boolean match = matcher.matches();
            if (!match) {
                throw new EmailException("Invalid Email Format");
            }
            if (set.isEmpty()) {
                throw new RoleException("You must check at least one role");
            }
            user.setUsername(request
                    .getParameter(HttpConstants.HTTP_VAR_USERNAME));
            user.setPassword(EncryptUtil.getMd5Hash(request
                    .getParameter(HttpConstants.HTTP_VAR_PASSWORD)));
         /*
            user.setFullName(request
                    .getParameter(HttpConstants.HTTP_VAR_FULLNAME));
            user.setNik(request.getParameter(HttpConstants.HTTP_VAR_NIK));
            user.setEmailAddress(request
                    .getParameter(HttpConstants.HTTP_VAR_EMAIL_ADDRESS));
            user.setDepartemen(request
                    .getParameter(HttpConstants.HTTP_VAR_DEPARTEMEN));
            user.setDivision(request
                    .getParameter(HttpConstants.HTTP_VAR_DIVISION));
            user.setAddress(request
                    .getParameter(HttpConstants.HTTP_VAR_ADDRESS));
        
        
            //Set set = new HashSet();
            if (request.getParameter(HttpConstants.HTTP_VAR_ADMIN_ROLE) != null)
                set.add(0);
            if (request.getParameter(HttpConstants.HTTP_VAR_CGW_ROLE) != null)
                set.add(1);
            if (request.getParameter(HttpConstants.HTTP_VAR_THRESHOLD_ROLE) != null)
                set.add(2);
            if (request.getParameter(HttpConstants.HTTP_VAR_MONITORING_ROLE) != null)
                set.add(3);
            user.setRoles(set);
        */
                // make connection to database
            conn = (Connection) Connector.getInstance().getConnection();
            // load user from database by username
            userLoad = User.Factory.loadByUsername(conn, request
                    .getParameter(HttpConstants.HTTP_VAR_USERNAME));
            request.setAttribute("registeredUser", "User is registered");
            request.getRequestDispatcher("/pages/superuser/user-add-success.jsp")
                    .forward(request, response);

        } catch (UserNameException e) {
            e.printStackTrace();
            request.setAttribute("password", request.getParameter("password"));
            request.setAttribute("confpassword", request.getParameter("confPassword"));
        
            request.setAttribute("user", user);
        
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/pages/superuser/user-details.jsp")
                    .forward(request, response);

        } catch (InvalidPasswordException e) {
            request.setAttribute("error", e.getMessage());
            e.printStackTrace();
            request.setAttribute("password", request.getParameter("password"));
            request.setAttribute("confpassword", request.getParameter("confPassword"));
        
            request.setAttribute("user", user);
        
            request.getRequestDispatcher("/pages/superuser/user-details.jsp")
                    .forward(request, response);

        } catch (EmailException ex) {
            ex.printStackTrace();
            request.setAttribute("password", request.getParameter("password"));
            request.setAttribute("confpassword", request.getParameter("confPassword"));
        
            request.setAttribute("user", user);
        
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/pages/superuser/user-details.jsp")
                    .forward(request, response);

        } catch (NIKException e) {
            e.printStackTrace();
            request.setAttribute("password", request.getParameter("password"));
            request.setAttribute("confpassword", request.getParameter("confPassword"));
        
            request.setAttribute("user", user);
        
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/pages/superuser/user-details.jsp")
                    .forward(request, response);

        } catch (RoleException ex) {
            ex.printStackTrace();
            request.setAttribute("password", request.getParameter("password"));
            request.setAttribute("confpassword", request.getParameter("confPassword"));
        
            request.setAttribute("user", user);
        
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/pages/superuser/user-details.jsp")
                    .forward(request, response);

        }
        catch (Exception sqle) {
            try {
                conn = (Connection) Connector.getInstance().getConnection();
                User.Factory.newsave(conn, user);

                Connection conn2 = Connector.getInstance().getConnection();
                PreparedStatement pstmt = conn2
                        .prepareStatement(User.Factory.STMT_INSERT_USER_AUDIT);
                UserSession userSession = UserSession.Factory
                        .getUserSession(request);

                pstmt.setString(1, userSession.getUser().getUsername());
                pstmt.setString(2, request.getRemoteAddr());
                pstmt.setString(3, "Create " + user.getUsername() + " by "
                        + userSession.getUser().getUsername());
                pstmt.executeUpdate();

            } catch (DaoException e) {
            } 
            catch (Exception e) {
            }
            request.setAttribute("createdUser", "User Has Been Created");
            request.getRequestDispatcher("/pages/superuser/user-add-success.jsp")
                    .forward(request, response);

            sqle.printStackTrace();
        }

    }
}
