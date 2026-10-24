package com.example.UserService.service;

import com.example.UserService.entity.Otp;
import com.example.UserService.entity.User;
import com.example.UserService.repository.OtpRepository;
import com.example.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Optional;

@Service
public class OtpService {
    @Autowired
    OtpRepository otpRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    void createOTPAndSendIt(User user){
//        String otpCode = String.format("%06d",new SecureRandom().nextInt(1000000));
        String otpCode = "123456";
        int validateOtp = 5*60*1000; // 60 sec
        Otp otp = new Otp(otpCode,new Date(System.currentTimeMillis()+validateOtp),user);
        otpRepository.save(otp);

        // send otp to this email
//        emailService.SendMessage(user.getEmail(), "OTP Activation", otpCode);
    }

    //===========================================================================================
    boolean isValidOtp(String email, String otpCode){
        User user = userRepository.findUserByEmail(email);
        Otp otp = otpRepository.findTopByUserOrderByExpirationTimeDesc(user);
        return (otp.getOtpCode().equals(otpCode) && otp.getExpirationTime().after(new Date(System.currentTimeMillis())));
    }
    void activateUser(String email, String otpCode){
        if(isValidOtp(email,otpCode)){
            User user = userRepository.findUserByEmail(email);
            user.setEnabled(true);
            userRepository.save(user);
        }
        else{
            throw new RuntimeException("otp code is not valid");
        }
    }

}
