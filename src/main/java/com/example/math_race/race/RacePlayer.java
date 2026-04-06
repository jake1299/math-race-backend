package com.example.math_race.race;

import com.example.math_race.entities.UserEntity;
import com.example.math_race.race.questions.MathQuestion;
import lombok.*;

import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class RacePlayer extends RaceAccount{
    private MathQuestion currentQuestion;
    private long questionRemainingTimeMs;
    private long questionStartTimeAtMs;
    private int currentScore;
    private int regularQAttempts;
    private int regularQSuccesses;
    private long regularQTimeMs;

    public RacePlayer(String accountId, String sessionActive, String joinToken ,String nickname){
        this(accountId,null,sessionActive,joinToken,nickname);
    }

    public RacePlayer(UserEntity user, String sessionActive, String joinToken , String nickname){
        this(user.getId()+"",user,sessionActive,joinToken,nickname);
    }

    public RacePlayer(String accountId, UserEntity user, String sessionActive, String joinToken ,String nickname){
        super(accountId,user,sessionActive,joinToken,nickname);
        this.currentScore = 0;
        this.regularQAttempts = 0;
    }

    public boolean checkAnswer(String answer){
        return currentQuestion != null && Objects.equals(currentQuestion.getCorrectAnswer(),answer);
    }

    public void addScore(int score){

        this.currentScore += score;
    }

    public long getCalculatedQuestionRemainingTime(RaceStatus currentRaceStatus) {
        if (currentRaceStatus != RaceStatus.IN_PROGRESS) {
            return this.questionRemainingTimeMs;
        }

        long timeElapsed = System.currentTimeMillis() - this.questionStartTimeAtMs;
        long actualRemaining = this.questionRemainingTimeMs - timeElapsed;

        return Math.max(0, actualRemaining);
    }

    public void addRegularAttempt() {
        this.regularQAttempts += 1;
    }

    public void addRegularSuccess() {
        this.regularQSuccesses += 1;
    }

    public void addRegularTimeMs(long timeMs) {
        this.regularQTimeMs += timeMs;
    }

    public long getQuestionTimeSpent() {
        long timeElapsed = System.currentTimeMillis() - this.questionStartTimeAtMs;

        return Math.min(timeElapsed, currentQuestion.getTimeLimitMillis());
    }

    @Override
    public String toString() {
        return "RacePlayer{" +
                "currentScore= " + currentScore +
                " id= " + getId()+
                " nickname= " + getNickname() +

                '}';
    }
}
