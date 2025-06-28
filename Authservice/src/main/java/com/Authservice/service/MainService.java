package com.Authservice.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Authservice.Apiresponse.ApiResponse;
import com.Authservice.Repository.UserRepository;
import com.Authservice.UserDto.UserDto;
import com.Authservice.UserEntity.UserEntity;

@Service

public class MainService {
	@Autowired
	private UserRepository repo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	ApiResponse response = new ApiResponse();

	public ApiResponse register(UserDto dto) {

		if (repo.existsByUsername(dto.getUsername())) {
			response.setMessage("Registration Failed");
			response.setStatuscode(500);
			response.setData("User already exists");
			return response;
		}
		if (repo.existsByEmail(dto.getEmail())) {
			response.setMessage("Registration Failed");
			response.setStatuscode(500);
			response.setData("Email already exists");
			return response;
		}
		UserEntity user = new UserEntity();
		BeanUtils.copyProperties(dto, user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("ROLE_USER");
		UserEntity saver = repo.save(user);
		

		response.setMessage("Registration Succesfull");
		response.setStatuscode(201);
		response.setData("User is registered");
		return response;
		// TODO Auto-generated method stub

	}

}
