package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import web.User;
import web.UserSession;
import database.Connector;
import exception.DaoException;
import exception.UserNotFoundException;

/**
 * Servlet implementation class StockOrderUpdate
 */
public class StockOrderUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(StockOrderUpdate.class);
	private final String tableQuery = "SELECT KODE_CUST as \"kode customer\",TGL_SO_SMS as \"tgl SO\",NO_SO_SMS as \"no SO\",TGL_PO as \"tgl PO\",NO_PO as \"no PO\",TYPE_BAYAR as \"tipe bayar\",case when F_SOBATAL = 1 then 'Batal' else case when F_APPCAB = 1 then 'Setuju' else case when F_APPPROTEK = 1 then 'Proteksi' else 'Menunggu' end end end as status,F_KONSINYASI,KET_SO as keterangan, ROW_NUMBER() OVER(ORDER BY TGL_SO_SMS DESC) AS ROWNUMBER FROM \"VISITEK-117\".TBSO_SMS where (NO_SO_SMS=? or TGL_SO_SMS between ? and ?) and F_SOBATAL = 0 and F_APPCAB = 0 and F_APPPROTEK = 0";
	private final String tableQueryCount = "SELECT count(1) FROM \"VISITEK-117\".TBSO_SMS where (NO_SO_SMS=? or TGL_SO_SMS between ? and ?) and F_SOBATAL = 0 and F_APPCAB = 0 and F_APPPROTEK = 0";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StockOrderUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = (String) request.getParameter("page");
		String tanggaSOAwal =request.getParameter("tanggal_so_awal");
		String tanggaSOAkhir =request.getParameter("tanggal_so_akhir");
		String nomorSO =request.getParameter("nomor_so");
        if (page != null) {            
        	PreparedStatement pstmt = null;
    		ResultSet rs = null;
            try {
            	int total_pages = -1;
            	Connection conn = Connector.getInstance().getConnectionAdmin();
    			pstmt = conn.prepareStatement(tableQueryCount);
    			pstmt.setString(1, nomorSO);
    			pstmt.setString(2, tanggaSOAwal);
    			pstmt.setString(3, tanggaSOAkhir);    			
    			rs = pstmt.executeQuery();
    			if(rs.next())
    				total_pages = rs.getInt(1);
    			rs.close();
    			pstmt.close();    			
    			
            	conn = Connector.getInstance().getConnectionAdmin();
    			pstmt = conn
    			        .prepareStatement("select * from ("+tableQuery+ ") where ROWNUMBER between ? and ?");	
    			pstmt.setString(1, nomorSO);
    			pstmt.setString(2, tanggaSOAwal);
    			pstmt.setString(3, tanggaSOAkhir);
    			pstmt.setInt(4, 10 * (Integer.parseInt(page) - 1));
    			pstmt.setInt(5, (10 * (Integer.parseInt(page) - 1))+10);
    			rs = pstmt.executeQuery();
    			ArrayList<String> tableColumn = new ArrayList<String>();
    			ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
    			int totalColumn = rs.getMetaData().getColumnCount();
    			for(int i = 1; i <totalColumn ; i++)
    				tableColumn.add(rs.getMetaData().getColumnName(i));
    			request.setAttribute("tableColumn",tableColumn);
    			while(rs.next()){
    				ArrayList<String> rowData = new ArrayList<String>();
    				for(int i = 1; i <totalColumn ; i++){
    					rowData.add(rs.getString(i));
    				}
    				tableData.add(rowData);
    			}
    			request.setAttribute("tableData",tableData);
    			request.setAttribute("total_pages", Integer
                        .toString((total_pages / 10)
                                + ((total_pages % 10) > 0 ? 1 : 0)));
                request.setAttribute("pages", page);
                } catch (DaoException ex) {
                    ex.printStackTrace();
                    logger.error(ex);
                } catch (NamingException ex) {
                    ex.printStackTrace();
                    logger.error(ex);
                } catch (SQLException ex) {
                    logger.error(ex);
                    ex.printStackTrace();
                } finally {
                	try {
        				if(rs!=null)
        					rs.close();
        				if(pstmt!=null)
        					pstmt.close();
        			} catch (Exception e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
                }                           
        }

        request.getRequestDispatcher("/pages/so/stock-order-update.jsp").forward(
                request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("Action") != null) {
	          
            String action = request.getParameter("Action");
            if (action.equalsIgnoreCase("Search")) {
            	String tanggaSOAwal =request.getParameter("tanggal_so_awal");
        		String tanggaSOAkhir =request.getParameter("tanggal_so_akhir");
        		String nomorSO =request.getParameter("nomor_so");        		
        		PreparedStatement pstmt = null;
        		ResultSet rs = null;
                try {
                	int total_pages = -1;
                	Connection conn = Connector.getInstance().getConnectionAdmin();
        			pstmt = conn.prepareStatement(tableQueryCount);
        			pstmt.setString(1, nomorSO);
        			pstmt.setString(2, tanggaSOAwal);
        			pstmt.setString(3, tanggaSOAkhir);
        			rs = pstmt.executeQuery();
        			if(rs.next())
        				total_pages = rs.getInt(1);
        			rs.close();
        			pstmt.close();    			
        			
                	conn = Connector.getInstance().getConnectionAdmin();
        			pstmt = conn
        			        .prepareStatement("select * from ("+tableQuery+ ") where ROWNUMBER between ? and ?");	
        			pstmt.setString(1, nomorSO);
        			pstmt.setString(2, tanggaSOAwal);
        			pstmt.setString(3, tanggaSOAkhir);
        			pstmt.setInt(4, 0);
        			pstmt.setInt(5, 10);
        			rs = pstmt.executeQuery();
        			
        			ArrayList<String> tableColumn = new ArrayList<String>();
        			ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
        			int totalColumn = rs.getMetaData().getColumnCount();
        			for(int i = 1; i <totalColumn ; i++)
        				tableColumn.add(rs.getMetaData().getColumnName(i));
        			request.setAttribute("tableColumn",tableColumn);
        			while(rs.next()){
        				ArrayList<String> rowData = new ArrayList<String>();
        				for(int i = 1; i <totalColumn ; i++){
        					rowData.add(rs.getString(i));
        				}
        				tableData.add(rowData);
        			}
        			request.setAttribute("tableData",tableData);
        			request.setAttribute("total_pages", Integer
                            .toString((total_pages / 10)
                                    + ((total_pages % 10) > 0 ? 1 : 0)));                    
                } catch (DaoException ex) {
                    ex.printStackTrace();
                   // logger.error(ex);
                } catch (NamingException ex) {
                    ex.printStackTrace();
                   // logger.error(ex);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                  //  logger.error(ex);
                } catch (Exception e) {
					e.printStackTrace();
				}
                request.getRequestDispatcher(
                        "/pages/so/stock-order-update.jsp?&tanggal_so_awal="+tanggaSOAwal+"&tanggal_so_akhir="+tanggaSOAkhir+"&nomor_so="+nomorSO).forward(request, response);
            } else if (action.equalsIgnoreCase("Delete")) {
                if (request.getParameterValues("deleted") != null) {
                    String[] delete = (String[]) request
                            .getParameterValues("deleted");
                    for (@SuppressWarnings("unused") String str : delete)

                        try {

                            Connection conn2 = Connector.getInstance().getConnection();
                            PreparedStatement pstmt = conn2
                                    .prepareStatement(User.Factory.STMT_INSERT_USER_AUDIT);
                            UserSession userSession = UserSession.Factory
                                    .getUserSession(request);
                            pstmt.setString(1, delete[0]);
                            pstmt.setString(2, request.getRemoteAddr());
                            pstmt.setString(3, "Delete username " + delete[0]
                                    + " by "
                                    + userSession.getUser().getUsername());
                            pstmt.executeUpdate();

                            int i = 0;
                            while (delete.length > i) {
                                User.Factory.delete(Connector.getInstance().getConnection(), delete[i]);
                                i++;
                            }
                            pstmt.close(); 

                        } catch (DaoException ex) {
                            logger.error(ex);
                        } catch (NamingException ex) {
                            logger.error(ex);
                        } catch (SQLException ex) {
                            logger.error(ex);
                        } catch (UserNotFoundException ex) {
                            logger.error(ex);
                        } catch (Exception e) {
							e.printStackTrace();
						}
                    request
                            .getRequestDispatcher(
                                    "/pages/admin/user-delete-success.jsp?message=delete")
                            .forward(request, response);
                }
            }            

        } else {
          
        	String tanggaSOAwal =request.getParameter("tanggal_so_awal");
    		String tanggaSOAkhir =request.getParameter("tanggal_so_akhir");
    		String nomorSO =request.getParameter("nomor_so");
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
            try {
            	int total_pages = -1;
            	Connection conn = Connector.getInstance().getConnectionAdmin();
    			pstmt = conn.prepareStatement(tableQueryCount);
    			pstmt.setString(1, nomorSO);
    			pstmt.setString(2, tanggaSOAwal);
    			pstmt.setString(3, tanggaSOAkhir);
    			rs = pstmt.executeQuery();
    			if(rs.next())
    				total_pages = rs.getInt(1);
    			rs.close();
    			pstmt.close();    			
    			
            	conn = Connector.getInstance().getConnectionAdmin();
    			pstmt = conn
    			        .prepareStatement("select * from ("+tableQuery+ ") where ROWNUMBER between ? and ?");	
    			pstmt.setString(1, nomorSO);
    			pstmt.setString(2, tanggaSOAwal);
    			pstmt.setString(3, tanggaSOAkhir);
    			pstmt.setInt(4, 0);
    			pstmt.setInt(5, 10);
    			rs = pstmt.executeQuery();
    			
    			ArrayList<String> tableColumn = new ArrayList<String>();
    			ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
    			int totalColumn = rs.getMetaData().getColumnCount();
    			for(int i = 1; i <totalColumn ; i++)
    				tableColumn.add(rs.getMetaData().getColumnName(i));
    			request.setAttribute("tableColumn",tableColumn);
    			while(rs.next()){
    				ArrayList<String> rowData = new ArrayList<String>();
    				for(int i = 1; i <totalColumn ; i++){
    					rowData.add(rs.getString(i));
    				}
    				tableData.add(rowData);
    			}
    			request.setAttribute("tableData",tableData);
    			request.setAttribute("total_pages", Integer
                        .toString((total_pages / 10)
                                + ((total_pages % 10) > 0 ? 1 : 0)));                    
            } catch (DaoException ex) {
                ex.printStackTrace();
               // logger.error(ex);
            } catch (NamingException ex) {
                ex.printStackTrace();
               // logger.error(ex);
            } catch (SQLException ex) {
                ex.printStackTrace();
              //  logger.error(ex);
            } catch (Exception e) {
				e.printStackTrace();
			}
            request.getRequestDispatcher(
                    "/pages/so/stock-order-update.jsp").forward(request,
                    response);

        }
	}

}
