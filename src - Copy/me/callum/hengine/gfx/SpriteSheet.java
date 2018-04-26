package me.callum.hengine.gfx;

import java.awt.image.BufferedImage;

/**
 * A simple image helper class which retrieved sub images from BufferedImages.
 *
 * @Author Hydrogen
 * @Version April 2018
 */
public class SpriteSheet {

    private BufferedImage image;

    public SpriteSheet(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getSubImage(int x, int y, int w, int h) {
        return image.getSubimage(x, y, w, h);
    }

    public BufferedImage getImage() {
        return image;
    }
}
