package me.callum.ld41.quiz;

import java.util.Random;

public class QuestionGenerator {

    private QuestionPool pool;
    private Random random;

    public QuestionGenerator() {
        pool = new QuestionPool();
        random = new Random();

        loadQuestions();
    }

    public Question pickQuestion() {
        int index = random.nextInt(pool.getQuestions().size());
        return pool.getQuestion(index);
    }

    public String pickCorrectAnswer(Question q) {
        int index = random.nextInt(q.answers.length);
        return q.answers[index];
    }

    public String[] pickWrongAnswers(Question q) {
        int answersPicked = 0;
        int[] indexesUsed = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        String[] answers = new String[2];

        do {
            int randomIndex = random.nextInt(q.incorrectAnswers.length);
            boolean canUse = true;
            for(int i = 0; i < answers.length; i++) {
                if(indexesUsed[i]==randomIndex) {
                    canUse = false;
                    break;
                }
            }

            if(canUse) {
                indexesUsed[answersPicked] = randomIndex;
                answers[answersPicked] = q.incorrectAnswers[answersPicked];
                answersPicked++;
            }
        } while(answersPicked<2);

        return answers;
    }

    void loadQuestions() {
        pool.addQuestion(
                "What is 10 + 5",
                new String[]{"15"},
                new String[]{"11", "9", "16", "19", "22", "41"});

        pool.addQuestion(
                "What of the following is prime?",
                new String[]{"13", "17", "31", "37"},
                new String[]{"10", "4", "9", "15", "21", "33", "144", "27", "14"});

        pool.addQuestion(
                "Which Ludum Dare was this game made for?",
                new String[]{"41"},
                new String[]{"42", "44", "39", "35", "40"});

        pool.addQuestion(
                "What is the most abundant gas in the atmosphere?",
                new String[]{"Nitrogen"},
                new String[]{"Hydrogen", "Oxygen", "Carbon Dioxide", "Argon", "Helium"});

        pool.addQuestion(
                "What is the tallest building in the world?",
                new String[]{"Burj Khalifa"},
                new String[]{"Shanghai Tower", "Trump Tower", "Zifeng Tower", "23 Marina", "Almas Tower", "Gevora Hotel"});

        pool.addQuestion(
                "Camels are best suited for which environment?",
                new String[]{"Deserts"},
                new String[]{"Oceans", "Flatlands", "Mountains", "Rural Areas", "Public Events"});

        pool.addQuestion(
                "Steel is an alloy of iron and what?",
                new String[]{"Carbon"},
                new String[]{"Argon", "Beetroot", "Calcium", "Caesium", "Copper"});

        pool.addQuestion(
                "To jump, press what?",
                new String[]{"Space"},
                new String[]{"W", "A", "S", "D", "Control", "Shift", "Up Arrow"});

    }
}
