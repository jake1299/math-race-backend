package com.example.math_race.service;

import com.example.math_race.dto.request.LoginRequest;
import com.example.math_race.dto.response.LoginResponse;
import com.example.math_race.entities.UserEntity;
import com.example.math_race.exception.ErrorCode;
import com.example.math_race.exception.LogicException;
import com.example.math_race.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public LoginResponse loginUser(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            throw new LogicException(ErrorCode.AUTH_FAILED);
        }

        if (user.getPassword().equals(request.getPassword())) {
            throw new LogicException(ErrorCode.AUTH_FAILED);
        }

        return new LoginResponse(
                "this is a token",
                user.getUsername(),
                user.getEmail()
        );
    }
}
