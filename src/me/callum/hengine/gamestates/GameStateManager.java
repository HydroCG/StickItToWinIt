package me.callum.hengine.gamestates;

import java.util.ArrayList;
import java.util.List;

public class GameStateManager {

    private List<GameState> gameStates;

    public GameState activeGamestate;

    public GameStateManager() {
        gameStates = new ArrayList<>();
    }

    public void loadState(String name) {
        GameState state = null;
        for (GameState iter : gameStates) {
            if(iter.getName().equalsIgnoreCase(name)) {
                state=iter;
                break;
            }
        }

        if(state == null) {
            System.err.println("No game state called: '" + name + "' exists");
            return;
        }

        if(activeGamestate!=null)
            activeGamestate.cleanup();
        activeGamestate=state;

        state.activate();
    }

    public void addGameState(GameState state) {
        this.gameStates.add(state);
    }

}
