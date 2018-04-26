package me.callum.ld41.entities;

import me.callum.hengine.components.PhysicsComponent;
import me.callum.hengine.main.Engine;
import me.callum.hengine.main.GameObject;
import me.callum.hengine.math.Vector2;
import me.callum.hengine.ui.Button;
import me.callum.hengine.ui.IButtonAction;
import me.callum.hengine.ui.Text;
import me.callum.hengine.ui.TextButton;
import me.callum.ld41.quiz.Question;
import me.callum.ld41.quiz.QuestionGenerator;

import java.awt.Font;
import java.util.Random;

public class QuestionEntity extends GameObject {

    public static QuestionEntity instance;

    private Text question;
    private TextButton[] quizAnswerButtons;
    private float timeSinceLastQuestion = 0.0f;
    private boolean questionActive = false;
    private QuestionGenerator questionGenerator;

    private IButtonAction correctAnswer;
    private IButtonAction wrongAnswer;

    public QuestionEntity() {
        super();

        if(instance != null) {
            destroy();
            return;
        }

        instance = this;

        questionGenerator = new QuestionGenerator();

        question = new Text("");
        question.setFont(new Font("Calibri", Font.BOLD, 28));

        quizAnswerButtons = new TextButton[3];

        correctAnswer = (event) -> {
            endQuestion((PhysicsComponent) Player.instance.getComponent(PhysicsComponent.class), true);
            System.out.println("Correct!");
        };

        wrongAnswer = (event) -> {
            endQuestion((PhysicsComponent) Player.instance.getComponent(PhysicsComponent.class), false);
            System.out.println("Incorrect!");
        };

        for(int i = 0; i < quizAnswerButtons.length; i++) {
            TextButton b = null;
            quizAnswerButtons[i] = (b = new TextButton("b"));
            b.getTextRenderer().setFont(new Font("Calibri", Font.BOLD, 20));
            b.setScale(new Vector2(2,1));
            b.setNormalImage("redbtn");
            b.setHoveredImage("greenbtn");
        }


        setDrawn(false);
    }

    void endQuestion(PhysicsComponent answerer, boolean correct) {
        questionActive=false;
        setDrawn(false);

        if(correct) {
            answerer.addMultiplier(0.25f, 2.0f);
        } else {
            answerer.addMultiplier(-0.25f, 2.0f);
        }
    }

    Random random = new Random();
    public void update() {
        if(!questionActive) {
            if (timeSinceLastQuestion > 4.0f) {
                Question question = questionGenerator.pickQuestion();
                String answer = questionGenerator.pickCorrectAnswer(question);
                String[] wrongAnswers = questionGenerator.pickWrongAnswers(question);

                int scrW = Engine.instance.getWindow().getWidth();

                this.question.setText(question.question);
                this.question.setPosition(scrW/2, 100);

                int correctButtonIndex = random.nextInt(quizAnswerButtons.length);

                quizAnswerButtons[correctButtonIndex].getTextRenderer().setText(answer);
                quizAnswerButtons[correctButtonIndex].setClickedAction(correctAnswer);

                int plotted = 0;
                for(int i = 0; i < quizAnswerButtons.length; i++) {
                    quizAnswerButtons[i].setPosition(scrW/2, 100 + 50*(i+1));
                    if(i!=correctButtonIndex) {
                        quizAnswerButtons[i].getTextRenderer().setText(wrongAnswers[plotted]);
                        quizAnswerButtons[i].setClickedAction(wrongAnswer);
                        plotted++;
                    }
                }

                setDrawn(true);
                setUpdated(true);
                questionActive=true;
                timeSinceLastQuestion=0;
            }

            timeSinceLastQuestion += Engine.deltaTime;
        }
    }

    @Override
    public void destroy() {
        if(question!=null)
            question.destroy();

        if(quizAnswerButtons!=null) {
            for (int i = 0; i < quizAnswerButtons.length; i++) {
                if (quizAnswerButtons[i] != null)
                    quizAnswerButtons[i].destroy();
            }
        }
    }

    @Override
    public void setDrawn(boolean b) {
        super.setDrawn(b);
        question.setDrawn(b);
        for(Button button : quizAnswerButtons) {
            button.setDrawn(b);
        }
    }

    @Override
    public void setUpdated(boolean b) {
        super.setUpdated(b);
        question.setUpdated(b);

        for(Button button : quizAnswerButtons) {
            button.setUpdated(b);
        }
    }

    @Override
    public void reset() {
        setDrawn(false);
    }
}
