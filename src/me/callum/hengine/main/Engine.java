package me.callum.hengine.main;

import me.callum.hengine.components.Collider;
import me.callum.hengine.gamestates.GameStateManager;
import me.callum.hengine.gfx.Renderer;
import me.callum.hengine.gfx.SpriteLoader;
import me.callum.hengine.math.Vector2;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

/**
 * The main class of Engine. This class constructs the numerous engine
 * components and is responsible for maintaining the state of game object.
 *
 * @Author Hydrogen
 * @Version April 2018
 */
public abstract class Engine {

    public static float TIME_SCALE = 1.0f;

    public static float deltaTime = 0.0f;
    public static float time = 0.0f;
    public static float unscaledTime  = 0.0f;

    private int curEntityID = 0;

    private HashMap<String, BufferedImage> textureBuffer;
    private HashMap<String, Clip> clipBuffer;

    public static Engine instance;

    private InputManager inputManager;
    private GameWindow window;
    private Renderer renderer;
    private Camera camera;


    public static boolean ready = false;

    private GameStateManager gameStateManager;

    /**
     * Starts the game and constructs a window with the parameters provided below.
     *
     * @param title the title of the window
     * @param w the width of the frame
     * @param h the height of the frame
     */
    public Engine(String title, int w, int h) {
        camera = new Camera(Vector2.zero());

        textureBuffer = new HashMap<String, BufferedImage>();
        clipBuffer = new HashMap<String, Clip>();

        gameStateManager = new GameStateManager();


        instance = this;

        window = new GameWindow(title, w, h);
        renderer = new Renderer(window);


        window.setVisible(true);
        window.addKeyListener((inputManager = new InputManager()));
        window.addMouseListener(inputManager);

        init();

        ready=true;
    }

    public void mousePressed(MouseEvent e) {
        // TODO: Clean up
        if(!Engine.ready) return;
        for(GameObject object : getGameObjects()) {
            object.onMousePressed(e);
            for(IGameComponent component : object.getComponents()) {
                component.onMousePressed(e);
            }
        }

        for(GameObject object : getUIObjects()) {
            object.onMousePressed(e);
            for(IGameComponent component : object.getComponents()) {
                component.onMousePressed(e);
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(!Engine.ready) return;
        for(GameObject object : getGameObjects()) {
            object.onMouseReleased(e);
            for(IGameComponent component : object.getComponents()) {
                component.onMouseReleased(e);
            }
        }

        for(GameObject object : getUIObjects()) {
            object.onMouseReleased(e);
            for(IGameComponent component : object.getComponents()) {
                component.onMouseReleased(e);
            }
        }
    }

    public abstract void init();

    public List<GameObject> getGameObjects() {
        return gameStateManager.activeGamestate.getGameObjects();
    }

    public List<GameObject> getUIObjects() {
        return gameStateManager.activeGamestate.getUIObjects();
    }

    public List<Collider> getColliders() {
        return gameStateManager.activeGamestate.getColliders();
    }

    public void updateLevel() {
        gameStateManager.activeGamestate.update();
    }

    /**
     * Registers a game object with the game engine so it can be
     * rendered and updated.
     *
     * @param entity the entity to register
     * @return the entity ID of the registered entity.
     */
    public int registerEntity(GameObject entity) {
        this.getGameObjects().add(entity);
        entity.setID(++curEntityID);
        return curEntityID;
    }

    /**
     * Loads a texture and buffers it internally for later use.
     *
     * @param identifier The name used to identify the texture once it's been loaded.
     *                   This is used as a HashMap key internally.
     *
     * @param path The path of the image relative to the source root of the jar file.
     *             E.g. to load "sword.png", the path would simply be "sword.png".
     *
     * @return the buffered image which is loaded and cached.
     */
    public BufferedImage loadTexture(String identifier, String path) {
        BufferedImage sprite = SpriteLoader.LoadSprite(path);
        textureBuffer.put(identifier, sprite);

        return sprite;
    }

    /**
     * Loads a clip into the internalized clip buffer
     *
     * @param identifier the identifier for the clip
     * @param path the path where the clip can be found on the disk.
     *
     * @return the clip loaded in or null if none was loaded
     */
    public Clip loadAudio(String identifier, String path) {
        try {
            AudioInputStream inputStream = null;
            Clip clip = AudioSystem.getClip();
            inputStream = AudioSystem.getAudioInputStream(getClass().getResource("/"+path));
            clip.open(inputStream);
            clipBuffer.put(identifier,clip);
            return clip;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Caches the specified texture into the texture buffer for use later.
     *
     * @param identifier The name of the resource, this is used to retrieve
     *                   the data from a HashMap when the image is required.
     *                   If a texture with this name already exists then it is
     *                   overriden with the new texture.
     *
     * @param texture The texture you want to cache.
     *
     * @return the texture (if any,) overwritten in the buffer.
     */
    public BufferedImage cacheTexture(String identifier, BufferedImage texture) {
        return textureBuffer.put(identifier,texture);
    }

    /**
     * Retrieves a buffered texture identified by the specified name.
     *
     * @param name the name of the texture. The name is the same value input as
     *             the identifier for loadTexture(identifier, path)
     *
     * @return A BufferedImage described by the name or null if no image with the given
     *         name exists
     */
    public BufferedImage getTexture(String name) {
        return textureBuffer.get(name);
    }

    /**
     * Retrieves a buffered audioclip identified with the specified name.
     *
     * @param name the name of the audioclip to retrieve.
     *             Like textures, the name should be the same as provided when it was loaded.
     *
     * @return the audioclip found or null
     */
    public Clip getAudioClip(String name) {
        return clipBuffer.get(name);
    }

    public GameWindow getWindow() { return window; }

    public Renderer getRenderer() {return renderer;}

    public InputManager getInputManager() {
        return inputManager;
    }

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public Camera getCamera() {
        return camera;
    }

    public Point getMousePos() {
        return renderer.getMousePosition();
    }

}
