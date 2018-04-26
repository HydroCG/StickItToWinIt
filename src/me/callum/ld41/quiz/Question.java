package me.callum.ld41.quiz;

public class Question {
    public String question;
    public String[] answers;
    public String[] incorrectAnswers;

    /**
     * Creates a new question object with the specified question and the correct answer provided.
     *
     * @param question the question which will be asked
     * @param correctAnswers the correct answer to the question
     * @param incorrectAnswers the incorrect answers which can be preesnted
     */
    public Question(String question, String[] correctAnswers, String[] incorrectAnswers) {
        this.question = question;
        this.answers=correctAnswers;
        this.incorrectAnswers=incorrectAnswers;
    }

}
