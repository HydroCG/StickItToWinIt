package me.callum.ld41.gamestates;

import me.callum.hengine.gamestates.GameState;
import me.callum.hengine.main.Engine;
import me.callum.hengine.math.Vector2;
import me.callum.hengine.ui.Text;
import me.callum.hengine.ui.TextButton;

import java.awt.Font;

public class MainMenu extends GameState {

    private TextButton playButton = null;
    private TextButton quitButton = null;
    private Text title = null;

    public MainMenu() {
        super("Main Menu");
    }

    @Override
    public void init() {
        title = new Text("Stick It To Finish!");
        title.setFont(new Font("Calibri", Font.BOLD, 100));

        playButton = new TextButton("Play");
        quitButton = new TextButton("Quit");

        playButton.setNormalImage("redbtn");
        playButton.setHoveredImage("greenbtn");
        playButton.setScale(new Vector2(3.0f,2.0f));

        playButton.setClickedAction((action) -> {
            Engine.instance.getGameStateManager().loadState("PlayingState");
            //Game.instance.get
        });


        quitButton.setNormalImage("redbtn");
        quitButton.setHoveredImage("greenbtn");
        quitButton.setScale(new Vector2(3.0f,2.0f));

        quitButton.setClickedAction((action) -> {
            System.exit(0);
        });

        //getUIObjects().add(playButton);
        //getUIObjects().add(quitButton);
    }

    @Override
    public void load() {
        int screenWidth = Engine.instance.getWindow().getWidth();
        int screenHeight = Engine.instance.getWindow().getHeight();

        title.setPosition(screenWidth/2,150);

        playButton.setPosition(screenWidth/2, 300);
        quitButton.setPosition(screenWidth/2, 400);
    }

    @Override
    public void cleanup() { }
}
