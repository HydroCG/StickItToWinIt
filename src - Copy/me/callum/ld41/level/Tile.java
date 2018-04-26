package me.callum.ld41.level;

import me.callum.hengine.components.BoxCollider;

import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage texture;

    public boolean collisionsEnabled;

    private BoxCollider collider;
    private int tileX, tileY;
    private TileLevelComponent tileComponent;

    public Tile(TileLevelComponent parentComponent, int tileX, int tileY) {
        this.tileComponent=parentComponent;
        this.tileX=tileX;
        this.tileY=tileY;
        this.collisionsEnabled=false;
    }

    public void setCollisionsEnabled(boolean collisionsEnabled) {
        this.collisionsEnabled=collisionsEnabled;

        /*
        if(collisionsEnabled) {
            if(collider==null) {
                collider = new BoxCollider(tileComponent.getParent(),
                        new Vector2(16, 16));
                collider.setOffset(
                        new Vector2((tileX+0.5f)*32,(tileY+0.5f)*32)
                );
            }
        } else {
            if(collider!=null) {
                tileComponent.getParent().removeComponent(collider);
                collider=null;
            }
        }
        */
    }

    public void setTexture(BufferedImage texture) {
        this.texture=texture;
    }

    public boolean isCollisionsEnabled() {
        return collisionsEnabled;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public int getX() {
        return tileX;
    }

    public int getY() {
        return tileY;
    }
}
