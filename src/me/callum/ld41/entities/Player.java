package me.callum.ld41.entities;

import me.callum.hengine.components.TextRenderer;
import me.callum.hengine.main.Engine;
import me.callum.hengine.main.GameObject;
import me.callum.hengine.main.InputManager;
import me.callum.hengine.math.Vector2;

import javax.sound.sampled.Clip;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

public class Player extends Character {

    private Random random;
    public static Player instance;
    private TextRenderer textRenderer;
    int place = 0;

    public Player() {
        super(false);
        random = new Random();
        instance = this;
        textRenderer = new TextRenderer(this, "1st", new Font("Calibri", Font.BOLD, 24));
        textRenderer.setUseWorldSpace(true);
        textRenderer.setOffset(new Vector2(0,-50));
    }

    @Override
    public void update() {
        if(Engine.deltaTime==0)return;
        InputManager inputManager = Engine.instance.getInputManager();
        Vector2 moveVector = Vector2.zero();

        if(inputManager.getKey(KeyEvent.VK_D)) {
            moveVector.x += 1;
        }

        if(inputManager.getKey(KeyEvent.VK_A)) {
            moveVector.x -= 1;
        }

        if(inputManager.getKey(KeyEvent.VK_SPACE)) {
            moveVector.y = 1;
        }

        setInputVector(moveVector);

        super.update();
        resetCameraPosition();
        updateOverheadText();

        place = 1;

        List<GameObject> gameObjects = Engine.instance.getGameObjects();
        for(GameObject obj : gameObjects) {
            if(obj instanceof Character) {
                if(obj==this) continue;

                if(obj.getPosition().x>getPosition().x) {
                    place++;
                }
            }
        }
    }

    public void resetCameraPosition() {
        Engine.instance.getCamera().getPosition().x = getPosition().x-Engine.instance.getWindow().getWidth()/2;

    }

    @Override
    public void onJump() {
        Clip c = Engine.instance.getAudioClip("jump-"+ (1+random.nextInt(4)));
        c.setMicrosecondPosition(0);
        c.start();
    }

    public int getPlace() {
        return place;
    }

    void updateOverheadText() {
        int place = getPlace();
        String suffix = "th";

        switch(place) {
            case 1:
                suffix="st";
                break;
            case 2:
                suffix = "nd";
                break;
            case 3:
                suffix = "rd";
                break;
            default:
                suffix = "th";
                break;
        }

        textRenderer.setText(place+suffix);
    }


}
