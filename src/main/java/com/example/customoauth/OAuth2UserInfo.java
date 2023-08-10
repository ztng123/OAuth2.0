package com.example.customoauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class OAuth2UserInfo implements OAuth2User {

    private OAuthUser user;
    private Map<String, Object> attributes;

    public OAuth2UserInfo(OAuthUser user,Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }





    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }


    @Override
    public String getName() {
        return user.getUsername();
    }
}