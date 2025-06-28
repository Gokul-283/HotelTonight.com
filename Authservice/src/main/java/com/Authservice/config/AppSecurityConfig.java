package com.Authservice.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.Authservice.service.CustomUserDetails;
import com.Authservice.service.JWTFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {
	String[] publicEndpoints = {
	        "/api/v1/auth/registerme",
	        "/api/v1/auth/login",
	        "/property/api/v1/property/search-property",
	        "/v3/api-docs/**",
	        "/swagger-ui/**",
	        "/swagger-ui.html",
	        "/swagger-resources/**",
	        "/webjars/**"
	    };
	@Autowired
	public JWTFilter jwtfilter;
	
	@Autowired
	public CustomUserDetails customauth;
	
	@Bean
	public PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authmanger(AuthenticationConfiguration cofig) throws Exception{
		return cofig.getAuthenticationManager();
	}
	
	@Bean
	public AuthenticationProvider authprovider() {
		DaoAuthenticationProvider authprovider  = new DaoAuthenticationProvider();
		authprovider.setUserDetailsService(customauth);
		authprovider.setPasswordEncoder(getEncoder());
		return authprovider;
	}
	
	
	
	
	@Bean
	public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable())  // Disable CSRF
	        .authorizeHttpRequests(auth -> 
	            auth.requestMatchers(
	            		publicEndpoints).permitAll()
	            .requestMatchers("/api/v1/test/message")
	           .hasAnyAuthority("USER")
	            .anyRequest()
	            .authenticated()).authenticationProvider(authprovider()).addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	
}

}