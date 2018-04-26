package me.callum.hengine.components;

import me.callum.hengine.main.Engine;
import me.callum.hengine.main.GameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Animator extends GameObjectComponent{

    public HashMap<Integer, ArrayList<BufferedImage>> animations;

    private int currentFrame = 0;
    private float timeElapsed = 0;
    private int state = 0;

    private float secondsPerFrame = 1.0f;

    public Animator(GameObject parent, float secondsPerFrame) {
        super(parent);
        animations = new HashMap<Integer, ArrayList<BufferedImage>>();
        this.secondsPerFrame=secondsPerFrame;
    }

    @Override
    public void earlyUpdate() {
        timeElapsed += Engine.deltaTime;

        if(timeElapsed > secondsPerFrame) {
            timeElapsed=0;
            currentFrame = (currentFrame + 1) % animations.get(state).size();
            getParent().setTexture(animations.get(state).get(currentFrame));
        }
    }

    public void setSecondsPerFrame(float seconds) {
        this.secondsPerFrame=seconds;
    }

    public void addAnimation(int state, String texture) {
        if(!animations.containsKey(state)) {
            animations.put(state, new ArrayList<BufferedImage>());
        }

        animations.get(state).add(Engine.instance.getTexture(texture));
    }


    public void setState(int state) {
        this.state =state;
        this.timeElapsed=0;
        this.currentFrame=0;
    }

    public float getSecondsPerFrame() {
        return secondsPerFrame;
    }

    public void lateUpdate() { }
    public void drawBefore(Graphics g) { }
    public void drawAfter(Graphics g) { }
}
