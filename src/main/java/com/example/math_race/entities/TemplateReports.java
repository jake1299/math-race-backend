package com.example.math_race.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TemplateReports extends BaseEntity {

    public enum ReportsStatus {
        NEW,
        IN_PROGRESS,
        REJECTED,
        FIXED
    }

    private ReportsStatus status;
    private RaceParticipantHistoryEntity raceParticipantHistory;

}
