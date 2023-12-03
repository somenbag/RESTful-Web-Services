package com.springboot.blogapp.service;

import com.springboot.blogapp.dto.LoginDto;
import com.springboot.blogapp.dto.SignupDto;

public interface AuthService {

    String login(LoginDto loginDto);
    String signup(SignupDto signupDto);
}
