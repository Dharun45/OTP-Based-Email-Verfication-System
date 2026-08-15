package com.dharun.otp_verification.controller;

import com.dharun.otp_verification.dto.RegisterRequest;
import com.dharun.otp_verification.dto.VerifyOtpRequest;
import com.dharun.otp_verification.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestBody VerifyOtpRequest verifyOtpRequest) {
        return userService.verifyOtp(verifyOtpRequest);
    }

    @PostMapping("/resend-otp")
    public String resendOtp(@RequestParam String email) {
        return userService.resendOtp(email);
    }
}