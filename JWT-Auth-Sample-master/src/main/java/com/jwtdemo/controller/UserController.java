package com.jwtdemo.controller;


import com.jwtdemo.auth.RegisterRequest;
import com.jwtdemo.config.JwtService;
import com.jwtdemo.model.User;
import com.jwtdemo.repo.UserRepo;
import com.jwtdemo.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class UserController {
	
	private final UserService userService;

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
    
    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody RegisterRequest registerRequest) {
        boolean isUpdated = userService.updateUser(registerRequest);
        if (isUpdated) {
            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.status(400).body("Failed to update user");
        }
    }
    
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("users/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/users/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/users/phone/{mobileNo}")
    public ResponseEntity<User> getUserByMobileNo(@PathVariable Long mobileNo) {
        User user = userService.getUserByMobileNo(mobileNo);
        return ResponseEntity.ok(user);
    }
    
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
        
    }
   
    @DeleteMapping("/users/email/{email}")
    public ResponseEntity<String> deleteUserByEmail(@PathVariable String email) {
        userService.deleteUserByEmail(email);
        return ResponseEntity.ok("User with email " + email + " is deleted successfully");
    }

    @DeleteMapping("/users/phone/{mobileNo}")
    public ResponseEntity<String> deleteUserByMobileNo(@PathVariable Long mobileNo) {
        userService.deleteUserByMobileNo(mobileNo);
        return ResponseEntity.ok("User with mobile number " + mobileNo + " is deleted successfully");
    }
    
}

