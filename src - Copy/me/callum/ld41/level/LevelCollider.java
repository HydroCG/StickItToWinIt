package me.callum.ld41.level;

import me.callum.hengine.components.BoxCollider;
import me.callum.hengine.components.Collider;
import me.callum.hengine.components.ColliderType;
import me.callum.hengine.components.SphereCollider;
import me.callum.hengine.math.Vector2;

public class LevelCollider extends Collider {

    private TileLevelComponent levelComponent;

    public LevelCollider(TileLevelComponent tileLevel) {
        super(tileLevel.getParent(), ColliderType.LEVEL);
        levelComponent=tileLevel;
    }

    @Override
    public boolean isCollidingWithLevel(LevelCollider col) {
        return false;
    }

    @Override
    public boolean isCollidingWithBox(BoxCollider col) {

        Vector2 checkVector = col.getPosition().copy();
        Vector2 extents = col.getExtents();

        // Bottom Right
        checkVector.add(extents);
        if(checkCollision(checkVector)) return true;


        // Bottom Left
        checkVector.x -= extents.x*2;
        if(checkCollision(checkVector)) return true;

        // Top Left
        checkVector.y -= extents.y*2;
        if(checkCollision(checkVector)) return true;

        // Top Right
        checkVector.x += extents.x*2;
        if(checkCollision(checkVector)) return true;

        return false;
    }

    boolean checkCollision(Vector2 position) {
        Tile container = levelComponent.getTileAtCoordinate(position.x,position.y);
        if(container==null)return false;

        float tileSize =levelComponent.getLevel().getTileSize();
        float hTileSize = tileSize;

        float containerX = container.getX()*tileSize;
        float containerY = container.getY()*tileSize;

        return (position.x >= containerX - (hTileSize+1) &&
                position.x <= containerX + (hTileSize+1) &&
                position.y >= containerY - (hTileSize+1) &&
                position.y <= containerY + (hTileSize+1));
    }

    @Override
    public boolean isCollidingWithSphere(SphereCollider col) {
        return false;
    }
}
