package me.callum.hengine.gamestates;

import me.callum.hengine.components.Collider;
import me.callum.hengine.main.GameObject;

import java.util.LinkedList;
import java.util.List;

public abstract class GameState {
    private String name;
    private boolean initialized = false;

    private List<GameObject> gameObjects;
    private List<GameObject> uiObjects;
    private List<Collider> colliders;


    public GameState(String name) {
        this.name=name;

        gameObjects = new LinkedList<>();
        uiObjects = new LinkedList<>();
        colliders = new LinkedList<>();
    }

    void activate() {
        if(!initialized) {
            init();
            initialized = true;
        }
        load();
    }

    public abstract void init();
    public abstract void load();
    public abstract void cleanup();

    public void update() {}

    public void setName(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public List<GameObject> getUIObjects() {
        return uiObjects;
    }

    public List<Collider> getColliders() {
        return colliders;
    }
}
