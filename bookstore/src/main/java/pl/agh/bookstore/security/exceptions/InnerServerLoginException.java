package pl.agh.bookstore.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InnerServerLoginException extends AuthenticationException{

	private static final long serialVersionUID = -3620312094600967515L;

	public InnerServerLoginException(String msg) {
		super(msg);

	}

}
