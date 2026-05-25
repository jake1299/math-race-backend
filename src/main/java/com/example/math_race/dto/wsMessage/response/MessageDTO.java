package com.example.math_race.dto.wsMessage.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private String from;
    private String to;
    private String message;
    private long sentAt;

    public MessageDTO(String from, String to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.sentAt = System.currentTimeMillis();
    }
}
