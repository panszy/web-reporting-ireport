package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import exception.DaoException;
import exception.InvalidPasswordException;
import common.PasswordManager;
import database.Connector;
import web.HttpConstants;
import web.UserSession;

public class ChangePassword extends HttpServlet {
    public static final long serialVersionUID = 1L;
    
    static Logger logger = Logger.getLogger(ChangePassword.class);
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/pages/change-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oldPassword = req.getParameter(HttpConstants.HTTP_VAR_OLD_PASSWORD);
        String newPassword = req.getParameter(HttpConstants.HTTP_VAR_NEW_PASSWORD);
        String confirmNewPassword = req.getParameter(HttpConstants.HTTP_VAR_CONFIRM_NEW_PASSWORD);
        
        if((oldPassword == null) ||
                (newPassword == null) ||
                (confirmNewPassword == null)) {
        }
        else {
            try {
                PasswordManager.changePassword(Connector.getInstance().getConnection(), 
                        UserSession.Factory.getUserSession(req).getUser(), 
                        oldPassword, 
                        newPassword, 
                        confirmNewPassword);
                req.setAttribute(HttpConstants.ATTR_VAR_CHANGE_PASSWORD_SUCCESS, true);
            }
            catch(DaoException ex) {
                logger.error(ex);
            }
            catch(NamingException ex) {
                logger.error(ex);
            }
            catch(SQLException ex) {
                logger.error(ex);
            }
            catch(InvalidPasswordException ex) {
                req.setAttribute(HttpConstants.ATTR_VAR_CHANGE_PASSWORD_ERROR, ex.getMessage());
            } catch (Exception e) {
            	logger.error(e);
			}
        }
        
        req.getRequestDispatcher("/pages/change-password.jsp").forward(req, resp);
        
        return;
    }

}
