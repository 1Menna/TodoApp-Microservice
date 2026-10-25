package com.example.UserService.controller;


import com.example.UserService.model.*;
import com.example.UserService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    //======= UnAuthorized EndPoints===================================================================
    //=================================================================================================
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }
    //------------------------------------------------------------------------------------------------
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.register(registerRequest));
    }
    @PostMapping("/regenerateOtp")
    public ResponseEntity<RegisterResponse> regenerateOtp(@RequestParam("email") String email){
        return ResponseEntity.ok(authService.reGenerateOtp(email));
    }
    //------------------------------------------------------------------------------------------------
    @PostMapping("/activate")
    public ResponseEntity<LoginResponse> activate(@RequestParam("username") String username, @RequestHeader("OTP") String OTP){
        return ResponseEntity.ok(authService.activate(username,OTP));
    }

    @RequestMapping("/forgetPassword")
    public ResponseEntity<RegisterResponse> forgetPassword(@RequestParam("email") String email){
        return ResponseEntity.ok(authService.forgetPassword(email));
    }

    //======= Authorized EndPoints===================================================================
    //================================================================================================

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.changePassword(loginRequest));
    }

    @RequestMapping("/checkToken")
    public ResponseEntity<Object> checkToken(){
        return authService.checkToken();
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody RegisterRequest registerRequest){
        return authService.updateUser(registerRequest);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody LoginRequest loginRequest){
        return authService.deleteUser(loginRequest);
    }





}
