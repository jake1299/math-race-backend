package com.example.math_race.entities;

import com.example.math_race.race.RacePlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceParticipantHistoryEntity extends BaseEntity {

    private RaceHistoryEntity race;
    private UserEntity user;
    private String nickname;
    private int finalScore;


    public RaceParticipantHistoryEntity(RaceHistoryEntity raceHistoryEntity, RacePlayer player) {
        super();
        this.race = raceHistoryEntity;
        this.user = player.getUser();
        this.nickname = player.getNickname();
        this.finalScore = player.getCurrentScore();
    }


    public boolean isGust() {
        return user == null;
    }
}