package com.mindfultrader.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mindfultrader.webapp.services.CustomUserDetailsService;
 
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
     
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        
        	.antMatchers("/homee", "/portfoliowatchlist", "/algorithm", "/algorithm/*", "/portfoliowatchlist/*", "/theory", "/account","/account/*", "/changeCompanies", "/listUsers", "/subscription1", "/subscription").authenticated()
        	.antMatchers("/homee", "/portfoliowatchlist", "/algorithm", "/algorithm/*", "/portfoliowatchlist/*", "/theory", "/account","/account/*").hasAnyAuthority("SUBSCRIBER1")
        	.antMatchers("/portfoliowatchlistAdimn", "/addCompanyToDatabase", "/addingCompany", "/changeCompanies", "/listUsers").hasAnyAuthority("ADMIN")
        	//.anyRequest().authenticated()
        	//.anyRequest().permitAll()
            .and()
            .formLogin()
                .usernameParameter("email")
                .defaultSuccessUrl("/homee")
                .permitAll()
            .and()
            .logout().logoutSuccessUrl("/").permitAll()
        	.and()
        	.exceptionHandling().accessDeniedPage("/403");
    }
     
}