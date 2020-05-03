package com.annonce.app.config.service;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.util.UriComponentsBuilder;

import com.annonce.app.model.User;
import com.annonce.app.repository.UserRepository;
@Component

public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired 
	private UserRepository userRepository;
	protected Log logger = LogFactory.getLog(this.getClass());

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {

		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
		setSession(request,authentication);
	}
	protected void handle(
			HttpServletRequest request,
			HttpServletResponse response, 
			Authentication authentication
			) throws IOException {


		String redirectionUrl = null;

		if (response.isCommitted()) {
			logger.debug(
					"Response has already been committed. Unable to redirect to "
							+ redirectionUrl);
			return;
		}

		String username = authentication.getName();
		request.getSession().setAttribute("nom", username);
		User user =userRepository.findByEmail(username);
		System.out.println(request.getSession().getAttribute("nom")+" session");
		if(user.getRoles().equals("FORMATEUR")) {
			redirectionUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/index").build().toUriString();
		}
		if(user.getRoles().equals("USER")){
			redirectionUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/home").build().toUriString();
		}

		redirectStrategy.sendRedirect(request, response, redirectionUrl);
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
	protected void setSession(HttpServletRequest request,Authentication principal) {






	}

}