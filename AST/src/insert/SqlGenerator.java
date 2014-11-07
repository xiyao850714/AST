package insert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

/**
 * 
 * @author xiyao
 * 
 */

public class SqlGenerator {
	public void GetInsertSql() {
		
		String insert_sql,table_name,values="";
		ArrayList<String> results;
		HashMap<String, String> column;
		DMLOperations dmlo = new DMLOperations();
		results = dmlo.dml();
		
		try {
			for (String result : results) {
				/* get table name from select * from table_name */
				table_name = result.split(" ")[3];
				
				if(!table_name.equals("COMBOIMAGES1T")) continue;
				column = dmlo.getCon().execute(result);
				String column_all = column.keySet().toString();
				values = "";
	            for ( int j = 0 ; j < column.keySet().toString().split(",").length; j++){
	            	values += "?,";
	            }
				insert_sql = "insert into " + table_name + " (" + column_all.substring(1, column_all.length()-1) + ") " + "values (" + values.substring(0, values.length()-1) + ")";
				System.out.println(insert_sql);
				
				dmlo.getCon();
				PreparedStatement statement = DBConnection.getDbconn().prepareStatement(insert_sql);
				
				int i = 1;
				for (String key : column.keySet()) {
					String value = null;
					switch (column.get(key)) {
					case "NUMBER":
						int p = new Random().nextInt(38);
						p = p == 0? 1 : p;
						int c = new Random().nextInt(p);
						BigDecimal b = newRandomBigDecimal(new Random(), p, c);
						statement.setBigDecimal(i, b);
						break;
					case "DATE":
						Calendar cal = Calendar.getInstance();
					    Date nowDate = new Date(cal.getTimeInMillis());
						statement.setDate(i, nowDate);
						break;
					case "CHAR":
						try {
							value = UnicodeGenerator.test();
							statement.setString(i, "a");
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						break;
					case "NVARCHAR2":
					case "VARCHAR2":
						try {
							value = UnicodeGenerator.test();
							statement.setString(i, value);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case "BLOB":
//						String fileName = UnicodeGenerator.test();
//						String clobData = getClobDataAsString(fileName);
						String inputTextFileName = "D:\\Downloads\\Ave Maria.mp3"; // get the file location from properties.
						InputStream inputStream = new FileInputStream(inputTextFileName);
						statement.setBlob(i, inputStream);
						//statememt.setString(i, "empty_blob()");
						break;
					case "CLOB":
//						String fileName = UnicodeGenerator.test();
//						String clobData = getClobDataAsString(fileName);
						String inputTextFileName1 = "conf/my.properties"; // get the file location from properties.
						Reader reader = new FileReader(inputTextFileName1);
						statement.setClob(i, reader);
						//statememt.setString(i, "empty_blob()");
						break;
					}
					i++;
				}
				i = 1;
				statement.executeUpdate(); //execute the sql
				dmlo.getCon().getDbconn().commit();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		dmlo.close();
	}
	
	public static BigDecimal newRandomBigDecimal(Random r, int precision, int scale) {
	    BigInteger n = BigInteger.TEN.pow(precision);
	    return new BigDecimal(newRandomBigInteger(n, r), scale);
	}

	private static BigInteger newRandomBigInteger(BigInteger n, Random rnd) {
	    BigInteger r;
	    do {
	        r = new BigInteger(n.bitLength(), rnd);
	    } while (r.compareTo(n) >= 0);

	    return r;
	}

	public static void main(String[] args) {
		
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
	    String now =  sdf.format(cal.getTime());
	    Date nowDate = new Date(cal.getTimeInMillis());
		
		for(int i = 0; i < 100; i++)
		{
		int p = new Random().nextInt(38);
		p = p == 0? 1 : p;
		int c = new Random().nextInt(p);
		
		BigDecimal b = newRandomBigDecimal(new Random(), p, c);
		System.out.println(b);
		}
		SqlGenerator sg = new SqlGenerator();
		sg.GetInsertSql();
	}
}
