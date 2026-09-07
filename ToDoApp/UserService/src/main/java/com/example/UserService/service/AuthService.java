package com.example.UserService.service;

import com.example.UserService.entity.User;
import com.example.UserService.model.LoginRequest;
import com.example.UserService.model.LoginResponse;
import com.example.UserService.model.RegisterRequest;
import com.example.UserService.model.RegisterResponse;
import com.example.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;


public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    OtpService otpService;

    LoginResponse login(LoginRequest loginRequest){
        // validate that it authenticated user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));

        // create and send token in response
        User user = userRepository.findUserByEmail(loginRequest.getEmail());
        Map<String,Object> claims = new HashMap<>();

        String token = jwtService.createToken(user,claims);

        return new LoginResponse(user.getEmail(), token);

    }
    //======================================================================================

     RegisterResponse register(RegisterRequest registerRequest){
        // create User object and save it in DB with enabled = false
        User user = User.builder()
                .role(registerRequest.getRole())
                .email(registerRequest.getEmail())
                .Password(registerRequest.getPassword())
                .enabled(false)
                .build();

        // send otp code to user's email
        otpService.createOTPAndSendIt(user);

        return new RegisterResponse(registerRequest.getEmail(), "User registered successfully. OTP has been sent to your email.");

     }
     //======================================================================================




}
