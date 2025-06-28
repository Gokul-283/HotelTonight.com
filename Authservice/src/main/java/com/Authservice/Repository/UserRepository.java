package com.Authservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Authservice.UserEntity.UserEntity;

public interface UserRepository extends JpaRepository <UserEntity, Long>{
	
	UserEntity findByUsername(String username);
	UserEntity findByEmail (String email);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	
	

}
