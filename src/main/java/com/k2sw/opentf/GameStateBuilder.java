package com.k2sw.opentf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class GameStateBuilder implements GameStateOrBuilder {
    //Between 0 and 14
    private int oxygen;
    //Between -30 and 10, in increments of 2
    private int temperature;
    private PlayerBuilder[] players;
    private Map<TileSlot, Tile> placedTiles;
    private Set<TileSlot> unplacedSlots;

    public GameStateBuilder(int oxygen, int temperature, PlayerBuilder[] players, Map<TileSlot, Tile> placedTiles, Set<TileSlot> unplacedSlots) {
        this.oxygen = oxygen;
        this.temperature = temperature;
        this.players = players;
        this.placedTiles = placedTiles;
        this.unplacedSlots = unplacedSlots;
    }

    public GameStateBuilder(GameState template){
        oxygen = template.getOxygen();
        temperature = template.getTemperature();

        PlayerBuilder[] results = new PlayerBuilder[template.getPlayers().length];
        for (int i = 0; i < results.length; i++){
            results[i] = new PlayerBuilder(template.getPlayers()[i]);
        }
        players = results;

        placedTiles = Collections.unmodifiableMap(template.getPlacedTiles());
        unplacedSlots = Collections.unmodifiableSet(template.getUnplacedSlots());
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

    public Map<TileSlot, Tile> getPlacedTiles() {
        return placedTiles;
    }

    public void setPlacedTiles(Map<TileSlot, Tile> placedTiles) {
        this.placedTiles = placedTiles;
    }

    public Set<TileSlot> getUnplacedSlots() {
        return unplacedSlots;
    }

    public void setUnplacedSlots(Set<TileSlot> unplacedSlots) {
        this.unplacedSlots = unplacedSlots;
    }

    public GameState build() {
        Player[] result = new Player[players.length];
        for (int i = 0; i < players.length; i++){
            result[i] = players[i].build();
        }
        Map<TileSlot, Tile> newPlacedTiles = Collections.unmodifiableMap(placedTiles);
        Set<TileSlot> newUnplacedSlots = Collections.unmodifiableSet(unplacedSlots);


        return new GameState(oxygen, temperature, result, newPlacedTiles, newUnplacedSlots);
    }
}
