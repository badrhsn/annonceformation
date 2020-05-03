package com.annonce.app.config.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.annonce.app.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserImpl implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	User user;
	List<String> auth;
	public UserImpl(User user, List<String> auth) {
		super();
		this.user = user;
		this.auth = auth;
	}

	
/*
	public UserImpl(List<String> auth, OAuth2User oAuth2User,User user) {
		super();
		this.auth = auth;
		this.oAuth2User = oAuth2User;
		this.user= user;
	}



	public UserImpl(List<String> auth, OidcUser oidcUser) {
		super();
		this.auth = auth;
		this.oidcUser = oidcUser;
	}


*/
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 if (null == auth) {
	            return Collections.emptySet();
	        }
	        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
	        auth.forEach(group -> {
	            grantedAuthorities.add(new SimpleGrantedAuthority(group));
	        });
	        return grantedAuthorities;
	}
	@JsonIgnore
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<String> getAuth() {
		return auth;
	}

	public void setAuth(List<String> auth) {
		this.auth = auth;
	}



}
