package pl.agh.library.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler extends AccessDeniedHandlerImpl{
	
	private String defaultErrorPage;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
			throws IOException, ServletException {
		setErrorPage(defaultErrorPage);
		if (accessDeniedException instanceof MissingCsrfTokenException || accessDeniedException instanceof InvalidCsrfTokenException){
			response.sendRedirect(defaultErrorPage);
			return;
		}
		super.handle(request, response, accessDeniedException);
	}

	public String getDefaultErrorPage() {
		return defaultErrorPage;
	}

	public void setDefaultErrorPage(String defaultErrorPage) {
		this.defaultErrorPage = defaultErrorPage;
	}
	
	

}
