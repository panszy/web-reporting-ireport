package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import database.Connector;
import web.HttpConstants;
import web.User;

public class CreateNewUser extends HttpServlet {
    public static final long serialVersionUID = 1L;

    static Logger logger = Logger.getLogger(CreateNewUser.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strUserId = request.getParameter(HttpConstants.HTTP_VAR_USER_ID);

        if (strUserId != null) {
            try {
                User user = User.Factory.load(Connector.getInstance().getConnection(), Integer.valueOf(strUserId).intValue());
                request.setAttribute(HttpConstants.ATTR_NAME_USER, user);
            }
            catch (Throwable t) {
                logger.warn("Unable to load user with user_id '" + strUserId + "'");
            }
        }
        HashMap<String,String> groupMap = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Connection conn = Connector.getInstance().getConnection();
            pstmt = conn.prepareStatement("select role,name from role order by name");
            rs = pstmt.executeQuery();
            groupMap = new HashMap<String,String>();
            while (rs.next())
                groupMap.put((String) rs.getString(1), (String) rs.getString(2));
            request.setAttribute("groupMap", groupMap);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        request.getRequestDispatcher("/pages/admin/user-details.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
