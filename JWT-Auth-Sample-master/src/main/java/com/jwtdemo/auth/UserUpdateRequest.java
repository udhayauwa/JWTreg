package com.jwtdemo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
	private int id;
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
}