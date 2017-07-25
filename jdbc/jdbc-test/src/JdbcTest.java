import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class JdbcTest {

	public static void main(String[] args) throws SQLException {

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		String dbUrl = "jdbc:mysql://localhost:3306/demo?useSSL=false";
		String user = "student";
		String pass = "student";

		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection(dbUrl, user, pass);

			// 2. Create a statement
			myStmt = myConn.createStatement();

			// 3. Execute SQL Query
			myRs = myStmt.executeQuery("select * from employees");

			// 4. Process the ResultSet
			while (myRs.next()) {
				System.out.println(myRs.getString("last_name") + ", " + myRs.getString("first_name"));
			}

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			if (myRs != null) {
				myRs.close();
			}

			if (myStmt != null) {
				myStmt.close();
			}

			if (myConn != null) {
				myConn.close();
			}
		}

	}

}
