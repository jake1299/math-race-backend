package com.example.math_race;

import com.example.math_race.questionGenerator.QuestionEngine;
import com.example.math_race.questionGenerator.tags.core.TemplateTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainIdan {
    
    public static void main(String[] args) {

        //[HUMAN:g=m:#1] [VERB:id=buy:(past_+(#1:g)+_s)]
        // past_male_p/s
        String tem = "[HUMAN:n:#n] [VERB:id=buy:(past_+(#n:g)+_s):#r]";
        //N =NAME
        //G =




        List<String[]> newTemplates = new ArrayList<>();
//        newTemplates.add(new String[]{
//                "שאלה",
//                "תשובה",
//                "תשובה שגויה 1",
//                "תשובה שגויה 2",
//                "תשובה שגויה 2",
//                "רמז"
//        });

        newTemplates.add(new String[]{
                "[HUMAN:#n] [VERB:id=buy:(past_+(#n:g)+_s):#r] [NUM:min=10;max=20:#X] [ITEM:type=STATIONERY:p:#2] כל [#2:s]" +
                       " עולה [NUM:min=5;max=10:#Q]. כמה עלה כל הקניה?" +

                        "[NUM:min=0;max=1:*:#E]"+

                        "[IF:(#E)=0:< >:<  >]",
                "[#X:mul_(#Q)]",
                "[NUM:valve=(#Q:add_5):mul_2]",
                "",
                "",
                "vf"
                }
        );

        newTemplates.add(new String[]{
                        "[PLACE:place_type=STORE:*:#a] [ITEM:type=(#a:t)]",
                        "",
                        "",
                        "",
                        "",
                        ""
                }
        );

        Map<String, TemplateTag> memory;
        QuestionEngine questionEngine = new QuestionEngine();
        for (int i = 0; i < newTemplates.size(); i++) {
            memory = new HashMap<>();
            String[] pair = newTemplates.get(i);

            String question = questionEngine.evaluateTemplate(pair[0], memory);
            String answer = questionEngine.evaluateTemplate(pair[1], memory);
            String w1answer = questionEngine.evaluateTemplate(pair[2], memory);
            String w2answer = questionEngine.evaluateTemplate(pair[3], memory);
            String w3answer = questionEngine.evaluateTemplate(pair[4], memory);
            String hint = questionEngine.evaluateTemplate(pair[5], memory);

            System.out.println("שאלה " + (i+1) + ": " + question);
            System.out.println("תשובה נכונה: " + answer);
            System.out.println("תשובה שגויה 1 : " + w1answer);
            System.out.println("תשובה שגויה 2 : " + w2answer);
            System.out.println("תשובה שגויה 3 : " + w3answer);
            System.out.println("רמז : " + hint);
            System.out.println("-------------------------");
        }
    }
}
