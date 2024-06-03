package com.jwtdemo.service;

import com.jwtdemo.auth.RegisterRequest;
import com.jwtdemo.auth.UserUpdateRequest;
import com.jwtdemo.model.User;
import com.jwtdemo.repo.UserRepo;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

@Service
public interface UserService {

    void sendVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException;
    void deleteUser(int id);
    boolean verify(String code);
    boolean updateUser(RegisterRequest registerRequest);

    List<User> getAllUsers();
    
    
}
