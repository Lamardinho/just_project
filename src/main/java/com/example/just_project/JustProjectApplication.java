package com.example.just_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class JustProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(JustProjectApplication.class, args);
    }

}
