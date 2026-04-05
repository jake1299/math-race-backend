package com.example.math_race.race;

import com.example.math_race.entities.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class RaceHost extends RaceAccount {

    public RaceHost(UserEntity user,String sessionActive, String joinToken,String nickname){
        super(user.getId()+"",user,sessionActive,joinToken,nickname);
    }

}
