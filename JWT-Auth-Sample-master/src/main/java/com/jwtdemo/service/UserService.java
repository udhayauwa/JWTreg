package com.jwtdemo.service;

import com.jwtdemo.auth.RegisterRequest;
import com.jwtdemo.model.User;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;


@Service
public interface UserService {

    void sendVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException;
    void deleteUser(int id);
    boolean verify(String code);
    boolean updateUser(RegisterRequest registerRequest);
    boolean deleteUserByEmail(String email);
    boolean deleteUserByMobileNo(Long mobileNo);
    
    List<User> getAllUsers();
	User getUserById(int id);
	User getUserByEmail(String email);
	User getUserByMobileNo(Long mobileNo);
	
	
    
}
