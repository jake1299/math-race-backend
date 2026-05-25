package com.example.math_race.race;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RandomEventEngine {

    private static final int JUNCTION_MAX_ROLL = 1000;
    private static final int HINT_MAX_ROLL = 100;

    public boolean shouldTriggerJunction(RacePlayer player, RaceManager manager) {
        int targetScore = manager.getSettings().getTargetScore();

        double progress = (double) player.getCurrentScore() / targetScore;
        if (progress < 0.15) return false;

        int questionsSinceLastJunction = player.getRegularQAttempts() - player.getLastJunctionRegularQCount();
          if (questionsSinceLastJunction < 5) return false;

        int threshold = 100;

        List<RacePlayer> players = manager.getRankedPlayers();
        int leaderScore = players.get(players.size()-1).getCurrentScore();
        int gapFromLeader = leaderScore - player.getCurrentScore();

        if (gapFromLeader > (targetScore * 0.15)) {
            threshold += 200;
        }

        if (player.getCurrentScore() == leaderScore) {
            threshold -= 50;
        }

        int streakBonus = Math.min(player.getCurrentRegularStreak() * 20, 100);
        threshold += streakBonus;

        double timeElapsedPercent = 1.0 - ((double) manager.getCalculatedRemainingTime() / manager.getSettings().getTotalDurationTimeMs());
        threshold += (int) (timeElapsedPercent * 150);

        threshold = Math.max(50, Math.min(threshold, 600));

        int roll = ThreadLocalRandom.current().nextInt(JUNCTION_MAX_ROLL);
        return roll < threshold;
    }

    public boolean shouldGiveHint(RacePlayer player, RaceManager manager) {
        int targetScore = manager.getSettings().getTargetScore();

        int expectedHintsQuota = Math.max(2, targetScore / 500);

        if (player.getNumHintsReceived() < expectedHintsQuota) {
            return true;
        }

        int threshold = 10;

        List<RacePlayer> rankedPlayers = manager.getRankedPlayers();
        int totalPlayers = rankedPlayers.size();

        if (totalPlayers > 0) {
            int playerRank = rankedPlayers.indexOf(player);

            if (playerRank >= 0 && playerRank <= 2) {
                threshold -= 5;
            }

            if (playerRank >= totalPlayers / 2 && totalPlayers >= 3) {
                threshold += 15;

                int leaderScore = rankedPlayers.get(0).getCurrentScore();
                int gapFromLeader = leaderScore - player.getCurrentScore();

                if (gapFromLeader > (targetScore * 0.25)) {
                    threshold += 20;
                }
            }
        }

        int streakBonus = Math.min(player.getCurrentRegularStreak() * 3, 15);
        threshold += streakBonus;

        threshold = Math.max(2, Math.min(threshold, 60));

        int roll = ThreadLocalRandom.current().nextInt(HINT_MAX_ROLL);
        return roll < threshold;
    }
}
