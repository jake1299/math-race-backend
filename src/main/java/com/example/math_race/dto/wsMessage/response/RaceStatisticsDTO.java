package com.example.math_race.dto.wsMessage.response;

import com.example.math_race.race.RacePlayer;
import com.example.math_race.race.RaceStatistics;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceStatisticsDTO {
    private double accuracyPercentage;
    private double autostradaPercentage;
    private double dirtRoadPercentage;
    private long averageResponseTimeMs;
    private int totalJunctionsOffered;

    private String streakMasterId;
    private int streakMasterAmount;

    private String accuracyKingId;
    private double accuracyKingPercentage;

    private String speedDemonId;
    private double speedDemonTimeMs;

    public RaceStatisticsDTO(RaceStatistics statistics) {
        this.accuracyPercentage = statistics.getAccuracyPercentage();
        this.autostradaPercentage = statistics.getAutostradaPercentage();
        this.dirtRoadPercentage = statistics.getDirtRoadPercentage();
        this.averageResponseTimeMs = statistics.getAverageResponseTimeMs();
        this.totalJunctionsOffered = statistics.getTotalJunctionsOffered();



        RacePlayer streakMaster = statistics.getStreakMaster();
        if (streakMaster != null) {
            this.streakMasterId = streakMaster.getId();
            this.streakMasterAmount = RaceStatistics.getPlayerMaxStreak(streakMaster);
        }

        RacePlayer accuracyKing = statistics.getAccuracyKing();
        if (accuracyKing != null) {
            this.accuracyKingId = accuracyKing.getId();
            this.accuracyKingPercentage = RaceStatistics.getPlayerAccuracyPercentage(accuracyKing);
        }

        RacePlayer speedDemon = statistics.getSpeedDemon();
        if (speedDemon != null) {
            this.speedDemonId = speedDemon.getId();
            this.speedDemonTimeMs = RaceStatistics.getPlayerAverageSuccessSpeedMs(speedDemon);
        }
    }
}
