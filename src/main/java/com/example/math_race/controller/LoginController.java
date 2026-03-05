package com.example.math_race.controller;

import com.example.math_race.entities.RaceHistoryEntity;
import com.example.math_race.entities.RaceParticipantHistoryEntity;
import com.example.math_race.entities.TokenEntity;
import com.example.math_race.entities.UserEntity;
import com.example.math_race.repositories.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.example.math_race.entities.TokenEntity.TokenType.*;

@RestController
public class LoginController {

    @Autowired
    private BaseRepository baseRepository;

    // http://localhost:8085/test-login
    @RequestMapping("/test-login")
    public String testLogin() {
        UserEntity user = new UserEntity(Math.random()+"", "xxxx", Math.random()+"");
        TokenEntity token = new TokenEntity(Math.random()+"", SESSION, user, new Date(), null, null);
        baseRepository.save(user);
        baseRepository.save(token);

        System.out.println("עובד 1");

        RaceHistoryEntity raceHistory = new RaceHistoryEntity();
        raceHistory.setHost(user);
        raceHistory.setTargetScore(300);

        RaceParticipantHistoryEntity raceParticipantHistory = new RaceParticipantHistoryEntity();
        raceParticipantHistory.setRace(raceHistory);
        raceParticipantHistory.setFinalScore(89);
        raceParticipantHistory.setNickname("Ani");
        raceParticipantHistory.setUser(user);

        baseRepository.save(raceHistory);
        System.out.println("עובד 2");

        baseRepository.save(raceParticipantHistory);
        System.out.println("עובד 3");

        System.out.println("הדפסות");
        System.out.println(baseRepository.loadObject(UserEntity.class, 1));
        System.out.println(baseRepository.loadObject(TokenEntity.class, 1));
        System.out.println(baseRepository.loadObject(RaceHistoryEntity.class, 1));
        System.out.println(baseRepository.loadObject(RaceParticipantHistoryEntity.class, 1));
        return "השרת של Math Race עובד! הבקשה הגיעה אליי.";
    }
}
