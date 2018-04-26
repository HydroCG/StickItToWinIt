package me.callum.ld41.level;

import me.callum.hengine.components.GameObjectComponent;
import me.callum.hengine.main.Engine;
import me.callum.hengine.main.GameObject;
import me.callum.hengine.math.Vector2;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class TileLevelComponent extends GameObjectComponent {

    private int levelW, levelH;
    private LinkedList<Tile> tiles = new LinkedList<Tile>();
    //private Tile[] tiles;

    public TileLevelComponent(GameObject parent) {
        super(parent);
    }

    public Tile placeTile(int xTile, int yTile) {
        BufferedImage wall = Engine.instance.getTexture("empty");

        int tileID = xTile + yTile * levelW;

        Tile newTile = new  Tile(this,xTile, yTile);
        newTile.setCollisionsEnabled(true);
        newTile.setTexture(wall);
        tiles.add(newTile);
        //tiles[tileID] = newTile;
        return newTile;
    }

    @Override
    public void earlyUpdate() {

    }

    @Override
    public void lateUpdate() {

    }

    float x = 0;
    @Override
    public void drawBefore(Graphics g) {
        Vector2 levelPos = getParent().getPosition();

        Vector2 cameraPos = Engine.instance.getCamera().getPosition();

        int tileSize = (int) getLevel().getTileSize();
        //System.out.println("Potato");
        //getLevel().setTileScale(1);
        float width = Engine.instance.getRenderer().getWidth();
        float height = Engine.instance.getRenderer().getHeight();

        float xPanning = 0;
        float yPanning = 0;

        xPanning = cameraPos.x + levelPos.intX();// + (levelW/2*tileSize);
        yPanning = cameraPos.y + levelPos.intY();// + (levelH/2*tileSize);

        int xDraw,yDraw;
        for(Tile t : tiles) {
            xDraw = (int)(t.getX()*tileSize-xPanning);
            yDraw = (int)(t.getY()*tileSize-yPanning);

            if(xDraw<-tileSize||xDraw>width) {
                continue;
            }

            g.drawImage(t.getTexture(),
                    (xDraw),
                    (yDraw), tileSize, tileSize, null);
        }

        /*      Legacy code, fast with smart collisions, scales badly though
        //if(true) return;
        for(int x = 0; x < levelW; x++) {
            for(int y = 0; y < levelH; y++) {
                //int tileXIndex = (int) (Math.floor(colsNotDrawn)+x);
                //int tileYIndex = (int) (Math.floor(rowsNotDrawn)+y);
                //int tileIndex = tileXIndex + tileYIndex * levelW;

                //if(tileIndex<0||tileIndex>=tiles.length) continue;
                Tile t =tiles[x+(y*levelW)];
                if(t==null)continue;
                g.drawImage(t.getTexture(),
                        (int)((x*tileSize)-xPanning),
                        (int)((y*tileSize)-yPanning), tileSize, tileSize, null);
            }
        }*/
    }

    /**
     * Translates a world-space coordinate into tile-spce and returns the found tile
     * @param x a x coordinate contained within the tile
     * @param y a y coordinate contianed within the tile
     * @return the tile found
     */
    public Tile getTileAtCoordinate(float x, float y) {
        x = (float)Math.floor(x / getLevel().getTileSize());
        y = (float)Math.floor(y / getLevel().getTileSize());
        return getTileAtPos((int) x, (int) y);
    }

    /**
     * Searches the loaded tiles for the tile with the specified x and y coordinate
     * @param x the x coordinate of the tile
     * @param y the y coordinate of the tile
     * @return the tile found or null if none was found
     */
    public Tile getTileAtPos(int x, int y) {
        Tile t = null;

        int upperQuadrant = tiles.size();
        int lowerQuadrant = 0;
        int searchIterations = 0;

        // Treat the array as a binary tree and search it
        while(true) {
            int searchPos = (upperQuadrant+lowerQuadrant)/2;
            t = tiles.get(searchPos);

            //System.out.println("Iteration: " + searchIterations++ + "\tX: " + t.getX() + "\t Y: " + t.getY()
             //       + "\tUQ: " + upperQuadrant + "\t LQ:" + lowerQuadrant);

            if(y > t.getY()) {
                if(upperQuadrant-lowerQuadrant==1) return null;
                lowerQuadrant = searchPos;
            } else if(y < t.getY()) {
                if(upperQuadrant-lowerQuadrant==1) return null;
                upperQuadrant = searchPos;
            } else {
                // y == x
                if(x > t.getX()) {
                    if(upperQuadrant-lowerQuadrant==1) return null;
                    lowerQuadrant = searchPos;
                } else if(x < t.getX()) {
                    if(upperQuadrant-lowerQuadrant==1) return null;
                    upperQuadrant = searchPos;
                } else {
                    return t;
                }
            }

        }
    }

    public void setSize(int x, int y) {
        this.levelW=x;
        this.levelH=y;

        //tiles = new Tile[levelW*levelH];
    }

    @Override
    public void drawAfter(Graphics g) {

    }

    public Level getLevel() {
        return (Level)getParent();
    }

    public void clearTiles() {
        tiles.clear();
    }
}
