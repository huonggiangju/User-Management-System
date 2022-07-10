package com.example.demo.user;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserService(); 
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider provider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider((AuthenticationProvider) authenticationManager());
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests()
				.antMatchers("/users").authenticated()
				.anyRequest().permitAll()
				.and()
			.formLogin()
				.loginPage("/login").permitAll()
				.usernameParameter("email")
				.defaultSuccessUrl("/users")
				.permitAll()
				.and()
			.logout()
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.logoutSuccessUrl("/")
				.permitAll();
	}
	
	
}
