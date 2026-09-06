package com.example.UserService.repository;

import com.example.UserService.entity.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtRepository extends JpaRepository<Jwt,Integer> {
}
