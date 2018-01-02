package pl.agh.bookstore.services;


import java.util.List;

import pl.agh.bookstore.model.Order;
import pl.agh.bookstore.model.OrderDetails;
import pl.agh.bookstore.security.model.LoginResults;
import pl.agh.bookstore.security.model.User;

public interface UserService {

	public LoginResults logUser(String username, String password) throws Exception;	
	public User loadUserByEmail(String username);
	public User getUser();
	public void addUser(User user) throws Exception;
	
	public List<Order> getAllUserOrders(int userId);
	public List<Order> getAllPendingOrders();
	public List<OrderDetails> getOrderDetails(int orderId);
	
	public void addOrder(Order order);
	public void updateOrder(Order order);
}
