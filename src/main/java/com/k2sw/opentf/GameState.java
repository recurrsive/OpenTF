package com.k2sw.opentf;

import java.util.*;

public class GameState {
    //Between 0 and 13
    private int oxygen;
    //Between -30 and 10, in increments of 2
    private int temperature;
    private Player[] players;
    private Map<TileSlot, Tile> placedTiles;
    private Set<TileSlot> unplacedSlots;

    public GameState(int oxygen, int temperature, Player[] players, Map<TileSlot, Tile> placedTiles, Set<TileSlot> unplacedSlots) {
        this.oxygen = oxygen;
        this.temperature = temperature;
        this.players = players;
        this.placedTiles = placedTiles;
        this.unplacedSlots = unplacedSlots;
    }

    public int getOxygen() {
        return oxygen;
    }

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

    public Map<TileSlot, Tile> getPlacedTiles() {
        return placedTiles;
    }

    public Set<TileSlot> getUnplacedSlots() {
        return unplacedSlots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameState gameState = (GameState) o;
        return oxygen == gameState.oxygen &&
                temperature == gameState.temperature &&
                Arrays.equals(players, gameState.players) &&
                Objects.equals(placedTiles, gameState.placedTiles) &&
                Objects.equals(unplacedSlots, gameState.unplacedSlots);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(oxygen, temperature, placedTiles, unplacedSlots);
        result = 31 * result + Arrays.hashCode(players);
        return result;
    }
}
