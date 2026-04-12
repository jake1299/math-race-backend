package com.example.math_race;


import com.example.math_race.questionGenerator.QuestionEngine;
import com.example.math_race.questionGenerator.tags.core.TemplateTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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




        List<String[]> newTemplates1 = new ArrayList<>();
//        newTemplates.add(new String[]{
//                "שאלה",
//                "תשובה",
//                "תשובה שגויה 1",
//                "תשובה שגויה 2",
//                "תשובה שגויה 2",
//                "רמז"
//        });

        newTemplates1.add(new String[]{
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

        newTemplates1.add(new String[]{
                        "[PLACE:place_type=STORE:*:#a] [ITEM:type=(#a:t)]",
                        "",
                        "",
                        "",
                        "",
                        ""
                }
        );

        System.out.println("\n----------------------------------------------------------");
        System.out.println("  Application 'Math Race' is running successfully!");
        System.out.println("----------------------------------------------------------\n");

        Map<String, TemplateTag> memory = new HashMap<>();
        QuestionEngine questionEngine = new QuestionEngine();


        String template1 = "[HUMAN:n:#1] [VERB:id=find;t=past;g=(#1:g);num=s:#V1] [NUM:min=10;max=20:#X] [ITEM:type=COLLECTIBLE:p:#2]. " +
                "[#1:he_she] [VERB:id=give;t=past;g=(#1:g);num=s:#V2] ל[HUMAN:n=!(#1:n):#3] [NUM:min=2;max=5:#Y] [#2:p]. " +
                "כמה [#2:p] נשארו ל[#1]?";

        String template1Result = "[NUM:value=(#X:add_-(#Y)):#R]";

        String c= "[NUM:min=0;max=1:hide:#W] [IF:#W=1:<How many intact [#1:p] remained?>:<How many [#1:p] were destroyed?>]";

        System.out.println("==============================");

        //System.out.println(extractUniqueTags(c));

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

        templates.add(new String[]{
                "ל[HUMAN:g=f:#1] יש [NUM:min=20;max=40:#X] [ITEM:type=STATIONERY:p:#2]. " +
                        "ל[HUMAN:g=m:#3] יש [NUM:min=5;max=15:#Y] [#2:p] **פחות** מאשר ל[#1]. " +
                        "כמה [#2:p] יש ל[#1] ול[#3] ביחד?",
                "[NUM:value=(#X:add_(#X:sub_(#Y))):#R]"
        });

        templates.add(new String[]{
                "[HUMAN:#1] [VERB:id=enter;g=(#1:g);t=past] ל[PLACE:place_type=STORE:s:#P1]. " +
                        "[#1:he_she] [VERB:id=buy;g=(#1:g);t=past] [NUM:min=2;max=3:#X] [ITEM:type=(#P1:t):p:#2] שעולים [NUM:min=4;max=6:#Y] שקלים כל [#2:one], " +
                        "וגם [ITEM:type=(#P1:t):s:#3] [#3:one] שעולה [NUM:min=10;max=15:#W] שקלים. " +
                        "אם [#1:he_she] נתן שטר של [NUM:value=50:#Z] שקלים, כמה עודף [#1:he_she] יקבל?",
                "[NUM:value=(#Z:sub_(#X:mul_(#Y))):sub_(#W):#R]"
        });

        templates.add(new String[]{
                "ב[PLACE:place_type=HOME:s:#P1] היו [NUM:min=20;max=50:#X] [ITEM:type=MONEY:p:#2]. " +
                        "חילקו את ה[#2:p] שווה בשווה בין [NUM:min=2;max=5:#Y] ילדים. " +
                        "[HUMAN:#3], [#3:one] הילדים, [VERB:id=lose;g=(#3:g);t=past] [NUM:min=1;max=3:#Z] מה[#2:p] [#3:his_hers]. " +
                        "כמה [#2:p] נשארו ל[#3]?",
                "[NUM:value=(#X:div_(#Y)):sub_(#Z):#R]"
        });
        //
        templates.add(new String[]{
                "[HUMAN:#1] [#1:loves] מאוד [ITEM:type=FOOD:p:#2]. [#1:he_she] [VERB:id=eat;g=(#1:g);t=past] [NUM:min=2;max=5:#X] [#2:p] בכל יום במשך [NUM:min=3;max=7:#Y] ימים ברצף. " +
                        "לאחר מכן, [#1:he_she] [VERB:id=eat;g=(#1:g);t=past] עוד [NUM:min=1;max=4:#Z] [#2:p] ביום האחרון. " +
                        "כמה [#2:p] סך הכל [#1:he_she] [VERB:id=eat;g=(#1:g);t=past]?",
                "[NUM:value=(#X:mul_(#Y)):add_(#Z):#R]"
        });

        templates.add(new String[]{
                "ב[PLACE:place_type=STORE:s:#P1] מסדרים מחדש את המלאי. " +
                        "יש שם [NUM:min=8;max=15:#X] מדפים, ועל כל מדף שמו [NUM:min=6;max=12:#Y] [ITEM:type=(#P1:t):p:#1]. " +
                        "לפתע [NUM:min=2;max=(#X:v):#Z] מדפים נשברו וכל ה[#1:p] שעליהם נהרסו. " +
                        "[NUM:min=0;max=1:*:#5]" +
                        "[IF:#5=0:<" +
                        "כמה [#1:p] שלמים נשארו [#P1:in_it]?" +
                        ">:<" +
                        "כמה [#1:p] נהרסו [#P1:in_it]?" +
                        ">]",

                "[IF:#5=0:<" +
                        "[NUM:value=(#X:sub_(#Z)):mul_(#Y):#R]" +
                        ">:<" +
                        "[NUM:value=(#Z):mul_(#Y):#R]" +
                        ">]"
        });

        templates.add(new String[]{
                // --- חלק 1: בניית הסיפור ---
                "[HUMAN:#1] [VERB:id=enter;t=past;g=(#1:g);num=s] ל[PLACE:place_type=STORE;type=FOOD|STATIONERY:s:#P1] בשעה [TIME:min=08.00;max=11.00:#T1] " +
                        "כדי [VERB:id=buy;f=inf] [ITEM:type=(#P1:t);unit_type=WEIGHT|COUNT:p:#2] [ADJ:id=new;g=(#2:g);num=p:#A1]. " +

                        // כאן קורה הקסם: היחידה (U1) נבחרת לפי מה שהפריט (#2) מרשה!
                        "[#1:he_she] [VERB:id=buy;t=past;g=(#1:g);num=s] [NUM:min=3;max=8:#X] [UNIT:type=(#2:allowed_unit):p:#U1] של [#2:p] [#A1]. " +

                        "כל [#U1:s] עולה [NUM:min=5;max=15:#Y] שקלים. " +
                        "תהליך הקנייה לקחה [#1:to_him_her] בדיוק [NUM:min=15;max=45:#M] דקות. " +

                        // --- חלק 2: פיצול השאלה (משתנה נסתר W) ---
                        "[NUM:min=0;max=1:*:#W]" +
                        "[IF:#W=0:<" +
                        "אם [#1:he_she] שילם בקופה בשטר של [NUM:value=200:#Z] שקלים, כמה עודף [#1:he_she] [VERB:id=receive;t=past;g=(#1:g);num=s]?" +
                        ">:<" +
                        "באיזו שעה [#1:he_she] [VERB:id=finish;t=past;g=(#1:g);num=s] את הקנייה ו[VERB:id=take;t=past;g=(#1:g);num=s] את ה[#2:p]?" +
                        ">]",

                // --- חלק 3: פיצול התשובה (חייב להתאים ל-W) ---
                "[IF:#W=0:<" +
                        "[NUM:value=(#Z:sub_(#X:mul_(#Y))):#R]" + // חישוב עודף
                        ">:<" +
                        "[TIME:value=(#T1:add_m_(#M)):#R]" +     // חישוב זמן סיום
                        ">]"
        });


        templates.add(new String[]{
                // 1. הגרלות נסתרות (מגדר מוכר, מקום, והתפקיד עצמו)
                "[NUM:min=0;max=1:*:#OG]" +
                        "[PLACE:place_type=STORE:*:#P1]" +
                        "[ROLE:place_id=(#P1:id);role_type=OPERATOR:*:#O1]" + // יצירת המוכר בזיכרון בלי להדפיס

                        // 2. הסיפור
                        "[HUMAN:#1] [VERB:id=enter;t=past;g=(#1:g);num=s] ל[#P1:s]. " +
                        "ה[IF:#OG=0:<[#O1:m_s] [VERB:id=offer;t=past;g=MALE;num=s]>:<[#O1:f_s] [VERB:id=offer;t=past;g=FEMALE;num=s]>] " +
                        "[#1:to_him_her] [VERB:id=buy;f=inf] [ITEM:type=(#P1:t):p:#2] [ADJ:id=new;g=(#2:g);num=p:#A1]. " +

                        "[#1:n] [VERB:id=want;t=past;g=(#1:g);num=s] [NUM:min=2;max=8:#X] " +
                        // טיפול ברווחים: הרווח נמצא בתוך ה-IF כדי שלא יהיה רווח כפול כשאין יחידה
                        "[UNIT:type=(#2:allowed_unit);item_category=(#2:t):p:#U1][IF:#2:allowed_unit=NONE:<>:< של>] [#2:p] [#A1]. " +

                        "המחיר עבור כל [IF:#2:allowed_unit=NONE:<[#2:s]>:<[#U1:s]>] הוא [NUM:min=5;max=20:#Y] שקלים. " +

                        // 3. השאלה
                        "[NUM:min=0;max=1:*:#W]" +
                        "[IF:#W=0:<" +
                        "כמה שקלים סך הכל [#1:he_she] [VERB:id=need;t=past;g=(#1:g);num=s] [VERB:id=pay;f=inf]?" +
                        ">:<" +
                        "[#1:n] [VERB:id=pay;t=past;g=(#1:g);num=s] בשטר של [NUM:value=200:#Z] שקלים. " +
                        "כמה עודף [#1:he_she] [VERB:id=receive;t=past;g=(#1:g);num=s] מה[IF:#OG=0:<[#O1:m_s]>:<[#O1:f_s]>]?" +
                        ">]",

                // 4. תשובה
                "[IF:#W=0:<[NUM:value=(#X:mul_(#Y)):#R]>:<[NUM:value=(#Z:sub_(#X:mul_(#Y))):#R]>]"
        });

        // מכאן

        templates.add(new String[]{
                // 1. הגרלות נסתרות (מקום, מוכר, פריט שמוכרים בחנות הזו)
                "[NUM:min=0;max=1:*:#OG]" +
                        "[PLACE:place_type=STORE:*:#P1]" +
                        "[ROLE:place_id=(#P1:id);role_type=OPERATOR:*:#O1]" +
                        "[ITEM:type=(#P1:t):*:#2]" +

                        // 2. הסיפור
                        "[HUMAN:#1] [VERB:id=enter;t=past;g=(#1:g);num=s] ל[#P1:s]. " +
                        "ה[IF:(#OG)=0:<[#O1:m_s] תלה>:<[#O1:f_s] תלתה>] שלט מבצע על ה[#2:p]: " +
                        "\"קנו [NUM:min=3;max=5:#X] [UNIT:type=(#2:allowed_unit):p:#U1][IF:(#2:allowed_unit)=NONE:<>:< של>] [#2:p] [ADJ:id=new;g=(#2:g);num=p:#A1], " +
                        "וקבלו [NUM:min=1;max=2:#B] מתנה!\". " +

                        "[#1:n] [VERB:id=buy;t=past;g=(#1:g);num=s] [NUM:min=2;max=8:#Y] [IF:(#2:allowed_unit)=NONE:<[#2:p]>:<[#U1:p]>]. " +
                        // 3. השאלה (האם מגיע לו בונוס?)
                        "כמה [IF:(#2:allowed_unit)=NONE:<[#2:p]>:<[#U1:p]>] של [#2:p] יש ל[#1] עכשיו בסך הכל?",

                // 4. התשובה (מחשב בונוס רק אם הוא קנה מספיק)
                "[IF:(#Y)>=(#X):<[NUM:value=(#Y:add_(#B)):#R]>:<[NUM:value=(#Y):#R]>]"
        });


        templates.add(new String[]{
                // 1. הגרלות נסתרות
                "[PLACE:place_type=OUTDOORS|HOME:*:#P1]" +
                        "[ITEM:type=(#P1:t):*:#2]" +

                        // 2. הסיפור עם הפיצול הדינמי למקום
                        "[HUMAN:#1] ו[HUMAN:n=!(#1:n):#3] [IF:(#P1:pt)=OUTDOORS:<בילו ב[#P1:s] וחיפשו מציאות>:<[VERB:id=arrange;t=past;g=MALE;num=p] את ה[#P1:s]>]. " +

                        "[#1:n] [VERB:id=find;t=past;g=(#1:g);num=s] [NUM:min=10;max=25:#X] [#2:p] [ADJ:id=old;g=(#2:g);num=p:#A1]. " +
                        "[#3:n] [VERB:id=find;t=past;g=(#3:g);num=s] [NUM:min=10;max=25:#Y] [#2:p] [#A1]. " +

                        "לאחר מכן, הם החליטו לאסוף את הכל יחד ולחלק את כל מה שנמצא שווה בשווה ל-[NUM:min=3;max=6:#Z] קופסאות קטנות. " +

                        // 3. מגרילים איזה סוג שאלה נשאל (0 = שארית, 1 = כמות בכל קופסה)
                        "[NUM:min=0;max=1:*:#W]" +

                        // 4. השאלה - מפוצלת לפי W
                        "[IF:(#W)=0:<" +
                        "כמה [#2:p] [#A1] נשארו בחוץ כי לא התחלקו שווה בשווה בין הקופסאות?" +
                        ">:<" +
                        "כמה [#2:p] [#A1] נכנסו בדיוק לתוך כל קופסה?" +
                        ">]",

                // 5. התשובה - מפוצלת לפי W (חייבת להיות תואמת לשאלה!)
                "[IF:(#W)=0:<" +
                        "[NUM:value=(#X:add_(#Y)):mod_(#Z):#R]" + // חישוב שארית
                        ">:<" +
                        "[NUM:value=(#X:add_(#Y)):div_(#Z):#R]" + // חישוב חלוקה רגילה
                        ">]"
        });

        templates.add(new String[]{
                // 1. הגרלות נסתרות (הפריט מותאם דינמית למקום!)
                "[PLACE:place_type=EDUCATION|HOME:*:#P1]" +
                        "[ITEM:type=(#P1:t):*:#2]" +

                        // 2. הסיפור - נקי לגמרי מתנאי IF מסורבלים בזכות מילון הפעלים החדש
                        "[HUMAN:#1] [VERB:id=sit;t=past;g=(#1:g);num=s] ב[#P1:s] בשעה [TIME:min=14.00;max=16.00:#T1]. " +
                        "[#1:he_she] [VERB:id=can;t=present;g=(#1:g);num=s] [VERB:id=arrange;f=inf] [NUM:min=4;max=8:#X] [#2:p] בכל דקה. " +

                        // 3. הגרלת סוג השאלה: 0 = כמות פריטים, 1 = שעת סיום
                        "[NUM:min=0;max=1:*:#W]" +

                        // 4. השאלה המפוצלת (ללא אות ל' מיותרת לפני פועל המקור)
                        "[IF:(#W)=0:<" +
                        "אם [#1:he_she] [VERB:id=arrange;t=past;g=(#1:g);num=s] [#2:p] ברצף עד השעה [TIME:value=(#T1:add_m_15):#T2], כמה [#2:p] [#1:he_she] [VERB:id=arrange;t=past;g=(#1:g);num=s] סך הכל?" +
                        ">:<" +
                        "אם היו שם [NUM:value=(#X:mul_20):#Y] [#2:p] בסך הכל, באיזו שעה [#1:he_she] [VERB:id=finish;t=past;g=(#1:g);num=s] [VERB:id=arrange;f=inf] את כולם?" +
                        ">]",

                // 5. התשובה המפוצלת (מותאמת בדיוק לשאלה שנבחרה)
                "[IF:(#W)=0:<" +
                        "[NUM:value=(#X:mul_15):#R]" + // חישוב כמות: קצב * 15 דקות
                        ">:<" +
                        "[TIME:value=(#T1:add_m_20):#R]" + // חישוב שעה: מוסיף בדיוק 20 דקות לזמן ההתחלה
                        ">]"
        });

        List<String[]> newTemplates = new ArrayList<>();
//        newTemplates.add(new String[]{
//                "שאלה",
//                "תשובה",
//                "תשובה שגויה 1",
//                "תשובה שגויה 2",
//                "תשובה שגויה 2",
//                "רמז"
//        });

        // 9. תבנית קניות משותפות (חברים קונים יחד ומקבלים עודף או מוסיפים פריט משותף)
        newTemplates.add(new String[]{
                "[PLACE:place_type=FOOD_SERVICE|ENTERTAINMENT:*:#P1]" +
                        "[ITEM:type=(#P1:t);unit_type=COUNT:*:#I1]" +
                        "[ITEM:type=(#P1:t);unit_type=COUNT;id=!(#I1:id):*:#I2]" +
                        "[HUMAN:*:#1]" +
                        "[NUM:min=3;max=6:*:#FRIENDS]" +
                        "[NUM:min=10;max=20:*:#P_PRICE]" +
                        "[NUM:min=15;max=35:*:#SHARED_PRICE]" +
                        "[NUM:value=(#FRIENDS:mul_(#P_PRICE)):*:#TOTAL_P]" +
                        "[NUM:value=(#TOTAL_P:add_(#SHARED_PRICE)):*:#TOTAL_COST]" +
                        "[NUM:value=200:*:#BILL]" +
                        "[NUM:min=0;max=1:*:#W]" +
                        "[#1:n] [VERB:id=buy:(past_+(#1:g)+_s)] [#FRIENDS] [#I1:p] ב[#P1:s] לחברים. כל [#I1:s] עולה [#P_PRICE] שקלים. " +
                        "[IF:(#W)=0:<בנוסף, [#1:he_she] [VERB:id=buy:(past_+(#1:g)+_s)] גם [#I2:s] עבור כולם ב-[#SHARED_PRICE] שקלים. כמה שקלים סך הכל [#1:he_she] [VERB:id=pay:(past_+(#1:g)+_s)]?>" +
                        ":<בנוסף, [#1:he_she] [VERB:id=buy:(past_+(#1:g)+_s)] [#I2:s] ב-[#SHARED_PRICE] שקלים, ו[VERB:id=pay:(past_+(#1:g)+_s)] לקופאי בשטר של 200 שקלים. כמה עודף מגיע [#1:to_him_her]?>]",
                "[IF:(#W)=0:<[NUM:value=(#TOTAL_COST):#R]>:<[NUM:value=(#BILL:sub_(#TOTAL_COST)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#TOTAL_P):#R]>:<[NUM:value=(#BILL:sub_(#TOTAL_P)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#TOTAL_COST:sub_10):#R]>:<[NUM:value=(#TOTAL_COST):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#TOTAL_P:add_(#FRIENDS)):#R]>:<[NUM:value=(#BILL:sub_(#SHARED_PRICE)):#R]>]",
                "[IF:(#W)=0:<חשב קודם את המחיר של כל [#I1:p] יחד (כפל), ואז הוסף את המחיר של [#I2:s].>:<חשב את העלות הכוללת (כפל ואז חיבור), וחסר את התוצאה מ-200.>]"
        });

        // 10. תבנית עבודה/סידור לפי קצב שעות (מטרה שבועית/יומית)
        newTemplates.add(new String[]{
                "[PLACE:place_type=EDUCATION|HOME:*:#P1]" +
                        "[ITEM:type=STATIONERY;unit_type=COUNT:*:#I1]" +
                        "[HUMAN:*:#1]" +
                        "[NUM:min=6;max=12:*:#PER_HOUR]" +
                        "[NUM:min=3;max=6:*:#HOURS]" +
                        "[NUM:value=(#PER_HOUR:mul_(#HOURS)):*:#BASE_DONE]" +
                        "[NUM:min=15;max=30:*:#EXTRA]" +
                        "[NUM:value=(#BASE_DONE:add_(#EXTRA)):*:#TOTAL_GOAL]" +
                        "[NUM:min=0;max=1:*:#W]" +
                        "[#1:n] [VERB:id=arrange:(present_+(#1:g)+_s)] [#I1:p] ב[#P1:s]. בכל שעה [#1:he_she] [VERB:id=arrange:(present_+(#1:g)+_s)] בדיוק [#PER_HOUR] [#I1:p]. " +
                        "[IF:(#W)=0:<אם [#1:he_she] [VERB:id=work:(past_+(#1:g)+_s)] במשך [#HOURS] שעות, ובערב [VERB:id=arrange:(past_+(#1:g)+_s)] עוד [#EXTRA] [#I1:p], כמה [#I1:p] [#1:he_she] [VERB:id=arrange:(past_+(#1:g)+_s)] סך הכל?>" +
                        ":<המטרה הייתה לסדר [#TOTAL_GOAL] [#I1:p]. אם [#1:he_she] [VERB:id=work:(past_+(#1:g)+_s)] רק במשך [#HOURS] שעות, כמה [#I1:p] עוד נשארו [#1:to_him_her] לסדר?>]",
                "[IF:(#W)=0:<[NUM:value=(#BASE_DONE:add_(#EXTRA)):#R]>:<[NUM:value=(#TOTAL_GOAL:sub_(#BASE_DONE)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#BASE_DONE:sub_(#EXTRA)):abs:#R]>:<[NUM:value=(#BASE_DONE:add_(#EXTRA)):#R]>]",
                "[NUM:value=(#BASE_DONE):#R]",
                "[IF:(#W)=0:<[NUM:value=(#TOTAL_GOAL:sub_10):#R]>:<[NUM:value=(#TOTAL_GOAL:sub_(#HOURS)):#R]>]",
                "[IF:(#W)=0:<הכפל את קצב הסידור במספר השעות, והוסף את הכמות של הערב.>:<הכפל את קצב הסידור במספר השעות, וחסר את התוצאה מתוך המטרה הכוללת.>]"
        });

// 11. תבנית שורות במערך (מטריצה) של פריטים + בלאי
        newTemplates.add(new String[]{
                "[PLACE:place_type=OUTDOORS|PUBLIC:*:#P1]" +
                        "[ITEM:type=(#P1:t):*:#I1]" +
                        "[HUMAN:*:#1]" +
                        "[NUM:min=6;max=12:*:#ROWS]" +
                        "[NUM:min=8;max=15:*:#PER_ROW]" +
                        "[NUM:value=(#ROWS:mul_(#PER_ROW)):*:#TOTAL_ITEMS]" +
                        "[NUM:min=10;max=25:*:#BROKEN]" +
                        "[NUM:min=2;max=5:*:#EXTRA_ROWS]" +
                        "[NUM:value=(#EXTRA_ROWS:mul_(#PER_ROW)):*:#ADDED_ITEMS]" +
                        "[NUM:min=0;max=1:*:#W]" +
                        "[#1:n] [VERB:id=arrange:(past_+(#1:g)+_s)] [IF:(#I1:allowed_unit)=NONE:<שורות של [#I1:p]>:<[UNIT:type=(#I1:allowed_unit);item_category=(#I1:t):p:#U1] של [#I1:p]>] ב[#P1:s]. " +
                        "[#1:he_she] [VERB:id=arrange:(past_+(#1:g)+_s)] [#ROWS] [IF:(#I1:allowed_unit)=NONE:<שורות>:<[#U1:p]>], ובכל [IF:(#I1:allowed_unit)=NONE:<שורה>:<[#U1:s]>] בדיוק [#PER_ROW] פריטים. " +
                        "[IF:(#W)=0:<לרוע המזל, [#BROKEN] פריטים נהרסו ונזרקו. כמה פריטים תקינים נשארו בסוף?>" +
                        ":<אחרי מנוחה, [#1:he_she] [VERB:id=add:(past_+(#1:g)+_s)] עוד [#EXTRA_ROWS] [IF:(#I1:allowed_unit)=NONE:<שורות מלאות>:<[#U1:p]>]. כמה פריטים יש עכשיו סך הכל?>]",
                "[IF:(#W)=0:<[NUM:value=(#TOTAL_ITEMS:sub_(#BROKEN)):#R]>:<[NUM:value=(#TOTAL_ITEMS:add_(#ADDED_ITEMS)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#TOTAL_ITEMS:add_(#BROKEN)):#R]>:<[NUM:value=(#TOTAL_ITEMS:sub_(#ADDED_ITEMS)):#R]>]",
                "[NUM:value=(#TOTAL_ITEMS):#R]",
                "[IF:(#W)=0:<[NUM:value=(#ROWS:mul_(#BROKEN)):#R]>:<[NUM:value=(#TOTAL_ITEMS:add_(#EXTRA_ROWS)):#R]>]",
                "[IF:(#W)=0:<חשב כמה פריטים היו בהתחלה (כמות הקבוצות כפול כמות הפריטים בכל אחת), וחסר את אלו שנהרסו.>:<חשב כמה פריטים היו בהתחלה (כפל), והוסף את מספר הפריטים שנוספו לאחר מכן (כפל נוסף).>]"
        });

        // 12. תבנית מרחק הקפות אימון (מספרים עגולים גדולים יותר - כפל עשרות ומאות)
        newTemplates.add(new String[]{
                "[PLACE:place_type=OUTDOORS|PUBLIC:*:#P1]" +
                        "[HUMAN:*:#1]" +
                        "[NUM:min=30;max=60;round=10:*:#LAP_LEN]" +
                        "[NUM:min=3;max=6:*:#LAPS]" +
                        "[NUM:value=(#LAP_LEN:mul_(#LAPS)):*:#TOTAL_DIST]" +
                        "[NUM:min=20;max=50;round=10:*:#REMAINING]" +
                        "[NUM:value=(#TOTAL_DIST:add_(#REMAINING)):*:#GOAL]" +
                        "[NUM:min=0;max=1:*:#W]" +
                        "[#1:n] [VERB:id=run:(present_+(#1:g)+_s)] במסלול מעגלי ב[#P1:s]. אורך כל הקפה הוא [#LAP_LEN] מטרים. " +
                        "[IF:(#W)=0:<אם [#1:he_she] [VERB:id=run:(past_+(#1:g)+_s)] [#LAPS] הקפות, ונשארו [#1:to_him_her] עוד [#REMAINING] מטרים לסיום האימון, מה אורך האימון הכולל במטרים?>" +
                        ":<המטרה של [#1:n] היא לרוץ [#GOAL] מטרים. לאחר השלמת [#LAPS] הקפות מלאות, כמה מטרים עוד נשארו [#1:to_him_her] לרוץ?>]",
                "[IF:(#W)=0:<[NUM:value=(#GOAL):#R]>:<[NUM:value=(#REMAINING):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#TOTAL_DIST:sub_(#REMAINING)):#R]>:<[NUM:value=(#GOAL:add_(#TOTAL_DIST)):#R]>]",
                "[NUM:value=(#TOTAL_DIST):#R]",
                "[IF:(#W)=0:<[NUM:value=(#GOAL:sub_20):#R]>:<[NUM:value=(#REMAINING:add_20):#R]>]",
                "[IF:(#W)=0:<חשב את המרחק ש[#1:he_she] כבר [VERB:id=run:(past_+(#1:g)+_s)] (מספר הקפות כפול אורך הקפה), וחבר למרחק שנשאר.>:<חשב את המרחק ש[#1:he_she] כבר [VERB:id=run:(past_+(#1:g)+_s)] (כפל), וחסר אותו מתוך יעד האימון הכולל.>]"
        });

// 13. תבנית עומס ומשקלים (עגלות/משאיות)
        newTemplates.add(new String[]{
                "[PLACE:place_type=STORE|HOME:*:#P1]" +
                        "[ITEM:type=(#P1:t):*:#I1]" +
                        "[HUMAN:*:#1]" +
                        "[NUM:min=4;max=8:*:#BOXES]" +
                        "[NUM:min=12;max=25:*:#WEIGHT_PER]" +
                        "[NUM:value=(#BOXES:mul_(#WEIGHT_PER)):*:#TOTAL_WEIGHT]" +
                        "[NUM:min=35;max=60:*:#EXTRA_WEIGHT]" +
                        "[NUM:value=(#TOTAL_WEIGHT:add_(#EXTRA_WEIGHT)):*:#MAX_CAPACITY]" +
                        "[NUM:min=0;max=1:*:#W]" +
                        "[#1:n] [VERB:id=arrange:(past_+(#1:g)+_s)] [IF:(#I1:allowed_unit)=NONE:<[#I1:p]>:<[UNIT:type=(#I1:allowed_unit);item_category=(#I1:t):p:#U1] של [#I1:p]>] ב[#P1:s]. " +
                        "המשקל של כל [IF:(#I1:allowed_unit)=NONE:<[#I1:s]>:<[#U1:s]>] הוא בדיוק [#WEIGHT_PER] קילוגרמים. [#1:he_she] [VERB:id=arrange:(past_+(#1:g)+_s)] [#BOXES] [IF:(#I1:allowed_unit)=NONE:<[#I1:p]>:<[#U1:p]>] על העגלה. " +
                        "[IF:(#W)=0:<בנוסף, [#1:he_she] [VERB:id=add:(past_+(#1:g)+_s)] לעגלה פריט בודד ששוקל [#EXTRA_WEIGHT] ק\"ג. מה המשקל הכולל על העגלה כעת?>" +
                        ":<העגלה יכולה לשאת מקסימום [#MAX_CAPACITY] קילוגרמים. כמה קילוגרמים נוספים אפשר להעמיס עליה לפני שתשבר?>]",
                "[IF:(#W)=0:<[NUM:value=(#TOTAL_WEIGHT:add_(#EXTRA_WEIGHT)):#R]>:<[NUM:value=(#MAX_CAPACITY:sub_(#TOTAL_WEIGHT)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#TOTAL_WEIGHT:sub_(#EXTRA_WEIGHT)):#R]>:<[NUM:value=(#MAX_CAPACITY:add_(#TOTAL_WEIGHT)):#R]>]",
                "[NUM:value=(#TOTAL_WEIGHT):#R]",
                "[IF:(#W)=0:<[NUM:value=(#MAX_CAPACITY:sub_10):#R]>:<[NUM:value=(#EXTRA_WEIGHT:add_10):#R]>]",
                "[IF:(#W)=0:<חשב את המשקל של [IF:(#I1:allowed_unit)=NONE:<[#I1:p]>:<[#U1:p]>] יחד (כפל), ואז הוסף את המשקל של הפריט הבודד.>:<חשב את המשקל של [IF:(#I1:allowed_unit)=NONE:<[#I1:p]>:<[#U1:p]>] שכבר על העגלה (כפל), וחסר אותו מהקיבולת המקסימלית.>]"
        });


        // ==========================================
        // easy templates (1-10)
        // ==========================================

        // easy 1 - אסף ואיבד (חיבור / חיסור)
        newTemplates.add(new String[]{
                "[HUMAN:*:#1]" +
                        "[ITEM:unit_type=COUNT:*:#I1]" +
                        "[NUM:min=10;max=25:*:#START]" +
                        "[NUM:min=3;max=8:*:#DELTA]" +
                        "[NUM:min=0;max=1:*:#W]" +
                        "[#1:n] [VERB:id=collect:(past_+(#1:g)+_s)] [#START] [#I1:p]. " +
                        "[IF:(#W)=0:<אחר כך [#1:he_she] [VERB:id=find:(past_+(#1:g)+_s)] עוד [#DELTA] [#I1:p]. כמה [#I1:p] יש ל[#1:n] סך הכל?>" +
                        ":<אחר כך [#1:he_she] [VERB:id=lose:(past_+(#1:g)+_s)] [#DELTA] [#I1:p]. כמה [#I1:p] נשאר ל[#1:n]?>]",
                "[IF:(#W)=0:<[NUM:value=(#START:add_(#DELTA)):#R]>:<[NUM:value=(#START:sub_(#DELTA)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#START:sub_(#DELTA)):#R]>:<[NUM:value=(#START:add_(#DELTA)):#R]>]",
                "[NUM:value=(#START):#R]",
                "[NUM:value=(#DELTA):#R]",
                "[IF:(#W)=0:<חבר את המספר ההתחלתי עם מה שנמצא.>:<חסר מהמספר ההתחלתי את מה שאבד.>]"
        });

        // easy 2 - ארגזים ופריטים (כפל / חילוק)
        newTemplates.add(new String[]{
                "[PLACE:place_type=STORE:*:#P1]" +
                        "[ITEM:type=(#P1:t);unit_type=COUNT:*:#I1]" +
                        "[HUMAN:*:#1]" +
                        "[NUM:min=2;max=6:*:#BOXES]" +
                        "[NUM:min=4;max=8:*:#PER_BOX]" +
                        "[NUM:value=(#BOXES:mul_(#PER_BOX)):*:#TOTAL]" +
                        "[NUM:min=0;max=1:*:#W]" +
                        "[IF:(#W)=0:<ב[#P1:s] יש [#BOXES] ארגזים. בכל ארגז יש [#PER_BOX] [#I1:p]. כמה [#I1:p] יש בסך הכל [#P1:in_it]?>" +
                        ":<ב[#P1:s] יש [#TOTAL] [#I1:p]. [#1:n] [VERB:id=arrange:(past_+(#1:g)+_s)] אותם בארגזים, [#PER_BOX] [#I1:p] בכל ארגז. כמה ארגזים נדרשו?>]",
                "[IF:(#W)=0:<[NUM:value=(#TOTAL):#R]>:<[NUM:value=(#BOXES):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#BOXES:add_(#PER_BOX)):#R]>:<[NUM:value=(#PER_BOX):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#TOTAL:sub_(#PER_BOX)):#R]>:<[NUM:value=(#TOTAL:sub_(#BOXES)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#BOXES:mul_2):#R]>:<[NUM:value=(#TOTAL:div_(#BOXES)):#R]>]",
                "[IF:(#W)=0:<כפל את מספר הארגזים בכמות שבכל ארגז.>:<חלק את הכמות הכוללת בכמות שבכל ארגז.>]"
        });

        // easy 3 - חלוקה שווה (חלוקה / כפל)
        newTemplates.add(new String[]{
                "[HUMAN:*:#1]" +
                        "[ITEM:unit_type=COUNT:*:#I1]" +
                        "[NUM:min=3;max=6:*:#FACTOR]" +
                        "[NUM:min=2;max=7:*:#QUOTIENT]" +
                        "[NUM:value=(#FACTOR:mul_(#QUOTIENT)):*:#TOTAL]" +
                        "[NUM:min=0;max=1:*:#W]" +
                        "[IF:(#W)=0:<ל[#1:n] יש [#TOTAL] [#I1:p]. [#1:he_she] [VERB:id=divide:(past_+(#1:g)+_s)] אותם שווה בשווה בין [#FACTOR] חברים. כמה [#I1:p] קיבל כל חבר?>" +
                        ":<[#1:n] רוצה לתת [#QUOTIENT] [#I1:p] לכל [#FACTOR] חברים. כמה [#I1:p] [#1:he_she] צריך סך הכל?>]",
                "[IF:(#W)=0:<[NUM:value=(#QUOTIENT):#R]>:<[NUM:value=(#TOTAL):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#FACTOR):#R]>:<[NUM:value=(#FACTOR:add_(#QUOTIENT)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#TOTAL:sub_(#FACTOR)):#R]>:<[NUM:value=(#QUOTIENT:mul_2):#R]>]",
                "[NUM:value=(#QUOTIENT:add_(#FACTOR)):#R]",
                "[IF:(#W)=0:<חלק את הכמות הכוללת במספר החברים.>:<כפל את הכמות לכל חבר במספר החברים.>]"
        });

        // easy 4 - חיסכון יומי (כפל / כפל + חיבור)
        newTemplates.add(new String[]{
                "[HUMAN:*:#1]" +
                        "[NUM:min=2;max=4:*:#DAYS]" +
                        "[NUM:min=5;max=10:*:#PER_DAY]" +
                        "[NUM:value=(#DAYS:mul_(#PER_DAY)):*:#BASE]" +
                        "[NUM:min=10;max=20:*:#BONUS]" +
                        "[NUM:min=0;max=1:*:#W]" +
                        "[#1:n] [VERB:id=save:(past_+(#1:g)+_s)] [#PER_DAY] שקלים בכל יום במשך [#DAYS] ימים. " +
                        "[IF:(#W)=0:<כמה שקלים [#1:n] [VERB:id=save:(past_+(#1:g)+_s)] סך הכל?>" +
                        ":<בנוסף, [#1:he_she] [VERB:id=receive:(past_+(#1:g)+_s)] [#BONUS] שקלים כמתנה. כמה שקלים יש ל[#1:n] עכשיו?>]",
                "[IF:(#W)=0:<[NUM:value=(#BASE):#R]>:<[NUM:value=(#BASE:add_(#BONUS)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#DAYS:add_(#PER_DAY)):#R]>:<[NUM:value=(#BONUS:add_(#PER_DAY)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#PER_DAY):#R]>:<[NUM:value=(#BASE):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#BASE:add_1):#R]>:<[NUM:value=(#BONUS:mul_(#DAYS)):#R]>]",
                "[IF:(#W)=0:<כפל את הסכום היומי במספר הימים.>:<כפל את הסכום היומי במספר הימים, ואז הוסף את המתנה.>]"
        });

        // easy 5 - כיתה (כפל / חלוקה)
        newTemplates.add(new String[]{
                "[PLACE:place_type=EDUCATION:*:#P1]" +
                        "[ITEM:type=STATIONERY;unit_type=COUNT:*:#I1]" +
                        "[HUMAN:*:#1]" +
                        "[NUM:min=4;max=8:*:#STUDENTS]" +
                        "[NUM:min=2;max=5:*:#EACH]" +
                        "[NUM:value=(#STUDENTS:mul_(#EACH)):*:#TOTAL]" +
                        "[NUM:min=0;max=1:*:#W]" +
                        "ב[#P1:s] יש [#STUDENTS] תלמידים. " +
                        "[IF:(#W)=0:<המורה [#1:n] [VERB:id=give:(past_+(#1:g)+_s)] לכל תלמיד [#EACH] [#I1:p]. כמה [#I1:p] חולקו בסך הכל?>" +
                        ":<ל[#1:n] יש [#TOTAL] [#I1:p] לחלק שווה בשווה. כמה [#I1:p] יקבל כל תלמיד?>]",
                "[IF:(#W)=0:<[NUM:value=(#TOTAL):#R]>:<[NUM:value=(#EACH):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#STUDENTS:add_(#EACH)):#R]>:<[NUM:value=(#STUDENTS):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#EACH:mul_2):#R]>:<[NUM:value=(#TOTAL:sub_(#STUDENTS)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#TOTAL:sub_(#STUDENTS)):#R]>:<[NUM:value=(#STUDENTS:mul_2):#R]>]",
                "[IF:(#W)=0:<כפל את מספר התלמידים בכמות שכל תלמיד קיבל.>:<חלק את הכמות הכוללת במספר התלמידים.>]"
        });

        // easy 6 - שני חברים יחד (חיבור / חיסור)
        newTemplates.add(new String[]{
                "[HUMAN:*:#1]" +
                        "[HUMAN:n=!(#1:n):*:#3]" +
                        "[ITEM:unit_type=COUNT:*:#I1]" +
                        "[NUM:min=8;max=20:*:#A]" +
                        "[NUM:min=5;max=15:*:#B]" +
                        "[NUM:value=(#A:add_(#B)):*:#TOTAL]" +
                        "[NUM:min=0;max=1:*:#W]" +
                        "[IF:(#W)=0:<ל[#1:n] יש [#A] [#I1:p] ול[#3:n] יש [#B] [#I1:p]. כמה [#I1:p] יש לשניהם ביחד?>" +
                        ":<יחד ל[#1:n] ול[#3:n] יש [#TOTAL] [#I1:p]. ל[#1:n] יש [#A] [#I1:p]. כמה [#I1:p] יש ל[#3:n]?>]",
                "[IF:(#W)=0:<[NUM:value=(#TOTAL):#R]>:<[NUM:value=(#B):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#A:sub_(#B)):#R]>:<[NUM:value=(#A):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#A):#R]>:<[NUM:value=(#TOTAL):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#B:mul_2):#R]>:<[NUM:value=(#TOTAL:add_(#A)):#R]>]",
                "[IF:(#W)=0:<פשוט חבר את שתי הכמויות יחד.>:<חסר את הכמות של [#1:n] מהסכום הכולל.>]"
        });

        // easy 7 - שורות ועמודות (כפל / חלוקה)
        newTemplates.add(new String[]{
                "[HUMAN:*:#1]" +
                        "[ITEM:unit_type=COUNT:*:#I1]" +
                        "[NUM:min=3;max=6:*:#ROWS]" +
                        "[NUM:min=4;max=8:*:#PER_ROW]" +
                        "[NUM:value=(#ROWS:mul_(#PER_ROW)):*:#TOTAL]" +
                        "[NUM:min=0;max=1:*:#W]" +
                        "[#1:n] [VERB:id=arrange:(past_+(#1:g)+_s)] [#I1:p] בשורות. " +
                        "[IF:(#W)=0:<[#1:he_she] [VERB:id=arrange:(past_+(#1:g)+_s)] [#ROWS] שורות, ובכל שורה בדיוק [#PER_ROW] [#I1:p]. כמה [#I1:p] יש בסך הכל?>" +
                        ":<[#1:he_she] [VERB:id=arrange:(past_+(#1:g)+_s)] [#TOTAL] [#I1:p] בשורות של [#PER_ROW] [#I1:p] כל אחת. כמה שורות יש?>]",
                "[IF:(#W)=0:<[NUM:value=(#TOTAL):#R]>:<[NUM:value=(#ROWS):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#ROWS:add_(#PER_ROW)):#R]>:<[NUM:value=(#PER_ROW):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#TOTAL:sub_(#PER_ROW)):#R]>:<[NUM:value=(#TOTAL:sub_(#ROWS)):#R]>]",
                "[NUM:value=(#ROWS:mul_2):#R]",
                "[IF:(#W)=0:<כפל את מספר השורות בכמות שבכל שורה.>:<חלק את הכמות הכוללת בכמות שבכל שורה.>]"
        });

        // easy 8 - קנייה בתקציב (כפל / חלוקה)
        newTemplates.add(new String[]{
                "[PLACE:place_type=STORE:*:#P1]" +
                        "[ITEM:type=(#P1:t);unit_type=COUNT:*:#I1]" +
                        "[HUMAN:*:#1]" +
                        "[NUM:min=2;max=8:*:#N]" +
                        "[NUM:min=3;max=7:*:#PRICE]" +
                        "[NUM:value=(#N:mul_(#PRICE)):*:#BUDGET]" +
                        "[NUM:min=0;max=1:*:#W]" +
                        "[IF:(#W)=0:<[#1:n] [VERB:id=buy:(past_+(#1:g)+_s)] ב[#P1:s] [#N] [#I1:p]. כל [#I1:s] עולה [#PRICE] שקלים. כמה שקלים [#1:n] [VERB:id=pay:(past_+(#1:g)+_s)] סך הכל?>" +
                        ":<ל[#1:n] יש בדיוק [#BUDGET] שקלים. [#I1:p] עולים [#PRICE] שקלים כל [#I1:one]. כמה [#I1:p] [#1:he_she] יכול לקנות?>]",
                "[IF:(#W)=0:<[NUM:value=(#BUDGET):#R]>:<[NUM:value=(#N):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#N:add_(#PRICE)):#R]>:<[NUM:value=(#PRICE):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#BUDGET:sub_(#PRICE)):#R]>:<[NUM:value=(#BUDGET:div_(#N)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#PRICE:mul_2):#R]>:<[NUM:value=(#BUDGET:sub_(#PRICE)):#R]>]",
                "[IF:(#W)=0:<כפל את מספר הפריטים במחיר כל פריט.>:<חלק את התקציב במחיר של פריט אחד.>]"
        });

        // easy 9 - הכנסה ורכישה (כפל / כפל וחיסור)
        newTemplates.add(new String[]{
                "[HUMAN:*:#1]" +
                        "[NUM:min=3;max=5:*:#DAYS]" +
                        "[NUM:min=6;max=10:*:#EARNED]" +
                        "[NUM:value=(#DAYS:mul_(#EARNED)):*:#BASE]" +
                        "[NUM:min=5;max=15:*:#SPENT]" +
                        "[NUM:min=0;max=1:*:#W]" +
                        "[#1:n] [VERB:id=work:(past_+(#1:g)+_s)] ו[VERB:id=receive:(past_+(#1:g)+_s)] [#EARNED] שקלים בכל יום. [#1:he_she] [VERB:id=work:(past_+(#1:g)+_s)] [#DAYS] ימים. " +
                        "[IF:(#W)=0:<כמה שקלים [#1:n] הרוויח סך הכל?>" +
                        ":<לאחר מכן [#1:he_she] [VERB:id=pay:(past_+(#1:g)+_s)] [#SPENT] שקלים. כמה שקלים נשארו ל[#1:n]?>]",
                "[IF:(#W)=0:<[NUM:value=(#BASE):#R]>:<[NUM:value=(#BASE:sub_(#SPENT)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#DAYS:add_(#EARNED)):#R]>:<[NUM:value=(#SPENT):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#EARNED):#R]>:<[NUM:value=(#BASE):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#BASE:add_5):#R]>:<[NUM:value=(#BASE:sub_(#DAYS)):#R]>]",
                "[IF:(#W)=0:<כפל את הסכום היומי במספר הימים.>:<כפל את הסכום היומי במספר הימים, ואז חסר את מה ששילם.>]"
        });

        // easy 10 - קיבל ונתן (חיבור / חיבור וחיסור)
        newTemplates.add(new String[]{
                "[HUMAN:*:#1]" +
                        "[HUMAN:n=!(#1:n):*:#3]" +
                        "[ITEM:unit_type=COUNT:*:#I1]" +
                        "[NUM:min=15;max=25:*:#START]" +
                        "[NUM:min=4;max=9:*:#GOT]" +
                        "[NUM:min=2;max=7:*:#GAVE]" +
                        "[NUM:value=(#START:add_(#GOT)):*:#MID]" +
                        "[NUM:min=0;max=1:*:#W]" +
                        "ל[#1:n] [VERB:id=be:(past_+(#1:g)+_s)] [#START] [#I1:p]. " +
                        "[#3:n] [VERB:id=give:(past_+(#3:g)+_s)] ל[#1:n] עוד [#GOT] [#I1:p]. " +
                        "[IF:(#W)=0:<כמה [#I1:p] יש ל[#1:n] עכשיו?>" +
                        ":<לאחר מכן, [#1:n] [VERB:id=give:(past_+(#1:g)+_s)] ל[#3:n] בחזרה [#GAVE] [#I1:p]. כמה [#I1:p] נשאר ל[#1:n]?>]",
                "[IF:(#W)=0:<[NUM:value=(#MID):#R]>:<[NUM:value=(#MID:sub_(#GAVE)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#START:sub_(#GOT)):#R]>:<[NUM:value=(#START:sub_(#GAVE)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#GOT):#R]>:<[NUM:value=(#MID):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#START:add_1):#R]>:<[NUM:value=(#START:add_(#GAVE)):#R]>]",
                "[IF:(#W)=0:<חבר את מה שהתחיל עם מה שקיבל.>:<חבר את מה שהתחיל עם מה שקיבל, ואז חסר את מה שנתן.>]"
        });
        // תבנית 14: תנועה ומהירות (דרך = מהירות * זמן)
        newTemplates.add(new String[]{
                // שאלה
                "[HUMAN:*:#1][PLACE:place_type=OUTDOORS:*:#P1][NUM:min=15;max=40:*:#SPEED][NUM:min=3;max=8:*:#TIME][NUM:value=(#SPEED:mul_(#TIME)):*:#DIST][NUM:min=0;max=1:*:#W]" +
                        "[#1:n] [VERB:id=run:(past_+(#1:g)+_s)] ב[#P1:s]. הקצב היה בדיוק [#SPEED] מטרים בדקה. [IF:(#W)=0:<אם [#1:he_she] [VERB:id=run:(past_+(#1:g)+_s)] במשך [#TIME] דקות ברצף, איזה מרחק [#1:he_she] עבר/ה בסך הכל?>:<אם [#1:he_she] עבר/ה מרחק כולל של [#DIST] מטרים באותו הקצב, כמה דקות נמשכה הריצה?>]",
                // תשובה נכונה
                "[IF:(#W)=0:<[NUM:value=(#DIST):#R]>:<[NUM:value=(#TIME):#R]>]",
                // תשובות שגויות
                "[IF:(#W)=0:<[NUM:value=(#DIST:add_(#SPEED)):#R]>:<[NUM:value=(#TIME:add_2):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#SPEED:add_(#TIME)):#R]>:<[NUM:value=(#DIST:sub_(#SPEED)):#R]>]",
                "[NUM:value=(#SPEED:mul_10):#R]",
                // רמז
                "[IF:(#W)=0:<כדי למצוא את הדרך, כפול את המהירות (קצב) בזמן.>:<כדי למצוא את הזמן, חלק את הדרך (המרחק הכולל) במהירות.>]"
        });

        // תבנית 15: חיסכון ומשוואה קווית (סכום התחלתי + קצב קבוע)
        newTemplates.add(new String[]{
                // שאלה
                "[HUMAN:*:#1][ITEM:type=MONEY|ENTERTAINMENT:*:#I1][NUM:min=50;max=120:*:#START][NUM:min=10;max=25:*:#RATE][NUM:min=4;max=8:*:#DAYS][NUM:value=(#RATE:mul_(#DAYS)):*:#ADDED][NUM:value=(#START:add_(#ADDED)):*:#TOTAL][NUM:min=0;max=1:*:#W]" +
                        "[#1:n] אוסף/ת [#I1:p]. בהתחלה היו [#1:to_him_her] [#START] [#I1:p], ובכל יום [#1:he_she] מוסיף/פה לאוסף עוד [#RATE] [#I1:p]. [IF:(#W)=0:<כמה [#I1:p] יהיו ל-[#1:n] בעוד [#DAYS] ימים?>:<לאחר מספר ימים היו באוסף [#TOTAL] [#I1:p]. כמה ימים עברו מאז שהתחיל/ה לאסוף?>]",
                // תשובה נכונה
                "[IF:(#W)=0:<[NUM:value=(#TOTAL):#R]>:<[NUM:value=(#DAYS):#R]>]",
                // תשובות שגויות
                "[IF:(#W)=0:<[NUM:value=(#ADDED):#R]>:<[NUM:value=(#DAYS:add_1):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#TOTAL:sub_(#RATE)):#R]>:<[NUM:value=(#TOTAL:sub_(#START)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#START:add_(#DAYS)):#R]>:<[NUM:value=(#TOTAL:div_(#RATE)):#R]>]",
                // רמז
                "[IF:(#W)=0:<חשב קודם כמה פריטים נוספו (קצב כפול ימים), ואז חבר לסכום ההתחלתי.>:<חסר את הכמות ההתחלתית מהכמות הסופית, ואת התוצאה חלק בכמות שמתווספת כל יום.>]"
        });

        // תבנית 16: ממוצעים פשוטים של 2 מספרים
        newTemplates.add(new String[]{
                // שאלה
                "[HUMAN:*:#1][ITEM:*:#I1][PLACE:*:#P1][NUM:min=20;max=40:*:#AVG][NUM:min=4;max=12:*:#DEV][NUM:value=(#AVG:sub_(#DEV)):*:#D1][NUM:value=(#AVG:add_(#DEV)):*:#D2][NUM:min=0;max=1:*:#W]" +
                        "[#1:n] [VERB:id=collect:(past_+(#1:g)+_s)] [#I1:p] ב[#P1:s]. ביום הראשון [#1:he_she] [VERB:id=collect:(past_+(#1:g)+_s)] [#D1] [#I1:p], וביום השני [#D2] [#I1:p]. [IF:(#W)=0:<מהו הממוצע של מספר ה-[#I1:p] שנאספו ביום?>:<אם הממוצע היומי היה [#AVG], וכמות ה-[#I1:p] ביום השני הייתה [#D2], כמה [#I1:p] נאספו ביום הראשון?>]",
                // תשובה נכונה
                "[IF:(#W)=0:<[NUM:value=(#AVG):#R]>:<[NUM:value=(#D1):#R]>]",
                // תשובות שגויות
                "[IF:(#W)=0:<[NUM:value=(#D1:add_(#D2)):#R]>:<[NUM:value=(#D2:sub_(#AVG)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#AVG:add_2):#R]>:<[NUM:value=(#AVG:sub_(#DEV)):add_(#DEV):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#D2:sub_(#D1)):#R]>:<[NUM:value=(#D1:add_2):#R]>]",
                // רמז
                "[IF:(#W)=0:<כדי למצוא ממוצע של שני ימים, חבר את שתי הכמויות וחלק ב-2.>:<כפול את הממוצע ב-2 כדי למצוא את הסכום הכולל, ואז חסר ממנו את הכמות של היום השני.>]"
        });

        // תבנית 17: יחסים וכפולות (פי X יותר)
        newTemplates.add(new String[]{
                // שאלה
                "[HUMAN:*:#1][HUMAN:n=!(#1:n):*:#2][ITEM:*:#I1][PLACE:*:#P1][NUM:min=10;max=20:*:#H1][NUM:min=3;max=6:*:#RATIO][NUM:value=(#H1:mul_(#RATIO)):*:#H2][NUM:value=(#H1:add_(#H2)):*:#TOTAL][NUM:min=0;max=1:*:#W]" +
                        "[#1:n] ו[#2:n] מסדרים [#I1:p] ב[#P1:s]. ל-[#1:n] יש [#H1] [#I1:p]. ל-[#2:n] יש פי [#RATIO] יותר מאשר ל-[#1:n]. [IF:(#W)=0:<כמה [#I1:p] יש לשניהם יחד?>:<בכמה [#I1:p] יותר יש ל-[#2:n] מאשר ל-[#1:n]?>]",
                // תשובה נכונה
                "[IF:(#W)=0:<[NUM:value=(#TOTAL):#R]>:<[NUM:value=(#H2:sub_(#H1)):#R]>]",
                // תשובות שגויות
                "[IF:(#W)=0:<[NUM:value=(#H2):#R]>:<[NUM:value=(#H2):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#H1:add_(#RATIO)):#R]>:<[NUM:value=(#TOTAL):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#TOTAL:add_(#H1)):#R]>:<[NUM:value=(#H1:mul_2):#R]>]",
                // רמז
                "[IF:(#W)=0:<חשב קודם כמה יש ל-[#2:n] (כפל), ואז חבר לכמות של [#1:n].>:<חשב כמה יש ל-[#2:n] (כפל), ואז חסר מזה את הכמות של [#1:n] כדי למצוא את ההפרש.>]"
        });

        // תבנית 18: שטח (מערכים ורשתות - כפל שורות בעמודות)
        newTemplates.add(new String[]{
                // שאלה
                "[PLACE:place_type=HOME|EDUCATION:*:#P1][ITEM:type=STATIONERY|FOOD:*:#I1][HUMAN:*:#1][NUM:min=6;max=12:*:#WIDTH][NUM:min=15;max=25:*:#LENGTH][NUM:value=(#WIDTH:mul_(#LENGTH)):*:#AREA][NUM:min=0;max=1:*:#W]" +
                        "[#1:n] מסדר/ת [#I1:p] בצורת מלבן גדול ב[#P1:s]. בשורה אחת יש [#LENGTH] [#I1:p], ובעמודה אחת יש [#WIDTH] [#I1:p]. [IF:(#W)=0:<כמה [#I1:p] סך הכל יש במלבן המלא?>:<אם המלבן המלא מכיל בדיוק [#AREA] [#I1:p], ובשורה אחת יש [#LENGTH] [#I1:p], כמה [#I1:p] יש בעמודה אחת?>]",
                // תשובה נכונה
                "[IF:(#W)=0:<[NUM:value=(#AREA):#R]>:<[NUM:value=(#WIDTH):#R]>]",
                // תשובות שגויות
                "[IF:(#W)=0:<[NUM:value=(#WIDTH:add_(#LENGTH)):#R]>:<[NUM:value=(#AREA:div_10):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#WIDTH:add_(#LENGTH)):mul_2:#R]>:<[NUM:value=(#LENGTH:sub_(#WIDTH)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#AREA:sub_(#WIDTH)):#R]>:<[NUM:value=(#WIDTH:add_2):#R]>]",
                // רמז
                "[IF:(#W)=0:<כדי למצוא את הכמות הכוללת במלבן (שטח), כפול את מספר הפריטים בשורה במספר הפריטים בעמודה.>:<חלק את הכמות הכוללת במספר הפריטים שבשורה אחת.>]"
        });

        // תבנית 19: שברים פשוטים (חלוקה לפי מכנה)
        newTemplates.add(new String[]{
                // שאלה
                "[HUMAN:*:#1][ITEM:type=FOOD|TOY:*:#I1][PLACE:*:#P1][NUM:min=3;max=6:*:#PARTS][NUM:min=6;max=12:*:#PER_PART][NUM:value=(#PARTS:mul_(#PER_PART)):*:#TOTAL][NUM:value=(#TOTAL:sub_(#PER_PART)):*:#LEFT][NUM:min=0;max=1:*:#W]" +
                        "ל-[#1:n] היו [#TOTAL] [#I1:p] ב[#P1:s]. [#1:he_she] נתן/נה לחבר בדיוק 1 חלקי [#PARTS] מכל ה-[#I1:p] שהיו לו/לה. [IF:(#W)=0:<כמה [#I1:p] קיבל החבר?>:<כמה [#I1:p] נשארו ל-[#1:n] בסוף?>]",
                // תשובה נכונה
                "[IF:(#W)=0:<[NUM:value=(#PER_PART):#R]>:<[NUM:value=(#LEFT):#R]>]",
                // תשובות שגויות
                "[IF:(#W)=0:<[NUM:value=(#LEFT):#R]>:<[NUM:value=(#PER_PART):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#TOTAL:sub_(#PARTS)):#R]>:<[NUM:value=(#TOTAL:sub_(#PARTS)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#PARTS):#R]>:<[NUM:value=(#TOTAL:add_(#PARTS)):#R]>]",
                // רמז
                "[IF:(#W)=0:<כדי למצוא כמה זה 1 חלקי [#PARTS], עליך לחלק את הכמות הכוללת ב-[#PARTS].>:<קודם חלק את הכמות הכוללת ב-[#PARTS] כדי לדעת כמה החבר קיבל, ואז חסר את זה מהכמות ההתחלתית.>]"
        });

        // תבנית 20: פרופורציות והכפלת כמויות
        newTemplates.add(new String[]{
                // שאלה
                "[PLACE:place_type=STORE:*:#P1][ITEM:type=(#P1:t):*:#I1][NUM:min=5;max=12:*:#PER_BOX][NUM:min=2;max=4:*:#BASE_BOX][NUM:value=(#BASE_BOX:mul_(#PER_BOX)):*:#BASE_ITEM][NUM:min=6;max=10:*:#NEW_BOX][NUM:value=(#NEW_BOX:mul_(#PER_BOX)):*:#NEW_ITEM][NUM:min=0;max=1:*:#W]" +
                        "ב[#P1:s], ארזו [#BASE_ITEM] [#I1:p] בתוך [#BASE_BOX] קופסאות שוות בגודלן. [IF:(#W)=0:<כמה [#I1:p] יהיו בתוך [#NEW_BOX] קופסאות מאותו סוג?>:<אם יש למפעל [#NEW_ITEM] [#I1:p], לכמה קופסאות מאותו סוג הם יזדקקו?>]",
                // תשובה נכונה
                "[IF:(#W)=0:<[NUM:value=(#NEW_ITEM):#R]>:<[NUM:value=(#NEW_BOX):#R]>]",
                // תשובות שגויות
                "[IF:(#W)=0:<[NUM:value=(#BASE_ITEM:add_(#NEW_BOX)):#R]>:<[NUM:value=(#NEW_ITEM:div_2):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#NEW_BOX:mul_(#BASE_ITEM)):#R]>:<[NUM:value=(#NEW_BOX:add_2):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#NEW_ITEM:sub_(#PER_BOX)):#R]>:<[NUM:value=(#NEW_ITEM:sub_(#BASE_ITEM)):#R]>]",
                // רמז
                "[IF:(#W)=0:<חשב תחילה כמה יש בקופסה אחת (על ידי חילוק), ואז כפול במספר הקופסאות החדש.>:<חשב תחילה כמה יש בקופסה אחת, ואז חלק את הכמות הכוללת החדשה בכמות שבקופסה אחת.>]"
        });

        // תבנית 21: שאלת גיל (משוואת חיבור בזמן)
        newTemplates.add(new String[]{
                // שאלה
                "[HUMAN:*:#1][HUMAN:n=!(#1:n):*:#2][NUM:min=10;max=16:*:#AGE1][NUM:min=3;max=7:*:#DIFF][NUM:value=(#AGE1:add_(#DIFF)):*:#AGE2][NUM:min=4;max=10:*:#YEARS][NUM:value=(#AGE1:add_(#YEARS)):*:#F1][NUM:value=(#AGE2:add_(#YEARS)):*:#F2][NUM:value=(#F1:add_(#F2)):*:#SUM_FUTURE][NUM:min=0;max=1:*:#W]" +
                        "[#1:n] בן/בת [#AGE1] כיום. [#2:n] מבוגר/ת מ-[#1:n] ב-[#DIFF] שנים. [IF:(#W)=0:<בעוד [#YEARS] שנים, בן/בת כמה יהיה/תהיה [#2:n]?>:<בעוד [#YEARS] שנים, מה יהיה סכום הגילים של שניהם יחד?>]",
                // תשובה נכונה
                "[IF:(#W)=0:<[NUM:value=(#F2):#R]>:<[NUM:value=(#SUM_FUTURE):#R]>]",
                // תשובות שגויות
                "[IF:(#W)=0:<[NUM:value=(#AGE2):#R]>:<[NUM:value=(#AGE1:add_(#AGE2)):add_(#YEARS):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#AGE1:add_(#YEARS)):#R]>:<[NUM:value=(#F1:add_(#AGE2)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#F2:add_(#DIFF)):#R]>:<[NUM:value=(#SUM_FUTURE:sub_(#DIFF)):#R]>]",
                // רמז
                "[IF:(#W)=0:<חשב קודם את הגיל של [#2:n] כיום, ואז הוסף לו [#YEARS] שנים.>:<חשב את הגיל של כל אחד מהם בעוד [#YEARS] שנים (אל תשכח להוסיף [#YEARS] לכל אחד בנפרד!), ואז חבר אותם.>]"
        });

        // תבנית 22: חיסור מדורג מיעד (ניהול מלאי)
        newTemplates.add(new String[]{
                // שאלה
                "[HUMAN:*:#1][ITEM:type=COLLECTIBLE|MONEY:*:#I1][PLACE:*:#P1][NUM:min=300;max=600;round=50:*:#TARGET][NUM:min=80;max=150;round=10:*:#D1][NUM:min=60;max=120;round=10:*:#D2][NUM:value=(#D1:add_(#D2)):*:#COLLECTED][NUM:value=(#TARGET:sub_(#COLLECTED)):*:#LEFT][NUM:min=0;max=1:*:#W]" +
                        "היעד של [#1:n] הוא לאסוף [#TARGET] [#I1:p] ב[#P1:s]. בשבוע הראשון [#1:he_she] [VERB:id=collect:(past_+(#1:g)+_s)] [#D1] [#I1:p], ובשבוע השני עוד [#D2] [#I1:p]. [IF:(#W)=0:<כמה [#I1:p] עוד נשארו ל-[#1:n] לאסוף כדי להגיע ליעד?>:<כמה [#I1:p] [#1:he_she] [VERB:id=collect:(past_+(#1:g)+_s)] בשבועיים הראשונים יחד?>]",
                // תשובה נכונה
                "[IF:(#W)=0:<[NUM:value=(#LEFT):#R]>:<[NUM:value=(#COLLECTED):#R]>]",
                // תשובות שגויות
                "[IF:(#W)=0:<[NUM:value=(#COLLECTED):#R]>:<[NUM:value=(#LEFT):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#TARGET:sub_(#D1)):#R]>:<[NUM:value=(#COLLECTED:add_(#D2)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#LEFT:add_10):#R]>:<[NUM:value=(#COLLECTED:sub_10):#R]>]",
                // רמז
                "[IF:(#W)=0:<חבר את מה שנאסף בשבוע הראשון והשני, וחסר את הסכום מתוך היעד הכולל.>:<פשוט חבר את שתי הכמויות שנאספו בשבוע הראשון והשני.>]"
        });

        // תבנית 23: היקף מלבן (2 * (אורך + רוחב))
        newTemplates.add(new String[]{
                // שאלה
                "[HUMAN:*:#1][ITEM:type=TOY|STATIONERY:*:#I1][PLACE:place_type=HOME:*:#P1][NUM:min=5;max=12:*:#WIDTH][NUM:min=15;max=25:*:#LENGTH][NUM:value=(#WIDTH:add_(#LENGTH)):*:#HALF][NUM:value=(#HALF:mul_2):*:#PERIMETER][NUM:min=0;max=1:*:#W]" +
                        "[#1:n] מכין/נה מסגרת בצורת מלבן ב[#P1:s] בעזרת [#I1:p]. אורך המסגרת הוא [#LENGTH] [#I1:p] ורוחבה הוא [#WIDTH] [#I1:p]. [IF:(#W)=0:<כמה [#I1:p] יצטרך/תצטרך [#1:n] סך הכל כדי להקיף את כל המסגרת (היקף המלבן)?>:<אם היקף המסגרת דורש בסך הכל [#PERIMETER] [#I1:p], והאורך שלה הוא [#LENGTH] [#I1:p], מהו הרוחב?>]",
                // תשובה נכונה
                "[IF:(#W)=0:<[NUM:value=(#PERIMETER):#R]>:<[NUM:value=(#WIDTH):#R]>]",
                // תשובות שגויות
                "[IF:(#W)=0:<[NUM:value=(#HALF):#R]>:<[NUM:value=(#WIDTH:mul_2):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#WIDTH:mul_(#LENGTH)):#R]>:<[NUM:value=(#PERIMETER:sub_(#LENGTH)):#R]>]",
                "[IF:(#W)=0:<[NUM:value=(#PERIMETER:sub_(#WIDTH)):#R]>:<[NUM:value=(#PERIMETER:div_2):#R]>]",
                // רמז
                "[IF:(#W)=0:<היקף מלבן שווה לפעמיים האורך ועוד פעמיים הרוחב.>:<כדי למצוא את הרוחב, חלק את ההיקף ב-2 (כדי למצוא סכום של אורך ורוחב אחד), ואז חסר מזה את האורך.>]"
        });

        // הרצה של המנוע
        System.out.println("--- מריץ ייצור שאלות ---");

        for (int i = 0; i < newTemplates.size(); i++) {
            memory = new HashMap<>(); // זיכרון נקי לכל שאלה
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

