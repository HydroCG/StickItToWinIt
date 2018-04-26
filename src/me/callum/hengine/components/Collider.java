package me.callum.hengine.components;

import me.callum.hengine.main.Engine;
import me.callum.hengine.main.GameObject;
import me.callum.hengine.math.Vector2;
import me.callum.ld41.level.LevelCollider;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public abstract class Collider extends GameObjectComponent {

    public boolean isTrigger = false;
    public Vector2 offset = Vector2.zero();

    public static boolean drawWireframe = false;

    private ColliderType type;

    public Collider(GameObject parent, ColliderType type) {
        super(parent);
        this.type = type;
    }

    public void setOffset(Vector2 offset) {
        this.offset.x=offset.x;
        this.offset.y=offset.y;
    }

    public void setTrigger(boolean trigger) {
        this.isTrigger=trigger;
    }

    public static long CALL_TIME_NANO = 0l;
    public static int CALLS = 0;

    public List<Collider> getCollidingWith() {
        long start = System.nanoTime();
        List<Collider> collidingWith = new ArrayList<Collider>();

        for(Collider collider : Engine.instance.getColliders()) {
            if(collider.getParent()==getParent()) continue;

            if(collider.type==ColliderType.TYPE_SPHERE) {
                if(isCollidingWithSphere((SphereCollider)collider)) {
                    collidingWith.add(collider);
                }
            } else if(collider.type==ColliderType.TYPE_BOX) {
                if(isCollidingWithBox((BoxCollider)collider)) {
                    collidingWith.add(collider);
                }
            } else if(collider.type == ColliderType.LEVEL) {
                if(isCollidingWithLevel((LevelCollider)collider)) {
                    collidingWith.add(collider);
                }
            }
        }


        CALL_TIME_NANO += System.nanoTime()-start;
        CALLS++;
        return collidingWith;
    }

    public abstract boolean isCollidingWithLevel(LevelCollider col);
    public abstract boolean isCollidingWithBox(BoxCollider col);
    public abstract boolean isCollidingWithSphere(SphereCollider col);

    @Override
    public void earlyUpdate() {
    }

    @Override
    public void lateUpdate() { }

    @Override
    public void drawBefore(Graphics g) { }



    public ColliderType getType() {
        return type;
    }

    public Vector2 getOffset() {
        return offset;
    }

    public boolean isTrigger() {
        return isTrigger;
    }

}
