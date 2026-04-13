package com.example.math_race.race;

public enum PlayerTrackState {
    REGULAR("medium",30),                // שאלות רגילות
    WAITING_FOR_CHOICE(10),     // השחקן קיבל צומת וממתינים שיבחר
    AUTOSTRADA("hard",90,1),             // השחקן במסלול אוטוסטרדה (שאלה 1 קשה)
    DIRT_ROAD("easy",15,10);            // השחקן בשביל עפר (10 שאלות קלות)

    private final String level;
    private final long timeLimitSeconds;
    private final Integer questionsNumber;

    PlayerTrackState(String level, long timeLimitSeconds, Integer questionsNumber) {
        this.level = level;
        this.timeLimitSeconds = timeLimitSeconds;
        this.questionsNumber = questionsNumber;
    }

    PlayerTrackState(String level, long timeLimitSeconds) {
     this(level, timeLimitSeconds, null);
    }

    PlayerTrackState(long timeLimitSeconds) {
        this(null, timeLimitSeconds);
    }

    public String getLevel() {
        return level;
    }

    public long getTimeLimitSeconds() {
        return timeLimitSeconds;
    }

    public long getTimeLimitMillis() {
        return timeLimitSeconds * 1000;
    }

    public Integer getQuestionsNumber() {
        return questionsNumber;
    }
 }