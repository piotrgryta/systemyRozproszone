package pl.agh.bookstore.security;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import pl.agh.bookstore.security.exceptions.InnerServerLoginException;
import pl.agh.bookstore.security.model.LoginResults;
import pl.agh.bookstore.security.model.User;
import pl.agh.bookstore.services.UserService;

public class CustomAuthenticationProvider implements AuthenticationProvider, Serializable{

	private static final long serialVersionUID = 1506354688679682733L;
	
	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		LoginResults logUser = LoginResults.INNER_SERVER_ERROR;
		try {
			logUser = userService.logUser(username, password);
		} catch (Exception e) {
			throw new InnerServerLoginException(e.getMessage());
		}
		switch (logUser) {
		case INVALID_PASSWORD:
			throw new BadCredentialsException(logUser.name());
		case NO_SUCH_USER:{
			System.out.println("NO_SUCH_USER: " + logUser.name());
			throw new BadCredentialsException(logUser.name());}
		case USER_BLOCKED:
			throw new BadCredentialsException(logUser.name());
		case USER_NOT_VERYFIED:
			throw new BadCredentialsException(logUser.name());
		case SUCCESS:
			break;
		default:
			throw new BadCredentialsException(logUser.name());
		}

		User user = userService.loadUserByEmail(username);		// username==email
		
		return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
	
	}

	@Override
	public boolean supports(Class<?> arg0) {
        return true;
	}

}
