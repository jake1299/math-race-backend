package com.example.math_race.questionGenerator.tags.types;

import com.example.math_race.questionGenerator.tags.core.MatchableTag;
import com.example.math_race.questionGenerator.tags.enums.Gender;

import java.util.HashMap;
import java.util.Map;

import static com.example.math_race.questionGenerator.tags.enums.Gender.MALE;

public class VerbTag implements MatchableTag {
    private String id;
    private Map<String, String> forms = new HashMap<>();

    public VerbTag(String id) {
        this.id = id;
    }


    @Override
    public String getProperty(String key) {
        if (key == null || key.isEmpty()) {
            return getWord("past",MALE,"s");
        }

        if (key.equals("id")) {
            return id;
        }

        if (key.equalsIgnoreCase("INF")) {
            return getWord("INF",MALE,"ANY");

        }

        return forms.getOrDefault(key.trim().toUpperCase(), id);
    }


    public void addForm(String tense, Gender gender, String number, String word) {
        String key = tense.toUpperCase() + "_" + gender.name() + "_" + number.toUpperCase();
        forms.put(key, word);
    }

    public String getWord(String tense, Gender gender, String number) {
        String key = tense.toUpperCase() + "_" + gender.name() + "_" + number.toUpperCase();
        return forms.getOrDefault(key, id);
    }

    public boolean matches(Map<String, String> constraints) {
        if (constraints.containsKey("id") && !constraints.get("id").equals("?")) {
            String reqId = constraints.get("id").trim();

            if (reqId.startsWith("!")) {
                String excludedId = reqId.substring(1);

                if (this.id.equalsIgnoreCase(excludedId)) return false;
            } else {
                if (!this.id.equalsIgnoreCase(reqId)) return false;
            }
        }

        return true;
    }
}