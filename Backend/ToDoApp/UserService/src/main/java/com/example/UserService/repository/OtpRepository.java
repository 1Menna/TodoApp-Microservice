package com.example.UserService.repository;

import com.example.UserService.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp,Integer> {
}
