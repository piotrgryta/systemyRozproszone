package pl.agh.bookstore.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DButil {
	Connection connection = null;
	ResourceBundle db_param;
	
	public static Connection getConnection() throws SQLException {
		String dbUrl = System.getenv("JDBC_DATABASE_URL");		
		if (dbUrl == null)
			try {
				Class.forName("org.postgresql.Driver");
				//dbUrl = "jdbc:postgresql://ec2-54-75-226-64.eu-west-1.compute.amazonaws.com:5432/dbl4gevc3lfv81"
				//		+ "?user=jfascyoiwdswsv&password=51426c9dbb268b37ccd350e29751cbd3c01cdcde3bdbb15506a7b220d232d1a8"
				//		+ "&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
				//System.out.println(dbUrl);
				//return DriverManager.getConnection(dbUrl);
				String serverName = "localhost:5433";
				String myDatabase = "postgres";
				String url = "jdbc:postgresql://" + serverName + "/" + myDatabase;
				return DriverManager.getConnection(url, "postgres", "postgres");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return DriverManager.getConnection(dbUrl);
	}
}
