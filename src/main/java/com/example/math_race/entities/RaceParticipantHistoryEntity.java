package com.example.math_race.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class RaceParticipantHistoryEntity extends BaseEntity {

    private RaceHistoryEntity race;
    private String token;
    private UserEntity user;
    private String nickname;
    private int finalScore;

    public RaceParticipantHistoryEntity() {
        this.token = UUID.randomUUID().toString().substring(0, 4); // הוספת מחולל טוקנים
        this.finalScore = 0;
    }

    public RaceParticipantHistoryEntity(UserEntity user, String nickname) {
        this();
        this.user = user;
        this.nickname = nickname;
    }

    public RaceParticipantHistoryEntity(UserEntity user) {
        this(user, user.getUsername());
    }

    public RaceParticipantHistoryEntity(String nickname) {
        this(null, nickname);
    }

    public boolean isGust() {
        return user == null;
    }
}