package web;

import java.io.Serializable;
import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import exception.AccountDeactivatedException;
import exception.AccountLockedException;
import exception.DaoException;
import exception.LoginException;
import exception.UserNotFoundException;
import common.EncryptUtil;
import database.Connector;

public class UserSession implements Serializable {
    public static final long serialVersionUID = 1L;
    static Logger logger = Logger.getLogger(UserSession.class);
    
    public static class Factory {
        public static UserSession getUserSession(HttpServletRequest request) {
            // get or create a user session
            return getUserSession(request.getSession());
        }
        
        public static UserSession getUserSession(HttpSession session) {
            // get or create a user session
            UserSession userSession = (UserSession) session.getAttribute(HttpConstants.ATTR_NAME_USER_SESSION);
            
            if(userSession == null) {
                // create new user session, default not logged in
                userSession = new UserSession();
                
                session.setAttribute(HttpConstants.ATTR_NAME_USER_SESSION, userSession);
            }
            
            userSession.session = session;
            return userSession;
        }
    }
    
    transient HttpSession session;
    
    User user;    

    public UserSession() {
        user = null;
    }
    
    public User getUser() {
        return user;
    }    

    public boolean isLoggedIn() {
        return (user == null ? false : true);
    }
    
    public void login(String username, String password) throws LoginException, UserNotFoundException, DaoException {
        logger.debug("Logging in with username '" + username + "'");
        
        try {
            Connection conn = Connector.getInstance().getConnection();

            User targetUser = User.Factory.loadByUsername(conn, username);             

            switch(targetUser.getStatus()) {
            case User.STATUS_LOCKED:
                throw new AccountLockedException();
            case User.STATUS_DEACTIVE:
                throw new AccountDeactivatedException();
            }
            
            // match the password
            if(
                EncryptUtil.getMd5Hash(password).compareTo(targetUser.getPassword())
//               password.compareTo(targetUser.getPassword())
               == 0) {
                // auth success, check failed logins
                AllUserSessions.Factory.get(session.getServletContext()).login(username);
                
                targetUser.setFailedLogins(0);
                User.Factory.newsave(conn, targetUser);
                
                user = targetUser;                                
                return;
            }
            else {
                // wrong password, increment failures
                targetUser.setFailedLogins(targetUser.getFailedLogins() + 1);

                // if failed logins >= 3, lock the account
                if(targetUser.getFailedLogins() >= 3) {
                    // lock the account and show account locked page
                    targetUser.setStatus(User.STATUS_LOCKED);
                    User.Factory.newsave(conn, targetUser);
                    
                    throw new AccountLockedException();
                }
                else {
                    User.Factory.newsave(conn, targetUser);
                }

                throw new LoginException();
            }
        }
        catch(Exception ex) {
            logger.error(ex);
        }                
    }
    
    public void logout() {
        AllUserSessions.Factory.get(session.getServletContext()).logout(user.getUsername());
        user = null;
    }
}
