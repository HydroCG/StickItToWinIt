package me.callum.hengine.components;

import me.callum.hengine.main.Engine;
import me.callum.hengine.main.GameObject;
import me.callum.hengine.math.Vector2;
import me.callum.ld41.level.LevelCollider;

import java.awt.Color;
import java.awt.Graphics;

public class BoxCollider extends Collider {

    public Vector2 extents;

    public BoxCollider(GameObject parent, Vector2 extents) {
        super(parent, ColliderType.TYPE_BOX);
        this.extents = extents;
    }

    @Override
    public void lateUpdate() {
    }

    @Override
    public void drawBefore(Graphics g) {

    }


    @Override
    public boolean isCollidingWithLevel(LevelCollider col) {
        return col.isCollidingWithBox(this);
    }

    @Override
    public boolean isCollidingWithBox(BoxCollider col) {
        long start = System.nanoTime();
        Vector2 p1 = getPosition();
        Vector2 p2 = col.getPosition();

        Vector2 e1 = getExtents();
        Vector2 e2 = col.getExtents();

        return  !(p1.x + e1.x < p2.x - e2.x ||
                p1.x - e1.x > p2.x + e2.x ||
                p1.y + e1.y < p2.y - e2.y ||
                p1.y - e1.y > p2.y + e2.y);
    }

    // TODO: Implement scaling with box-sphere collisions
    @Override
    public boolean isCollidingWithSphere(SphereCollider col) {
        Vector2 p1 = getParent().getPosition().copy();
        p1.add(offset);

        Vector2 p2 = col.getParent().getPosition().copy();
        p2.add(col.offset);

        Vector2 difference = p1.copy();
        difference.sub(p2);
        difference.normalize();

        float sphereLineDist = Math.min(p1.squareDistance(p2), col.radius)+2;

        float x = p2.getX() + (float)(sphereLineDist * Math.sin(difference.x));
        float y = p2.getY() + (float)(sphereLineDist * Math.sin(difference.y));

        if(x < p1.x-extents.x ||
            x > p1.x+extents.x ||
            y < p1.y-extents.y ||
            y > p1.y+extents.y){

            // We need to check if edges are colliding instead
            float rSquared = col.radius*col.radius;

            // Top left
            Vector2 vertex = new Vector2(p1.x-extents.x, p1.y-extents.y);
            if(vertex.squareDistance(p2)<rSquared) return true;

            // Bottom left
            vertex.y = p1.y+extents.y;
            if(vertex.squareDistance(p2)<rSquared) return true;

            // Bottom right
            vertex.x = p1.x+extents.x;
            if(vertex.squareDistance(p2)<rSquared) return true;

            // Top right
            vertex.y = p1.y + extents.y;
            if(vertex.squareDistance(p2)<rSquared) return true;

        } else {
            return true;
        }
        return false;
    }

    @Override
    public void drawAfter(Graphics g)
    {
        if(!drawWireframe)return;

        Vector2 position = getPosition();
        Vector2 extents = getExtents();
        Vector2 cameraPos = Engine.instance.getCamera().getPosition();

        g.setColor(Color.yellow);
        g.drawRect(position.intX()-cameraPos.intX()-extents.intX(),
                position.intY()-cameraPos.intY()-extents.intY(),
                extents.intX()*2,
                extents.intY()*2);
    }


    public Vector2 getPosition() {
        Vector2 position = getParent().getPosition().copy();
        position.x += (offset.x * getParent().getScale().x);
        position.y += (offset.y * getParent().getScale().y);

        return position;
    }

    public Vector2 getOffset() {
        Vector2 offset = this.offset.copy();
        offset.mul(getParent().getScale());
        return offset;
    }

    public Vector2 getExtents() {
        Vector2 extents = this.extents.copy();
        extents.mul(getParent().getScale());
        return extents;
    }
}
