package com.dharun.otp_verification.service;

import com.dharun.otp_verification.dto.RegisterRequest;
import com.dharun.otp_verification.dto.VerifyOtpRequest;
import com.dharun.otp_verification.entity.User;
import com.dharun.otp_verification.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpGenerator otpGenerator;

    @Autowired
    private EmailService emailService;

    public String register(RegisterRequest registerRequest) {
        // Check if email already exists
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return "Email already registered";
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setRole("USER");
        user.setVerified(false);

        String otp = otpGenerator.generateOtp();
        user.setOtp(otp);
        user.setOtpExpiryTime(LocalDateTime.now().plusMinutes(5));

        userRepository.save(user);

        emailService.sendOtp(registerRequest.getEmail(), otp);

        return "OTP sent to your email";
    }

    public String verifyOtp(VerifyOtpRequest verifyOtpRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(verifyOtpRequest.getEmail());

        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        User user = optionalUser.get();

        if (user.isVerified()) {
            return "User already verified";
        }

        if (!user.getOtp().equals(verifyOtpRequest.getOtp())) {
            return "Invalid OTP";
        }

        if (LocalDateTime.now().isAfter(user.getOtpExpiryTime())) {
            return "OTP expired. Please request a new one";
        }

        user.setVerified(true);
        user.setOtp(null); // clear OTP after successful verification
        user.setOtpExpiryTime(null);
        userRepository.save(user);

        return "Email verified successfully";
    }

    public String resendOtp(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        User user = optionalUser.get();

        if (user.isVerified()) {
            return "User already verified";
        }

        String otp = otpGenerator.generateOtp();
        user.setOtp(otp);
        user.setOtpExpiryTime(LocalDateTime.now().plusMinutes(5));
        userRepository.save(user);

        emailService.sendOtp(email, otp);

        return "New OTP sent to your email";
    }
}