package com.annonce.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.annonce.app.model.User;


public interface UserRepository  extends JpaRepository<User, String> {

	List<User> findByNameLike(String name); 
	User findByEmail(String email);
	boolean existsByEmail(String email);
	
	

}