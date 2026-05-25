package com.example.math_race.dto.response;

import com.example.math_race.race.RaceManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicRaceInfoResponse {

    private String name;
    private String roomCode;
    private String hostNickname;
    private String hostUsername;
    private long startTime;
    private int targetScore;
    private int participants;


    public PublicRaceInfoResponse(RaceManager race) {
        this.name = race.getSettings().getRaceName();
        this.roomCode = race.getRoomCode();
        this.hostNickname = race.getHost().getNickname();
        this.hostUsername = race.getHost().getUser().getUsername();
        this.startTime = race.getCreatedAtMs();
        this.targetScore = race.getSettings().getTargetScore();
        this.participants = race.getPlayers().size();
    }
}
