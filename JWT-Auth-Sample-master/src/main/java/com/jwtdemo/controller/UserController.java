package com.jwtdemo.controller;


import com.jwtdemo.config.JwtService;
import com.jwtdemo.model.User;
import com.jwtdemo.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
@PreAuthorize("hasRole('USER')")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public String test(){
        return "USER controller:: access granted";
    }

    @GetMapping("/userDetails")
    public Optional<User> getUserDetails(@RequestHeader(value = "Authorization") String auth){
        Optional<User> user = null;
        if (auth != null || !auth.startsWith("Bearer ")){
            String jwt = auth.substring(7);
            String email = jwtService.extractUsername(jwt);
            user = userRepo.findByEmail(email);
        }
        return user;
    }

}
