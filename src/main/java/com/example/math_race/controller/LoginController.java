package com.example.math_race.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    // http://localhost:8080/test-login
    @GetMapping("/test-login")
    public String testLogin() {
        return "השרת של Math Race עובד! הבקשה הגיעה אליי.";
    }
}
