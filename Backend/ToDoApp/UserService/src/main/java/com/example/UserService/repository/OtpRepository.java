package com.example.UserService.repository;

import com.example.UserService.entity.Otp;
import com.example.UserService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OtpRepository extends JpaRepository<Otp,Integer> {

    Otp findTopByUserOrderByExpirationTimeDesc(User user);
}
