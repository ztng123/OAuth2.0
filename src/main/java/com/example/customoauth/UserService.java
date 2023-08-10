package com.example.customoauth;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service

public class UserService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public long save(DTO dto) {
        System.out.println("Creating user: " + dto.getUsername());
        if(userRepository.existsByUsername(dto.getUsername())){
            throw new UserAlreadyExistsException("username already exists");
        }
        User userToSave = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .enabled(true)
                .build();
        System.out.println("User created: " + userToSave);
        User savedUser = userRepository.save(userToSave);
        System.out.println("User saved: " + savedUser);
        return savedUser.getId();

    }

    public User updatePassword(long id, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        user.setPassword(passwordEncoder.encode(newPassword));

        return userRepository.save(user);
    }
    @Transactional
    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}