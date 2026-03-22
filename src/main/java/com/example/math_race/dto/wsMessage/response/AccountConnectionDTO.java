package com.example.math_race.dto.wsMessage.response;

import com.example.math_race.race.RaceAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountConnectionDTO {
    private String id;
    private boolean isOnline;
    private String nickname;

    public AccountConnectionDTO(RaceAccount account) {
        this.id = account.getId();
        this.isOnline = account.isConnected();
        this.nickname = account.getNickname();
    }
}
