package com.example.math_race.dto.wsMessage.response;

import com.example.math_race.race.RaceHost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostDetailsDTO {
    private String id;
    private String nickname;
    private String userName;
    private boolean isOnline;

    public HostDetailsDTO(RaceHost raceHost){
        this.id = raceHost.getId();
        this.userName = raceHost.getUser().getUsername();
        this.nickname = raceHost.getNickname();
        this.isOnline = raceHost.isConnected();
    }
}
