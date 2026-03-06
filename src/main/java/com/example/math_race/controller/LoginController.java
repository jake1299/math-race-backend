package com.example.math_race.controller;

import com.example.math_race.dto.request.ChangePasswordRequest;
import com.example.math_race.dto.request.ForgotPasswordRequest;
import com.example.math_race.dto.request.LoginRequest;
import com.example.math_race.dto.request.RegisterRequest;
import com.example.math_race.repositories.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private BaseRepository baseRepository;

    // http://localhost:8085/api/auth/login
    @PostMapping("/login")
    public void login(@RequestBody LoginRequest request) {
        System.out.println("--- Login Request Received ---");
        System.out.println("Email: " + request.getEmail());
        System.out.println("Password: " + request.getPassword());
    }

    // http://localhost:8085/api/auth/register
    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest request) {
        System.out.println("--- Register Request Received ---");
        System.out.println("Username: " + request.getUsername());
        System.out.println("Email: " + request.getEmail());
        System.out.println("Password: " + request.getPassword());
    }

    // http://localhost:8085/api/auth/forgot-password
    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestBody ForgotPasswordRequest request) {
        System.out.println("--- Forgot Password Request Received ---");
        System.out.println("Email to reset: " + request.getEmail());
    }

    // http://localhost:8085/api/auth/change-password
    @PostMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordRequest request) {
        System.out.println("--- Change Password Request Received ---");
        System.out.println("New Password: " + request.getNewPassword());
    }
}