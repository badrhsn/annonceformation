package com.annonce.app.config.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;

import com.annonce.app.dto.FacebookOAuth2UserInfo;
import com.annonce.app.dto.OAuth2UserInfo;
import com.annonce.app.model.User;
import com.annonce.app.repository.UserRepository;
import com.annonce.app.service.UserService;



@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

 @Autowired
 private UserService userService;
	@Autowired
    private UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
     
		OAuth2UserInfo userInfo ;

        try {
            Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
    		System.out.println(attributes);

            String provider = oAuth2UserRequest.getClientRegistration().getRegistrationId();
            System.out.println(provider);
            if (provider.equals("facebook")) {
            	userInfo = new FacebookOAuth2UserInfo(attributes);
    	        updateUser(userInfo);
            }    		
            return oAuth2User;
        } catch (AuthenticationException ex) {
            throw ex;
        }
    }
    private void  updateUser(OAuth2UserInfo userInfo) {
        User user = userRepository.findByEmail(userInfo.getEmail());
        if(user == null) {
            user = new User();
            user.setEmail(userInfo.getEmail());
            user.setImgUrl(userInfo.getImageUrl());
            user.setName(userInfo.getName());
            user.setFirst_name(userInfo.getFitrstName());  
            user.setLast_name(userInfo.getName());
            userRepository.save(user);
        }
    }
}