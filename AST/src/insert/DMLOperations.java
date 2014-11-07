package insert;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

/**
 * 
 * @author xiyao
 * 
 */

public class DMLOperations {
	static ResultSet rs;
	static Statement stmt;
	static PreparedStatement pstmt;
	static String desc_sql;
	static String SQL_SELECT_ALL = "select * from comboimages";
	private DBConnection con;

	public DBConnection getCon() {
		return this.con;
	}

	public Properties LoadConnectProperties() {
		String propfile = "conf/connect.properties";
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(propfile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}

	public void close() {
		this.con.close();
	}

	public void closeStatement() {
		this.con.closeStatement();
	}

	public void closeResultSet() {
		this.con.closeResultSet();
	}

	public DMLOperations() {
		Properties prop = this.LoadConnectProperties();
		DBConnection con = new DBConnection(prop.getProperty("url"),
				prop.getProperty("user"), prop.getProperty("passwd"));
		this.con = con;
	}

	public ArrayList<String> dml() {
		String SELECT_ALL_TABLE = "select * from user_tables";
		ArrayList<String> results = new ArrayList<String>();
//		ArrayList<String> insert_sqls = new ArrayList<String>();
		String table_name;
		try {
			rs = con.executeQuery(SELECT_ALL_TABLE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
                table_name = rs.getString("table_name");
				results.add("select * from " + table_name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	public static void main(String[] args) throws SQLException,
			ClassNotFoundException, IOException {
		DMLOperations dmlo = new DMLOperations();
		// dmlo.dml(SQL_SELECT_ALL);
		// dmlo.dml("DESC BASEIMAGES");
		dmlo.getCon().execute(SQL_SELECT_ALL);

		dmlo.close();

	}

}
