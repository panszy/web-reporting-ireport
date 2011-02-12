package servlet;

import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import database.Connector;
import web.User;
import web.UserSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class UserGroupServlet extends HttpServlet {
    private Connection conn=null;
    private PreparedStatement pstmt=null,pstmt2=null,pstmt3=null,pstmt4=null;
    private ResultSet rs=null;
    private List menuList=null;
    private List idList=null;
    Map menuMap=null;
    public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
    {
        doPost(request,response);
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {  
         if(request.getParameter("deleted")!=null){
             try{
             conn=Connector.getInstance().getConnection();
             for(String groupid:request.getParameterValues("deleted"))
             {    
                 pstmt=conn.prepareStatement("delete from menu where role=?");
                 pstmt.setString(1, groupid);
                 pstmt.execute();
                 pstmt2=conn.prepareStatement("delete from role where role=?");
                 pstmt2.setString(1, groupid);
                 pstmt2.execute();
                 UserSession userSession = UserSession.Factory
                 .getUserSession(request);

                 pstmt4 = conn.prepareStatement(User.Factory.STMT_INSERT_USER_AUDIT);
                
                 pstmt4.setString(1, userSession.getUser().getUsername());
                 pstmt4.setString(2, request.getRemoteAddr());
                 pstmt4.setString(3, "Delete User Group  " + groupid + " by "
                         + userSession.getUser().getUsername());
                 pstmt4.executeUpdate();
                 
                     }   
             
             }catch(NamingException ne)
             {
                 ne.printStackTrace();
             }
             catch(SQLException sqle)
             {
                 sqle.printStackTrace();
             } catch (Exception e) {
				e.printStackTrace();
			}
             finally{
                 try{
                     rs.close();
                     pstmt.close();
                     pstmt2.close();
                     pstmt4.close();
                 }catch(Exception e)
                 {
                     
                 }
             }
             request.getRequestDispatcher("/pages/admin/delete-user-group.jsp").forward(request, response);
         }else{
        try{ 
             menuMap=new HashMap();
             conn=Connector.getInstance().getConnection();
             pstmt=conn.prepareStatement("select menu_detail.menu_id,menu_detail.name from menu,menu_detail where menu_detail.menu_id=menu.menu_id");
             rs=pstmt.executeQuery();
             while(rs.next())
             {  // idList.add(rs.getString(1));
                 //menuList.add(rs.getString(2));
                 menuMap.put(rs.getString(1), rs.getString(2));
             }
             
             
             
         } catch (NamingException e) {
             e.printStackTrace();
         
        }
         catch(SQLException e)
         {
             e.printStackTrace();
         } catch (Exception e) {
			e.printStackTrace();
		}
         finally{
             try{
                 rs.close();
                 pstmt.close();
                 pstmt2.close();
                 pstmt3.close();
             }catch(Exception e)
             {
                 
             }
         }
      //   request.setAttribute("idList", idList);
         request.setAttribute("menuMap", menuMap);
         request.getRequestDispatcher("/pages/admin/create-user-group.jsp").forward(request, response);

    }
   }
}
