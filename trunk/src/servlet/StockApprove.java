package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.Connector;
import exception.DaoException;

/**
 * Servlet implementation class StockApprove
 */
public class StockApprove extends HttpServlet {
	private final String tableQuery = "SELECT KODE_CAB as \"kode cabang\",KODE_TRN as \"kode transaksi\",KODE_CUST as \"kode customer\",KODE_PEG as \"kode pegawai\",KODE_TYPESO as \"type SO\",KODE_TYPEDO as \"type DO\",TGL_SO_SMS as \"tgl SO\",NO_SO_SMS as \"no SO\",TGL_PO as \"tgl PO\",NO_PO as \"no PO\",TYPE_BAYAR as \"tipe bayar\",case when F_SOBATAL = 1 then 'Batal' else case when F_APPCAB = 1 then 'Setuju' else case when F_APPPROTEK = 1 then 'Proteksi' end end end as status,F_KONSINYASI,KET_SO as keterangan FROM DB2ADMIN.TBSO_SMS";
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StockApprove() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Connection conn = Connector.getInstance().getConnectionAdmin();
			pstmt = conn
			        .prepareStatement(tableQuery);			       
			rs = pstmt.executeQuery();
			ArrayList<String> tableColumn = new ArrayList<String>();
			ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
			int totalColumn = rs.getMetaData().getColumnCount();
			for(int i = 1; i <=totalColumn ; i++)
				tableColumn.add(rs.getMetaData().getColumnName(i));
			request.setAttribute("tableColumn",tableColumn);
			while(rs.next()){
				ArrayList<String> rowData = new ArrayList<String>();
				for(int i = 1; i <=totalColumn ; i++){
					rowData.add(rs.getString(i));
				}
				tableData.add(rowData);
			}
			request.setAttribute("tableData",tableData);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		request.getRequestDispatcher("/pages/so/stock-approval.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("stock_order_id", 1453454);
		if(request.getParameter("type").equals("approve")){
			request.setAttribute("message", "Stock has been approved successfully");			
		} else if(request.getParameter("type").equals("cancel")){
			request.setAttribute("message", "Stock has been canceled successfully");
		} else if(request.getParameter("type").equals("reject")){
			request.setAttribute("message", "Stock has been rejected successfully");
		}
		request.getRequestDispatcher("/pages/so/stock-approval-success.jsp").forward(request, response);
	}

}
