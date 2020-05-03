package com.annonce.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.annonce.app.model.User;
import com.annonce.app.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public void createUser(User user) {
		BCryptPasswordEncoder  encoder = new  BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword())); 
		user.setRoles("USER");
		userRepository.save(user);
	}
	
	public void createFormateur(User user) {
		BCryptPasswordEncoder  encoder = new  BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword())); 
		user.setRoles("FORMATEUR");
		userRepository.save(user);
	}
	public User findOne(String email) {
		
		  return userRepository.getOne(email);
		}

		public boolean isUserPresent(String email) {
			// TODO Auto-generated method stub
			User u=userRepository.findByEmail(email);
			if(u!=null)
				return true;
			
			return false;
		}

		public List<User> findAll() {
			// TODO Auto-generated method stub
			return userRepository.findAll();
		}

		public List<User> findByName(String name) {
			// TODO Auto-generated method stub
			return  userRepository.findByNameLike("%"+name+"%");
		}
		
		public void updateUser(User user) {
			BCryptPasswordEncoder  encoder = new  BCryptPasswordEncoder();
			user.setPassword(encoder.encode(user.getPassword())); 
			userRepository.save(user);
		}
		public boolean existsByEmail(String email) {
			// TODO Auto-generated method stub
			return userRepository.existsByEmail(email);
		}

		public User findByEmail(String email) {
			// TODO Auto-generated method stub
			return userRepository.findByEmail(email);
		}
		
}
