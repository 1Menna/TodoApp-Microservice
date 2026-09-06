package com.example.UserService.entity;

import com.example.UserService.entity.enums.TokenType;
import jakarta.persistence.*;

@Entity
@Table(name="jwt")
public class Jwt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "token")
    String token;

    @Column(name = "token_type") // to tell hibernate to store this value as string in DB, rather than index 0, 1, ...
    @Enumerated(EnumType.STRING)
    TokenType tokenType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
}
