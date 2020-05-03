package com.annonce.app.config.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.annonce.app.model.User;
import com.annonce.app.repository.UserRepository;



@Service
public class CustomUserDetailsService  implements UserDetailsService{

	OidcUser oidcUser;
    private UserRepository userRepository;

	
	@Autowired
	public CustomUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(userName);
		System.out.println(user.getEmail());
		List<String> auth = user.getRoleList();
		for(int i =0;i<auth.size();i++) {
			System.out.println(auth.get(i).toString());

		}
		if (user == null) {
            throw new UsernameNotFoundException(userName + " not found!");
        }
        return new UserImpl(user, auth);
	}

}
