package com.Authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Authservice.Apiresponse.ApiResponse;
import com.Authservice.UserDto.LoginDto;
import com.Authservice.UserDto.UserDto;
import com.Authservice.service.JwtService;
import com.Authservice.service.MainService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	
	
	
	
@Autowired
public JwtService jwtt;
	@Autowired
	public AuthenticationManager author;

	@Autowired
	private MainService service;

	@PostMapping("/registerme")
	public ResponseEntity<ApiResponse<String>> register(@RequestBody UserDto dto) {
		ApiResponse<String> response = service.register(dto);
		return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatuscode()));
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginDto dto) {
		ApiResponse response = new ApiResponse();

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(dto.getUsername(),
				dto.getPassword());

		try {
			Authentication authenticate = author.authenticate(auth);
			if (authenticate.isAuthenticated()) {
				String jwttokens = jwtt.generateToken(dto.getUsername(), authenticate.getAuthorities().iterator().next().getAuthority());
				
				response.setMessage("Login Succesfull");
				response.setStatuscode(201);
				response.setData(jwttokens);
				return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatuscode()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setMessage("Login failed");
		response.setStatuscode(401);
		response.setData("User not logged In");
		return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatuscode()));
		 }}
