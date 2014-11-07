package insert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * 
 * @author xiyao
 * 
 */

public class DBConnection {
	private static Connection dbconn = null;
	public static Connection getDbconn() {
		return dbconn;
	}

	private static Statement stmt = null;
	private static PreparedStatement pstmt = null;
	private static ResultSet rs = null;
	private String connect_string;
	private String username;
	private String password;
	
	

	//
	public DBConnection(String s1, String s2, String s3) {
		connect_string = s1;
		username = s2;
		password = s3;
	}

	private void conn() throws IOException, ClassNotFoundException {
		try {
			if ((dbconn == null) || dbconn.isClosed())
				this.open();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// return dbconn;
	}

	public void open() throws SQLException {
		/* Register the driver */
		System.out.print("Register driver ... ");
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		System.out.println("Succeed.");

		/* Connect to the database */
		System.out.print("Connect to database: " + connect_string + " ... ");
		try {
			dbconn = DriverManager.getConnection(connect_string, username,
					password);
			dbconn.setAutoCommit(false);
			System.out.println("Done.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (dbconn == null)
				System.out.println("Failed.");
		}
	}

	public ResultSet executeQuery(String sql) throws SQLException {

		try {
			conn();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		stmt = dbconn.createStatement();

		pstmt = dbconn.prepareStatement(sql);

		ResultSet rs = pstmt.executeQuery();

		return rs;
	}

	public HashMap<String, String> execute(String sql) throws SQLException {
		HashMap<String, String> columns = new HashMap<String,String>();
		
		try {
			conn();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		stmt = dbconn.createStatement();

		pstmt = dbconn.prepareStatement(sql);

		ResultSet rs = pstmt.executeQuery();

		ResultSetMetaData md = rs.getMetaData();

		for (int i = 1; i <= md.getColumnCount(); i++) {
			System.out.println(md.getColumnName(i) + "---"
					+ md.getColumnTypeName(i) + "\n");
			
			columns.put(md.getColumnName(i), md.getColumnTypeName(i));
		}

		return columns;
	}

	void close() {
		closeStatement();
		closeResultSet();
		closeConnection();
	}

	// 封装方法关闭语句对象
	void closeStatement() {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stmt = null;
		}
	}

	// 封装方法关闭结果集对象
	void closeResultSet() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rs = null;
		}
	}

	// 封装方法关闭数据库连接对象
	private void closeConnection() {
		try {
			if (dbconn != null && !dbconn.isClosed()) {
				dbconn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbconn = null;
	}
}
