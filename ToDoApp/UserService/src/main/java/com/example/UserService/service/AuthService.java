package com.example.UserService.service;

import com.example.UserService.auth.SecurityConfig;
import com.example.UserService.entity.Jwt;
import com.example.UserService.entity.User;
import com.example.UserService.entity.enums.TokenType;
import com.example.UserService.model.*;
import com.example.UserService.repository.JwtRepository;
import com.example.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtRepository jwtRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    OtpService otpService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    //======= UnAuthorized EndPoints===================================================================
    //=================================================================================================
    public LoginResponse login(LoginRequest loginRequest){
        // validate that it authenticated user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));

        // create and send token in response
        User user = userRepository.findUserByEmail(loginRequest.getEmail());
        Map<String,Object> claims = new HashMap<>();

        String token = jwtService.createToken(user,claims);
        Jwt jwtToken = Jwt.builder().tokenType(TokenType.BEARER).token(token).user(user).build();
        jwtRepository.save(jwtToken);

        return new LoginResponse(user.getEmail(), token);

    }
    //------------------------------------------------------------------------------------------------
     public RegisterResponse register(RegisterRequest registerRequest){
        // create User object and save it in DB with enabled = false
        User user = User.builder()
                .role(registerRequest.getRole())
                .email(registerRequest.getEmail())
                .Password(passwordEncoder.encode(registerRequest.getPassword()))
                .enabled(false)
                .build();
        userRepository.save(user);

        // send otp code to user's email
        otpService.createOTPAndSendIt(user);

        return new RegisterResponse(registerRequest.getEmail(), "User registered successfully. OTP has been sent to your email.");

     }
     public RegisterResponse reGenerateOtp(String email){
        User user = userRepository.findUserByEmail(email);
        otpService.createOTPAndSendIt(user);

        return new RegisterResponse(email, "OTP has been sent to your email.");
     }
    //------------------------------------------------------------------------------------------------
    public LoginResponse activate (String username, String otpCode){
         // get otp code from auth header and validate it
          otpService.activateUser(username, otpCode);

         // create and send token in response
         User user = userRepository.findUserByEmail(username);
         Map<String,Object> claims = new HashMap<>();

         String token = jwtService.createToken(user,claims);
        Jwt jwtToken = Jwt.builder().tokenType(TokenType.BEARER).token(token).user(user).build();
        jwtRepository.save(jwtToken);

         return new LoginResponse(username, token);
     }
    //------------------------------------------------------------------------------------------------
    public RegisterResponse forgetPassword(String email){
        User user = userRepository.findUserByEmail(email);
        otpService.createOTPAndSendIt(user);

        return new RegisterResponse(email, "OTP has been sent to your email.");
    }

    //======= Authorized EndPoints===================================================================
    //================================================================================================

    public String changePassword(RegisterRequest registerRequest){
        User user = userRepository.findUserByEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        userRepository.save(user);

        return "password changed successfully";
    }

    public ResponseEntity<Object> checkToken(){
        return ResponseEntity.ok().build();
    }




}
