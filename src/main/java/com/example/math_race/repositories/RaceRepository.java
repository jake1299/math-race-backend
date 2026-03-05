package com.example.math_race.repositories;

import com.example.math_race.entities.RaceEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class RaceRepository extends BaseRepository {

    @Autowired
    public RaceRepository(SessionFactory sf) {
        super(sf);
    }

    public void saveRace(RaceEntity race) {

    }

    public RaceEntity loadRace(int oid) {
        return null;
    }
}