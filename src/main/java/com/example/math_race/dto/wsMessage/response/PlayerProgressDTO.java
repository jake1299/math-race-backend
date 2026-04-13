package com.example.math_race.dto.wsMessage.response;

import com.example.math_race.race.PlayerTrackState;
import com.example.math_race.race.RaceManager;
import com.example.math_race.race.RacePlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerProgressDTO {
    private String id;
    private String nickname;
    private Integer currentScore;
    private MathQuestionDTO currentQuestion;
    private PlayerTrackState trackState;
    private boolean isOnline;

    public PlayerProgressDTO(RaceManager race, RacePlayer player, boolean showScore){
        this.id = player.getId();
        this.nickname = player.getNickname();
        this.currentScore = showScore ? player.getCurrentScore() : null;
        this.isOnline = player.isConnected();
        this.trackState =  showScore ? player.getTrackState() : null;
        this.currentQuestion = showScore && race.getStatus().isRunning() ? new MathQuestionDTO(race,player,player.getCurrentQuestion()) : null;
    }
}
