package com.k2sw.opentf;

import java.util.ArrayList;

public class GameStateBuilder implements GameStateOrBuilder {
    //Between 0 and 14
    private int oxygen;
    //Between -30 and 10, in increments of 2
    private int temperature;
    private PlayerBuilder[] players;

    public GameStateBuilder(int oxygen, int temperature, PlayerBuilder[] players) {
        this.oxygen = oxygen;
        this.temperature = temperature;
        this.players = players;
    }

    public int getOxygen() {
        return oxygen;
    }

    public void setOxygen(int oxygen) {
        this.oxygen = oxygen;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public PlayerBuilder[] getPlayers() {
        return players;
    }

    public void setPlayers(PlayerBuilder[] players) {
        this.players = players;
    }

    public PlayerBuilder getPlayerByID(PlayerID id){
        for (PlayerBuilder p : players){
            if (p.getPlayerID() == id) return p;
        }
        return null;
    }

    public GameState build() {
        Player[] result = new Player[players.length];
        for (int i = 0; i < players.length; i++){
            result[i] = players[i].build();
        }


        return new GameState(oxygen, temperature, result);
    }
}
