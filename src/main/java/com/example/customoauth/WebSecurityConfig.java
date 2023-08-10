package com.example.customoauth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;


@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

    private final UserDetailService userService;

    private final PasswordEncoder passwordEncoder;

    private final OauthUserService oauthUserService;

    private final UserAuthenticationFailureHandler userAuthenticationFailureHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/login", "/join").permitAll()
                        .requestMatchers(HttpMethod.POST, "/joinProc","/updatePassword").permitAll()
                        .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .failureHandler(userAuthenticationFailureHandler)
                        .permitAll())
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oauthUserService))
                        .redirectionEndpoint(redirection -> redirection
                                .baseUri("/login/oauth2/code/google"))
                        .permitAll())
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login"))
                .csrf(AbstractHttpConfigurer::disable)
                .build(); // Return the SecurityFilterChain
    }



    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception{
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        return daoAuthenticationProvider;
    }






}
