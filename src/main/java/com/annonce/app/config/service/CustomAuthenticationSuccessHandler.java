package com.annonce.app.config.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.annonce.app.model.User;
import com.annonce.app.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		if (response.isCommitted()) {
			return;
		}
		HttpSession session = request.getSession();
		if(authentication.getPrincipal() instanceof OidcUser){
			String redirectionUrl = null;
			OidcUser oiduser = (OidcUser) authentication.getPrincipal();
			String email = oiduser.getAttribute("email");
			System.out.println("authentifaction succes"+email);   
			User user = userRepository.findByEmail(email);
			session.setAttribute("nom", email);
			if(user.getPassword()!= null) {
				Collection<SimpleGrantedAuthority> oldAuthorities = (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+user.getRoles());
				List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
				updatedAuthorities.add(authority);
				//updatedAuthorities.addAll(oldAuthorities);

				SecurityContextHolder.getContext().setAuthentication(
						new UsernamePasswordAuthenticationToken(
								SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
								SecurityContextHolder.getContext().getAuthentication().getCredentials(),
								updatedAuthorities)
						);
				if(user.getRoles().equals("FORMATEUR")) {
					redirectionUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/index").build().toUriString();
				}
				if(user.getRoles().equals("USER")){
					redirectionUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/home").build().toUriString();
				}
				System.out.println("new Auhthority"+SecurityContextHolder.getContext().getAuthentication().getAuthorities());
				//OidcUserAuthority auth = (OidcUserAuthority) getAuthorities(user.getRoleList()) ;
				
				getRedirectStrategy().sendRedirect(request, response, redirectionUrl);
			}
			else {         	

				redirectionUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/singup-process").build().toUriString();
				getRedirectStrategy().sendRedirect(request, response, redirectionUrl);
			}

		}
		else{

			OAuth2User oiduser = (OAuth2User) authentication.getPrincipal();
			String email = oiduser.getAttribute("email");
			User user = userRepository.findByEmail(email);
			session.setAttribute("nom", email); 
			if(user.getPassword()!= null) {
				Collection<SimpleGrantedAuthority> oldAuthorities = (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+user.getRoles());
				List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
				updatedAuthorities.add(authority);
				SecurityContextHolder.getContext().setAuthentication(
						new UsernamePasswordAuthenticationToken(
								SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
								SecurityContextHolder.getContext().getAuthentication().getCredentials(),
								updatedAuthorities)
						);
				System.out.println("new Auhthority"+SecurityContextHolder.getContext().getAuthentication().getAuthorities());
				//OidcUserAuthority auth = (OidcUserAuthority) getAuthorities(user.getRoleList()) ;
				String redirectionUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/index").build().toUriString();
				getRedirectStrategy().sendRedirect(request, response, redirectionUrl);
			}
			else {         		
				String redirectionUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/singup-process").build().toUriString();
				getRedirectStrategy().sendRedirect(request, response, redirectionUrl);
			}

		}


	}


}

