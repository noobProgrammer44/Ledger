package com.ledger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// @Configuration indicates that a class is a source of bean definition
@Configuration
public class AppConfig {


    // @Bean on a method says: call this once and keep the result as a bean.
    @Bean
    // BCryptPasswordEncoder belongs to a library, so we can’t put @Service on it.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}