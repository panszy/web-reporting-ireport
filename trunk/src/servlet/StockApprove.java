package servlet;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
public class StockApprove extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(StockOrderUpdate.class);
	private final String tableQuery = "SELECT KODE_CAB as \"kode cabang\",KODE_CUST as \"kode customer\",TGL_SO_SMS as \"tgl SO\",NO_SO_SMS as \"no SO\",TGL_PO as \"tgl PO\",NO_PO as \"no PO\",case when TYPE_BAYAR = 1.0 then 'kredit' else 'tunai' end as \"tipe bayar\",case when F_SOBATAL = 1 then 'Batal' else case when F_APPCAB = 1 then 'Setuju' else case when F_APPPROTEK = 1 then 'Proteksi' else 'Menunggu' end end end as status,KET_SO as keterangan, ROW_NUMBER() OVER(ORDER BY TGL_SO_SMS DESC) AS ROWNUMBER FROM \"DB2ADMIN\".TBSO_SMS where (NO_SO_SMS=? or TGL_SO_SMS between ? and ?) and F_SOBATAL = 0 and F_APPCAB = 0 and F_APPPROTEK = 0";
	private final String tableQueryCount = "SELECT count(1) FROM \"DB2ADMIN\".TBSO_SMS where (NO_SO_SMS=? or TGL_SO_SMS between ? and ?) and F_SOBATAL = 0 and F_APPCAB = 0 and F_APPPROTEK = 0";
	private final String approveOrder = "call SPIUSO_SMS2(?,?,?,?,?)";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StockApprove() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = (String) request.getParameter("page");
		String tanggaSOAwal = request.getParameter("tanggal_so_awal");
		String tanggaSOAkhir = request.getParameter("tanggal_so_akhir");
		String nomorSO = request.getParameter("nomor_so");
		String nomorSOEdit = request.getParameter("no_so");
		
		if (page != null && nomorSOEdit == null) {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				int total_pages = -1;
				Connection conn = Connector.getInstance().getConnectionAdmin();
				pstmt = conn.prepareStatement(tableQueryCount);
				pstmt.setString(1, nomorSO);
				pstmt.setString(2, (tanggaSOAwal == null || tanggaSOAwal
						.equals("")) ? sdf.format(new Date(System.currentTimeMillis()+86400000)) : tanggaSOAwal);
				pstmt.setString(3, (tanggaSOAkhir == null || tanggaSOAkhir
						.equals("")) ? sdf.format(new Date(System.currentTimeMillis()+86400000)) : tanggaSOAkhir);
				rs = pstmt.executeQuery();
				if (rs.next())
					total_pages = rs.getInt(1);
				rs.close();
				pstmt.close();

				conn = Connector.getInstance().getConnectionAdmin();
				pstmt = conn.prepareStatement("select * from (" + tableQuery
						+ ") as tbl where tbl.ROWNUMBER between ? and ?");
				pstmt.setString(1, nomorSO);
				pstmt.setString(2, (tanggaSOAwal == null || tanggaSOAwal
						.equals("")) ? sdf.format(new Date(System.currentTimeMillis()+86400000))
								: tanggaSOAwal);
				pstmt.setString(3, (tanggaSOAkhir == null || tanggaSOAkhir
						.equals("")) ? sdf.format(new Date(System.currentTimeMillis()+86400000)) : tanggaSOAkhir);
				pstmt.setInt(4, 10 * (Integer.parseInt(page) - 1));
				pstmt.setInt(5, (10 * (Integer.parseInt(page) - 1)) + 10);
				rs = pstmt.executeQuery();
				ArrayList<String> tableColumn = new ArrayList<String>();
				ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
				int totalColumn = rs.getMetaData().getColumnCount();
				for (int i = 1; i < totalColumn; i++)
					tableColumn.add(rs.getMetaData().getColumnName(i));
				request.setAttribute("tableColumn", tableColumn);
				while (rs.next()) {
					ArrayList<String> rowData = new ArrayList<String>();
					for (int i = 1; i < totalColumn; i++) {
						rowData.add(rs.getString(i));
					}
					tableData.add(rowData);
				}
				request.setAttribute("tableData", tableData);
				request.setAttribute(
						"total_pages",
						Integer.toString((total_pages / 10)
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
					if (rs != null)
						rs.close();
					if (pstmt != null)
						pstmt.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		request.getRequestDispatcher("/pages/so/stock-approval.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("Action") != null) {

			String action = request.getParameter("Action");
			if (action.equalsIgnoreCase("Search")) {
				String tanggaSOAwal = request.getParameter("tanggal_so_awal");
				System.out.println("tanggaSOAwal="+tanggaSOAwal);
				String tanggaSOAkhir = request.getParameter("tanggal_so_akhir");
				System.out.println("tanggaSOAkhir="+tanggaSOAkhir);
				String nomorSO = request.getParameter("nomor_so");
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try {
					int total_pages = -1;
					Connection conn = Connector.getInstance()
							.getConnectionAdmin();
					pstmt = conn.prepareStatement(tableQueryCount);
					pstmt.setString(1, nomorSO);
					pstmt.setString(2, (tanggaSOAwal == null || tanggaSOAwal
							.equals("")) ? sdf.format(new Date(System.currentTimeMillis()+86400000))
							: tanggaSOAwal);
					pstmt.setString(3, (tanggaSOAkhir == null || tanggaSOAkhir
							.equals("")) ? sdf.format(new Date(System.currentTimeMillis()+86400000))
							: tanggaSOAkhir);
					rs = pstmt.executeQuery();
					if (rs.next())
						total_pages = rs.getInt(1);
					rs.close();
					pstmt.close();

					conn = Connector.getInstance().getConnectionAdmin();
					pstmt = conn.prepareStatement("select * from ("
							+ tableQuery
							+ ") as tbl where tbl.ROWNUMBER between ? and ?");
					pstmt.setString(1, nomorSO);
					pstmt.setString(2, (tanggaSOAwal == null || tanggaSOAwal
							.equals("")) ? sdf.format(new Date(System.currentTimeMillis()+86400000))
									: tanggaSOAwal);
					pstmt.setString(3, (tanggaSOAkhir == null || tanggaSOAkhir
							.equals("")) ? sdf.format(new Date(System.currentTimeMillis()+86400000))
									: tanggaSOAkhir);
					pstmt.setInt(4, 0);
					pstmt.setInt(5, 10);
					rs = pstmt.executeQuery();

					ArrayList<String> tableColumn = new ArrayList<String>();
					ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
					int totalColumn = rs.getMetaData().getColumnCount();
					for (int i = 1; i < totalColumn; i++)
						tableColumn.add(rs.getMetaData().getColumnName(i));
					request.setAttribute("tableColumn", tableColumn);
					while (rs.next()) {
						ArrayList<String> rowData = new ArrayList<String>();
						for (int i = 1; i < totalColumn; i++) {
							rowData.add(rs.getString(i));
						}
						tableData.add(rowData);
					}
					request.setAttribute("tableData", tableData);
					request.setAttribute(
							"total_pages",
							Integer.toString((total_pages / 10)
									+ ((total_pages % 10) > 0 ? 1 : 0)));
				} catch (DaoException ex) {
					ex.printStackTrace();
					// logger.error(ex);
				} catch (NamingException ex) {
					ex.printStackTrace();
					// logger.error(ex);
				} catch (SQLException ex) {
					ex.printStackTrace();
					// logger.error(ex);
				} catch (Exception e) {
					e.printStackTrace();
				}
				request.getRequestDispatcher(
						"/pages/so/stock-approval.jsp?&tanggal_so_awal="
								+ tanggaSOAwal + "&tanggal_so_akhir="
								+ tanggaSOAkhir + "&nomor_so=" + nomorSO)
						.forward(request, response);
			} else if (request.getParameterValues("approved") != null) {
				if (action.equalsIgnoreCase("approve")) {
					String[] approve = (String[]) request
							.getParameterValues("approved");
					PreparedStatement pstmt = null;
					for (@SuppressWarnings("unused")
					String str : approve)
						try {

							int i = 0;
							while (approve.length > i) {
								Connection conn = Connector.getInstance()
										.getConnection();
								pstmt = conn
										.prepareStatement(User.Factory.STMT_INSERT_USER_AUDIT);
								UserSession userSession = UserSession.Factory
										.getUserSession(request);
								pstmt.setString(1, approve[i].split(";")[1]);
								pstmt.setString(2, request.getRemoteAddr());
								pstmt.setString(3,
										"Approve order with NO SO SMS "
												+ approve[i].split(";")[1]
												+ " by "
												+ userSession.getUser()
														.getUsername());
								pstmt.executeUpdate();
								pstmt.close();

								conn = Connector.getInstance()
										.getConnectionAdmin();
								pstmt = conn.prepareStatement(approveOrder);
								pstmt.setString(1, approve[i].split(";")[0]);
								pstmt.setString(2, approve[i].split(";")[1]);
								pstmt.setString(3, "0");
								pstmt.setString(4, "1");
								pstmt.setString(5, "0");
								pstmt.executeUpdate();
								pstmt.close();
								i++;
							}

						} catch (DaoException ex) {
							logger.error(ex);
						} catch (NamingException ex) {
							logger.error(ex);
						} catch (SQLException ex) {
							logger.error(ex);
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							try {
								if (pstmt != null)
									pstmt.close();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					request.setAttribute("approvedData", approve);
					request.setAttribute("message",
							"Stock Order has been approved successfully");
					request.getRequestDispatcher(
							"/pages/so/stock-approval-success.jsp").forward(
							request, response);
				} else if (action.equalsIgnoreCase("protect")) {
					String[] protect = (String[]) request
							.getParameterValues("approved");
					PreparedStatement pstmt = null;
					for (@SuppressWarnings("unused")
					String str : protect)
						try {

							int i = 0;
							while (protect.length > i) {
								Connection conn = Connector.getInstance()
										.getConnection();
								pstmt = conn
										.prepareStatement(User.Factory.STMT_INSERT_USER_AUDIT);
								UserSession userSession = UserSession.Factory
										.getUserSession(request);
								pstmt.setString(1, protect[i].split(";")[1]);
								pstmt.setString(2, request.getRemoteAddr());
								pstmt.setString(3,
										"Protect order with NO SO SMS "
												+ protect[i].split(";")[1]
												+ " by "
												+ userSession.getUser()
														.getUsername());
								pstmt.executeUpdate();
								pstmt.close();

								conn = Connector.getInstance()
										.getConnectionAdmin();
								pstmt = conn.prepareStatement(approveOrder);
								pstmt.setString(1, protect[i].split(";")[0]);
								pstmt.setString(2, protect[i].split(";")[1]);
								pstmt.setString(3, "0");
								pstmt.setString(4, "0");
								pstmt.setString(5, "1");
								pstmt.executeUpdate();
								pstmt.close();
								i++;
							}

						} catch (DaoException ex) {
							logger.error(ex);
						} catch (NamingException ex) {
							logger.error(ex);
						} catch (SQLException ex) {
							logger.error(ex);
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							try {
								if (pstmt != null)
									pstmt.close();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					request.setAttribute("approvedData", protect);
					request.setAttribute("message",
							"Stock Order has been protected successfully");
					request.getRequestDispatcher(
							"/pages/so/stock-approval-success.jsp").forward(
							request, response);
				} else if (action.equalsIgnoreCase("cancel")) {
					String[] cancel = (String[]) request
							.getParameterValues("approved");
					PreparedStatement pstmt = null;
					for (@SuppressWarnings("unused")
					String str : cancel)
						try {

							int i = 0;
							while (cancel.length > i) {
								Connection conn = Connector.getInstance()
										.getConnection();
								pstmt = conn
										.prepareStatement(User.Factory.STMT_INSERT_USER_AUDIT);
								UserSession userSession = UserSession.Factory
										.getUserSession(request);
								pstmt.setString(1, cancel[i].split(";")[1]);
								pstmt.setString(2, request.getRemoteAddr());
								pstmt.setString(3,
										"Cancel order with NO SO SMS "
												+ cancel[i].split(";")[1]
												+ " by "
												+ userSession.getUser()
														.getUsername());
								pstmt.executeUpdate();
								pstmt.close();

								conn = Connector.getInstance()
										.getConnectionAdmin();
								pstmt = conn.prepareStatement(approveOrder);
								pstmt.setString(1, cancel[i].split(";")[0]);
								pstmt.setString(2, cancel[i].split(";")[1]);
								pstmt.setString(3, "1");
								pstmt.setString(4, "0");
								pstmt.setString(5, "0");
								pstmt.executeUpdate();
								pstmt.close();
								i++;
							}

						} catch (DaoException ex) {
							logger.error(ex);
						} catch (NamingException ex) {
							logger.error(ex);
						} catch (SQLException ex) {
							logger.error(ex);
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							try {
								if (pstmt != null)
									pstmt.close();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					request.setAttribute("approvedData", cancel);
					request.setAttribute("message",
							"Stock Order has been cancelled successfully");
					request.getRequestDispatcher(
							"/pages/so/stock-approval-success.jsp").forward(
							request, response);
				}
			} else {

				String tanggaSOAwal = request.getParameter("tanggal_so_awal");
				String tanggaSOAkhir = request.getParameter("tanggal_so_akhir");
				String nomorSO = request.getParameter("nomor_so");
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try {
					int total_pages = -1;
					Connection conn = Connector.getInstance()
							.getConnectionAdmin();
					pstmt = conn.prepareStatement(tableQueryCount);
					pstmt.setString(1, nomorSO);
					pstmt.setString(2, (tanggaSOAwal == null || tanggaSOAwal
							.equals("")) ? sdf.format(new Date(System.currentTimeMillis()+86400000))
							: tanggaSOAwal);
					pstmt.setString(3, (tanggaSOAkhir == null || tanggaSOAkhir
							.equals("")) ? sdf.format(new Date(System.currentTimeMillis()+86400000))
							: tanggaSOAkhir);
					rs = pstmt.executeQuery();
					if (rs.next())
						total_pages = rs.getInt(1);
					rs.close();
					pstmt.close();

					conn = Connector.getInstance().getConnectionAdmin();
					pstmt = conn.prepareStatement("select * from ("
							+ tableQuery
							+ ") as tbl where tbl.ROWNUMBER between ? and ?");
					pstmt.setString(1, nomorSO);
					pstmt.setString(2, (tanggaSOAwal == null || tanggaSOAwal
							.equals("")) ? sdf.format(new Date(System.currentTimeMillis()+86400000))
									: tanggaSOAwal);
					pstmt.setString(3, (tanggaSOAkhir == null || tanggaSOAkhir
							.equals("")) ? sdf.format(new Date(System.currentTimeMillis()+86400000))
									: tanggaSOAkhir);
					pstmt.setInt(4, 0);
					pstmt.setInt(5, 10);
					rs = pstmt.executeQuery();

					ArrayList<String> tableColumn = new ArrayList<String>();
					ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
					int totalColumn = rs.getMetaData().getColumnCount();
					for (int i = 1; i < totalColumn; i++)
						tableColumn.add(rs.getMetaData().getColumnName(i));
					request.setAttribute("tableColumn", tableColumn);
					while (rs.next()) {
						ArrayList<String> rowData = new ArrayList<String>();
						for (int i = 1; i < totalColumn; i++) {
							rowData.add(rs.getString(i));
						}
						tableData.add(rowData);
					}
					request.setAttribute("tableData", tableData);
					request.setAttribute(
							"total_pages",
							Integer.toString((total_pages / 10)
									+ ((total_pages % 10) > 0 ? 1 : 0)));
				} catch (DaoException ex) {
					ex.printStackTrace();
					// logger.error(ex);
				} catch (NamingException ex) {
					ex.printStackTrace();
					// logger.error(ex);
				} catch (SQLException ex) {
					ex.printStackTrace();
					// logger.error(ex);
				} catch (Exception e) {
					e.printStackTrace();
				}
				request.getRequestDispatcher("/pages/so/stock-approval.jsp")
						.forward(request, response);

			}
		}
	}

}
