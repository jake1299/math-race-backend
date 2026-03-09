package com.example.math_race.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    AUTH_FAILED(1000, "Invalid email or password");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
