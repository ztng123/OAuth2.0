package com.example.customoauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example"})
public class CustomoauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomoauthApplication.class, args);
    }

}
