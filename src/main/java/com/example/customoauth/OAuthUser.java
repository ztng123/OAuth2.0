package com.example.customoauth;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;
import java.util.Map;

import static jakarta.persistence.GenerationType.*;

@Entity
@Data
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthUser implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "picture")
    private String picture;



    @Builder
    public OAuthUser(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public OAuthUser update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }




    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("OIDC_USER"));
    }

    @Override
    public String getUsername() {
        return name;
    }


    @Override
    public String getPassword() {
        return null;  // OAuth user does not have password
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;  // or provide actual implementation
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // or provide actual implementation
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // or provide actual implementation
    }

    @Override
    public boolean isEnabled() {
        return true;  // or provide actual implementation
    }


}