package pl.agh.library.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import pl.agh.library.security.model.User;
import pl.agh.library.security.model.UserTypes;
import pl.agh.library.services.UserService;

@Component
public class FilterSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{

	@Autowired
	UserService userService;
	
	List<ConfigAttribute> allUsers = new LinkedList<>();
	Map<RequestMatcher, List<ConfigAttribute>> requestMap = null;
	
	public FilterSecurityMetadataSource(){
		loadAllUsersList();
		loadRequestMap();
	}
	
	private void loadAllUsersList(){
		String s = "ROLE_";
		allUsers = SecurityConfig.createList(s + UserTypes.USER.name(), s + UserTypes.USER_NO_PASSWORD.name(), s + UserTypes.ADMIN.name(), s + UserTypes.ADMIN_NO_PASSWORD.name(), s + UserTypes.DEFAULT.name());
	}
	
	private void loadRequestMap(){
		requestMap = new LinkedHashMap<>();		
		requestMap.put(new AntPathRequestMatcher("/**/admin/**"), SecurityConfig.createList("ROLE_" + UserTypes.ADMIN.name(), "ROLE_" + UserTypes.ADMIN_NO_PASSWORD.name()));		

	}
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object arg0) throws IllegalArgumentException {
		FilterInvocation fi = (FilterInvocation) arg0;
		String fullRequestUrl = fi.getFullRequestUrl();
		String requestUrl = fi.getRequestUrl();
		System.out.println("Full request URL:  " + fullRequestUrl);
		
		List<ConfigAttribute> configAttributes = new ArrayList<>(0);
		
		//TODO change user to user service
		User user = userService.getUser();
		if (user != null)
			loadRequestMap();
		
		if (requestMap == null)
			loadRequestMap();
		
		AntPathMatcher apm = new AntPathMatcher();

		for (Map.Entry<RequestMatcher, List<ConfigAttribute>> entry: requestMap.entrySet()){
			RequestMatcher key = entry.getKey();
			List<ConfigAttribute> value = entry.getValue();
			AntPathRequestMatcher aprm = (AntPathRequestMatcher) key;
			boolean patternMatch = apm.match(aprm.getPattern(), requestUrl.toLowerCase());
			
			if (patternMatch){
				configAttributes = value;
			}
		}
		
		return configAttributes;
	}
	
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}
	
	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
