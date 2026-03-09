package com.example.math_race.exception;

import lombok.Getter;

@Getter
public class LogicException extends RuntimeException {
    private final ErrorCode errorCode;

    public LogicException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}