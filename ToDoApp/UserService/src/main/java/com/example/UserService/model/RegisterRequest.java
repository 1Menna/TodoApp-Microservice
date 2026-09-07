package com.example.UserService.model;
import com.example.UserService.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequest {
    String email;
    String Password;
    boolean enabled;
    Role role;
}
