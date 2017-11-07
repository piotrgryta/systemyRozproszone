package pl.agh.bookstore.security;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import pl.agh.bookstore.model.LoginBean;
import pl.agh.bookstore.security.model.User;

public class CustomAuthenticationSuccessUrlHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	@Autowired
	@Qualifier("myProps")
	Properties appConf;

	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		
		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
		User user = (User) authentication.getPrincipal();
		String redirectUrl = onAuthenticationSuccess(request, response, user);
		redirectStrategy.sendRedirect(request, response, redirectUrl);		
		return;
	}

	public String onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			User user) throws ServletException, IOException {
		
		SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
		
		String redirectUrl = appConf.getProperty("baseFullUrl");
		DefaultSavedRequest destination = ((DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST"));
		String successUrl = "";
		if (destination != null){
			successUrl = destination.getServletPath();
		}
		
		LoginBean loginBean = new LoginBean();
		request.getSession().setAttribute("loginBean", loginBean);
		
		if (successUrl != null){
			redirectUrl += successUrl;
		} else {
			if (savedRequest == null || savedRequest.getRedirectUrl() == null){
				if (!user.isActive()){
					redirectUrl += "/myprofile/changePassword";
				}
			}
		}
		
		if (loginBean != null){
			loginBean.setLogged(true);
			loginBean.setUserName(user.getEmail());
		}
		return redirectUrl;
	}
	
}
