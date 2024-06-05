package com.jwtdemo.service.impl;

import com.jwtdemo.auth.RegisterRequest;
import com.jwtdemo.model.User;
import com.jwtdemo.repo.UserRepo;
import com.jwtdemo.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
																							

@Service
public abstract class UserServiceImpl implements UserService {
	
	
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public void sendVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "udhayauwa@gmail.com";
        String senderName = "Udhayasankar";
        String subject = "Verify your registration";
        String content = "Dear [[name]],<br>" +
                "Please click the link below to verify your registration:<br>" +
                "<h3><a href=\"[[url]]\" target=\"_self\">VERIFY</a></h3>" +
                "Thank you,<br>" +
                "Udhay";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress,senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]",user.getFirstName());
        String verifyUrl = "http://localhost:8080/v1/auth/verify?code="+user.getVerificationCode();
        content = content.replace("[[url]]",verifyUrl);
        helper.setText(content,true);
        mailSender.send(message);
        System.out.println("mail sent");
    }

    @Override
    public boolean verify(String code) {
        User user = userRepo.findByVerificationCode(code);
        if (user == null || !user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepo.save(user);
            return true;
        }
    }
    
    @Override
    public boolean updateUser(RegisterRequest registerRequest) {
        User existingUser = userRepo.findById(registerRequest.getId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        User updatedUser = User.builder()
                .id(existingUser.getId())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .mobileNo(registerRequest.getMobileNo())
                .alternativeNo(registerRequest.getAlternativeNo())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .collegeName(registerRequest.getCollegeName())
                .collegeLocation(registerRequest.getCollegeLocation())
                .degree(registerRequest.getDegree())
                .stream(registerRequest.getStream())
                .percentage(registerRequest.getPercentage())
                .passingYear(registerRequest.getPassingYear())
                .arrears(registerRequest.getArrears())
                .location(registerRequest.getLocation())
                .experience(registerRequest.getExperience())
                .languages(registerRequest.getLanguages())
                .role(existingUser.getRole())
                .isEnabled(existingUser.isEnabled())
                .verificationCode(existingUser.getVerificationCode())
                .build();
        userRepo.save(updatedUser);
        return true;
    }
    
    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }    
    
    @Override
    public User getUserById(int id) {
        return userRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }
    
    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }
    
    @Override
    public User getUserByMobileNo(Long mobileNo) {
        return userRepo.findByMobileNo(mobileNo)
                .orElseThrow(() -> new EntityNotFoundException("User not found with mobile number: " + mobileNo));
    }
    
    @Override
    public void deleteUser(int id) {
        if (!userRepo.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepo.deleteById(id);
    }
    
    @Override
    public boolean deleteUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                             .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
        userRepo.delete(user);
        return true;
    }
    
    @Override
    public boolean deleteUserByMobileNo(Long mobileNo) {
        User user = userRepo.findByMobileNo(mobileNo)
                             .orElseThrow(() -> new EntityNotFoundException("User not found with mobile number: " + mobileNo));
        userRepo.delete(user);
        return true;
    }
}
