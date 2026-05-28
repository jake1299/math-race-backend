package com.example.math_race.models.dictionary;


import java.util.Map;

public record VerbJsonModel(
        String id,
        Map<String, String> forms
) {}
