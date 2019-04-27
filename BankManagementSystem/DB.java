import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DB {
	ResultSet rec;
	Statement st;

	public DB() {
		try {
		Class.forName("com.mysql.cj.jdbc.Driver"); //load the driver
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:8889/bank","root","012345"); //connect to db
		st = con.createStatement();

		}catch (Exception e) {
			System.out.println(e.toString());
			System.out.println("Ops.. Something went wrong buddy. Try again");
		}
	}
	
	public Statement getStatement() {
		return st;
	}

}