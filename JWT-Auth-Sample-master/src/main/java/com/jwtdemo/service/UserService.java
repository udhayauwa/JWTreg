package com.jwtdemo.service;

import com.jwtdemo.auth.UserUpdateRequest;
import com.jwtdemo.model.User;
import com.jwtdemo.repo.UserRepo;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface UserService {

    void sendVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException;

    boolean verify(String code);
    boolean updateUser(UserUpdateRequest userUpdateRequest);
}
