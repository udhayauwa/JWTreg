package com.jwtdemo.auth;

import com.jwtdemo.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private Long mobileNo;
    private Long alternativeNo;
    private String email;
    private String password;

    private String collegeName;
    private String collegeLocation;
    private String degree;
    private String stream;
    private String percentage;
    private String passingYear;
    private Boolean arrears;

    private String location;
    private int experience;
    private String languages;

    private Role role;
}
