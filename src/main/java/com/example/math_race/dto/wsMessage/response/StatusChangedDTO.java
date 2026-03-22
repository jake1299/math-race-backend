package com.example.math_race.dto.wsMessage.response;

import com.example.math_race.race.RaceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusChangedDTO {
    private RaceStatus status;
}
