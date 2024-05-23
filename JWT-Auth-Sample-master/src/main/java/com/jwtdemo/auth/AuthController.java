package com.jwtdemo.auth;


import com.jwtdemo.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(value = "*")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) throws MessagingException, UnsupportedEncodingException {
        AuthenticationResponse authResponse = authService.register(registerRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code){
        if (userService.verify(code)){
            System.out.println("success");
            return "verify success";
        }else {
            System.out.println("verify failed");
            return "verify failed";
        }
    }
    
    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        boolean isUpdated = userService.updateUser(userUpdateRequest);
        if (isUpdated) {
            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.status(400).body("Failed to update user");
        }
    }
}
