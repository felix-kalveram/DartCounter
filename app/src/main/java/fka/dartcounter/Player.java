package fka.dartcounter;

import java.util.ArrayList;


/**
 * Created by FKA on 07.03.2016.
 */
public class Player extends MainActivity {

    private String playerName;

    private int gameType;
    private int remainingPoints;
    private int playedRounds;
    private int thrownDarts;
    private int roundPoints;
    private int averageRound;
    private int averageAll;

    private boolean playing;
    private boolean winning;

    private ArrayList<Integer> arrayListRoundPoints;


    ///***   Constructor   ***///
    public Player() {
    }

    public Player(String playerName,
                  int gameType,
                  int remainingPoints,
                  int playedRounds,
                  int thrownDarts,
                  int roundPoints,
                  int averageRound,
                  int averageAll,
                  boolean playing) {
        this.playerName = playerName;
        this.gameType = gameType;
        this.remainingPoints = gameType;
        this.playedRounds = 0;
        this.thrownDarts = 0;
        this.roundPoints = 0;
        this.averageRound = 0;
        this.averageAll = 0;
        this.playing = false;
        this.winning = false;
    }


    ///***   Getters   ***///
    public String getPlayerName() {
        return playerName;
    }

    public int getGameType() {
        return gameType;
    }

    public int getRemainingPoints() {
        return remainingPoints;
    }

    public int getPlayedRounds() {
        return playedRounds;
    }

    public int getThrownDarts() {
        return thrownDarts;
    }

    public int getRoundPoints() {
        return roundPoints;
    }

    public int getAverageRound() {
        return averageRound;
    }

    public int getAverageAll() {
        return averageAll;
    }

    public ArrayList<Integer> getArrayListRoundPoints() {
        return arrayListRoundPoints;
    }

    public boolean isPlaying() {
        return playing;
    }

    public boolean isWinning() {
        return winning;
    }

    ///***   Setters   ***///
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    public void setRemainingPoints(int remainingPoints) {
        this.remainingPoints = remainingPoints;
    }

    public void setPlayedRounds(int playedRounds) {
        this.playedRounds = playedRounds;
    }

    public void setThrownDarts(int thrownDarts) {
        this.thrownDarts = thrownDarts;
    }

    public void setRoundPoints(int roundPoints) {
        this.roundPoints = roundPoints;
    }

    public void setAverageRound(int averageRound) {
        this.averageRound = averageRound;
    }

    public void setAverageAll(int averageAll) {
        this.averageAll = averageAll;
    }

    public void setArrayListRoundPoints(ArrayList<Integer> arrayListRoundPoints) {
        this.arrayListRoundPoints = arrayListRoundPoints;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public void setWinning(boolean winning) {
        this.winning = winning;
    }

    ///***   Methods   ***///
    public ArrayList<Integer> addRoundToArrayList() {
        arrayListRoundPoints.add(roundPoints);
        return arrayListRoundPoints;
    }

    public int calculateAverageRound() {
        averageRound = (sumArrayList(arrayListRoundPoints) / playedRounds);
        return averageRound;
    }

    public int calculateAverageAll() {
        averageAll = (sumArrayList(arrayListRoundPoints) / thrownDarts);
        return averageAll;
    }

    public ArrayList<Integer> removeLastRound(ArrayList<Integer> arrayList) {
        if (arrayList.size() > 0) {
            remainingPoints += (arrayList.size() - 1);
            arrayList.remove((arrayList.size() - 1));
            calculateAverageRound();
            calculateAverageAll();
        }
        return arrayListRoundPoints;
    }

    public int sumArrayList(ArrayList<Integer> arrayList) {
        int sum = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            sum += arrayList.get(i);
        }
        return sum;
    }

    public int calculateRoundPoints(int dart1, int dart2, int dart3) {
        roundPoints = dart1 + dart2 + dart3;
        return roundPoints;
    }

    public void calculateRemainingPoints(Player player, int dart1, int dart2, int dart3) {
        player.setRemainingPoints(getRemainingPoints() - calculateRoundPoints(dart1, dart2, dart3));
    }

    public void playerWins(Player player) {
        if (player.getRemainingPoints() == 0) {
            player.setWinning(true);
        }
    }

}
