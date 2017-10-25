package pl.agh.library.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.intercept.RunAsManager;
import org.springframework.security.access.intercept.RunAsManagerImpl;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import pl.agh.library.security.model.UserTypes;

@EnableWebSecurity
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationSuccessUrlHandler authenticationSuccessHandler;
	
	@Autowired
	private CustomAuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	private CustomAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private CustomAccessDecisionManager accessDecisionManager;
	
	@Autowired
	private FilterSecurityMetadataSource filter;

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(new CustomAuthenticationProvider());
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler).accessDeniedPage("/403");
		http.headers().frameOptions().sameOrigin();
		http.authorizeRequests()
				.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>(){

					@Override
					public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
						fsi.setAccessDecisionManager(accessDecisionManager);
						fsi.setSecurityMetadataSource(filter);
						try {
							fsi.afterPropertiesSet();
						} catch (Exception e) {
							e.printStackTrace();
						}		
						return fsi;
					}
					
				});
			
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/admin/**").hasAnyRole(UserTypes.ADMIN.name());
		
		http.formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password")
				.failureHandler(authenticationFailureHandler).successHandler(authenticationSuccessHandler).permitAll();
		http.logout().permitAll();

	}
	
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}



	@Bean
	public RunAsManager runAsManager() throws Exception {
		RunAsManagerImpl runAsManager = new RunAsManagerImpl();
		runAsManager.setKey(UserTypes.ADMIN.name());
		runAsManager.afterPropertiesSet();
		return runAsManager;
	}

}
