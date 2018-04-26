package me.callum.hengine.components;

import me.callum.hengine.main.GameObject;
import me.callum.hengine.main.IGameComponent;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

public abstract class GameObjectComponent implements IGameComponent {

    private GameObject parent;

    public GameObjectComponent(GameObject parent) {
        this.parent = parent;
        parent.addComponent(this);
    }

    public GameObject getParent() {
        return parent;
    }


    @Override
    public void earlyUpdate() {}

    @Override
    public void lateUpdate() {}

    @Override
    public void drawBefore(Graphics g) { }

    @Override
    public void drawAfter(Graphics g) {  }

    @Override
    public void onMousePressed(MouseEvent event) {}

    @Override
    public void onMouseReleased(MouseEvent event) {}

}
