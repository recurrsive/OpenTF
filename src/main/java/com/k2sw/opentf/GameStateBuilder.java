package com.k2sw.opentf;

import java.util.*;

public class GameStateBuilder {
    //Between 0 and 14
    private int oxygen;
    //Between -30 and 10, in increments of 2
    private int temperature;
    private PlayerBuilder[] players;
    private Map<TileSlot, Tile> placedTiles;
    private Set<TileSlot> unplacedSlots;

    public GameStateBuilder() {
        this.oxygen = 0;
        this.temperature = 0;
        this.players = new PlayerBuilder[0];
        this.placedTiles = new HashMap<>();
        this.unplacedSlots = new HashSet<>();
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

    public GameStateBuilder withOxygen(int oxygen) {
        this.oxygen = oxygen;
        return this;
    }

    public int getTemperature() {
        return temperature;
    }

    public GameStateBuilder withTemperature(int temperature) {
        this.temperature = temperature;
        return this;
    }

    public PlayerBuilder[] getPlayers() {
        return players;
    }

    public GameStateBuilder withPlayers(PlayerBuilder[] players) {
        this.players = players;
        return this;
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

    public GameStateBuilder withPlacedTiles(Map<TileSlot, Tile> placedTiles) {
        this.placedTiles = placedTiles;
        return this;
    }

    public Set<TileSlot> getUnplacedSlots() {
        return unplacedSlots;
    }

    public GameStateBuilder withUnplacedSlots(Set<TileSlot> unplacedSlots) {
        this.unplacedSlots = unplacedSlots;
        return this;
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
