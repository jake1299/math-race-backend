package com.example.math_race.race.questions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item implements QuestionEntity {
    private String singular;
    private String plural;
    private Gender gender;
    private Set<ItemCategory> categories;

    public Item(String singular, String plural, Gender gender, ItemCategory... categories) {
        this.singular = singular;
        this.plural = plural;
        this.gender = gender;
        this.categories = new HashSet<>(Arrays.asList(categories));
    }

    @Override
    public String getProperty(String key) {
        if ("s".equals(key)) return singular;
        if ("p".equals(key)) return plural;
        if ("g".equals(key)) return gender.name();

        if ("t".equalsIgnoreCase(key)) {
            return categories.stream()
                    .map(Enum::name)
                    .collect(java.util.stream.Collectors.joining("|"));
        }

        if ("one".equals(key)) {
            return gender == Gender.MALE ? "אחד" : "אחת";
        }

        return singular;
    }

    public boolean matches(Map<String, String> constraints) {

        if (constraints.containsKey("type") && !constraints.get("type").equals("?")) {
            String rawExpr = constraints.get("type").trim().toUpperCase();
            boolean isNegated = rawExpr.startsWith("!");

            if (isNegated) {
                rawExpr = rawExpr.substring(1);
            }

            String[] orGroups = rawExpr.split("\\|");
            boolean expressionMatch = false;

            for (String group : orGroups) {

                String[] andRequirements = group.split("&");
                boolean allInGroupMatch = true;

                for (String req : andRequirements) {
                    try {
                        ItemCategory cat = ItemCategory.valueOf(req.trim());
                        if (!this.categories.contains(cat)) {
                            allInGroupMatch = false;
                            break;
                        }
                    } catch (IllegalArgumentException e) {
                        allInGroupMatch = false;
                        break;
                    }
                }

                if (allInGroupMatch) {
                    expressionMatch = true;
                    break;
                }
            }

            if (isNegated) {
                if (expressionMatch) return false;
            } else {
                if (!expressionMatch) return false;
            }
        }


        if (constraints.containsKey("s") && !constraints.get("s").equals("?")) {
            String reqS = constraints.get("s").trim();
            if (reqS.startsWith("!")) {
                if (this.singular.equalsIgnoreCase(reqS.substring(1))) return false;
            } else {
                if (!this.singular.equalsIgnoreCase(reqS)) return false;
            }
        }

        if (constraints.containsKey("p") && !constraints.get("p").equals("?")) {
            String reqP = constraints.get("p").trim();
            if (reqP.startsWith("!")) {
                if (this.plural.equalsIgnoreCase(reqP.substring(1))) return false;
            } else {
                if (!this.plural.equalsIgnoreCase(reqP)) return false;
            }
        }

        if (constraints.containsKey("g") && !constraints.get("g").equals("?")) {
            String reqGender = constraints.get("g").trim().toLowerCase();
            boolean isNot = reqGender.startsWith("!");
            String genderVal = isNot ? reqGender.substring(1) : reqGender;

            boolean isMaleMatch = (genderVal.equals("m") || genderVal.equals("male")) && this.gender == Gender.MALE;
            boolean isFemaleMatch = (genderVal.equals("f") || genderVal.equals("female")) && this.gender == Gender.FEMALE;

            if (isNot) {
                if (isMaleMatch || isFemaleMatch) return false;
            } else {
                if (!isMaleMatch && !isFemaleMatch) return false;
            }
        }

        return true;
    }
}