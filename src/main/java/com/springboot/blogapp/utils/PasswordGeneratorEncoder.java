package com.springboot.blogapp.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLOutput;

public class PasswordGeneratorEncoder {

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("request"));
        System.out.println(passwordEncoder.encode("bag"));
    }
}
