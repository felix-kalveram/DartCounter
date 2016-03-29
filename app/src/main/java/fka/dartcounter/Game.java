package fka.dartcounter;

import java.util.ArrayList;


/**
 * Created by FKA on 23.03.2016.
 */
public class Game extends MainActivity {

    int gameType;
    int lastGameAction;
    ArrayList<Integer> gameActions = new ArrayList<>();

    /// constructor
    public Game() {
    }

    public Game(int gameType, int lastGameAction, ArrayList<Integer> gameActions) {
        this.gameType = gameType;
        this.lastGameAction = lastGameAction;
        this.gameActions = gameActions;
    }

    /// getters
    public int getGameType() {
        return gameType;
    }

    public ArrayList<Integer> getGameActions() {
        return gameActions;
    }

    public int getLastGameAction() {
        lastGameAction = gameActions.get(gameActions.size() - 1);
        return lastGameAction;
    }

    /// setters
    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    /// methods
    public ArrayList<Integer> addGameAction(int gameAction) {
        gameActions.add(gameAction);
        return gameActions;
    }

    public ArrayList<Integer> deleteLastGameAction() {
        gameActions.remove(gameActions.size() - 1);
        return gameActions;
    }

}
