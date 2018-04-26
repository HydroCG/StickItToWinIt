package me.callum.hengine.main;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

public interface IGameComponent {
    void earlyUpdate();
    void lateUpdate();
    void drawBefore(Graphics g);
    void drawAfter(Graphics g);

    void onMousePressed(MouseEvent event);
    void onMouseReleased(MouseEvent event);
}
