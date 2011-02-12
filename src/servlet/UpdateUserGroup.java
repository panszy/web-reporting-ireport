package servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

import database.Connector;

public class UpdateUserGroup extends HttpServlet {
    private Enumeration enu;
    private Connection conn;
    private PreparedStatement pstmt;
    private String groupId;
    private String groupName;
    private Map menuMap;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        enu = request.getParameterNames();
        menuMap = (Map) request.getAttribute("menuMap");
        groupId = request.getParameter("groupid");
        groupName = request.getParameter("groupname");
        try {
            conn = Connector.getInstance().getConnection();
            pstmt = conn.prepareStatement("delete from menu where role=?");
            pstmt.setString(1, groupId);
            pstmt.execute();
            String menu;
            while (enu.hasMoreElements()) {
                menu = (String) enu.nextElement();
                if (menu.equalsIgnoreCase("groupname") || menu.equalsIgnoreCase("groupid"))
                    continue;
                pstmt = conn.prepareStatement("insert into menu (role,menu_id)values(?,?)");
                pstmt.setString(1, groupId);
                pstmt.setString(2, menu);  // menu_id
                pstmt.execute();
            }
            conn.commit();
        } catch (NamingException ne) {
            ne.printStackTrace();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
            try {
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        request.getRequestDispatcher("/pages/admin/update-usergroup-success.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
