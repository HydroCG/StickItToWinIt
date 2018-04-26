package me.callum.hengine.components;

import me.callum.hengine.main.Engine;
import me.callum.hengine.main.GameObject;
import me.callum.hengine.main.IGameComponent;
import me.callum.hengine.math.Vector2;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.List;

public class PhysicsComponent extends GameObjectComponent{

    private boolean gravity = true;

    private Vector2 velocity = Vector2.zero();
    private Vector2 terminalVelocity = new Vector2(300,300);

    private HashMap<Float, Float> multipliers = new HashMap<Float, Float>();

    public PhysicsComponent(GameObject parent) {
        super(parent);
    }

    @Override
    public void earlyUpdate() {
        velocity.y += Engine.deltaTime * 1200;
    }

    @Override
    public void lateUpdate() {
        List<IGameComponent> colliders = getParent().getComponents(Collider.class);
        float mulitplier = calculateMultiplier();

        Vector2 xMove = new Vector2(velocity.x, 0);
        xMove.mul(Engine.deltaTime);
        xMove.mul(mulitplier);

        Vector2 yMove = new Vector2(0, velocity.y);
        yMove.mul(Engine.deltaTime);
        yMove.mul(mulitplier);


        // X move
        if(processCollision(colliders, xMove)) {
            velocity.x=0;
        }

        // Y move
        if(processCollision(colliders, yMove)) {
            velocity.y=0;
        }

    }

    boolean processCollision(List<IGameComponent> colliders, Vector2 moveVector) {
        getParent().getPosition().add(moveVector);
        List<Collider> collidingWith = null;
        boolean collided = false;

        Vector2 normal = moveVector.normalized();
        Vector2 inverted = normal.copy();
        inverted.mul(-1);

        for(IGameComponent iter : colliders) {
            Collider collider = (Collider)iter;
            collidingWith = collider.getCollidingWith();

            for(Collider otherCol : collidingWith) {
                otherCol.getParent().onCollision(collider, moveVector);
                getParent().onCollision(otherCol, inverted);

                if(!otherCol.isTrigger() && ! (otherCol.getParent() instanceof me.callum.ld41.entities.Character) ) {
                    collided=true;
                }

            }
        }

        if(collided) {
            getParent().getPosition().sub(moveVector);
            collided = true;
        }
        return collided;
    }

    @Override
    public void drawBefore(Graphics g) {

    }

    @Override
    public void drawAfter(Graphics g) {

    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.clone(velocity);
    }

    public void setTerminalVelocity(Vector2 velocity) {
        terminalVelocity.clone(velocity);
    }

    public void addForce(Vector2 force) {
        this.velocity.add(force);
    }

    public void setGravityEnabled(boolean gravity) {
        this.gravity = gravity;
    }

    public boolean isGravityEnabled() {
        return gravity;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    float calculateMultiplier() {
        float mul = 1.0f;

        for(Float f : multipliers.keySet()) {
            if(multipliers.get(f)>Engine.time) {
                mul += f;
            } else {
                multipliers.remove(f);
            }
        }
        return mul;
    }

    public void addMultiplier(float multiplier, float time) {

        if(multipliers.containsKey(multiplier)) {
            multipliers.put(multiplier, multipliers.get(multiplier) + time);
        } else {
            multipliers.put(multiplier, Engine.time + time);
        }
    }
}
