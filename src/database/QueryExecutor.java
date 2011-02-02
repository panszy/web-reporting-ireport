package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class QueryExecutor {
	
	private final String selectQueryById = "Select query,type from querylist where id = ?";
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
			this.query = rs.getString(1);
			this.queryType = rs.getInt(2);
		} finally {
			if(rs != null)
				rs.close();
			if(ps != null)
				ps.close();
			Connector.getInstance().putConnection(conn);
		}
	}
	
	public ArrayList<String[]> executeQuery(Object[] parameters) throws Exception {
		Connection conn = Connector.getInstance().getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			int counter = 1;
			for(Object o: parameters)
				ps.setObject(counter++, o);
			if(queryType == 0){
				rs = ps.executeQuery();
				return convert(rs);
			} else {
				ps.executeUpdate();
				return null;
			}
		} finally {
			if(rs != null)
				rs.close();
			if(ps != null)
				ps.close();
			Connector.getInstance().putConnection(conn);
		}				
	}
	
	private ArrayList<String[]> convert(ResultSet rs) throws Exception {
		ArrayList<String[]> result = new ArrayList<String[]>();
		while(rs.next()){
			String[] element = new String[rs.getFetchSize()];
			for(int i = 1; i <= rs.getFetchSize(); i++)
				element[i-1] = rs.getString(i);
			result.add(element);
		}
		return result;
	}

	public static void main(String[] args)  throws Exception {
		new Connector("db2admin", "password", "localhost", 50000, 1, 10);
		QueryExecutor q = new QueryExecutor(1);
		System.out.println(q.executeQuery(new Object[0]));
	}
}
