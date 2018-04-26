package me.callum.hengine.components;

import me.callum.hengine.main.GameObject;
import me.callum.ld41.level.LevelCollider;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

@Deprecated
public class SphereCollider extends Collider {

    private boolean colliding;
    public float radius;

    public SphereCollider(GameObject parent, float radius) {
        super(parent, ColliderType.TYPE_SPHERE);
        this.radius=radius;
    }

    @Override
    public boolean isCollidingWithLevel(LevelCollider col) {
        return false;
    }

    public boolean isCollidingWithBox(BoxCollider col) {
        return col.isCollidingWithSphere(this);
    }

    public boolean isCollidingWithSphere(SphereCollider col) {
        float distance = col.getParent().getPosition().distance(getParent().getPosition());
        return distance < radius + col.radius;
    }

    @Override
    public void lateUpdate() {
        colliding=getCollidingWith().size()>0;
    }

    @Override
    public void drawBefore(Graphics g) {

    }

    @Override
    public void drawAfter(Graphics g)
    {
        if(!drawWireframe)return;

        g.setColor(colliding ? Color.red:Color.yellow);
        ((Graphics2D) g).drawOval((int)(getParent().getPosition().x-radius), (int)(getParent().getPosition().y-radius), (int)radius*2,(int)radius*2);
    }
}
