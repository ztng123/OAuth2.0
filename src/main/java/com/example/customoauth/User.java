package com.example.customoauth;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;

import static jakarta.persistence.GenerationType.*;


@Entity
@Data
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
public class User implements UserDetails {

    @jakarta.persistence.Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "username", updatable = false, unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "email")
    private String email;

    @Column(name = "picture")
    private String picture;



    @Builder
    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password; // 비밀번호를 암호화해서 저장
        this.enabled = true;

    }

    public Long getId(){
        return id;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getEnabled() {
        return enabled;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority("user"));
    }

    /**
     * UserDetails 구현
     * 비밀번호를 리턴
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * UserDetails 구현
     * PK값을 반환해준다
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * UserDetails 구현
     * 계정 만료 여부
     * true : 만료안됨
     * false : 만료됨
     */



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * UserDetails 구현
     * 계정 잠김 여부
     * true : 잠기지 않음
     * false : 잠김
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * UserDetails 구현
     * 계정 비밀번호 만료 여부
     * true : 만료 안됨
     * false : 만료됨
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * UserDetails 구현
     * 계정 활성화 여부
     * true : 활성화됨
     * false : 활성화 안됨
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

//    public void setUsername(String username) {
//        this.username = username;
//    }



}