package com.example.math_race.dto.wsMessage.response;

import com.example.math_race.race.RaceManager;
import com.example.math_race.race.RacePlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerJoinedDTO {

    private PlayerProgressDTO player;

    public PlayerJoinedDTO(RaceManager race,RacePlayer player, boolean showScore){
        this.player = new PlayerProgressDTO(race,player,showScore);
    }
}
