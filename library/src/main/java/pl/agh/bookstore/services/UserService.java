package pl.agh.library.services;


import pl.agh.library.security.model.LoginResults;
import pl.agh.library.security.model.User;

public interface UserService {

	public LoginResults logUser(String username, String password) throws Exception;	
	public User loadUserByEmail(String username);
	public User getUser();
	
	public void addUser(User user) throws Exception;
}
