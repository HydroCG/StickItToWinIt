package me.callum.hengine.main;

import me.callum.hengine.components.Collider;
import me.callum.hengine.math.Vector2;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;

public interface IGameObject {

    void earlyUpdateComponents();

    void lateUpdateComponents();

    void drawComponentsBefore(Graphics g);
    void drawComponentsAfter(Graphics g);

    void registerObject();
    void addComponent(IGameComponent component);
    void removeComponent(IGameComponent component);

    void setTexture(BufferedImage texture);
    void setTexture(String name);
    void setID(int id);
    void setPosition(float x, float y);
    void setPosition(Vector2 pos);
    void setRotation(float radians);

    /**
     * Called after the components drawBefore() but before drawAter and component#drawAfter()
     * @param g a valid graphics context
     */
    void drawBefore(Graphics g);

    /**
     * Called before the components drawAfter() and after the texture is drawn
     * @param g a valid graphics context
     */
    void drawAfter(Graphics g);

    void setDrawn(boolean drawn);
    void setUpdated(boolean update);
    boolean isDrawn();
    boolean isUpdated();

    void reset();

    void onCollision(Collider collider, Vector2 normal);
    void onMousePressed(MouseEvent event);
    void onMouseReleased(MouseEvent event);

    void destroy();
    boolean isDestroyed();

    int getID();

    BufferedImage getTexture();
    String getTextureName();
    Vector2 getPosition();
    float getRotation();
    boolean isTextureFlipped();

    List<IGameComponent> getComponents();
    IGameComponent getComponent(Class t);
}
