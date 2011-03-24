package servlet;

import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import database.Connector;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class UserGroup extends HttpServlet{
    /**
	 * 
	 */
	private static final long serialVersionUID = 198833059161243550L;
	private Connection conn=null;
    private PreparedStatement pstmt=null;
    private ResultSet rs=null;
    @SuppressWarnings("unused")
	private List<String> groupList=null;
    private Map<String,String> menuMap;
    public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
    {
       doPost(request,response);
    }
    
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {  try{ 
          // groupList=new ArrayList();
            menuMap=new HashMap<String,String>();
            conn=Connector.getInstance().getConnection();
            pstmt=conn.prepareStatement("select role,name from role");
            rs=pstmt.executeQuery();
            while(rs.next())
            {
                //groupList.add(rs.getString(1));
                menuMap.put(rs.getString(1),rs.getString(2));
            }
            Connector.putConnection(conn);
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
            }catch(Exception e)
            {
                
            }
        }
        request.setAttribute("menuMap", menuMap);
        request.getRequestDispatcher("/pages/admin/user-group.jsp").forward(request, response);
    }
}
