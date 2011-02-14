package servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import web.User;
import web.UserSession;

import database.Connector;
public class EditUserGroup extends HttpServlet{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection conn=null;
    private PreparedStatement pstmt=null,pstmt4=null;
    private ResultSet rs=null;
    private String groupId;
    private String groupName;
    private List<String> groupMenuList;
    private Map<String,String> menuMap;
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
    { 
      groupMenuList=new ArrayList<String>();
      menuMap=new HashMap<String,String>();
      groupId=request.getParameter("groupid");
      groupName=request.getParameter("groupname");
      try{
          conn=Connector.getInstance().getConnection();
          pstmt=conn.prepareStatement("select menu_detail.menu_id from menu_detail,menu where menu.menu_id=menu_detail.menu_id and menu.role=?");
          pstmt.setString(1,groupId);
          rs=pstmt.executeQuery();
          while(rs.next())
              groupMenuList.add(rs.getString(1));
          pstmt=conn.prepareStatement("select menu_detail.menu_id,menu_detail.name from menu_detail");
          rs=pstmt.executeQuery();
          
          pstmt4 = conn.prepareStatement(User.Factory.STMT_INSERT_USER_AUDIT);
            UserSession userSession = UserSession.Factory
            .getUserSession(request);

            pstmt4.setString(1, userSession.getUser().getUsername());
            pstmt4.setString(2, request.getRemoteAddr());
            pstmt4.setString(3, "Edit User Group  " + groupId + " by "
                    + userSession.getUser().getUsername());
            pstmt4.executeUpdate();

          while(rs.next())
              menuMap.put(rs.getString(1), rs.getString(2));
              
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
          }catch(SQLException sqle)
          {
              sqle.printStackTrace();
          }
      }
      request.setAttribute("groupid", groupId);
      request.setAttribute("groupname",groupName);
      request.setAttribute("menuList", groupMenuList);
      request.setAttribute("menuMap", menuMap);
      request.getRequestDispatcher("/pages/admin/user-group-edit.jsp").forward(request, response);
      
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
    {
        doGet(request,response);
    }
}
