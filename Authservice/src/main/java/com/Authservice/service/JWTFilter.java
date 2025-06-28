package com.Authservice.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JWTFilter extends OncePerRequestFilter{
	@Autowired
	private JwtService jwtfilteration;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		if(header!=null && header.startsWith("Bearer ")) {
			String finaljwt = header.substring(7);
			String username = jwtfilteration.validateTokenAndRetrieveSubject(finaljwt);
			System.out.println(username);
			
			
		} 
		
		filterChain.doFilter(request, response);
	} 

}
