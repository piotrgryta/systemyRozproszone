package pl.agh.bookstore.security;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler{
	
	@Autowired
	@Qualifier("myProps")
	Properties appConf;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {

		String errorType = null;
		if (authException instanceof BadCredentialsException){
			errorType = authException.getMessage();
			if (errorType != null){
				errorType = URLEncoder.encode(errorType, StandardCharsets.UTF_8.name());
			}
			response.sendRedirect(appConf.getProperty("baseFullUrl") + "/login?error=" + errorType);

		} else {
			response.sendError(500, authException.getMessage());
		}
		
	}

}
