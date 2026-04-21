package com.example.math_race.dto.wsMessage.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageToPlayerRequest {

    @NotBlank(message = "player id is required")
    private String playerId;

    @NotBlank(message = "message is required")
    private String message;
}
