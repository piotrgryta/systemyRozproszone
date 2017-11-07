package pl.agh.library.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import pl.agh.library.connection.ConnectionPool;
import pl.agh.library.security.model.LoginResults;
import pl.agh.library.security.model.User;
import pl.agh.library.security.model.UserTypes;

public class UserServiceImpl implements UserService{

	@Autowired
	ConnectionPool connectionPool;
	
	@Override
	public LoginResults logUser(String username, String password) throws Exception {

		String query = "Select * from users.users where username = ? and active = true";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = connectionPool.getConnectionPool();
			ps = con.prepareStatement(query);
			ps.setString(1, username);

			rs = ps.executeQuery();
			User user = null;
			while (rs.next()) {
				user = new User();
				user.setPassword(rs.getString("password"));
			}

			if (user == null) {
				return LoginResults.NO_SUCH_USER;

			} else {
				if (!user.getPassword().equals(password)) {
					return LoginResults.INVALID_PASSWORD;
				} else {
					return LoginResults.SUCCESS;
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			connectionPool.close(con, ps, rs);
		}

	}

	@Override
	public User loadUserByEmail(String email) {

		User user = null;
		String query = "Select * from users.users  where email = ? ";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = connectionPool.getConnectionPool();
			ps = con.prepareStatement(query);
			ps.setString(1, email);
			rs = ps.executeQuery();

			while (rs.next()) {
				user = new User();
				user.setObjectid(rs.getInt("objectid"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setType(UserTypes.DEFAULT.getUserType(rs.getString("role")));
				user.setActive(rs.getBoolean("active"));
			}

			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			connectionPool.close(con, ps, rs);
		}	  
	}

	@Override
	public User getUser() {
		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (user instanceof User){
			return (User) user;
		}
		return null;
	}

	@Override
	public void addUser(User user) {
		String query = "INSERT INTO users.users (username, email, password, role, active)"
				+ " VALUES (?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = connectionPool.getConnectionPool();
			ps = con.prepareStatement(query);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, "ROLE_" + UserTypes.USER.name());
			ps.setBoolean(5, true);
			ps.executeUpdate();
			con.commit();
		} catch (Exception e) {

			e.printStackTrace();
			connectionPool.close(con, ps, rs);
		} finally {
			connectionPool.close(con, ps, rs);
		}
	}

}
