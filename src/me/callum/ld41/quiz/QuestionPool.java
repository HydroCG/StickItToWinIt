package me.callum.ld41.quiz;

import java.util.ArrayList;
import java.util.List;

public class QuestionPool {

    private List<Question> questions;

    public QuestionPool() {
        questions = new ArrayList<Question>();
    }

    public void addQuestion(Question q) {
        if(q.answers.length<1) {
            System.err.println("Error: Can't add question: " + q.question + "; it has no answers.");
            return;
        }
        if(q.incorrectAnswers.length<3) {
            System.err.println("Error: Can't add question: " + q.question + "; it has less than three wrong answers.");
            return;
        }

        this.questions.add(q);
    }

    public void addQuestion(String question, String[] answers, String[] wrongAnswers) {
        addQuestion(new Question(question,answers,wrongAnswers));
    }

    public Question getQuestion(int index) {
        return questions.get(index);
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
