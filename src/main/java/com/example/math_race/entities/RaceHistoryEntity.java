package com.example.math_race.entities;

import com.example.math_race.race.RaceManager;
import com.example.math_race.race.RaceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceHistoryEntity extends BaseEntity {

    private String name;
    private String roomCode;
    private UserEntity host;
    private int targetScore;
    private RaceStatus status;
    private long createdAtMillis;
    private long endedAtMillis;
    private long totalDurationMillis;
    private long totalPausedDurationMillis;

    public RaceHistoryEntity(RaceManager raceManager) {
        super();
        setId(raceManager.getId());
        this.name = raceManager.getSettings().getRaceName();
        this.roomCode = raceManager.getRoomCode();
        this.host = raceManager.getHost().getUser();
        this.targetScore = raceManager.getSettings().getTargetScore();
        this.status = raceManager.getStatus();
        this.createdAtMillis = raceManager.getCreatedAtMs();
        this.endedAtMillis = raceManager.getEndedAtMs();
        this.totalDurationMillis = raceManager.getSettings().getTotalDurationTimeMs();
        this.totalPausedDurationMillis = raceManager.getTotalPausedDurationTimeMs();
    }
}
