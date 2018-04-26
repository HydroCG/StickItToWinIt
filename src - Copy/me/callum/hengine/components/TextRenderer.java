package me.callum.hengine.components;

import me.callum.hengine.main.Engine;
import me.callum.hengine.main.GameObject;
import me.callum.hengine.math.Vector2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class TextRenderer extends UIComponent {

    private String text;
    private Font font;
    private Vector2 offset = Vector2.zero();

    private boolean worldSpace = false;

    public TextRenderer(GameObject parent, String text, Font font) {
        super(parent);
        this.text=text;
        this.font=font;
    }

    @Override
    public void drawAfter(Graphics g) {
        g.setColor(Color.black);

        Vector2 position = getParent().getPosition();


        int xDraw = (int) (position.x + offset.x) ;
        int yDraw = (int) (position.y + offset.y);

        if(worldSpace) {
            xDraw -= Engine.instance.getCamera().getPosition().x;
            yDraw -= Engine.instance.getCamera().getPosition().y;
        }

        g.setFont(font);

        String text = getText();
        int w = g.getFontMetrics().stringWidth(text);
        g.drawString(text,xDraw-w/2, yDraw+font.getSize()/4);
    }

    public void setUseWorldSpace(boolean worldSpace) {
        this.worldSpace = worldSpace;
    }

    public void setOffset(Vector2 offset) {
        this.offset.clone(offset);
    }

    public void setFont(Font f) {
        this.font = f;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Font getFont() {
        return font;
    }


    public Vector2 getOffset() {
        return offset;
    }
}
