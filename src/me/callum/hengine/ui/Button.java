package me.callum.hengine.ui;

import me.callum.hengine.main.Engine;
import me.callum.hengine.main.GameObject;
import me.callum.hengine.math.Vector2;

import java.awt.Point;
import java.awt.event.MouseEvent;

public abstract class Button extends GameObject {

    private int width;
    private int height;

    private IButtonAction onClicked;
    private boolean mouseHovering;

    public Button() {
        super();
    }

    @Override
    public void registerObject() {
        Engine.instance.getUIObjects().add(this);
    }

    @Override
    public void update() {
        if(!this.isUpdated())return;
        if(!this.isDrawn()) return;

        Point mousePos = Engine.instance.getMousePos();
        Vector2 pos = getPosition().copy();

        int width = (int)(this.width*getScale().x);
        int height = (int)(this.height*getScale().y);

        pos.x -= width/2;
        pos.y -= height/2;

        boolean isMouseOver = false;

        if(mousePos != null) {
            if(mousePos.x > pos.x &&
                    mousePos.x < pos.x + width &&
                    mousePos.y > pos.y &&
                    mousePos.y < pos.y + height) {
                // Mouse is hovering
                isMouseOver=true;
            }
        }

        if(isMouseOver && !mouseHovering) {
            startHovering();
        } else if(!isMouseOver && mouseHovering) {
            stopHovering();
        }

        mouseHovering=isMouseOver;
    }

    public void setWidth(int w) {
        width = w;
    }
    public void setHeight(int h) {
        height = h;
    }

    abstract void startHovering();
    abstract void stopHovering();

    public void setClickedAction(IButtonAction action) {
        onClicked=action;
    }

    public IButtonAction getOnClicked() {
        return onClicked;
    }


    @Override
    public void onMousePressed(MouseEvent e) {
        if(!isDrawn() || !isUpdated()) return;

        if(mouseHovering) {
            if(getOnClicked()!=null) {
                getOnClicked().action(e);
            }
        }
    }

}
