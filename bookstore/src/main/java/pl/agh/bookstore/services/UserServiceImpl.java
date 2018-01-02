package pl.agh.bookstore.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

import pl.agh.bookstore.connection.DButil;
import pl.agh.bookstore.model.Order;
import pl.agh.bookstore.model.OrderDetails;
import pl.agh.bookstore.model.dbtables.OrderDetailsTable;
import pl.agh.bookstore.model.dbtables.OrdersTable;
import pl.agh.bookstore.model.dbtables.UsersTable;
import pl.agh.bookstore.security.model.LoginResults;
import pl.agh.bookstore.security.model.User;
import pl.agh.bookstore.security.model.UserTypes;

public class UserServiceImpl implements UserService {

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
			con = null;
			rs = null;
			ps = null;
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
			con = null;
			rs = null;
			ps = null;
		}
	}

	@Override
	public User getUser() {
		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (user instanceof User) {
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
			con = null;
			ps = null;
		}
	}

	@Override
	public List<Order> getAllUserOrders(int userId) {
		StringBuilder query = new StringBuilder("Select * from users.orders where ");
		query.append(OrdersTable.USER_ID.getColumnName() + " = ?");
		List<Order> orders = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DButil.getConnection();
			ps = con.prepareStatement(query.toString());
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Order order = new Order();
				order.setOrder_id(rs.getInt(OrdersTable.ORDER_ID.getColumnName()));
				order.setOrderdate(rs.getDate(OrdersTable.ORDER_DATE.getColumnName()));
				order.setShippeddate(rs.getDate(OrdersTable.SHIPPED_DATE.getColumnName()));
				order.setUser_id(rs.getInt(OrdersTable.USER_ID.getColumnName()));
				orders.add(order);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			con = null;
			rs = null;
			ps = null;
		}
		return orders;
	}

	@Override
	public List<Order> getAllPendingOrders() {
		StringBuilder query = new StringBuilder("Select * from users.orders where ");
		query.append(OrdersTable.SHIPPED_DATE.getColumnName() + " is null");
		List<Order> orders = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DButil.getConnection();
			ps = con.prepareStatement(query.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				Order order = new Order();
				order.setOrder_id(rs.getInt(OrdersTable.ORDER_ID.getColumnName()));
				order.setOrderdate(rs.getDate(OrdersTable.ORDER_DATE.getColumnName()));
				order.setShippeddate(rs.getDate(OrdersTable.SHIPPED_DATE.getColumnName()));
				order.setUser_id(rs.getInt(OrdersTable.USER_ID.getColumnName()));
				orders.add(order);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			con = null;
			rs = null;
			ps = null;
		}
		return orders;
	}

	@Override
	public List<OrderDetails> getOrderDetails(int orderId) {
		StringBuilder query = new StringBuilder("Select * from orders.order_details where ");
		query.append(OrderDetailsTable.ORDER_ID + " = ?");
		List<OrderDetails> orderDetails = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DButil.getConnection();
			ps = con.prepareStatement(query.toString());
			ps.setInt(1, orderId);
			rs = ps.executeQuery();
			while (rs.next()) {
				OrderDetails orderDetail = new OrderDetails();
				orderDetail.setBook_id(rs.getInt(OrderDetailsTable.BOOK_ID.getColumnName()));
				orderDetail.setDiscount(rs.getDouble(OrderDetailsTable.DISCOUNT.getColumnName()));
				orderDetail.setOrder_id(rs.getInt(OrderDetailsTable.ORDER_ID.getColumnName()));
				orderDetail.setQuantity(rs.getInt(OrderDetailsTable.QUANTITY.getColumnName()));
				orderDetail.setUnitprice(rs.getDouble(OrderDetailsTable.UNITPRICE.getColumnName()));
				orderDetails.add(orderDetail);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			con = null;
			rs = null;
			ps = null;
		}
		return orderDetails;
	}

	@Override
	public void addOrder(Order order) {
		StringBuilder query = new StringBuilder("Insert into orders.orders(");
		query.append(OrdersTable.ORDER_DATE.getColumnName() + ",");
		query.append(OrdersTable.ORDER_ID.getColumnName() + ",");
		query.append(OrdersTable.SHIPPED_DATE.getColumnName() + ",");
		query.append(OrdersTable.USER_ID.getColumnName() + ") ");
		query.append("VALUES(?,?,?,?)");
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DButil.getConnection();
			ps = con.prepareStatement(query.toString());
			ps.setDate(1, new java.sql.Date(order.getOrderdate().getTime()));
			ps.setInt(2, order.getOrder_id());
			ps.setDate(3, new java.sql.Date(order.getShippeddate().getTime()));
			ps.setInt(4, order.getUser_id());
			ps.executeUpdate();

			query = new StringBuilder("Insert into orders.order_details(");
			query.append(OrderDetailsTable.BOOK_ID.getColumnName() + ",");
			query.append(OrderDetailsTable.DISCOUNT.getColumnName() + ",");
			query.append(OrderDetailsTable.ORDER_ID.getColumnName() + ",");
			query.append(OrderDetailsTable.QUANTITY.getColumnName() + ",");
			query.append(OrderDetailsTable.UNITPRICE.getColumnName() + ") ");
			query.append("VALUES(?,?,?,?,?)");
			for (OrderDetails od : order.getOrderDetails()) {
				ps = null;
				ps = con.prepareStatement(query.toString());
				ps.setInt(1, od.getBook_id());
				ps.setDouble(2, od.getDiscount());
				ps.setInt(3, od.getOrder_id());
				ps.setInt(4, od.getQuantity());
				ps.setDouble(5, od.getUnitprice());
				ps.executeUpdate();
			}
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con = null;
			ps = null;
		}

	}

	@Override
	public void updateOrder(Order order) {
		StringBuilder query = new StringBuilder("Update users.orders set ");
		query.append(OrdersTable.ORDER_DATE.getColumnName() + " = ? ");
		query.append(OrdersTable.SHIPPED_DATE.getColumnName() + " = ? ");
		query.append(OrdersTable.USER_ID.getColumnName() + " = ?");
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DButil.getConnection();
			ps = con.prepareStatement(query.toString());
			ps.setDate(1, new java.sql.Date(order.getOrderdate().getTime()));
			ps.setDate(2, new java.sql.Date(order.getShippeddate().getTime()));
			ps.setInt(3, order.getUser_id());
			ps.executeUpdate();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con = null;
			ps = null;
		}
	}
}
