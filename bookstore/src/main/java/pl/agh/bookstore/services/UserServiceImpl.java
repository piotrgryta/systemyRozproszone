package pl.agh.bookstore.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.security.core.context.SecurityContextHolder;

import pl.agh.bookstore.connection.DButil;
import pl.agh.bookstore.model.dbtables.UsersTable;
import pl.agh.bookstore.security.model.LoginResults;
import pl.agh.bookstore.security.model.User;
import pl.agh.bookstore.security.model.UserTypes;

public class UserServiceImpl implements UserService{

	@Override
	public LoginResults logUser(String username, String password) throws Exception {
		StringBuilder query = new StringBuilder("Select * from users.users where ");
		query.append(UsersTable.USERNAME.getColumnName() + " = ? and ");
		query.append(UsersTable.ACTIVE.getColumnName() + " = true");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DButil.getConnection();
			ps = con.prepareStatement(query.toString());
			ps.setString(1, username);
			rs = ps.executeQuery();
			User user = null;
			while (rs.next()) {
				user = new User();
				user.setPassword(rs.getString(UsersTable.PASSWORD.getColumnName()));
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
			con = null; rs = null; ps = null;
		}
	}

	@Override
	public User loadUserByEmail(String email) {
		User user = null;
		StringBuilder query = new StringBuilder("Select * from users.users where ");
		query.append(UsersTable.EMAIL.getColumnName() + " = ?");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DButil.getConnection();
			ps = con.prepareStatement(query.toString());
			ps.setString(1, email);
			rs = ps.executeQuery();

			while (rs.next()) {
				user = new User();
				user.setObjectid(rs.getInt(UsersTable.USER_ID.getColumnName()));
				user.setUsername(rs.getString(UsersTable.USERNAME.getColumnName()));
				user.setEmail(rs.getString(UsersTable.EMAIL.getColumnName()));
				user.setPassword(rs.getString(UsersTable.PASSWORD.getColumnName()));
				user.setType(UserTypes.DEFAULT.getUserType(rs.getString(UsersTable.ROLE.getColumnName())));
				user.setActive(rs.getBoolean(UsersTable.ACTIVE.getColumnName()));
			}
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			con = null;	rs = null; ps = null;
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
		StringBuilder query = new StringBuilder("INSERT INTO users.users(");
		query.append(UsersTable.USERNAME.getColumnName() + ",");
		query.append(UsersTable.EMAIL.getColumnName() + ",");
		query.append(UsersTable.PASSWORD.getColumnName() + ",");
		query.append(UsersTable.ROLE.getColumnName() + ",");
		query.append(UsersTable.ACTIVE.getColumnName() + ") ");
		query.append("VALUES(?, ?, ?, ?, ?)");

		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DButil.getConnection();
			ps = con.prepareStatement(query.toString());
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, "ROLE_" + UserTypes.USER.name());
			ps.setBoolean(5, true);
			ps.executeUpdate();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con = null;	ps = null;
		}
	}

}
