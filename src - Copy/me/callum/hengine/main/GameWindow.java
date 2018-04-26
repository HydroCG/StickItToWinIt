package me.callum.hengine.main;

import javax.swing.JFrame;
import java.awt.Dimension;

public class GameWindow extends JFrame {

    public GameWindow(String title, int w, int h) {
        super(title);

        setPreferredSize(new Dimension(w,h));
        setSize(w,h);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

}
