package me.callum.hengine.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputManager implements KeyListener, MouseListener {

    private boolean[] keys = new boolean[65535];

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()]=true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()]=false;
    }

    public boolean getKey(int keyCode) {
        return keys[keyCode];
    }

    public void mousePressed(MouseEvent e) {
        Engine.instance.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Engine.instance.mouseReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) { }


    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}
