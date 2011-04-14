package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import common.Util;

public class QueryExecutor {

	private final String selectQueryById = "select query,type from querylist where id = ?";
	private String query;
	private int queryType;

	public QueryExecutor(int queryId) throws Exception {
		Connection conn = Connector.getInstance().getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(selectQueryById);
			ps.setInt(1, queryId);
			rs = ps.executeQuery();
			if (rs.next()) {
				this.query = rs.getString(1);
				this.queryType = rs.getInt(2);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();

		}
	}

	public ArrayList<String[]> executeQuery(Object[] parameters)
			throws Exception {
		Connection conn = Connector.getInstance().getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			int counter = 1;
			for (Object o : parameters)
				ps.setObject(counter++, o);
			if (queryType == 0) {
				rs = ps.executeQuery();
				return convert(rs);
			} else {
				ps.executeUpdate();
				return null;
			}
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();

		}
	}

	private ArrayList<String[]> convert(ResultSet rs) throws Exception {
		ArrayList<String[]> result = new ArrayList<String[]>();
		while (rs.next()) {
			String[] element = new String[rs.getMetaData().getColumnCount()];
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
				element[i - 1] = rs.getString(i);
			result.add(element);
		}
		return result;
	}

	public static void main(String[] args) throws Exception {		
		
//		ArrayList<String[]> res = Util.sendQuery("localhost", 9876, 1, new String[0]);
//
//		for (String[] stringList : res) {
//			for (String string : stringList)
//				System.out.print(string + "|");
//			System.out.println("");
//		}
//		
//		res = Util.sendQuery("localhost", 9876, 1, new String[0]);
//
//		for (String[] stringList : res) {
//			for (String string : stringList)
//				System.out.print(string + "|");
//			System.out.println("");
//		}
//		
//		res = Util.sendQuery("localhost", 9876, 2, new String[0]);
//
//		for (String[] stringList : res) {
//			for (String string : stringList)
//				System.out.print(string + "|");
//			System.out.println("");
//		}
//		
//		res = Util.sendQuery("localhost", 9876, 4, new String[]{"3"});
//
//		for (String[] stringList : res) {
//			for (String string : stringList)
//				System.out.print(string + "|");
//			System.out.println("");
//		}				
	}	
}
