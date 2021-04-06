package com.mindfultrader.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Configuration
	@Order(1)	
	public static class App1ConfigurationAdapter extends WebSecurityConfigurerAdapter {
	    public App1ConfigurationAdapter() {
	        super();
	    }
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.antMatcher("/admin*")
	          .authorizeRequests()
	          .anyRequest()
	          .hasRole("ADMIN")
	          
	          .and()
	          .formLogin()
	          .loginPage("/loginadmin")
	          .loginProcessingUrl("/admin_login")
	          .failureUrl("/loginAdmin?error=loginError")
	          .defaultSuccessUrl("/adminPage")
	          
	          .and()
	          .logout()
	          .logoutUrl("/admin_logout")
	          .logoutSuccessUrl("/protectedLinks")
	          .deleteCookies("JSESSIONID")
	          
	          .and()
	          .exceptionHandling()
	          .accessDeniedPage("/403")
	          
	          .and()
	          .csrf().disable();
	    }
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.inMemoryAuthentication()
	          .withUser("admin")
	          .password(encoder().encode("admin"))
	          .roles("ADMIN");
	    }
	}
	@Configuration
	@Order(2)
	public static class App2ConfigurationAdapter extends WebSecurityConfigurerAdapter {

	    public App2ConfigurationAdapter() {
	        super();
	    }

	    protected void configure(HttpSecurity http) throws Exception {
	        http.antMatcher("/user*")
	          .authorizeRequests()
	          .anyRequest()
	          .hasRole("USER")
	          
	          .and()
	          .formLogin()
	          .loginPage("/loginuser")
	          .loginProcessingUrl("/user_login")
	          .failureUrl("/loginUser?error=loginError")
	          .defaultSuccessUrl("/users")
	          
	          .and()
	          .logout()
	          .logoutUrl("/user_logout")
	          .logoutSuccessUrl("/protectedLinks")
	          .deleteCookies("JSESSIONID")
	          
	          .and()
	          .exceptionHandling()
	          .accessDeniedPage("/403")
	          
	          .and()
	          .csrf().disable();
	    }
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.inMemoryAuthentication()
	          .withUser("user")
	          .password(encoder().encode("user"))
	          .roles("USER");
	    }
	}

	@Bean
	public static PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}
	}