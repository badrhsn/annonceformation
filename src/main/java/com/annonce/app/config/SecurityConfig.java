package com.annonce.app.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.annonce.app.config.service.CustomAuthenticationSuccessHandler;
import com.annonce.app.config.service.CustomOAuth2UserService;
import com.annonce.app.config.service.CustomOidcUserService;
import com.annonce.app.config.service.CustomUserDetailsService;
import com.annonce.app.config.service.MySimpleUrlAuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Autowired
	private CustomOidcUserService customOidcUserService;
	@Autowired 
	private CustomOAuth2UserService customOAuth2UserService;
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Autowired
	private MySimpleUrlAuthenticationSuccessHandler mySimpleUrlAuthenticationSuccessHandler;
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
    
    	return super.authenticationManagerBean();
    }
	@Bean DaoAuthenticationProvider authenticationProvider() {
		  DaoAuthenticationProvider daoAuthenticationProvider = new
		  DaoAuthenticationProvider();
		  daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		  daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		  daoAuthenticationProvider.setAuthoritiesMapper(authoritiesMapper());
		  return daoAuthenticationProvider;
	}
	public GrantedAuthoritiesMapper authoritiesMapper() { 
		SimpleAuthorityMapper  authorityMapper = new SimpleAuthorityMapper();
		  authorityMapper.setConvertToUpperCase(true);
		  return authorityMapper; }
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.authenticationProvider(authenticationProvider());
;
	}
	/*
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select email as principal, password as credentails, true from user where email=?")
		.authoritiesByUsernameQuery("select user_email as principal, role_name as role from user_roles where user_email=?")
		.passwordEncoder(passwordEncoder()).rolePrefix("ROLE_");  
		
	}*/
   
	@Bean
	public PasswordEncoder passwordEncoder() {
		// TODO Auto-generated method stub
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/register", "/", "/index", "/about", "/login", "/css/**","/categorie","/search","/assets/**","/js/**", "/completeprofile").permitAll()
				.antMatchers("/profile","/editprofile").hasAnyRole("USER,FORMATEUR")
				.antMatchers("/formateur/dashboard/**").hasRole("FORMATEUR")
				.anyRequest().authenticated()
				.and()
		        .oauth2Login()
		        .loginPage("/login")
		        .userInfoEndpoint()
		        .oidcUserService(customOidcUserService)
		        .userService(customOAuth2UserService)
		        .and().userInfoEndpoint()
		        .and()
		        .authorizationEndpoint()
		        .baseUri("/oauth2/authorization")
		        .and()
		        .successHandler(customAuthenticationSuccessHandler)
				.and().formLogin().loginPage("/login").permitAll()
				.successHandler(mySimpleUrlAuthenticationSuccessHandler)
				.and()
				.logout().invalidateHttpSession(true)
		        .clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		        .logoutSuccessUrl("/");
	}
}

