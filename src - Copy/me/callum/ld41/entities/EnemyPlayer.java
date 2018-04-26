package me.callum.ld41.entities;

import me.callum.hengine.components.PhysicsComponent;
import me.callum.hengine.main.Engine;
import me.callum.hengine.math.Vector2;
import me.callum.ld41.level.Tile;
import me.callum.ld41.level.TileLevelComponent;

public class EnemyPlayer extends Character{

    private TileLevelComponent levelComponent;

    private float startAfter = 0.0f;

    private float lastSpeedBoost = 0.0f;
    public EnemyPlayer(TileLevelComponent tileLevelComponent) {
        super(true);

        // Delay the objects start so they don't all run in sync
        startAfter = 0.3f + (float)Math.random()/2.0f;

        this.levelComponent=tileLevelComponent;
    }

    public void update() {
        if(Engine.time<startAfter) {
            setInputVector(new Vector2(0,0));
            return;
        }

        if(Engine.time+(startAfter*3)-lastSpeedBoost>9) {
            if(Math.random()<0.7) {
                ((PhysicsComponent)getComponent(PhysicsComponent.class)).addMultiplier(0.25f, 2.0f);
            } else {
                ((PhysicsComponent)getComponent(PhysicsComponent.class)).addMultiplier(-0.25f, 2.0f);
            }
            lastSpeedBoost=Engine.time;
        }


        Vector2 action = findAction();
        //setInputVector(new Vector2(1,1));
        setInputVector(action);
        super.update();
    }

    Vector2 findAction() {
        Vector2 newVec = Vector2.zero();
        Vector2 pos = getPosition();
        float tileSize = levelComponent.getLevel().getTileSize()+5;

        boolean isJumping = false;
        for(int x = 1; x <= 3; x++) {
            Tile eastOfPlayer = levelComponent.getTileAtCoordinate(pos.x + (x*tileSize), pos.y+tileSize/2);

            for(int deepness = 1; deepness < 2; deepness++) {
                Tile southEast = levelComponent.getTileAtCoordinate(pos.x + x*tileSize, pos.y+(deepness*tileSize)+5);
                if(southEast == null) {
                    newVec.y = 1;
                    isJumping = true;
                    break;
                }
            }

            if(eastOfPlayer!=null) {
                newVec.y=1;
                isJumping=true;
            }

            if(isJumping) break;
        }

        newVec.x=1;
        return newVec;
    }
}
