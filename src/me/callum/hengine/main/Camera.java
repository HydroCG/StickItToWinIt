package me.callum.hengine.main;

import me.callum.hengine.math.Vector2;

public class Camera {

    private Vector2 position;
    private Vector2 offset;

    public Camera(Vector2 position) {
        this.position = position;
        this.offset=Vector2.zero();
    }

    public void setOffset(Vector2 offset) {
        this.offset.x=offset.x;
        this.offset.y=offset.y;
    }

    public void setPosition(Vector2 pos) {
        this.position.x=pos.x;
        this.position.y=pos.y;
    }

    public Vector2 getOffset() {
        return offset;
    }

    public Vector2 getPosition() {
        return position;
    }
}
