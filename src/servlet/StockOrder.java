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
 * Servlet implementation class StockOrder
 */
public class StockOrder extends HttpServlet {
	private final String comboTypeSOQuery = "select NAMA_TYPESO,KODE_TYPESO from db2admin.tbmastypeso where KODE_TYPESO=2 or KODE_TYPESO=4";
	private final String comboJenisTransaksi = "select NAMA_TRN,KODE_TRN from db2admin.tbmastrn where KODE_TRN=1 or KODE_TRN=3";

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StockOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Connection conn = Connector.getInstance().getConnectionAdmin();
			pstmt = conn.prepareStatement(comboTypeSOQuery);
			rs = pstmt.executeQuery();
			ArrayList<String> comboTypeSOQuery = new ArrayList<String>();
			while(rs.next()){
				comboTypeSOQuery.add(rs.getString(1)+","+rs.getString(2));
			}
			request.setAttribute("comboTypeSOQuery", comboTypeSOQuery);
			
			rs.close();
			pstmt.close();
			pstmt = conn.prepareStatement(comboJenisTransaksi);
			rs = pstmt.executeQuery();
			ArrayList<String> comboJenisTransaksi = new ArrayList<String>();
			while(rs.next()){
				comboJenisTransaksi.add(rs.getString(1)+","+rs.getString(2));
			}
			request.setAttribute("comboJenisTransaksi", comboJenisTransaksi);
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
		request.getRequestDispatcher("/pages/so/stock-order.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("stock_order_id", 1453454);
		request.setAttribute("message", "Stock has been ordered successfully");
		request.getRequestDispatcher("/pages/so/stock-order-success.jsp")
				.forward(request, response);
	}

}
