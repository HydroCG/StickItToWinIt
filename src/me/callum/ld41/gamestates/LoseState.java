package me.callum.ld41.gamestates;

import me.callum.hengine.gamestates.GameState;
import me.callum.hengine.main.Engine;
import me.callum.hengine.math.Vector2;
import me.callum.hengine.ui.Text;
import me.callum.hengine.ui.TextButton;

import java.awt.Font;

public class LoseState extends GameState {

    private TextButton retryButton;
    private TextButton mainMenuButton;

    private Text text;

    public LoseState() {
        super("Lose Menu");
    }

    @Override
    public void init() {
        text = new Text("You lose!");
        text.setFont(new Font("Calibri", Font.BOLD, 74));

        retryButton = new TextButton("Retry");
        mainMenuButton = new TextButton("Return to menu");

        retryButton.setNormalImage("redbtn");
        retryButton.setHoveredImage("greenbtn");
        retryButton.setScale(new Vector2(3.0f,2.0f));

        retryButton.setClickedAction((action) -> {
            Engine.instance.getGameStateManager().loadState("PlayingState");

            //Game.instance.get
        });

        mainMenuButton.setNormalImage("redbtn");
        mainMenuButton.setHoveredImage("greenbtn");
        mainMenuButton.setScale(new Vector2(3.0f,2.0f));

        mainMenuButton.setClickedAction((action) -> {
            Engine.instance.getGameStateManager().loadState("Main Menu");
        });

    }

    @Override
    public void load() {
        int screenW = Engine.instance.getWindow().getWidth();
        int screenH = Engine.instance.getWindow().getHeight();

        text.setPosition(screenW/2,150);

        retryButton.setPosition(screenW/2, 300);
        mainMenuButton.setPosition(screenW/2, 400);

    }

    @Override
    public void cleanup() {

    }
}
