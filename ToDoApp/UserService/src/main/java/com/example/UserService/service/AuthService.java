package com.example.UserService.service;

import com.example.UserService.entity.User;
import com.example.UserService.model.LoginRequest;
import com.example.UserService.model.LoginResponse;
import com.example.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;


public class AuthService {
    @Autowired
    OtpService otpService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    LoginResponse login(LoginRequest loginRequest){
        // validate that it authenticated user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));

        // create and send token in response
        User user = userRepository.findUserByEmail(loginRequest.getEmail());
        Map<String,Object> claims = new HashMap<>();

        String token = jwtService.createToken(user,claims);

        return new LoginResponse(user.getEmail(), token);

    }



}
