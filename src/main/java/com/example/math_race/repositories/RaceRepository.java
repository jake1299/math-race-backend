package com.example.math_race.repositories;

import com.example.math_race.entities.RaceHistoryEntity;
import com.example.math_race.entities.RaceParticipantHistoryEntity;
import com.example.math_race.race.RaceManager;
import com.example.math_race.race.RacePlayer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class RaceRepository extends BaseRepository{

    @Autowired
    public RaceRepository(SessionFactory sf) {
        super(sf);
    }

    public void saveRaceToHistory(RaceManager race) {
        RaceHistoryEntity raceHistoryEntity = new RaceHistoryEntity(race);
        save(raceHistoryEntity);

        List<RacePlayer> rankedPlayers = race.getRankedPlayers();
        int currentRank = 1;

        for (RacePlayer player : rankedPlayers) {
            RaceParticipantHistoryEntity raceParticipantHistory =
                    new RaceParticipantHistoryEntity(raceHistoryEntity, player, currentRank);

            save(raceParticipantHistory);
            currentRank++;
        }

        getCurrentSession().flush();
        System.out.println("הכל נשמר ? " + raceHistoryEntity.getId());
    }
}
