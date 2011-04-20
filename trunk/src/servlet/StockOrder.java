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
	private final String comboQuery = "select name from menu_detail";

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
			Connection conn = Connector.getInstance().getConnection();
			pstmt = conn.prepareStatement(comboQuery);
			rs = pstmt.executeQuery();
			ArrayList<String> comboData = new ArrayList<String>();
			while(rs.next()){
				comboData.add(rs.getString(1));
			}
			request.setAttribute("comboData", comboData);
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
