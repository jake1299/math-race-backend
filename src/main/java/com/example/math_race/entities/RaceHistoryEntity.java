package com.example.math_race.entities;

import com.example.math_race.race.RaceManager;
import com.example.math_race.race.RaceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceHistoryEntity extends BaseEntity {

    private String name;
    private String roomCode;
    private UserEntity host;
    private int targetScore;
    private String raceId;
    private RaceStatus status;
    private long createdAtMillis;
    private long endedAtMillis;
    private long totalDurationMillis;
    private long totalPausedDurationMillis;

    public RaceHistoryEntity(RaceManager raceManager) {
        super();
        this.name = raceManager.getSettings().getRaceName();
        this.roomCode = raceManager.getRoomCode();
        this.host = raceManager.getHost().getUser();
        this.targetScore = raceManager.getSettings().getTargetScore();
        this.raceId = raceManager.getId();
        this.status = raceManager.getStatus();
        this.createdAtMillis = raceManager.getCreatedAtMillis();
        this.endedAtMillis = raceManager.getEndedAtMillis();
        this.totalDurationMillis = raceManager.getSettings().getTotalDurationMillis();
        this.totalPausedDurationMillis = raceManager.getTotalPausedDurationMillis();
    }
}
