package web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

public class AllUserSessions {
    public static class Factory {
        public static AllUserSessions get(ServletContext ctx) {
            AllUserSessions allUserSessions = (AllUserSessions) ctx.getAttribute(HttpConstants.ATTR_ALL_USER_SESSIONS);
            if(allUserSessions == null) {
                allUserSessions = new AllUserSessions();
                ctx.setAttribute(HttpConstants.ATTR_ALL_USER_SESSIONS, allUserSessions);
            }
            
            return allUserSessions;
        }
    }
    
    Map<String, Date> users = new HashMap<String, Date>();
    
    public boolean isUserLoggedIn(String username) {
        if(users.get(username) != null) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public void login(String username) throws AccountAlreadyLoggedInException {
        if(isUserLoggedIn(username)) throw new AccountAlreadyLoggedInException("Account with username '" + username + "' already logged in");
        else {
            users.put(username, new Date());
        }
    }
    
    public void logout(String username) {
        users.remove(username);
    }
}
