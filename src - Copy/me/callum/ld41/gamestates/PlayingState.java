package me.callum.ld41.gamestates;

import me.callum.hengine.gamestates.GameState;
import me.callum.hengine.main.Engine;
import me.callum.hengine.main.GameObject;
import me.callum.hengine.math.Vector2;
import me.callum.hengine.ui.Text;
import me.callum.ld41.entities.EnemyPlayer;
import me.callum.ld41.entities.FinishSquare;
import me.callum.ld41.entities.Player;
import me.callum.ld41.entities.QuestionEntity;
import me.callum.ld41.level.Level;
import me.callum.ld41.level.LevelCollider;
import me.callum.ld41.level.TileLevelComponent;
import me.callum.ld41.utils.TimeUtils;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

public class PlayingState extends GameState {

    private Vector2 spawnpoint;
    private Player player;
    private EnemyPlayer[] enemies;

    private Level level;
    private TileLevelComponent levelComponent;
    private LevelCollider levelCollider;

    private QuestionEntity questionEntity;
    private float startTime = 0.0f;
    private Text countdown;

    private Text timeElapsed;

    private static int curLevel = 0;

    public PlayingState() {
        super("PlayingState");
    }

    @Override
    public void init() {
        enemies = new EnemyPlayer[9];

        countdown = new Text("3");
        countdown.setFont(new Font("Calibri", Font.BOLD, 108));

        countdown.setDrawn(false);
        countdown.setUpdated(false);

        timeElapsed = new Text("00:00");
        timeElapsed.setFont(new Font("Calibri", Font.BOLD, 50));
        timeElapsed.setDrawn(false);
        timeElapsed.setUpdated(false);

        level = new Level();
        level.setPosition(0, 0);
        level.setScale(new Vector2(0.5f,0.5f));

        levelComponent = new TileLevelComponent(level);
        levelCollider = new LevelCollider(levelComponent);

        loadNextLevel();

        level.setDrawn(false);
        level.setUpdated(false);

        questionEntity = new QuestionEntity();

    }

    @Override
    public void load() {
        Engine.time=0;
        Engine.unscaledTime=0;

        countdown.setPosition(Engine.instance.getWindow().getWidth()/2, Engine.instance.getWindow().getHeight()/2);
        countdown.setDrawn(true);
        countdown.setUpdated(true);

        timeElapsed.setPosition(Engine.instance.getWindow().getWidth()-100, 80);
        timeElapsed.setDrawn(true);
        timeElapsed.setUpdated(true);
        timeElapsed.setText(TimeUtils.formatTimeMMSS(0));

        level.setDrawn(true);
        level.setUpdated(true);

        startTime = Engine.unscaledTime;
        Engine.TIME_SCALE=0;

        for(GameObject obj : getGameObjects()) {
            obj.reset();
        }


        for(EnemyPlayer enemy : enemies) {
            enemy.setDrawn(true);
            enemy.setUpdated(true);
        }

        player.setDrawn(true);
        player.setUpdated(true);

        setSpawnpoints();
    }

    @Override
    public void cleanup() { }

    @Override
    public void update() {
        float timeSinceStarting = Engine.unscaledTime-startTime;

        if(timeSinceStarting<3) {
            countdown.setText(""+(int)Math.ceil(3-timeSinceStarting));
            return;
        } else if(timeSinceStarting < 5) {
            timeElapsed.setText(TimeUtils.formatTimeMMSS((int) (timeSinceStarting-3)));
            Engine.TIME_SCALE=1;
            countdown.setText("Go!");
            return;
        }

        if(countdown.isDrawn()) {
            countdown.setDrawn(false);
            countdown.setUpdated(false);
        }

        timeElapsed.setText(TimeUtils.formatTimeMMSS((int) (timeSinceStarting-3)));
    }

    public void loadNextLevel() {
        curLevel++;
        switch(curLevel) {
            case 1:
                generateWorld("level1");
                break;
            case 2:
                generateWorld("level2");
                break;
            case 3:
                generateWorld("level3");
                curLevel=0;
                break;
            default:
                curLevel=0;
                generateWorld("level1");
            break;
        }
        setSpawnpoints();
    }

    void setSpawnpoints() {
        player.setPosition(spawnpoint);

        for(EnemyPlayer enemy : enemies) {
            enemy.setPosition(spawnpoint);
        }

        player.resetCameraPosition();
    }


    public void generateWorld(String levelID) {
        BufferedImage blueprint = Engine.instance.getTexture(levelID);

        int w = blueprint.getWidth();
        int h = blueprint.getHeight();

        levelComponent.clearTiles();

        for(GameObject object : getGameObjects()) {
            if(object instanceof FinishSquare) {
                object.destroy();
            }
        }

        levelComponent.setSize(w, h);
        float tileSize = level.getTileSize();
        int r, g, b;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Color pixel = new Color(blueprint.getRGB(x, y));
                if (pixel.getAlpha() == 0) continue;

                r = pixel.getRed();
                g = pixel.getGreen();
                b = pixel.getBlue();

                if (r == 255 && g == 0 && b == 0) { // Platform spawn
                    levelComponent.placeTile(x, y);
                } else if (r == 255 && g == 216 && b == 0) { // Player spawn
                    spawnpoint = new Vector2(x * tileSize, (y * tileSize) - 5);
                } else if (r == 0 && g == 148 && b == 255) { // Finish line
                    FinishSquare finishLineSegment = new FinishSquare();
                    finishLineSegment.setPosition(x * tileSize, y * tileSize);
                }
            }
        }

        for(int i = 0; i < enemies.length; i++) {
            if(enemies[i]!=null)
                enemies[i].destroy();

            enemies[i] = new EnemyPlayer(levelComponent);

        }

        if(player != null) {
            player.destroy();
        }
        player = new Player();
    }

    public Level getLevel() {
        return level;
    }
}
