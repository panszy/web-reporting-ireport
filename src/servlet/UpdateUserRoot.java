package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.omg.CORBA.Request;
import exception.DaoException;
import exception.EmailException;
import exception.InvalidPasswordException;
import exception.NIKException;
import common.PasswordManager;
import database.Connector;
import exception.RoleException;
import web.HttpConstants;
import web.User;
import exception.UserNotFoundException;
import web.UserSession;

public class UpdateUserRoot extends HttpServlet {
    public static final long serialVersionUID = 1L;
    
    static Logger logger = Logger.getLogger(SearchUser.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strUsername = (String)request.getParameter("user");
        
        if(strUsername != null) {
            try {
                User user = User.Factory.loadByUsername(Connector.getInstance().getConnection(),strUsername);
                request.setAttribute(HttpConstants.ATTR_NAME_USER, user);
            }
            catch(Throwable t) {
                logger.warn("Unable to load user with user_id '" + strUsername + "'");
            }
        }
        
        request.getRequestDispatcher("/pages/superuser/user-update.jsp").forward(request, response);
      
       // doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String username = (String)request.getParameter("username");
        
        Map statusMap=new HashMap();
        statusMap.put("active",0);
        statusMap.put("deactive",1);
        statusMap.put("locked",2);
        if(request.getParameter("origin_username")==null)
            doGet(request,response);
        else{     
      
        String originusername = (String)request.getParameter("origin_username");        
        String fullname = (String)request.getParameter(HttpConstants.HTTP_VAR_FULLNAME);
       
        String nik = (String)request.getParameter(HttpConstants.HTTP_VAR_NIK);
        String emailaddress = (String)request.getParameter(HttpConstants.HTTP_VAR_EMAIL_ADDRESS);
        String department=(String)request.getParameter(HttpConstants.HTTP_VAR_DEPARTEMEN);
        String division=(String)request.getParameter(HttpConstants.HTTP_VAR_DIVISION);
        String address=(String)request.getParameter(HttpConstants.HTTP_VAR_ADDRESS);
        String Action = (String)request.getParameter("Action");

        int status=(Integer)statusMap.get(request.getParameter("status"));
        Set roleSet=new HashSet();
        if((request.getParameter("admin")!=null)||(request.getParameter("adminori")!=null))
            roleSet.add(0);
        if((request.getParameter("cgw")!=null)||(request.getParameter("cgwori")!=null))
            roleSet.add(1);
        if((request.getParameter("threshold")!=null)||(request.getParameter("thresholdori")!=null))
            roleSet.add(2);
        if((request.getParameter("monitoring")!=null)||(request.getParameter("monitoringori")!=null))
            roleSet.add(3);
    
        
        if(Action.equalsIgnoreCase("Update")){
            User user = null;
            try {   
                user = User.Factory.loadByUsername(Connector.getInstance().getConnection(),originusername);
                user.setFullName(fullname);
                user.setEmailAddress(emailaddress);
                user.setUsername(originusername);
                user.setNik(nik);  
                user.setRoles(roleSet);
                user.setStatus(status);
                user.setDepartemen(department);
                user.setDivision(division);
                user.setAddress(address);
                user.setFailedLogins(0);
                Pattern pattern=Pattern.compile(".+@.+\\.[a-z]+");
                Matcher matcher=pattern.matcher(request.getParameter(HttpConstants.HTTP_VAR_EMAIL_ADDRESS));
                boolean match=matcher.matches();
                if(!match)
                {
                   throw new EmailException("Invalid Email Format"); 
                }
                if(request.getParameter(HttpConstants.HTTP_VAR_NIK)==""){
                    throw new NIKException("You must Fill NIK column");
                }
                if(roleSet.isEmpty())
                {
                    throw new RoleException("You must check at least one role");
                }
                User.Factory.newsave(Connector.getInstance().getConnection(),user);
                
                request.setAttribute(HttpConstants.ATTR_NAME_USER, user);    
                request.getRequestDispatcher("/pages/superuser/user-update-success.jsp").forward(request, response);
              
            } catch (UserNotFoundException e) { 
                logger.error(e);
            } catch (DaoException e) {           
                logger.error(e);
            } catch (NamingException e){
                logger.error(e);
            } catch (SQLException e){
                logger.error(e);
            }
            catch(EmailException e)
            {
                e.printStackTrace();
                request.setAttribute(HttpConstants.ATTR_NAME_USER, user);
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/pages/superuser/user-update.jsp").forward(request, response);
                
            }
            catch(NIKException e)
            {
                e.printStackTrace();
                request.setAttribute(HttpConstants.ATTR_NAME_USER, user);
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/pages/superuser/user-update.jsp").forward(request, response);
               
            }
            catch(RoleException e)
            {
                e.printStackTrace();
                request.setAttribute(HttpConstants.ATTR_NAME_USER, user);
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/pages/superuser/user-update.jsp").forward(request, response);
               
            } catch (Exception e) {
				e.printStackTrace();
			}
        /*    user.setFullName(fullname);
            user.setEmailAddress(emailaddress);
            user.setUsername(originusername);
            user.setNik(nik);  
            user.setRoles(roleSet);
            user.setStatus(status);
            user.setDepartemen(department);
            user.setDivision(division);
            user.setAddress(address);*/
            try {
                //User.Factory.save(ConnectionManager.getConnection(request),user);
                Connection conn = Connector.getInstance().getConnection();
                PreparedStatement pstmt=conn.prepareStatement(User.Factory.STMT_INSERT_USER_AUDIT);
                UserSession userSession = UserSession.Factory.getUserSession(request);

                pstmt.setString(1, userSession.getUser().getUsername());
                pstmt.setString(2, request.getRemoteAddr());
                pstmt.setString(3, "Update "+ user.getUsername()+" by "+userSession.getUser().getUsername());
                pstmt.executeUpdate();
                
               // request.setAttribute(HttpConstants.ATTR_NAME_USER, user);    
               // request.getRequestDispatcher("/pages/superuser/user-update-success.jsp").forward(request, response);
            }
            catch(NamingException ex) {
                logger.error(ex);
            }
            catch(SQLException ex) {
                logger.error(ex);
            }
         //  request.getRequestDispatcher("/pages/admin/user-update-success.jsp").forward(request, response);
 catch (Exception e) {
				e.printStackTrace();
			}
        } else if(Action.equalsIgnoreCase("Reset Password")){
            User user = null;
            try {           
                user = User.Factory.loadByUsername(Connector.getInstance().getConnection(),originusername);
            } catch (UserNotFoundException e) { 
                logger.error(e);
            } catch (DaoException e) {           
                logger.error(e);
            } catch (NamingException e){
                logger.error(e);
            } catch (SQLException e){
                logger.error(e);
            } catch (Exception e) {
				e.printStackTrace();
			}
            try {
                PasswordManager.resetPassword(Connector.getInstance().getConnection(),user);                
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
                logger.error(ex);
            } catch (Exception e) {
				e.printStackTrace();
			}
            request.getRequestDispatcher("/pages/superuser/user-delete-success.jsp?message=reset&nik="+user.getNik()).forward(request, response);
        }
    }
    }     
}

