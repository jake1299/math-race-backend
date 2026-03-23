package com.example.math_race;

import com.example.math_race.race.questions.MathQuestionGenerator;
import com.example.math_race.race.questions.QuestionEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@EnableAsync
public class MathRaceApplication {

	public static void main(String[] args) {
		//SpringApplication.run(MathRaceApplication.class, args);

		System.out.println("\n----------------------------------------------------------");
		System.out.println("  Application 'Math Race' is running successfully!");
		System.out.println("----------------------------------------------------------\n");

		Map<String, QuestionEntity> memory = new HashMap<>();


		String template1 = "[HUMAN::#1] [VERB:id=find;t=past;g=(#1:g);num=s:#V1] [NUM:min=10;max=20:#X] [ITEM:type=COLLECTIBLE:p:#2]. " +
				"[#1:he_she] [VERB:id=give;t=past;g=(#1:g);num=s:#V2] ל[HUMAN:n=!(#1:n):#3] [NUM:min=2;max=5:#Y] [#2:p]. " +
				"כמה [#2:p] נשארו ל[#1]?";

		String template1Result = "[NUM:value=(#X:add_-(#Y)):#R]";

		System.out.println("==============================");


		List<String[]> templates = new ArrayList<>();

		// 1. חיסור (כמה נשארו)
		templates.add(new String[]{
				"[HUMAN:g=m:#1] [VERB:id=find;t=past;g=(#1:g);num=s:#V1] [NUM:min=10;max=20:#X] [ITEM:type=COLLECTIBLE:p:#2]. " +
						"[#1:he_she] [VERB:id=give;t=past;g=(#1:g);num=s:#V2] ל[HUMAN:n=!(#1:n):#3] [NUM:min=2;max=5:#Y] [#2:p]. " +
						"כמה [#2:p] נשארו ל[#1]?",
				"[NUM:value=(#X:add_-(#Y)):#R]"
		});

		// 2. כפל (ארגזים ומארזים)
		templates.add(new String[]{
				"ב[PLACE:type=FOOD:s:#P1] יש [NUM:min=3;max=6:#X] ארגזים. " +
						"בכל ארגז יש [NUM:min=5;max=10:#Y] [ITEM:type=FOOD:p:#1]. " +
						"כמה [#1:p] יש בסך הכל [#P1:in_it]?",
				"[NUM:value=(#X:mul_(#Y)):#R]"
		});

		// 3. השוואה (למי יש יותר) - דורש שתי פעולות חיבור
		templates.add(new String[]{
				"ל[HUMAN:n:#1] יש [NUM:min=10;max=30:#X] [ITEM:type=COLLECTIBLE:p:#2]. " +
						"ל[HUMAN:n=!(#1:n):#3] יש [NUM:min=5;max=15:#Y] [#2:p] יותר מאשר ל[#1]. " +
						"כמה [#2:p] יש לשניהם ביחד?",
				"[NUM:value=(#X:add_(#X:add_(#Y))):#R]"
		});

		// 4. חילוק (חלוקה שווה)
		templates.add(new String[]{
				"[HUMAN:#1] [VERB:id=buy;g=(#1:g);t=past] [NUM:min=12;max=24:#X] [ITEM:type=STATIONERY:p:#2]. " +
						"[#1:he_she] רוצה לחלק אותם שווה בשווה בין [NUM:min=2;max=4:#Y] חברות [#1:his_hers]. " +
						"כמה [#2:p] כל חברה תקבל?",
				"[NUM:value=(#X:div_(#Y)):#R]"
		});

		// 5. קניות ועודף (שילוב כפל וחיסור)
		templates.add(new String[]{
				"[HUMAN:#1] נכנס ל[PLACE:type=TOY;place_type=STORE:s:#P1]. " +
						"[#1:he_she] [VERB:id=buy;g=(#1:g);t=past] [NUM:min=2;max=4:#X] [ITEM:type=TOY:p:#2], " +
						"כאשר כל [#2:s] עולה [NUM:min=5;max=10:#Y] שקלים. " +
						"אם [#1:he_she] שילם בשטר של [NUM:value=50:#Z] שקלים, כמה עודף נשאר?",
				"[NUM:value=(#Z:sub_(#X:mul_(#Y))):#R]"
		});

		templates.add(new String[]{
				"[HUMAN:#1] [VERB:id=find;g=(#1:g);t=past] [NUM:min=5;max=15:#X] [ITEM:type=COLLECTIBLE:p:#2] ב[PLACE:place_type=OUTDOORS:s:#P1]. " +
						"גם [HUMAN:n=!(#1:n):#3] [VERB:id=find;g=(#3:g);t=past] [NUM:min=5;max=15:#Y] [#2:p] [#P1:in_it]. " +
						"הם החליטו לחלק את כל ה[#2:p] שווה בשווה בין [NUM:min=2;max=4:#Z] חברים. " +
						"כמה [#2:p] קיבל כל חבר?",
				"[NUM:value=(#X:add_(#Y)):div_(#Z):#R]"
		});

		templates.add(new String[]{
				"ל[HUMAN:#1] יש [NUM:min=3;max=5:#X] קופסאות של [ITEM:type=FOOD|TOY:p:#2]. " +
						"בכל קופסה יש [NUM:min=6;max=10:#Y] [#2:p]. " +
						"[#1:he_she] [VERB:id=give;g=(#1:g);t=past] ל[HUMAN:n=!(#1:n):#3] [NUM:min=5;max=10:#Z] [#2:p]. " +
						"כמה [#2:p] נשארו ל[#1]?",
				"[NUM:value=(#X:mul_(#Y)):sub_(#Z):#R]"
		});

		// הרצה של המנוע
		System.out.println("--- מריץ ייצור שאלות ---");

		for (int i = 0; i < templates.size(); i++) {
			memory = new HashMap<>(); // זיכרון נקי לכל שאלה
			String[] pair = templates.get(i);

			String question = MathQuestionGenerator.gene(pair[0], memory);
			String answer = MathQuestionGenerator.gene(pair[1], memory);

			System.out.println("שאלה " + (i+1) + ": " + question);
			System.out.println("תשובה נכונה: " + answer);
			System.out.println("-------------------------");
		}
	}
}