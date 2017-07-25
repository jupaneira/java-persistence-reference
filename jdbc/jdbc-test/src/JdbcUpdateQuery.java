import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class JdbcUpdateQuery {

	public static void main(String[] args) throws SQLException {

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		String dbUrl = "jdbc:mysql://localhost:3306/demo?useSSL=false";
		String user = "student";
		String pass = "student";

		try {
			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.createStatement();

			System.out.println("BEFORE THE UPDATE..");
			displayEmployee(myConn, "John", "Doe");

			int rowsAffected = myStmt.executeUpdate(
					"update employees set email='john@yeye.com' where last_name='Doe' and first_name='John'");

			System.out.println("AFTER THE UPDATE..");
			displayEmployee(myConn, "John", "Doe");

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}

	}

	private static void displayEmployee(Connection myConn, String firstName, String lastName) throws SQLException {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.prepareStatement(
					"select last_name, first_name, email from employees where first_name=? and last_name=?");

			myStmt.setString(1, firstName);
			myStmt.setString(2, lastName);

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				String theLastName = myRs.getString("last_name");
				String theFirstName = myRs.getString("first_name");
				String email = myRs.getString("email");

				System.out.printf("%s %s, %s\n", theLastName, theFirstName, email);
			}

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myStmt, myRs);
		}
	}

	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) throws SQLException {
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

	private static void close(Statement myStmt, ResultSet myRs) throws SQLException {
		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {
			myStmt.close();
		}
	}

}
