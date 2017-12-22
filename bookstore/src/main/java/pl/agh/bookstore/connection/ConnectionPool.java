package pl.agh.bookstore.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.pool.HikariPool;


public class ConnectionPool{

	private  Object _synch = new Object();
	private  HikariPool pool;
	
	@Autowired 
	@Qualifier("dbProperties")
	Properties dbProperties;
	
	private void init() {
		String urlDB = "postgres://jfascyoiwdswsv:51426c9dbb268b37ccd350e29751cbd3c01cdcde3bdbb15506a7b220d232d1a8@ec2-54-75-226-64.eu-west-1.compute.amazonaws.com:5432/dbl4gevc3lfv81"
		+ "?sslmode=required";
		System.out.println(urlDB);
		try {
				Class.forName("org.postgresql.Driver");

				String aa= dbProperties.get("maximumPoolSize").toString();
				System.err.println(aa);
				HikariConfig hconfig = new HikariConfig(dbProperties);
				hconfig.setAutoCommit(false);
				hconfig.setUsername("postgres");
				hconfig.setPassword("postgres");
				hconfig.setMinimumIdle(2);
				hconfig.setLeakDetectionThreshold(1000*60*6*1);
				hconfig.setMaxLifetime(1000*60*12);
				hconfig.setJdbcUrl(urlDB);
				pool = new HikariPool(hconfig);
				System.out.println();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	public Connection getConnectionPool() throws ConnectionPoolException, SQLException {
		if(pool == null) throw new ConnectionPoolException();
		
		synchronized(_synch) {
			Connection con = null;
			String urlDB = System.getenv("JDBC_DATABASE_URL");
			try {
				Class.forName("org.postgresql.Driver");
				con = pool.getConnection();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return con;
		}
	}

	public void close(Connection conn, Statement statement, ResultSet result) {
		//@formatter:off
		if(conn != null) try { conn.close(); } catch (SQLException e) {e.printStackTrace();}
		if(statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
		if(result != null) try { result.close(); } catch (SQLException e) {e.printStackTrace();}
		//@formatter:on
	}

	public void destroyed() {
		if(pool != null) {
			try {
				pool.shutdown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}