package com.example.math_race.questionGenerator.tags.core;

import java.util.Map;

public interface MatchableTag extends TemplateTag {
    boolean matches(Map<String, String> constraints);
}
