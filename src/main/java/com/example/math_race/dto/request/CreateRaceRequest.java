package com.example.math_race.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class CreateRaceRequest {
    private String name;

    @NotNull(message = "Target score is required")
    @Positive(message = "Target score must be a positive number")
    private Integer targetScore;

    private String nickname;
}
