package com.example.math_race.dto.wsMessage.response;

import com.example.math_race.race.RaceManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeRaceNameDTO {
    private String raceName;

    public ChangeRaceNameDTO(RaceManager raceManager) {
        this.raceName = raceManager.getSettings().getRaceName();
    }
}
