package me.callum.ld41.main;

import me.callum.hengine.gfx.SpriteLoader;
import me.callum.hengine.gfx.TileSheet;
import me.callum.hengine.main.Engine;
import me.callum.ld41.gamestates.LoseState;
import me.callum.ld41.gamestates.MainMenu;
import me.callum.ld41.gamestates.PlayingState;
import me.callum.ld41.gamestates.WinState;

import java.awt.Color;

public class Game extends Engine {
    public Game() {
        super("LD41 Submission", 1280, 800);
    }

    @Override
    public void init() {
        loadTextures();
        initWorld();
    }

    void initWorld() {
        getGameStateManager().addGameState(new MainMenu());
        getGameStateManager().addGameState(new PlayingState());
        getGameStateManager().addGameState(new WinState());
        getGameStateManager().addGameState(new LoseState());

        getGameStateManager().loadState("Main Menu");
    }

    void loadTextures() {
        int tileW = 32;
        int tileH = 32;

        TileSheet tileSet = new TileSheet(SpriteLoader.LoadSprite("tileset.png"));
        tileSet.loadSubImages(5, 5, tileW, tileH, 0, 0);

        TileSheet playerAnims = new TileSheet(SpriteLoader.LoadSprite("playeranims.png"));
        playerAnims.loadSubImages(5, 5, tileW, tileH, 0, 0);

        Color red = Color.red;
        Color blue = Color.blue;
        Color orange = Color.orange;

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                cacheTexture("player-"   + x + "" + y, playerAnims.getImage(x, y));
                cacheTexture("player-r-" + x + "" + y, SpriteLoader.tintSprite(playerAnims.getImage(x, y),red));
                cacheTexture("player-b-" + x + "" + y, SpriteLoader.tintSprite(playerAnims.getImage(x, y),blue));
                cacheTexture("player-o-" + x + "" + y, SpriteLoader.tintSprite(playerAnims.getImage(x, y),orange));
            }
        }

        cacheTexture("empty", tileSet.getImage(0, 0));
        cacheTexture("finishline", tileSet.getImage(1, 0));

        cacheTexture("level1", SpriteLoader.LoadSprite("level1.png"));
        cacheTexture("level2", SpriteLoader.LoadSprite("level2.png"));
        cacheTexture("level3", SpriteLoader.LoadSprite("level3.png"));

        cacheTexture("redbtn", SpriteLoader.LoadSprite("ui/redbtn.png"));
        cacheTexture("greenbtn", SpriteLoader.LoadSprite("ui/greenbtn.png"));

        // Load player jump sounds
        loadAudio("jump-1", "sound/jump1.wav");
        loadAudio("jump-2", "sound/jump2.wav");
        loadAudio("jump-3", "sound/jump3.wav");
        loadAudio("jump-4", "sound/jump4.wav");
    }

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl","True");
        new Game();
    }

}
