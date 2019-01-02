package com.k2sw.opentf;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameState implements GameStateOrBuilder {
    //Between 0 and 13
    private int oxygen;
    //Between -30 and 10, in increments of 2
    private int temperature;
    private Player[] players;
    private Map<TileSlot, Tile> placedTiles;
    private Set<TileSlot> unplacedSlots;

    public GameState(int oxygen, int temperature, Player[] players) {
        this.oxygen = oxygen;
        this.temperature = temperature;
        this.players = players;
    }

    @Override
    public int getOxygen() {
        return oxygen;
    }

    @Override
    public int getTemperature() {
        return temperature;
    }

    public Player getPlayerByID(PlayerID id){
        for (Player p : players){
            if (p.getPlayerID() == id) return p;
        }
        return null;
    }

    public Player[] getPlayers() {
        return players;
    }
}
