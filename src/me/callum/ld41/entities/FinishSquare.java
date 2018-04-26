package me.callum.ld41.entities;

import me.callum.hengine.components.BoxCollider;
import me.callum.hengine.components.Collider;
import me.callum.hengine.main.Engine;
import me.callum.hengine.main.GameObject;
import me.callum.hengine.math.Vector2;

public class FinishSquare extends GameObject{

    public FinishSquare() {
        super();
        BoxCollider col = new BoxCollider(this,new Vector2(16,16));
        col.setTrigger(true);
        setTexture("finishline");
    }

    @Override
    public void update() {

    }

    @Override
    public void onCollision(Collider obj, Vector2 normal) {
        if(obj.getParent() instanceof Character) {
            obj.getParent().setDrawn(false);
            obj.getParent().setUpdated(false);
            if (obj.getParent() instanceof Player) {
                //Engine.TIME_SCALE=0;
                Player player = (Player) obj.getParent();
                int place = player.getPlace();

                if (place == 1) {
                    Engine.instance.getGameStateManager().loadState("Win Menu");
                } else {
                    Engine.instance.getGameStateManager().loadState("Lose Menu");
                }
            }
        }
    }
}
