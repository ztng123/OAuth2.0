package com.example.customoauth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuthUserRepository extends JpaRepository<OAuthUser, Long> {
    Optional<OAuthUser> findByName(String name);

    Optional<OAuthUser> findByEmail(String email);

}

