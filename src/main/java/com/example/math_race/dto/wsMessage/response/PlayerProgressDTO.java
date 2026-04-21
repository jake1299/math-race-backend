package com.example.math_race.dto.wsMessage.response;

import com.example.math_race.race.PlayerTrackState;
import com.example.math_race.race.RaceManager;
import com.example.math_race.race.RacePlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.math_race.race.PlayerTrackState.WAITING_FOR_CHOICE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerProgressDTO {
    private String id;
    private String userName;
    private String nickname;
    private String carColor;
    private Integer currentScore;
    private MathQuestionDTO currentQuestion;
    private JunctionOfferDTO currentJunction;
    private PlayerTrackState trackState;
    private boolean isOnline;

    public PlayerProgressDTO(RaceManager race, RacePlayer player, boolean showScore){
        this.id = player.getId();
        this.carColor = player.getCarColor();
        this.userName = player.isGuest() ? null : player.getUser().getUsername();
        this.nickname = player.getNickname();
        this.currentScore = showScore ? player.getCurrentScore() : null;
        this.isOnline = player.isConnected();
        this.trackState =  showScore ? player.getTrackState() : null;
        this.currentQuestion = showScore && !race.getStatus().isClosed() && player.getCurrentQuestion() != null ? new MathQuestionDTO(race,player,player.getCurrentQuestion()) : null;
        this.currentJunction = showScore ? player.getTrackState().equals(WAITING_FOR_CHOICE) ? new JunctionOfferDTO(race,player) : null : null;
    }
}
