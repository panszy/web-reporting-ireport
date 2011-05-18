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

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.UserSession;

import database.Connector;
import exception.DaoException;

/**
 * Servlet implementation class StockOrder
 */
public class StockOrder extends HttpServlet {
	private final String comboTypeSOQuery = "select NAMA_TYPESO,KODE_TYPESO from \"DB2ADMIN\".tbmastypeso where KODE_TYPESO=2 or KODE_TYPESO=4";
	private final String comboJenisTransaksi = "select NAMA_TRN,KODE_TRN from \"DB2ADMIN\".tbmastrn where KODE_TRN=1 or KODE_TRN=3";
	private final String comboTipeTransaksi = "select NAMA_TYPEDO,KODE_TYPEDO from \"DB2ADMIN\".tbmastypedo where KODE_TYPEDO=1 or KODE_TYPEDO=2";
	private final String comboJenisObat = "select NAMA_JNSOBAT,KODE_JNSOBAT from \"DB2ADMIN\".tbmasjnsobat where KODE_JNSOBAT = 1 AND F_AKTIF =1";
	private final String simpanOrder = "call SPIUSO_SMS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private final String hapusOrder = "call SPDELSO_SMS(?,?,?)";
	private final String simpanOrderDetail = "call SPIUDTSO_SMS(?,?,?,?,?,?)";
	private final String hapusOrderDetail = "call SPDELDTSO_SMS(?,?)";

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
			while (rs.next()) {
				comboTypeSOQuery.add(rs.getString(1) + "," + rs.getString(2));
			}
			request.setAttribute("comboTypeSO", comboTypeSOQuery);
			rs.close();
			pstmt.close();

			conn = Connector.getInstance().getConnectionAdmin();
			pstmt = conn.prepareStatement(comboJenisTransaksi);
			rs = pstmt.executeQuery();
			ArrayList<String> comboJenisTransaksi = new ArrayList<String>();
			while (rs.next()) {
				comboJenisTransaksi
						.add(rs.getString(1) + "," + rs.getString(2));
			}
			request.setAttribute("comboJenisTransaksi", comboJenisTransaksi);
			rs.close();
			pstmt.close();

			conn = Connector.getInstance().getConnectionAdmin();
			pstmt = conn.prepareStatement(comboJenisObat);
			rs = pstmt.executeQuery();
			ArrayList<String> comboJenisObat = new ArrayList<String>();
			while (rs.next()) {
				comboJenisObat.add(rs.getString(1) + "," + rs.getString(2));
			}
			request.setAttribute("comboJenisObat", comboJenisObat);
			rs.close();
			pstmt.close();

			conn = Connector.getInstance().getConnectionAdmin();
			pstmt = conn.prepareStatement(comboTipeTransaksi);
			rs = pstmt.executeQuery();
			ArrayList<String> comboTipeTransaksi = new ArrayList<String>();
			while (rs.next()) {
				comboTipeTransaksi.add(rs.getString(1) + "," + rs.getString(2));
			}
			request.setAttribute("comboTipeTransaksi", comboTipeTransaksi);
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
				if (rs != null)
					rs.close();
				if (pstmt != null)
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

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd");
		String action = request.getParameter("Action");
		if (action.equalsIgnoreCase("Batal")) {
			response.sendRedirect(request.getContextPath()
					+ "/pages/stock-order-update?page=1&tanggal_so_awal="
					+ sdfOutput.format(new Date()) + "&tanggal_so_akhir="
					+ sdfOutput.format(new Date()) + "&nomor_so=");
		} else {
			String noSo = "";
			File f = new File("counter.donotdelete");
			if (f.exists()) {
				FileReader fr = new FileReader(f);
				char[] buffer = new char[1024];
				int length = fr.read(buffer);
				fr.close();
				noSo = String.copyValueOf(buffer, 0, length);
				long counter = Long.parseLong(String.copyValueOf(buffer, 0,
						length).substring(8));
				if (f.delete()) {
					String tempCounter = Long.toString(++counter);
					while (tempCounter.length() < 9)
						tempCounter = "0" + tempCounter;
					String newValue = "SM.5.11." + tempCounter;
					FileWriter fw = new FileWriter("counter.donotdelete");
					fw.write(newValue);
					fw.close();
				}
			} else {
				noSo = "SM.5.11.000000001";
				FileWriter fw = new FileWriter("counter.donotdelete");
				fw.write("SM.5.11.000000001");
				fw.close();
			}

			String tanggalSo = request.getParameter("tanggal_so");
			String noPo = request.getParameter("po");
			String tanggalPo = request.getParameter("tanggal_po");			
			String kodeTransaksi = request.getParameter("jenis_transaksi");			
			String tipeBayar = request.getParameter("tipe_bayar");
			String tipeDO = request.getParameter("tipe_transaksi");
			String catatan = request.getParameter("catatan");
			String tipeSO = request.getParameter("type_so");
			String konsinyasi = tipeSO.equals("4") ? "1" : "0";

			try {
				tanggalPo = sdfOutput.format(sdf.parse(tanggalPo));
				tanggalSo = sdfOutput.format(sdf.parse(tanggalSo));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String kodeCabang = UserSession.Factory.getUserSession(request)
					.getUser().getKodeCabang();
			String kodeCustomer = UserSession.Factory.getUserSession(request)
					.getUser().getKodeCustomer();
			
			String[] kodeBarang = request.getParameterValues("kodebarang");
			String[] qty = request.getParameterValues("qty");

			try {
				Connection conn = Connector.getInstance().getConnectionAdmin();
				pstmt = conn.prepareStatement(simpanOrder);
				pstmt.setString(1, kodeCabang);
				pstmt.setString(2, kodeTransaksi);
				pstmt.setString(3, kodeCustomer);
				pstmt.setString(4, "9999");
				pstmt.setString(5, tipeSO);
				pstmt.setString(6, tipeDO);
				pstmt.setString(7, tanggalSo);
				pstmt.setString(8, noSo);
				pstmt.setString(9, tanggalPo);
				pstmt.setString(10, "PO." + noPo);
				pstmt.setString(11, tipeBayar.equals("tunai") ? "0" : "1");
				pstmt.setString(12, "0");
				pstmt.setString(13, konsinyasi);
				pstmt.setString(14, "0");
				pstmt.setString(15, "0");
				pstmt.setString(16, catatan);
				pstmt.setInt(17, 0);
				pstmt.executeUpdate();
				pstmt.close();

				for(int cnt = 0; cnt < qty.length ; cnt++){
					conn = Connector.getInstance().getConnectionAdmin();
					pstmt = conn.prepareStatement(simpanOrderDetail);
					pstmt.setString(1, kodeCabang);
					pstmt.setString(2, noSo);
					pstmt.setInt(3, (cnt+1));
					pstmt.setString(4, kodeBarang[cnt]);
					pstmt.setString(5, qty[cnt]);
					pstmt.setString(6, qty[cnt]);
					pstmt.executeUpdate();
					pstmt.close();
				}

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
					if (rs != null)
						rs.close();
					if (pstmt != null)
						pstmt.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			request.setAttribute("no_so", noSo);
			request.setAttribute("tanggal_so", tanggalSo);
			request.setAttribute("no_po", noPo);
			request.setAttribute("tanggal_po", tanggalPo);
			request.setAttribute("kode_barang", kodeBarang);
			request.setAttribute("quantity", qty);
			request.setAttribute("tipe_bayar", tipeBayar);
			request.setAttribute("catatan", catatan);
			request.setAttribute("message",
					"Stock has been ordered successfully");
			request.getRequestDispatcher("/pages/so/stock-order-success.jsp")
					.forward(request, response);
		}
	}

}
