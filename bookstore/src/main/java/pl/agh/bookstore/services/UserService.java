package pl.agh.bookstore.services;


import pl.agh.bookstore.security.model.LoginResults;
import pl.agh.bookstore.security.model.User;

public interface UserService {

	public LoginResults logUser(String username, String password) throws Exception;	
	public User loadUserByEmail(String username);
	public User getUser();
	
	public void addUser(User user) throws Exception;
}
