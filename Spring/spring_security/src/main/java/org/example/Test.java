package org.example;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Test {
    public static void main(String[] args) {
        String password = BCrypt.hashpw("123", BCrypt.gensalt());
        System.out.println(password);
    }
}
