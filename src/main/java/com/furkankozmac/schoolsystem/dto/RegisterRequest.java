package com.furkankozmac.schoolsystem.dto;

import com.furkankozmac.schoolsystem.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private Role role;
}
