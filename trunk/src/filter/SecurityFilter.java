package filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import exception.DaoException;
import exception.AccountAlreadyLoggedInException;
import exception.AccountDeactivatedException;
import exception.AccountLockedException;
import exception.LoginException;
import web.HttpConstants;
import web.User;
import exception.UserNotFoundException;
import web.UserSession;

public class SecurityFilter implements Filter {
    public static final String PATH_ROLE_ADMINISTATOR = "admin";

    public static final String PATH_ROLE_STUDENT = "student";

    public static final String PATH_ROLE_LECTURE = "lecture";

    public static final String PATH_ROLE_SUPERUSER = "superuser";

    static Logger logger = Logger.getLogger(SecurityFilter.class);

    public static final Map<String, Integer> pathRoles = new HashMap<String, Integer>();

    static {
        pathRoles.put(PATH_ROLE_ADMINISTATOR, User.ROLE_ADMINISTRATOR);
        pathRoles.put(PATH_ROLE_STUDENT,
                User.ROLE_STUDENT);
        pathRoles.put(PATH_ROLE_LECTURE, User.ROLE_LECTURE);
        pathRoles.put(PATH_ROLE_SUPERUSER, User.ROLE_SUPERUSER);
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = null;
        if (request instanceof HttpServletRequest) {
            httpRequest = (HttpServletRequest) request;
            UserSession userSession = UserSession.Factory
                    .getUserSession(httpRequest);

            logger.info("Processing URI '" + httpRequest.getRequestURI()
                    + "' with context path '" + httpRequest.getContextPath()
                    + "'");

            if ((httpRequest.getParameter(HttpConstants.HTTP_VAR_ACTION) != null)
                    && (httpRequest.getParameter(HttpConstants.HTTP_VAR_ACTION)
                            .compareTo("login") == 0)) {
                // authenticate user
                request.setAttribute(HttpConstants.HTTP_VAR_USERNAME, request
                        .getParameter(HttpConstants.HTTP_VAR_USERNAME));

                // process login action
                try {
                    userSession
                            .login(
                                    request
                                            .getParameter(HttpConstants.HTTP_VAR_USERNAME),
                                    request
                                            .getParameter(HttpConstants.HTTP_VAR_PASSWORD));
                } catch (AccountAlreadyLoggedInException ex) {
                    request.setAttribute(HttpConstants.ATTR_NAME_ERROR_TITLE,
                            "Account is still logged in from another client");
                    request
                            .setAttribute(
                                    HttpConstants.ATTR_NAME_ERROR_TEXT,
                                    "<font color='red'>The system has detected that your account is still logged in from another client.<br>"
                                            + "To login, please logout from the other client or wait until the other client session expires.</font><br><br>"
                                            + "Please contact the system administrator for further information.<br>"
                                            + "To log in using different username, click <a href='"
                                            + httpRequest.getContextPath()
                                            + "/pages/login'>here</a>.");
                    httpRequest.getRequestDispatcher("/error.jsp").forward(
                            request, response);
                    return;
                } catch (AccountLockedException ex) {
                    request.setAttribute(HttpConstants.ATTR_NAME_ERROR_TITLE,
                            "Account has been locked");
                    request
                            .setAttribute(
                                    HttpConstants.ATTR_NAME_ERROR_TEXT,
                                    "<font color='red'>Your account has been locked by the system.<br>"
                                            + "This could happen because you've entered wrong passwords or other security reasons.</font><br><br>"
                                            + "Please contact the system administrator for further information.<br>"
                                            + "To log in using different username, click <a href='"
                                            + httpRequest.getContextPath()
                                            + "/pages/login'>here</a>.");
                    httpRequest.getRequestDispatcher("/error.jsp").forward(
                            request, response);
                    return;
                } catch (AccountDeactivatedException ex) {
                    request.setAttribute(HttpConstants.ATTR_NAME_ERROR_TITLE,
                            "Account has been deactivated");
                    request
                            .setAttribute(
                                    HttpConstants.ATTR_NAME_ERROR_TEXT,
                                    "<font color='red'>Your account has been deactivated by the system. "
                                            + "</font><br><br>"
                                            + "Please contact the system administrator for further information.<br>"
                                            + "To log in using different username, click <a href='"
                                            + httpRequest.getContextPath()
                                            + "/pages/login'>here</a>.");
                    httpRequest.getRequestDispatcher("/error.jsp").forward(
                            request, response);
                    return;
                } catch (LoginException ex) {
                    request.setAttribute(HttpConstants.ATTR_NAME_LOGIN_STATUS,
                            HttpConstants.ATTR_VALUE_LOGIN_STATUS_FAIL);
                } catch (UserNotFoundException ex) {
                    request.setAttribute(HttpConstants.ATTR_NAME_LOGIN_STATUS,
                            HttpConstants.ATTR_VALUE_LOGIN_STATUS_FAIL);
                } catch (DaoException ex) {
                    logger.error(ex);
                    request.setAttribute(HttpConstants.ATTR_NAME_ERROR_TITLE,
                            "Error");
                    request.setAttribute(HttpConstants.ATTR_NAME_ERROR_TEXT, ex
                            .getMessage());
                    // report fatal error
                    httpRequest.getRequestDispatcher("/error.jsp").forward(
                            request, response);

                    return;
                } catch (Exception ex) {
                    logger.error(ex);
                    request.setAttribute(HttpConstants.ATTR_NAME_ERROR_TITLE,
                            "Error");
                    request.setAttribute(HttpConstants.ATTR_NAME_ERROR_TEXT, ex
                            .getMessage());
                    // report fatal error
                    httpRequest.getRequestDispatcher("/error.jsp").forward(
                            request, response);

                    return;
                }
            }

            // check user login state
            if (!userSession.isLoggedIn()) {
                // forward to login screen
                    httpRequest.getRequestDispatcher("/login.jsp").forward(
                            request, response);
                return;
            } else {
                // check password expiration
                if ((userSession.getUser().getPasswordExpiry() == null)
                        || (userSession.getUser().getPasswordExpiry()
                                .before(new Date()))) {
                    httpRequest.setAttribute(
                            HttpConstants.ATTR_VAR_PASSWORD_EXPIRE, true);
                    httpRequest.getRequestDispatcher("/pages/change-password")
                            .forward(request, response);

                    return;
                }
                /*
                 * // check user privilege String[] paths =
                 * httpRequest.getRequestURI().split("/"); if(paths.length > 2) {
                 * String checkedPath = null;
                 * 
                 * if(httpRequest.getContextPath().compareTo("") == 0) {
                 * checkedPath = paths[2]; } else { checkedPath = paths[3]; }
                 * 
                 * logger.debug("Checking path privilege for '" + checkedPath +
                 * "'");
                 * 
                 * Integer requiredRole = pathRoles.get(checkedPath);
                 * 
                 * if(requiredRole != null) { // special role is needed to
                 * access the page User usr = null; try { // prevent looking up
                 * database for all roles, only for administrator
                 * if(userSession.getUser().hasRole(0)){ String user =
                 * httpRequest.getParameter("user"); usr =
                 * User.Factory.loadByUsername(ConnectionManager.getConnection(httpRequest),user); } }
                 * catch (Exception e) { // do nothing }
                 * if(!userSession.getUser().hasRole(requiredRole)) { //
                 * insufficient privilege
                 * httpRequest.getRequestDispatcher("/pages/insufficient-privilege.jsp").forward(request,
                 * response);
                 * 
                 * return; } // prevent administrator change superuser else
                 * if(usr!=null && (usr.hasRole(4) ||
                 * (usr.hasRole(0)&&(!usr.getUsername().equals(userSession.getUser().getUsername())))) &&
                 * userSession.getUser().hasRole(0)) {
                 * httpRequest.getRequestDispatcher("/pages/insufficient-privilege.jsp").forward(request,
                 * response);
                 * 
                 * return;
                 *  }
                 *  } else { // null required role! logger.error("Required role
                 * is null, probably unconfigured subpath"); } }
                 */
            }
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
    }

}
