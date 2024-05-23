package com.jwtdemo.service.impl;

import com.jwtdemo.auth.UserUpdateRequest;
import com.jwtdemo.model.User;
import com.jwtdemo.repo.UserRepo;
import com.jwtdemo.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	
	
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
        String verifyUrl = "http://locahost:8080/v1/auth/verify?code="+user.getVerificationCode();
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
    public boolean updateUser(UserUpdateRequest userUpdateRequest) {
        Optional <User> optionalUser = userRepo.findById(userUpdateRequest.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(userUpdateRequest.getFirstName());
            user.setLastName(userUpdateRequest.getLastName());
            user.setMobileNo(userUpdateRequest.getMobileNo());
            user.setAlternativeNo(userUpdateRequest.getAlternativeNo());
            user.setEmail(userUpdateRequest.getEmail());
            user.setPassword(userUpdateRequest.getPassword());
            user.setCollegeName(userUpdateRequest.getCollegeName());
            user.setCollegeLocation(userUpdateRequest.getCollegeLocation());
            user.setDegree(userUpdateRequest.getDegree());
            user.setStream(userUpdateRequest.getStream());
            user.setPercentage(userUpdateRequest.getPercentage());
            user.setPassingYear(userUpdateRequest.getPassingYear());
            user.setArrears(userUpdateRequest.getArrears());
            user.setLocation(userUpdateRequest.getLocation());
            user.setExperience(userUpdateRequest.getExperience());
            user.setLanguages(userUpdateRequest.getLanguages());
            userRepo.save(user);
            return true;
        }
        return false;
}
    
    
}
