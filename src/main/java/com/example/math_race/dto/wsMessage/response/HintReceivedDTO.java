package com.example.math_race.dto.wsMessage.response;

import com.example.math_race.race.RacePlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HintReceivedDTO {

    private String hint;
    private String playerId;

    public HintReceivedDTO(RacePlayer player) {
        this.hint = player.getCurrentQuestion().getHint();
        this.playerId = player.getId();
    }
}
