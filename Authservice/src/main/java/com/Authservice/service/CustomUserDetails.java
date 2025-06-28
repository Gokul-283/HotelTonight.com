package com.Authservice.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Authservice.Repository.UserRepository;
import com.Authservice.UserEntity.UserEntity;
@Service
public class CustomUserDetails implements UserDetailsService{
@Autowired
	public UserRepository repo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity byUsername = repo.findByUsername(username);
		return new User(byUsername.getUsername(), byUsername.getPassword(), Collections.singleton(new SimpleGrantedAuthority(byUsername.getRole())));
	}

}
