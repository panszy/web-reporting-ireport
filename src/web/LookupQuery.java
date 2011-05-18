package web;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.DaoException;

public class LookupQuery {

	final public static String userQuery = "select user_id, username, full_name, nik, status, failed_logins, password_expiry,email_address,department,division,address,user.group, ROW_NUMBER() OVER(ORDER BY user_id DESC) AS ROWNUMBER  from user";
	final public static String userQueryFilter = "user.user_id, user.username, user.full_name, user.nik, user.status, user.failed_logins, user.password_expiry,user.email_address,user.department,user.division,user.address,user.group";
	final public static String userQueryCount = "select count(1) from user";
	final public static String userAuditQuery = "select user_audit.user,ip_address,date_time,action, ROW_NUMBER() OVER(ORDER BY user DESC) AS ROWNUMBER  from user_audit";
	final public static String userAuditQueryFilter = "user_audit.user,user_audit.ip_address,user_audit.date_time,user_audit.action";
	final public static String userAuditQueryCount = "select count(1) from user_audit";
	final public static String kodeBarangQuery = "select \"DB2ADMIN\".TBMASBAR.kode_bar as \"Kode Barang\",\"DB2ADMIN\".TBMASBAR.nama_bar as \"Nama Barang\", \"DB2ADMIN\".tbmaspab.nama_pab as \"Pabrik\", \"DB2ADMIN\".TBMASBAR.satuan_bar, \"DB2ADMIN\".TBMASBAR.kemasan_bar, ROW_NUMBER() OVER(ORDER BY \"DB2ADMIN\".TBMASBAR.kode_bar DESC) AS ROWNUMBER from \"DB2ADMIN\".TBMASBAR,\"DB2ADMIN\".tbmaspab where \"DB2ADMIN\".tbmaspab.kode_pab = \"DB2ADMIN\".TBMASBAR.kode_pab";	
	final public static String kodeBarangQueryFilter = "\"DB2ADMIN\".TBMASBAR.kode_bar,\"DB2ADMIN\".TBMASBAR.nama_bar,\"DB2ADMIN\".tbmaspab_sms.nama_pab";
	final public static String kodeBarangQueryCount = "select count(1) from \"DB2ADMIN\".TBMASBAR,\"DB2ADMIN\".tbmaspab where \"DB2ADMIN\".tbmaspab.kode_pab = \"DB2ADMIN\".TBMASBAR.kode_pab";
	final public static String kodeSalesmanQuery = "select \"DB2ADMIN\".TBMASBAR.nama_bar as \"Nama Barang\",\"DB2ADMIN\".TBMASBAR.kode_bar as \"Kode Barang\", ROW_NUMBER() OVER(ORDER BY \"DB2ADMIN\".TBMASBAR.kode_bar DESC) AS ROWNUMBER from \"DB2ADMIN\".TBMASBAR";
	final public static String kodeSalesmanQueryFilter = "\"DB2ADMIN\".TBMASBAR.nama_bar,\"DB2ADMIN\".TBMASBAR.kode_bar";
	final public static String kodeSalesmanQueryCount = "select count(1) from \"DB2ADMIN\".TBMASBAR";
	final public static String kodeSOQuery = "select \"DB2ADMIN\".TBSO_SMS.no_so_sms as \"No SO SMS\",\"DB2ADMIN\".TBSO_SMS.tgl_so_sms as \"Tgl SO SMS\",\"DB2ADMIN\".TBSO_SMS.no_po as \"No PO\",\"DB2ADMIN\".TBSO_SMS.tgl_po as \"Tgl PO\", ROW_NUMBER() OVER(ORDER BY \"DB2ADMIN\".TBSO_SMS.tgl_so_sms DESC) AS ROWNUMBER from \"DB2ADMIN\".TBSO_SMS where \"DB2ADMIN\".TBSO_SMS.F_SOBATAL=0 and \"DB2ADMIN\".TBSO_SMS.F_APPCAB=0 and \"DB2ADMIN\".TBSO_SMS.F_APPPROTEK=0 and \"DB2ADMIN\".TBSO_SMS.KODE_CAB=? and \"DB2ADMIN\".TBSO_SMS.KODE_CUST=? ";
	final public static String kodeSOQueryFilter = "\"DB2ADMIN\".TBSO_SMS.no_so_sms,\"DB2ADMIN\".TBSO_SMS.tgl_so_sms,\"DB2ADMIN\".TBSO_SMS.no_po,\"DB2ADMIN\".TBSO_SMS.tgl_po";
	final public static String kodeSOQueryCount = "select count(1) from \"DB2ADMIN\".TBSO_SMS where \"DB2ADMIN\".TBSO_SMS.F_SOBATAL=0 and \"DB2ADMIN\".TBSO_SMS.F_APPCAB=0 and \"DB2ADMIN\".TBSO_SMS.F_APPPROTEK=0  and \"DB2ADMIN\".TBSO_SMS.KODE_CAB=? and \"DB2ADMIN\".TBSO_SMS.KODE_CUST=? ";

	public static int countNumberLike(Connection conn, int filter,
			String field, String STMT_QUERY, String[] parameters) throws DaoException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Field f = LookupQuery.class.getDeclaredField(STMT_QUERY + "Count");
			String query = (String) f.get(LookupQuery.class);
			f = LookupQuery.class.getDeclaredField(STMT_QUERY + "Filter");
			String[] filters = ((String) f.get(LookupQuery.class)).split(",");
			if (query.indexOf("where") > 0)
				stmt = conn.prepareStatement(query + " and "
						+ filters[filter] + " like '%" + field + "%'");
			else
				stmt = conn.prepareStatement(query + " where "
						+ filters[filter] + " like '%" + field + "%'");
			if(parameters!=null)
				for(int counter = 0;counter <  parameters.length;counter++)
					stmt.setString(counter+1, parameters[counter]);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				throw new DaoException("Error retrieving count of users");
			}
		} catch (Exception ex) {
			throw new DaoException(ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static List<ArrayList<String>> listlike(Connection conn,
			int filter, String field, int start, int rows,
			String STMT_QUERY, String showedField, String[] parameters) throws DaoException {

		List<ArrayList<String>> datas = new ArrayList<ArrayList<String>>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Field f = LookupQuery.class.getDeclaredField(STMT_QUERY);
			String query = (String) f.get(LookupQuery.class);
			f = LookupQuery.class.getDeclaredField(STMT_QUERY + "Filter");
			String[] filters = ((String) f.get(LookupQuery.class)).split(",");
			if ((start == -1) || (rows == -1)) {
				stmt = conn.prepareStatement(query);
				int counter = 1;
				if(parameters!=null)
					while(counter <  parameters.length){
						stmt.setString(counter, parameters[counter-1]);
						counter++;
					}
			} else {
				if (query.indexOf("where") > 0)
					stmt = conn.prepareStatement("select * from (" + query
							+ " and " + filters[filter] + " like '%"
							+ field
							+ "%') as tbl where tbl.ROWNUMBER between ? and ?");
				else
					stmt = conn.prepareStatement("select * from (" + query
							+ " where " + filters[filter] + " like '%"
							+ field
							+ "%') as tbl where tbl.ROWNUMBER between ? and ?");
				int counter = 1;
				if(parameters!=null)
					while(counter <=  parameters.length){						
						stmt.setString(counter, parameters[counter-1]);
						counter++;
					}
				stmt.setInt(counter++, start);
				stmt.setInt(counter, start + rows);
			}
			rs = stmt.executeQuery();

			while (rs.next()) {
				ArrayList<String> data = new ArrayList<String>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {										
						data.add(rs.getString(i));
				}
				datas.add(data);
			}

			return datas;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DaoException(ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
