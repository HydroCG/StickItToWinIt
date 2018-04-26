package me.callum.ld41.gamestates;

import me.callum.hengine.gamestates.GameState;
import me.callum.hengine.main.Engine;
import me.callum.hengine.math.Vector2;
import me.callum.hengine.ui.Text;
import me.callum.hengine.ui.TextButton;

import java.awt.Font;

public class WinState extends GameState {

    private TextButton nextLevelButton;
    private TextButton mainMenuButton;

    private Text text;

    public WinState() {
        super("Win Menu");
    }

    @Override
    public void init() {
        text = new Text("You win!");
        text.setFont(new Font("Calibri", Font.BOLD, 74));

        nextLevelButton = new TextButton("Proceed");
        mainMenuButton = new TextButton("Return to menu");

        nextLevelButton.setNormalImage("redbtn");
        nextLevelButton.setHoveredImage("greenbtn");
        nextLevelButton.setScale(new Vector2(3.0f,2.0f));

        nextLevelButton.setClickedAction((action) -> {
            Engine.instance.getGameStateManager().loadState("PlayingState");
            ((PlayingState)Engine.instance.getGameStateManager().activeGamestate).loadNextLevel();
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

        nextLevelButton.setPosition(screenW/2, 300);
        mainMenuButton.setPosition(screenW/2, 400);

    }

    @Override
    public void cleanup() {

    }
}
