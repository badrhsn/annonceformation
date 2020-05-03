package com.annonce.app.config.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.annonce.app.dto.GoogleOAuth2UserInfo;
import com.annonce.app.dto.OAuth2UserInfo;
import com.annonce.app.model.User;
import com.annonce.app.repository.UserRepository;


@Service
public class CustomOidcUserService extends OidcUserService{

	@Autowired
    private UserRepository userRepository;

	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		OidcUser oidcUser = super.loadUser(userRequest);
	
		Map<String, Object> attributes = oidcUser.getAttributes();
		System.out.println(oidcUser.getAttributes());
		OAuth2UserInfo userInfo = new GoogleOAuth2UserInfo(attributes);
        updateUser(userInfo);
        
      //  return new UserImpl(oidcUser,updateUser(userInfo), updateUser(userInfo).getRoleList(),attributes);
		return oidcUser;
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
