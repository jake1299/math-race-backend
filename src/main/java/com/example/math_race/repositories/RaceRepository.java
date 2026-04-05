package com.example.math_race.repositories;

import com.example.math_race.entities.RaceHistoryEntity;
import com.example.math_race.entities.RaceParticipantHistoryEntity;
import com.example.math_race.race.RaceManager;
import com.example.math_race.race.RacePlayer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class RaceRepository extends BaseRepository{

    @Autowired
    public RaceRepository(SessionFactory sf) {
        super(sf);
    }

    public void saveRaceToHistory(RaceManager race){
        RaceHistoryEntity raceHistoryEntity = new RaceHistoryEntity(race);
        save(raceHistoryEntity);
        for (RacePlayer player : race.getPlayers().values()){
            RaceParticipantHistoryEntity raceParticipantHistory = new RaceParticipantHistoryEntity(raceHistoryEntity, player);
            save(raceParticipantHistory);
        }
    }
}
