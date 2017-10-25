package pl.agh.engine.db;

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
		String aa= dbProperties.get("maximumPoolSize").toString();
		System.err.println(aa);
		HikariConfig hconfig = new HikariConfig(dbProperties);
		hconfig.setAutoCommit(false);
		hconfig.setMinimumIdle(2);
		hconfig.setLeakDetectionThreshold(1000*60*6*1);
		hconfig.setMaxLifetime(1000*60*12);
		pool = new HikariPool(hconfig);
	}

	public Connection getConnectionPool() throws ConnectionPoolException, SQLException {
		if(pool == null) throw new ConnectionPoolException();
		
		synchronized(_synch) {
			Connection con = pool.getConnection();
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