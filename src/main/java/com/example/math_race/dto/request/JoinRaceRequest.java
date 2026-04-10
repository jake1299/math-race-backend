package com.example.math_race.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class JoinRaceRequest {
    private String nickname;
}
