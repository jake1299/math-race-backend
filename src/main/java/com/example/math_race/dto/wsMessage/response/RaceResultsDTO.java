package com.example.math_race.dto.wsMessage.response;

import com.example.math_race.race.RaceManager;
import com.example.math_race.race.RacePlayer;
import com.example.math_race.race.RaceStatistics;
import com.example.math_race.race.RaceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceResultsDTO {
    private RaceStatus status;
    private List<PlayerProgressDTO> players;
    private RaceStatisticsDTO statistics;

    public RaceResultsDTO(RaceManager race) {
        this.status = race.getStatus();
        this.players = new ArrayList<>();
        this.statistics = new RaceStatisticsDTO(new RaceStatistics(race));

        for (RacePlayer player : race.getPlayers().values()){
            this.players.add(new PlayerProgressDTO(race,player,true,true));
        }

    }
}
