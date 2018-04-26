package me.callum.hengine.ui;

import me.callum.hengine.main.Engine;

import java.awt.image.BufferedImage;

public class ImageButton extends Button {

    private BufferedImage normalImage;
    private BufferedImage hoveredImage;

    public void setNormalImage(String texture) {
        this.normalImage=Engine.instance.getTexture(texture);
        setTexture(normalImage);
        setWidth(normalImage.getWidth());
        setHeight(normalImage.getHeight());
    }

    public void setHoveredImage(String texture) {
        this.hoveredImage=Engine.instance.getTexture(texture);
    }

    @Override
    void startHovering() {
        setTexture(hoveredImage);
    }

    @Override
    void stopHovering() {
        setTexture(normalImage);
    }
}
