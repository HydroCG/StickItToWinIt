package me.callum.ld41.entities;

import me.callum.hengine.components.Animator;
import me.callum.hengine.components.BoxCollider;
import me.callum.hengine.components.Collider;
import me.callum.hengine.components.PhysicsComponent;
import me.callum.hengine.main.Engine;
import me.callum.hengine.main.GameObject;
import me.callum.hengine.math.Vector2;

public class Character extends GameObject {

    private PhysicsComponent physicsComponent;
    private Animator animator;
    private BoxCollider collider;

    private int xDirection = 1;
    private final int STATE_RUNNING = 0;

    private boolean inAir = false;
    private float moveSpeed = 200.0f;

    private Vector2 inputVector = Vector2.zero();

    private final String[] colors = {"r","b","o"};

    public Character(boolean pickColor) {
            super();


            String colorMod = "";
            if(pickColor) {
                float random = (float)Math.random();
                for(int i = 0; i < colors.length;i++) {
                    if(random < (1.0f/(i+1)) ) {
                        colorMod=colors[i]+"-";
                    }
                }
            }

        physicsComponent = new PhysicsComponent(this);
        collider = new BoxCollider(this, new Vector2(16,16));
        animator = new Animator(this, 0.3f);

        setTexture("player-"+colorMod+"00");
        animator.addAnimation(STATE_RUNNING, "player-"+colorMod+"00");
        animator.addAnimation(STATE_RUNNING, "player-"+colorMod+"10");
    }

    @Override
    public void update() {
        if(Engine.deltaTime==0)return;
        Vector2 moveVector = inputVector.copy();

        boolean invertTexture = false;
        boolean textureFlipped = isTextureFlipped();

        if(moveVector.x > 0) {
            if(textureFlipped) invertTexture=true;
        } else if(moveVector.x < 0) {
            if(!textureFlipped) invertTexture=true;
        }

        if(invertTexture) {
            setTextureFlipped(!isTextureFlipped());
        }

        boolean jump = moveVector.y>0;

        moveVector.mul(moveSpeed);

        if(jump && !inAir) {
            inAir = true;
            physicsComponent.addForce(new Vector2(0,-450));
            onJump();
        }

        physicsComponent.getVelocity().x = moveVector.getX();
    }

    public void onJump() {

    }

    public void setInputVector(Vector2 vec) {
        inputVector.clone(vec);
    }

    public void addInputVector(Vector2 xMove) {
        inputVector.add(xMove);
    }

    @Override
    public void onCollision(Collider col, Vector2 normal) {
        if(normal.y<0 && !col.isTrigger && ! (col.getParent() instanceof me.callum.ld41.entities.Character)) {
            inAir=false;
        }
    }

    public PhysicsComponent getPhysicsComponent() {
        return physicsComponent;
    }

    public BoxCollider getCollider() {
        return collider;
    }

    public int getHeading() {
        return xDirection;
    }
}
