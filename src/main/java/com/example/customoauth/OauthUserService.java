package com.example.customoauth;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
@Slf4j
@Service
public class OauthUserService extends DefaultOAuth2UserService {

    private final OAuthUserRepository userRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("Creating user");
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");

        System.out.println("OAuth2User details: ");
        System.out.println("Name: " + oAuth2User.getAttribute("name"));
        System.out.println("Email: " + oAuth2User.getAttribute("email"));

        Optional<OAuthUser> optionalUser = userRepository.findByEmail(email);
        OAuthUser user;

        if(optionalUser.isEmpty()) {
            user = OAuthUser.builder()
                    .name(oAuth2User.getAttribute("name"))
                    .email(oAuth2User.getAttribute("email"))
                    .build();
            userRepository.save(user);
            System.out.println("Saved OAuthUser details: " + user);
        } else {
            user = optionalUser.get();
            System.out.println("Retrieved OAuthUser details: " + user);
        }

        return new OAuth2UserInfo(user, oAuth2User.getAttributes());
    }




}

