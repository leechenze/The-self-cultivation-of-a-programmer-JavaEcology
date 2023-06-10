package org.example.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCrypt;

// @Configuration
public class EncryptionConfig {

    @Bean
    public String encrypt(String passwordText) {
        System.out.println(BCrypt.hashpw(passwordText, BCrypt.gensalt()));
        return BCrypt.hashpw(passwordText, BCrypt.gensalt());
    }


}
