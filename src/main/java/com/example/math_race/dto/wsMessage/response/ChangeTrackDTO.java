package com.example.math_race.dto.wsMessage.response;

import com.example.math_race.race.RacePlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeTrackDTO {
    private String state;
    private String playerId;
    private int totalTrackQuestions;

    public ChangeTrackDTO(RacePlayer player) {
        this.state = player.getTrackState().name();
        this.playerId = player.getId();

        Integer questionsNum = player.getTrackState().getQuestionsNumber();
        this.totalTrackQuestions = (questionsNum != null) ? questionsNum : 0;
    }
}
