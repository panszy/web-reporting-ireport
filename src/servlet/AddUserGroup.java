package servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Enumeration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connector;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import web.User;
import web.UserSession;

public class AddUserGroup extends HttpServlet {
    
	private static final long serialVersionUID = 5385813167953211657L;
	private Connection conn1 = null, conn2 = null, conn = null;
    private PreparedStatement pstmt = null, pstmt4 = null;
    private ResultSet rs = null;
    private String groupId;
    private String groupName;
    private Map<String,String> menuMap;
    Enumeration<String> enu;

    @SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> menuList = new ArrayList<String>();
        String menuid;        
        menuMap=(Map<String,String>)request.getAttribute("menuMap");

        try {
            groupName = request.getParameter("groupname");
            enu = (Enumeration<String>) request.getParameterNames();
            enu.nextElement();

            conn1 = Connector.getInstance().getConnection();
            pstmt = conn1.prepareStatement("insert into role (name) values(?)");
            pstmt.setString(1, groupName);
            pstmt.execute();

            pstmt = conn1.prepareStatement("select role from role where name=?");
            pstmt.setString(1, groupName);
            rs = pstmt.executeQuery();
            if (rs.next())
                groupId = rs.getString(1);
            conn2 = Connector.getInstance().getConnection();
            while (enu.hasMoreElements()) {
                menuid = (String) enu.nextElement();
                pstmt = conn2.prepareStatement("insert into menu (role,menu_id) values(?,?)");
                pstmt.setString(1, groupId);
                pstmt.setString(2, menuid);
                pstmt.execute();
                menuList.add(menuid);
            }

            conn = Connector.getInstance().getConnection();

            pstmt4 = conn.prepareStatement(User.Factory.STMT_INSERT_USER_AUDIT);
            UserSession userSession = UserSession.Factory
                    .getUserSession(request);

            pstmt4.setString(1, userSession.getUser().getUsername());
            pstmt4.setString(2, request.getRemoteAddr());
            pstmt4.setString(3, "Create User Group  " + groupId + " by "
                    + userSession.getUser().getUsername());
            pstmt4.executeUpdate();

            request.setAttribute("groupId", groupId);
            request.setAttribute("groupName", groupName);
            request.setAttribute("menu", enu);
            request.getRequestDispatcher("/pages/admin/addusergroupsuccess.jsp").forward(request, response);

        }        
        catch (SQLException e) {

            //   request.setAttribute("idList", idList);
            request.setAttribute("menu", menuList);
            request.setAttribute("enum", enu);
            request.setAttribute("groupId", groupId);
            request.setAttribute("groupName", groupName);
            request.setAttribute("error", "UserGroup already exist");
            request.setAttribute("menuMap", menuMap);
            request.getRequestDispatcher("/pages/admin/create-user-group.jsp").forward(request, response);

        } catch (Exception e) {
			e.printStackTrace();
		}
        finally {
            try {
                pstmt.close();
                pstmt4.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
