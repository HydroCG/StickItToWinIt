package me.callum.hengine.gfx;

import me.callum.hengine.components.Collider;
import me.callum.hengine.main.Engine;
import me.callum.hengine.main.GameObject;
import me.callum.hengine.math.Vector2;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple renderer class which draws and updates all objects
 * currently loaded into the game scene.
 *
 * @Author Hydrogen
 * @Version April 2018
 *
 */
public class Renderer extends JPanel {

    /**
     * Creates a renderer instance and adds it as a child to the specified frame.
     *
     * @param frame the JFrame to add the renderer to.
     */
    public Renderer(JFrame frame) {
        super();

        setSize(frame.getSize());
        setPreferredSize(frame.getPreferredSize());
        setVisible(true);
        setBackground(Color.darkGray);

        frame.add(this);

        requestFocusInWindow();
    }


    public void drawObject(Graphics2D g, GameObject object, boolean useCamera) {

        if(!object.isDrawn()) return;

        g.rotate(object.getRotation(), object.getPosition().x, object.getPosition().y);
        Vector2 cameraPos = useCamera ? Engine.instance.getCamera().getPosition() : Vector2.zero();

        object.drawComponentsBefore(g);
        object.drawBefore(g);

        g.setColor(Color.red);
        if(object.getTexture()!=null) {
            int w = (int)(object.getTexture().getWidth() * object.getScale().x);
            int h = (int) (object.getTexture().getHeight() * object.getScale().y);

            if(object.isTextureFlipped()) {
                g.drawImage(object.getTexture(),
                        (int) Math.round(object.getPosition().getX() - cameraPos.getX() - w / 2.0f + w),
                        (int) Math.round(object.getPosition().getY() - cameraPos.getY() - h / 2.0f),
                        -w, h, null);
            } else {
                g.drawImage(object.getTexture(),
                        (int) Math.round(object.getPosition().getX() - cameraPos.getX() - w / 2.0f),
                        (int) Math.round(object.getPosition().getY() - cameraPos.getY() - h / 2.0f),
                        w, h, null);
            }
        }

        object.drawAfter(g);
        object.drawComponentsAfter(g);

        g.rotate(-object.getRotation(), object.getPosition().x, object.getPosition().y);
    }

    double msPerTick = (1000.0/60.0f);
    int frames = 0;
    long timer = System.nanoTime();
    long lastFrame = System.nanoTime();

    /**
     *
     * @param g the graphics object
     * @param obj the object to be provcessed
     * @param useCamera whether or not if the the camera should be used for offsets
     * @return true if the specified object should be deleted
     */
    boolean process(Graphics g, GameObject obj,boolean useCamera) {
        if(obj.isUpdated()) {
            obj.earlyUpdateComponents();
            obj.update();
            obj.processComponentQueue();

            obj.lateUpdateComponents();
        }

        drawObject((Graphics2D) g, obj, useCamera);
        return obj.isDestroyed();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0,0,getWidth(),getHeight());

        long start = System.nanoTime();

        float deltaTime = (start - lastFrame)/1000000000.0f;
        //System.out.println(deltaTime*60);
        Engine.deltaTime=deltaTime*Engine.TIME_SCALE;

        Engine.unscaledTime+=deltaTime;
        Engine.time += deltaTime * Engine.TIME_SCALE;

        List<GameObject> destroyedObjects = new LinkedList<GameObject>();

        if(Engine.ready) {
            for (GameObject renderable : Engine.instance.getGameObjects()) {
                if(process(g,renderable, true)) destroyedObjects.add(renderable);
            }

            for(GameObject object : destroyedObjects) {
                object.processComponentQueue();
                Engine.instance.getGameObjects().remove(object);
            }

            destroyedObjects.clear();
            for (GameObject renderable : Engine.instance.getUIObjects()) {
                process(g,renderable, false);
            }

            for(GameObject object : destroyedObjects) {
                object.processComponentQueue();
                Engine.instance.getUIObjects().remove(object);
            }
            Engine.instance.updateLevel();

        }



        lastFrame = start;

        long now = System.nanoTime();
        long timeTaken = now-start;

        frames++;

        if(now-timer>1000000000) {
            System.out.println("FPS: "+frames);
            frames=0;
            timer=now;

            double totalTime = Collider.CALL_TIME_NANO/1000000d;
            double timePerCall = totalTime/Collider.CALLS;
            Collider.CALL_TIME_NANO=0;
            Collider.CALLS=0;

        }
        repaint();
        if(true) return;
        try {
            long sleepTime = (long)msPerTick-(timeTaken/1000000)-1;
            if(sleepTime>0)
              Thread.sleep(sleepTime);
            repaint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
