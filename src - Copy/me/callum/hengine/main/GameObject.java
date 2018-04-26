package me.callum.hengine.main;

import me.callum.hengine.components.Collider;
import me.callum.hengine.math.Vector2;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing an object that can interact with the game world.
 * When instantiated, the instance is placed in the game world and assigned an ID.
 *
 * @Author Hydrogen
 * @Version April 2018
 */
public class GameObject implements IGameObject {

    private boolean shouldFlipTexture = false;
    private boolean draw = true;
    private boolean update = true;
    private boolean destroyed = false;

    private Vector2 position = Vector2.zero();
    private Vector2 scale = new Vector2(1,1);
    private float rotation = 0.0f;

    private String textureName = "";
    private BufferedImage texture = null;
    private int id;

    private List<IGameComponent> components;
    private List<IGameComponent> addQueue;
    private List<IGameComponent> removeQueue;


    /**
     * Defines a new game object and automatically registers it with the game engine.
     */
    public GameObject() {
        components = new ArrayList<IGameComponent>();
        addQueue = new ArrayList<IGameComponent>();
        removeQueue = new ArrayList<IGameComponent>();

        registerObject();
    }

    public void registerObject() {
        Engine.instance.registerEntity(this);
    }


    /**
     * Called internally by the engine,
     * <p>
     * Calls the earlyUpdate() method on all the objects components.
     */
    public void earlyUpdateComponents() {
        for (IGameComponent component : components) component.earlyUpdate();
    }

    /**
     * Called internally by the engine,
     * <p>
     * Calls the lateUpdate() method on all the objects components.
     */
    public void lateUpdateComponents() {
        for (IGameComponent component : components) component.lateUpdate();
    }

    /**
     * Called internally by the engine,
     * <p>
     * Calls the draw(Graphics) method on all the objects components, giving them a chance to
     * draw custom items to the screen. This is called before game objects are drawn
     */
    public void drawComponentsBefore(Graphics g) {
        for (IGameComponent component : components) component.drawBefore(g);
    }

    /**
     * Called internally by the engine,
     * <p>
     * Calls the draw(Graphics) method on all the objects components, giving them a chance to
     * draw custom items to the screen. This is called after game objects are drawn
     */
    public void drawComponentsAfter(Graphics g) {
        for (IGameComponent component : components) component.drawAfter(g);
    }

    /**
     * Called once per frame, per object. Last called <i>Engine.deltaTime</i> seconds ago.
     */
    public void update() {
    }

    /**
     * Adds the specified component as a child of this game object.
     *
     * @param component the component to add.
     */
    public void addComponent(IGameComponent component) {
        if(isDestroyed())return;
        //if (component instanceof Collider) {
        //    Engine.instance.getColliders().add((Collider) component);
        //}
        synchronized(addQueue) {
            addQueue.add(component);
        }
        //this.components.add(component);
    }

    /**
     * Removes the specified component from the game object.
     * Note: Calling this from a component whos child is this object can cause issues.
     *
     * @param component the component's reference to remove from the object.
     */
    public void removeComponent(IGameComponent component) {
        //this.components.remove(component);
        synchronized(removeQueue) {
            removeQueue.add(component);
        }
    }

    /**
     * Called internally.
     * After each entity has been processed, this will register them with the engine
     */
    public void processComponentQueue() {
        synchronized(components) {
            for (IGameComponent comp : addQueue) {
                if (comp instanceof Collider) {
                    Engine.instance.getColliders().add((Collider) comp);
                }

                System.out.println("Added " + comp.getClass()  + " at pos: " + getPosition());

                this.components.add(comp);
            }

            for (IGameComponent comp : removeQueue) {
                if (comp instanceof Collider) {
                    Engine.instance.getColliders().remove(comp);
                }
                System.out.println("Removed " + comp.getClass()  + " from pos: " + getPosition());

                this.components.remove(comp);
            }

            addQueue.clear();
            removeQueue.clear();
        }
    }

    /**
     * Assigns the specified texture as the texture for this game object.
     *
     * @param image the new texture.
     */
    public void setTexture(BufferedImage image) {
        this.texture=image;
    }


    /**
     * Assigns the game object the texture specified. The texture is loaded
     * from the Engine.class's image buffer and should be indexed by it's assigned name.
     *
     * @param name the name of the texture. This should be the same name you used during
     *             loadTexture() and not a path.
     */
    public void setTexture(String name) {
        this.textureName = name;
        this.texture = Engine.instance.getTexture(name);
    }

    /**
     * @param id the which this object will be assigned.
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Sets the x and y positions of the game object
     *
     * @param x the new x position for the object
     * @param y the new y position for the object
     */
    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    /**
     * Copies the x and y co-ordinates from the vector and sets the object's
     * position to be equal to them.
     *
     * @param pos the new position of this object
     */
    public void setPosition(Vector2 pos) {
        this.position.x = pos.x;
        this.position.y = pos.y;
    }

    /**
     * Sets the scale of the object equal to a copy the scale provided.
     * Components should utilize this scale for things such as offsets
     *
     * @param scale the new scale of the object
     */
    public void setScale(Vector2 scale) {
        this.scale.x=scale.x;
        this.scale.y=scale.y;
    }

    /**
     * Sets the rotation of this object to the rotation provided.
     *
     * @param rotation the new rotation of this game object in radians
     */
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    /**
     * Sets whether the texture should be flipped horizontally when it's rendered
     *
     * @param flipped true to flip the texture, false to not.
     */
    public void setTextureFlipped(boolean flipped) {
        this.shouldFlipTexture=flipped;
    }

    public void reset() {}

    /**
     * Set whether or not if this object and it's components should be drawn
     *
     * @param drawn true if you want the object to be drawn, false if not.
     */
    public void setDrawn(boolean drawn) {
        this.draw=drawn;
    }

    /**
     * Sets whether or not the object will receive update() calls and whether components
     * will receive earlyUpdate() and lateUpdate() calls
     *
     * @param update true to keep updating the object, false to sotp
     */
    public void setUpdated(boolean update) {
        this.update=update;
    }

    @Override
    public void drawBefore(Graphics g) { }

    @Override
    public void drawAfter(Graphics g) {}

    /**
     * @return the BufferedImage loaded in by the setTexture() method or null
     * if the texture has since been destroyed.
     */
    public BufferedImage getTexture() {
        return texture;
    }

    /**
     * @return the texture name provided to the setTexture() method.
     */
    public String getTextureName() {
        return textureName;
    }

    /**
     * @return the position the game object is currently at.
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * @return the current x and y scale of the object
     */
    public Vector2 getScale() {
        return scale;
    }

    /**
     * @return the current rotation of the game object in radians.
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * @return whether or not if the object will have update() invoked
     */
    public boolean isUpdated() {
        return update;
    }

    /**
     * @return whether or not if the object will be rendered
     */
    public boolean isDrawn() {
        return draw;
    }



    /**
     * @return the unique identifier the obeject has. This is in the range of 1 <= id <= 2,147,483,647
     */
    public int getID() {
        return id;
    }

    /**
     * @return all components parented to this object.
     */
    public List<IGameComponent> getComponents() {
        return components;
    }

    public List<IGameComponent> getComponents(Class t) {
        List<IGameComponent> found = new ArrayList<IGameComponent>();
        for(IGameComponent component : this.components) {
            if(t.isAssignableFrom(component.getClass())) {
                found.add(component);
            }
        }

        for(IGameComponent component : this.addQueue) {
            if(t.isAssignableFrom(component.getClass())) {
                found.add(component);
            }
        }

        return found;
    }

    public IGameComponent getComponent(Class t) {
        for(IGameComponent component : this.components) {
            if(t.isAssignableFrom(component.getClass())) {
               return component;
            }
        }

        for(IGameComponent component : this.addQueue) {
            if(t.isAssignableFrom(component.getClass())) {
                return component;
            }
        }
        return null;
    }

    public void onCollision(Collider collider, Vector2 normal) {

    }

    @Override
    public void onMousePressed(MouseEvent event) {

    }

    @Override
    public void onMouseReleased(MouseEvent event) {

    }

    @Override
    public void destroy() {
        destroyed=true;
        update=false;
        draw=false;

        addQueue.clear();
        for(IGameComponent component : components) {
            removeComponent(component);
        }
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    public boolean isTextureFlipped() {
        return shouldFlipTexture;
    }
}
