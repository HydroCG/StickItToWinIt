package me.callum.hengine.gfx;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class SpriteLoader {

    public static BufferedImage LoadSprite(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(SpriteLoader.class.getResource("/"+path));
        } catch (Exception e) {
            System.out.println("Failed to load the image: " + path);
            e.printStackTrace();
        }

        return image;
    }

    public static BufferedImage tintSprite(BufferedImage image, Color tint) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        int w = image.getWidth();
        int h = image.getHeight();

        int nr = tint.getRed();
        int ng = tint.getGreen();
        int nb = tint.getBlue();
        int na = tint.getAlpha();

        int r,g,b,a;

        for(int x = 0; x<w;x++) {
            for(int y=0;y<h;y++) {
                int pixel = image.getRGB(x,y);

                a = (((pixel>>24) & 0xff));
                r = (((pixel>>16) & 0xff)+nr)/2;
                g = (((pixel>>8) & 0xff)+ng)/2;
                b = (((pixel) & 0xff)+nb)/2;

                newImage.setRGB(x,y,(a<<24)|(r<<16)|(g<<8)|b);
            }
        }

        return newImage;
    }
}
