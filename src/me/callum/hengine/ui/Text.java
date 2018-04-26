package me.callum.hengine.ui;

import me.callum.hengine.components.TextRenderer;
import me.callum.hengine.main.Engine;
import me.callum.hengine.main.GameObject;

import java.awt.Font;

public class Text extends GameObject {

    public static Font DEFAULT_FONT = new Font("Calibri", Font.PLAIN, 15);

    private TextRenderer textRenderer;

    public Text(String text) {
        super();
        textRenderer = new TextRenderer(this, text, DEFAULT_FONT);
    }

    @Override
    public void registerObject() {
        Engine.instance.getUIObjects().add(this);
    }

    public void setText(String t) {
        textRenderer.setText(t);
    }

    public void setFont(Font font) {
        textRenderer.setFont(font);
    }

    public String getText() {
        return textRenderer.getText();
    }

    public Font getFont() {
        return textRenderer.getFont();
    }

    public TextRenderer getTextRenderer() {
        return textRenderer;
    }
}
