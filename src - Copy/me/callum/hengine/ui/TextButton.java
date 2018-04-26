package me.callum.hengine.ui;

import me.callum.hengine.components.TextRenderer;

import java.awt.Font;
import java.awt.Graphics;

public class TextButton extends ImageButton {
    private TextRenderer textRenderer;

    public TextButton(String text) {
        super();

       textRenderer = new TextRenderer(this, text, new Font("Calibri", Font.PLAIN, 28));
    }

    @Override
    void startHovering() {
        super.startHovering();
    }

    @Override
    void stopHovering() {
        super.stopHovering();
    }


    public void drawAfter(Graphics g) {

    }

    public TextRenderer getTextRenderer() {
        return textRenderer;
    }

}
