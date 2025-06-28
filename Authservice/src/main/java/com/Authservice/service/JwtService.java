package com.Authservice.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JwtService {
	private static final String secretKey = "myboy";
	private static final long expiretime = 7458961;

	
	public String generateToken(String username, String role) {
		return JWT.create()
				.withSubject(username)
				.withClaim("role", role)
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis()+expiretime))
				.sign(Algorithm.HMAC256(secretKey));
	}


	public String validateTokenAndRetrieveSubject(String finaljwt) {
		return JWT.require(Algorithm.HMAC256(secretKey)).build().verify(finaljwt).getSubject();
		
	}
	
	
}
