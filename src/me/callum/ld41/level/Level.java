package me.callum.ld41.level;

import me.callum.hengine.main.GameObject;

public class Level extends GameObject {

    public Level() {
        super();

    }

    public float getTileScale() {
        return getScale().x;
    }

    public float getTileSize() {
        return getTileScale()*32;
    }

}
