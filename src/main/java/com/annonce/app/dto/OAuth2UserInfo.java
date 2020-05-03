package com.annonce.app.dto;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;



public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;
    
//    protected Collection<? extends GrantedAuthority> authorities;
    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
       // this.authorities = authorities;
    }
 
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();
 
    public abstract String getName();
 
    public abstract String getEmail();
    
    public abstract String getImageUrl();
    
    public abstract String getFitrstName();
    
    public abstract String getLastName();
    
    }

 
  