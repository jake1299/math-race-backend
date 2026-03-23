package com.example.math_race.race.questions;

import com.example.math_race.race.RacePlayer;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.math_race.race.questions.ItemCategory.*;

@Component
public class MathQuestionGenerator {


    static ArrayList<Human> humans =  new ArrayList<>();
    static ArrayList<Item> items =  new ArrayList<>();
    static ArrayList<Verb> verbs = new ArrayList<>();
    static ArrayList<Place> places = new ArrayList<>();
    static {
        fillHumans();
        fillVerbs();
        fillItems();
        fillPlaces();
    }


    String template = "[HUMAN:g=?:#1] [VERB:id=buy;g=(#1:g);t=past;num=s:#2] [NUM:min=2;max=10:mul_3:#3] [ITEM:type=FOOD:p:#4]";
// זה יכול להדפיס: "Noa קנתה 5 apples" או "Shimon קנה 3 bananas"

    String t = "[ITEM:param=m;param=?:take:name]";



    public static String gene(String template, Map<String, QuestionEntity> memory) {
        Set<String> tags = extractUniqueTags(template);

        if (memory == null) {
            memory = new HashMap<>();
        }
        String result = template;

        for (String tag : tags) {

            if (tag.startsWith("[IF:")) {
                try {
                    int secondColon = tag.indexOf(":", 4);
                    if (secondColon == -1) continue;
                    String conditionFromTag = tag.substring(4, secondColon);

                    String regex = "\\[IF:" + java.util.regex.Pattern.quote(conditionFromTag) + ":<(.*?)>:<(.*?)>]";
                    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
                    java.util.regex.Matcher matcher = pattern.matcher(result);

                    if (matcher.find()) {
                        String fullMatchInResult = matcher.group(0);
                        String trueOption = matcher.group(1);
                        String falseOption = matcher.group(2);

                        String operator = "";
                        if (conditionFromTag.contains(">=")) operator = ">=";
                        else if (conditionFromTag.contains("<=")) operator = "<=";
                        else if (conditionFromTag.contains(">")) operator = ">";
                        else if (conditionFromTag.contains("<")) operator = "<";
                        else if (conditionFromTag.contains("=")) operator = "=";

                        if (!operator.isEmpty()) {
                            int opIndex = conditionFromTag.indexOf(operator);
                            String leftSide = conditionFromTag.substring(0, opIndex).trim();
                            String expectedStr = conditionFromTag.substring(opIndex + operator.length()).trim();

                            String entityId = leftSide;
                            String propertyKey = "";

                            if (leftSide.contains(":")) {
                                String[] varParts = leftSide.split(":", 2);
                                entityId = varParts[0].trim();
                                propertyKey = varParts[1].trim();
                            }

                            String actualStr = "";
                            if (memory.containsKey(entityId)) {
                                actualStr = memory.get(entityId).getProperty(propertyKey);
                            }

                            boolean conditionMet = false;
                            if (operator.equals("=")) {
                                conditionMet = actualStr.equals(expectedStr);
                            } else {
                                try {
                                    double actualNum = Double.parseDouble(actualStr);
                                    double expectedNum = Double.parseDouble(expectedStr);
                                    conditionMet = switch (operator) {
                                        case ">" -> actualNum > expectedNum;
                                        case "<" -> actualNum < expectedNum;
                                        case ">=" -> actualNum >= expectedNum;
                                        case "<=" -> actualNum <= expectedNum;
                                        default -> conditionMet;
                                    };
                                } catch (NumberFormatException nfe) {
                                    System.out.println("Warning: Numeric comparison failed for: " + actualStr);
                                }
                            }

                            String chosenText = conditionMet ? trueOption : falseOption;
                            String resolvedText = gene(chosenText, memory);
                            result = result.replace(fullMatchInResult, resolvedText);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing IF tag: " + tag);
                }
                continue;
            }
            TagInfo info = TagInfo.parse(tag);
            if (!tag.startsWith("[#")) {
                Map<String, String> resolvedConstraints = new HashMap<>();

                for (Map.Entry<String, String> entry : info.getConstraints().entrySet()) {
                    resolvedConstraints.put(entry.getKey(), resolveValue(entry.getValue(), memory));
                }

                QuestionEntity chosen = null;
                if ("HUMAN".equals(info.getType())) chosen = findHuman(resolvedConstraints);
                else if ("ITEM".equals(info.getType())) chosen = findItem(resolvedConstraints);
                else if ("NUM".equals(info.getType())) chosen = findNumber(resolvedConstraints);
                else if ("VERB".equals(info.getType())) chosen = findVerb(resolvedConstraints);
                else if ("PLACE".equals(info.getType())) chosen = findPlace(resolvedConstraints);

                if (chosen != null) {
                    memory.put(info.getId(), chosen);

                    String property = info.getProperty();
                    String newProperty = splitBeforeAndWithinParentheses(property);
                    String[] splitNewProperty;

                    if (newProperty != null) {
                        splitNewProperty = newProperty.split(":",2);
                        property = splitNewProperty[0] + resolveValue(splitNewProperty[1], memory);
                    }

                    String resolvedProp = resolveValue(property, memory);
                    result = result.replace(tag, !info.getProperty().equals("*") ? chosen.getProperty(resolvedProp) : "");
                }
            } else {
                if (memory.containsKey(info.getId())) {
                    QuestionEntity entity = memory.get(info.getId());

                    String resolvedProp = resolveValue(info.getProperty(), memory);
                    result = result.replace(tag, entity.getProperty(resolvedProp));
                }
            }
        }
        return result;
    }

    public static Set<String> extractUniqueTags(String template) {
        Set<String> tags = new LinkedHashSet<>();

        int indexStart = -1;
        int depth = 0; // המונה שמציל אותנו

        for (int i = 0; i < template.length(); i++) {
            char c = template.charAt(i);

            if (c == '[') {
                if (depth == 0) {
                    indexStart = i;
                }
                depth++;

            } else if (c == ']') {
                if (depth > 0) {
                    depth--;

                    if (depth == 0) {
                        String tag = template.substring(indexStart, i + 1);
                        tags.add(tag);
                        indexStart = -1;
                    }
                }
            }
        }

        return tags;
    }

//    public static Set<String> extractUniqueTags(String template) {
//        Set<String> tags = new LinkedHashSet<>();
//
//        int indexStart = -1;
//        for (int i = 0; i < template.length(); i++) {
//            if (template.charAt(i) == '[') {
//                indexStart = i;
//            } else if (template.charAt(i) == ']' && indexStart != -1) {
//                String tag = template.substring(indexStart, i + 1);
//                tags.add(tag);
//                indexStart = -1;
//            }
//        }
//
//        return tags;
//    }

    private static String resolveValue(String value, Map<String, QuestionEntity> memory) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        boolean isNot = false;
        String expression = value.trim();

        if (expression.startsWith("!(") && expression.endsWith(")")) {
            isNot = true;
            expression = expression.substring(2, expression.length() - 1);
        } else if (expression.startsWith("(") && expression.endsWith(")")) {
            expression = expression.substring(1, expression.length() - 1);
        } else {
            return value;
        }

        String[] parts = expression.split(":", 2);
        String id = parts[0];
        String property = (parts.length > 1) ? parts[1] : "";


        if (memory.containsKey(id)) {
            String newProperty = splitBeforeAndWithinParentheses(property);
            String[] splitNewProperty;

            if (newProperty != null) {
                splitNewProperty = newProperty.split(":",2);
                property = splitNewProperty[0] + resolveValue(splitNewProperty[1], memory);
            }

            String resolvedValue = memory.get(id).getProperty(property);
            return isNot ? "!" + resolvedValue : resolvedValue;
        }

        return value;
    }


    public static String splitBeforeAndWithinParentheses(String str) {
        if (str == null || !str.endsWith(")")) {
            return null;
        }

        int firstOpen = str.indexOf('(');

        if (firstOpen == -1) {
            return null;
        }

        String x = str.substring(0, firstOpen);
        String y = str.substring(firstOpen);

        return x + ":" + y;
    }


    public static Human findHuman(Map<String, String> constraints) {
        List<Human> matches = humans.stream()
                .filter(h -> h.matches(constraints))
                .toList();

        if (matches.isEmpty()) {
            System.out.println("Warning: No human matches constraints: " + constraints);
            return null;
        }

        return matches.get(ThreadLocalRandom.current().nextInt(matches.size()));
    }

    public static Item findItem(Map<String, String> constraints) {
        List<Item> matches = items.stream()
                .filter(h -> h.matches(constraints))
                .toList();

        if (matches.isEmpty()) {
            System.out.println("Warning: No item matches constraints: " + constraints);
            return null;
        }

        return matches.get(ThreadLocalRandom.current().nextInt(matches.size()));
    }

    public static Place findPlace(Map<String, String> constraints) {
        List<Place> matches = places.stream()
                .filter(p -> p.matches(constraints))
                .toList();

        if (matches.isEmpty()) {
            System.out.println("Warning: No place matches constraints: " + constraints); // שונה ל-place
            return null;
        }

        return matches.get(ThreadLocalRandom.current().nextInt(matches.size()));
    }

    public static NumberEntity findNumber(Map<String, String> constraints) {
        int min, max;
        try {
            min = Integer.parseInt(constraints.getOrDefault("min", "1").trim());
            max = Integer.parseInt(constraints.getOrDefault("max", "100").trim());

            if (min > max) {
                int temp = min;
                min = max;
                max = temp;
            }
        } catch (NumberFormatException e) {
            min = 1;
            max = 100;
        }

        if (constraints.containsKey("value") && !constraints.get("value").equals("?")) {
            String valStr = constraints.get("value").trim();

            if (valStr.startsWith("!")) {
                try {
                    int forbiddenValue = Integer.parseInt(valStr.substring(1));

                    if (min == max && min == forbiddenValue) {
                        return new NumberEntity(min);
                    }

                    int randomNumber;
                    do {
                        randomNumber = java.util.concurrent.ThreadLocalRandom.current().nextInt(min, max + 1);
                    } while (randomNumber == forbiddenValue);

                    return new NumberEntity(randomNumber);
                } catch (NumberFormatException e) {
                    System.out.println("Warning: Invalid value: " + valStr);
                }
            }

            else {
                try {
                    return new NumberEntity(Integer.parseInt(valStr));
                } catch (NumberFormatException e) {
                    System.out.println("Warning: Invalid value for number: " + valStr);
                }
            }
        }

        int randomNumber = java.util.concurrent.ThreadLocalRandom.current().nextInt(min, max + 1);
        return new NumberEntity(randomNumber);
    }

    public static QuestionEntity findVerb(Map<String, String> constraints) {
        List<Verb> matches = verbs.stream()
                .filter(v -> v.matches(constraints))
                .toList();

        if (matches.isEmpty()) {
            System.out.println("Warning: No verb matches constraints: " + constraints);
            return null;
        }

        // בחירת פועל (למקרה שיש כמה, למרות שלרוב יהיה רק אחד עם אותו ID)
        Verb chosenVerb = matches.get(ThreadLocalRandom.current().nextInt(matches.size()));

        // קריאת האילוצים הדקדוקיים (ברירת מחדל: עבר, זכר, יחיד)
        String g = constraints.getOrDefault("g", "MALE").toUpperCase();
        String t = constraints.getOrDefault("t", "past").toLowerCase();
        String num = constraints.getOrDefault("num", "s").toLowerCase();

        // שליפת המילה המדויקת (למשל "קנתה")
        String exactWord = chosenVerb.getWord(t, g, num);

        // החזרת ישות וירטואלית שתמיד מחזירה את המילה המדויקת ללא קשר ל-Key שנבקש ב-Property
        return key -> exactWord;
    }

    public static void fillHumans() {
        humans.add(new Human("שמעון", Gender.MALE));
        humans.add(new Human("יוסף", Gender.MALE));
        humans.add(new Human("אברהם", Gender.MALE));
        humans.add(new Human("דוד", Gender.MALE));

        humans.add(new Human("נועה", Gender.FEMALE));
        humans.add(new Human("תמר", Gender.FEMALE));
        humans.add(new Human("יעל", Gender.FEMALE));
        humans.add(new Human("מאיה", Gender.FEMALE));
    }

    public static void fillItems() {
        // --- FOOD (אוכל - דברים שאוכלים או קונים במכולת) ---
        items.add(new Item("תפוח", "תפוחים", Gender.MALE, ItemCategory.FOOD, ItemCategory.GENERAL));
        items.add(new Item("בננה", "בננות", Gender.FEMALE, ItemCategory.FOOD, ItemCategory.GENERAL));
        items.add(new Item("תפוז", "תפוזים", Gender.MALE, ItemCategory.FOOD, ItemCategory.GENERAL));
        items.add(new Item("סוכרייה", "סוכריות", Gender.FEMALE, ItemCategory.FOOD, ItemCategory.GENERAL));
        items.add(new Item("פיצה", "פיצות", Gender.FEMALE, ItemCategory.FOOD, ItemCategory.GENERAL));

        // --- COLLECTIBLE (פריטי אספנות - דברים שאוספים בכיס או באלבום) ---
        items.add(new Item("גולה", "גולות", Gender.FEMALE, ItemCategory.COLLECTIBLE, ItemCategory.GENERAL));
        items.add(new Item("קלף", "קלפים", Gender.MALE, ItemCategory.COLLECTIBLE, ItemCategory.GENERAL));
        items.add(new Item("מדבקה", "מדבקות", Gender.FEMALE, ItemCategory.COLLECTIBLE, ItemCategory.GENERAL));
        items.add(new Item("גלויה", "גלויות", Gender.FEMALE, ItemCategory.COLLECTIBLE, ItemCategory.GENERAL));
        // מטבע זהב הוא גם כסף וגם פריט אספנות - זה שילוב מעולה!
        items.add(new Item("מטבע זהב", "מטבעות זהב", Gender.MALE, ItemCategory.MONEY, ItemCategory.COLLECTIBLE, ItemCategory.GENERAL));

        // --- STATIONERY (ציוד לימודי) ---
        items.add(new Item("עיפרון", "עפרונות", Gender.MALE, ItemCategory.STATIONERY, ItemCategory.GENERAL));
        items.add(new Item("מחברת", "מחברות", Gender.FEMALE, ItemCategory.STATIONERY, ItemCategory.GENERAL));
        items.add(new Item("ספר", "ספרים", Gender.MALE, ItemCategory.STATIONERY, ItemCategory.GENERAL));
        items.add(new Item("מחק", "מחקים", Gender.MALE, ItemCategory.STATIONERY, ItemCategory.GENERAL));

        // --- TOY (צעצועים) ---
        items.add(new Item("כדור", "כדורים", Gender.MALE, ItemCategory.TOY, ItemCategory.GENERAL));
        items.add(new Item("בובה", "בובות", Gender.FEMALE, ItemCategory.TOY, ItemCategory.GENERAL));
        items.add(new Item("מכונית צעצוע", "מכוניות צעצוע", Gender.FEMALE, ItemCategory.TOY, ItemCategory.GENERAL));

        // --- MONEY (כסף - בדרך כלל ליחידות מידה של מחיר ועודף) ---
        items.add(new Item("שקל", "שקלים", Gender.MALE, ItemCategory.MONEY, ItemCategory.GENERAL));
        items.add(new Item("שטר", "שטרות", Gender.MALE, ItemCategory.MONEY, ItemCategory.GENERAL));
    }

    public static void fillVerbs() {
        // --- קנה (מתאים ל: MONEY, GENERAL) ---
        Verb buy = new Verb("buy");
        buy.addForm("past", "MALE", "s", "קנה");
        buy.addForm("past", "FEMALE", "s", "קנתה");
        buy.addForm("past", "MALE", "p", "קנו");
        buy.addForm("past", "FEMALE", "p", "קנו");
        verbs.add(buy);

        // --- אכל (מתאים ל: FOOD) ---
        Verb eat = new Verb("eat");
        eat.addForm("past", "MALE", "s", "אכל");
        eat.addForm("past", "FEMALE", "s", "אכלה");
        eat.addForm("past", "MALE", "p", "אכלו");
        eat.addForm("past", "FEMALE", "p", "אכלו");
        verbs.add(eat);

        // --- נתן (מתאים ל: COLLECTIBLE, GENERAL) ---
        Verb give = new Verb("give");
        give.addForm("past", "MALE", "s", "נתן");
        give.addForm("past", "FEMALE", "s", "נתנה");
        give.addForm("past", "MALE", "p", "נתנו");
        give.addForm("past", "FEMALE", "p", "נתנו");
        verbs.add(give);

        // --- קיבל (מתאים ל: COLLECTIBLE, GENERAL, MONEY) ---
        Verb receive = new Verb("receive");
        receive.addForm("past", "MALE", "s", "קיבל");
        receive.addForm("past", "FEMALE", "s", "קיבלה");
        receive.addForm("past", "MALE", "p", "קיבלו");
        receive.addForm("past", "FEMALE", "p", "קיבלו");
        verbs.add(receive);

        // --- מצא (מתאים ל: COLLECTIBLE, GENERAL) ---
        Verb find = new Verb("find");
        find.addForm("past", "MALE", "s", "מצא");
        find.addForm("past", "FEMALE", "s", "מצאה");
        find.addForm("past", "MALE", "p", "מצאו");
        find.addForm("past", "FEMALE", "p", "מצאו");
        verbs.add(find);

        // --- איבד (מתאים ל: COLLECTIBLE, GENERAL) ---
        Verb lose = new Verb("lose");
        lose.addForm("past", "MALE", "s", "איבד");
        lose.addForm("past", "FEMALE", "s", "איבדה");
        lose.addForm("past", "MALE", "p", "איבדו");
        lose.addForm("past", "FEMALE", "p", "איבדו");
        verbs.add(lose);

        // --- אסף (מתאים ל: COLLECTIBLE) ---
        Verb collect = new Verb("collect");
        collect.addForm("past", "MALE", "s", "אסף");
        collect.addForm("past", "FEMALE", "s", "אספה");
        collect.addForm("past", "MALE", "p", "אספו");
        collect.addForm("past", "FEMALE", "p", "אספו");
        verbs.add(collect);

        // --- חילק (מתאים ל: שאלות חילוק - FOOD, COLLECTIBLE) ---
        Verb divide = new Verb("divide");
        divide.addForm("past", "MALE", "s", "חילק");
        divide.addForm("past", "FEMALE", "s", "חילקה");
        divide.addForm("past", "MALE", "p", "חילקו");
        divide.addForm("past", "FEMALE", "p", "חילקו");
        verbs.add(divide);

        // --- נכנס (מתאים ל: PLACE) ---
        Verb enter = new Verb("enter");
        enter.addForm("past", "MALE", "s", "נכנס");
        enter.addForm("past", "FEMALE", "s", "נכנסה");
        enter.addForm("past", "MALE", "p", "נכנסו");
        enter.addForm("past", "FEMALE", "p", "נכנסו");
        verbs.add(enter);

        Verb be = new Verb("be");
        be.addForm("past", "MALE", "s", "היה");
        be.addForm("past", "FEMALE", "s", "הייתה");
        be.addForm("past", "MALE", "p", "היו");
        be.addForm("past", "FEMALE", "p", "היו");
        verbs.add(be);
    }

    public static void fillPlaces(){
        // --- חנויות ועסקים (STORE) ---
        places.add(new Place("מכולת", "מכולות", Gender.FEMALE, PlaceType.STORE, FOOD, GENERAL));
        places.add(new Place("סופרמרקט", "סופרמרקטים", Gender.MALE, PlaceType.STORE, FOOD, GENERAL));
        places.add(new Place("מאפייה", "מאפיות", Gender.FEMALE, PlaceType.STORE, FOOD));
        places.add(new Place("קיוסק", "קיוסקים", Gender.MALE, PlaceType.STORE, FOOD));
        places.add(new Place("שוק", "שווקים", Gender.MALE, PlaceType.STORE, FOOD, COLLECTIBLE, GENERAL));
        places.add(new Place("חנות צעצועים", "חנויות צעצועים", Gender.FEMALE, PlaceType.STORE, TOY));
        places.add(new Place("חנות עתיקות", "חנויות עתיקות", Gender.FEMALE, PlaceType.STORE, COLLECTIBLE));
        places.add(new Place("חנות יצירה", "חנויות יצירה", Gender.FEMALE, PlaceType.STORE, STATIONERY, TOY));
        places.add(new Place("חנות כלי כתיבה", "חנויות כלי כתיבה", Gender.FEMALE, PlaceType.STORE, STATIONERY, GENERAL));
        places.add(new Place("קניון", "קניונים", Gender.MALE, PlaceType.STORE, GENERAL, FOOD, TOY, STATIONERY, MONEY));
        places.add(new Place("חנות", "חנויות", Gender.FEMALE, PlaceType.STORE, GENERAL, FOOD, TOY));

        // --- מקומות ציבוריים (PUBLIC) ---
        places.add(new Place("ספרייה", "ספריות", Gender.FEMALE, PlaceType.PUBLIC, STATIONERY, GENERAL));
        places.add(new Place("בנק", "בנקים", Gender.MALE, PlaceType.PUBLIC, MONEY));
        places.add(new Place("קופה", "קופות", Gender.FEMALE, PlaceType.PUBLIC, MONEY)); // יכול להיות גם בתוך חנות
        places.add(new Place("כספומט", "כספומטים", Gender.MALE, PlaceType.PUBLIC, MONEY));
        places.add(new Place("משחקייה", "משחקיות", Gender.FEMALE, PlaceType.PUBLIC, TOY));
        places.add(new Place("בית ספר", "בתי ספר", Gender.MALE, PlaceType.PUBLIC, STATIONERY, GENERAL)); // חדש

        // --- מקומות בחוץ (OUTDOORS) ---
        places.add(new Place("פארק", "פארקים", Gender.MALE, PlaceType.OUTDOORS, TOY, COLLECTIBLE, GENERAL));
        places.add(new Place("גינה", "גינות", Gender.FEMALE, PlaceType.OUTDOORS, TOY, GENERAL)); // חדש
        places.add(new Place("רחוב", "רחובות", Gender.MALE, PlaceType.OUTDOORS, GENERAL)); // חדש
        places.add(new Place("חצר", "חצרות", Gender.FEMALE, PlaceType.OUTDOORS, GENERAL, TOY)); // חדש

        // --- מקומות פרטיים (HOME) ---
        places.add(new Place("בית", "בתים", Gender.MALE, PlaceType.HOME, GENERAL, FOOD, TOY, COLLECTIBLE));
        places.add(new Place("חדר", "חדרים", Gender.MALE, PlaceType.HOME, GENERAL, TOY, STATIONERY)); // חדש
    }













    public MathQuestion generateForPlayer(RacePlayer player) {
        String expression = "המלך ביקש מעידן שיקנה לו 3 תפוחים, עידן קנה 3 תפוחים והביא מהבית עוד 2 ונתן הכל למלך. כמה תפוחים סהכ הביא עידן למלך ?";
        List<String> options = List.of("6","3","5","2");
        String correctAnswer = "5";
        int timeLimitSeconds = 15;
        int score = 20;

        return new MathQuestion(expression,options,correctAnswer,timeLimitSeconds,score);
    }
}
