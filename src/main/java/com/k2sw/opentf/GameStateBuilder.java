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
    private List<Card> deck;

    public GameStateBuilder() {
        this.oxygen = 0;
        this.temperature = 0;
        this.players = new PlayerBuilder[0];
        this.placedTiles = new HashMap<>();
        this.unplacedSlots = new HashSet<>();
        this.deck = new ArrayList<>();
    }

    public GameStateBuilder(GameState template){
        oxygen = template.getOxygen();
        temperature = template.getTemperature();

        PlayerBuilder[] results = new PlayerBuilder[template.getPlayers().length];
        for (int i = 0; i < results.length; i++){
            results[i] = new PlayerBuilder(template.getPlayers()[i]);
        }
        players = results;

        placedTiles = new HashMap<>();
        placedTiles.putAll(template.getPlacedTiles());
        unplacedSlots = new HashSet<>();
        unplacedSlots.addAll(template.getUnplacedSlots());
        deck = new ArrayList<>(template.getDeck());
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

    public CardStateBuilder[] getAllTableaus() {
        ArrayList<CardStateBuilder> playedCardList = new ArrayList<>();
        for (PlayerBuilder player : players) {
            Collections.addAll(playedCardList, player.getTableau());
        }
        CardStateBuilder[] results = new CardStateBuilder[playedCardList.size()];
        playedCardList.toArray(results);
        return results;
    }

    public Map<TileSlot, Tile> getPlacedTiles() {
        return placedTiles;
    }

    public Set<Tile> getAdjacentPlacedTiles(TileSlot slot) {
        List<TileSlot> neighbors = slot.getNeighbors();
        Set<Tile> neighborTiles = new HashSet<>();
        for (TileSlot s : neighbors) {
            if (placedTiles.containsKey(s)) neighborTiles.add(placedTiles.get(s));
        }
        return neighborTiles;
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

    public List<Card> getDeck() {
        return deck;
    }

    public GameStateBuilder withDeck(List<Card> deck) {
        this.deck = deck;
        return this;
    }

    public Set<TileSlot> getTileSlotsByType(TileSlotType type){
        Set<TileSlot> result = new HashSet<>();
        for (TileSlot slot : unplacedSlots) {
            if (slot.getTileSlotType() == type) result.add(slot);
        }
        return result;
    }

    public GameStateBuilder placeTile(TileSlot slot, Tile tile){
        if (!unplacedSlots.contains(slot)) throw new RuntimeException("Slot to be placed on is not in GameStateBuilder");
        placedTiles.put(slot, tile);
        unplacedSlots.remove(slot);
        return this;
    }

    public GameState build() {
        Player[] result = new Player[players.length];
        for (int i = 0; i < players.length; i++){
            result[i] = players[i].build();
        }
        Map<TileSlot, Tile> newPlacedTiles = Collections.unmodifiableMap(placedTiles);
        Set<TileSlot> newUnplacedSlots = Collections.unmodifiableSet(unplacedSlots);
        List<Card> newDeck = Collections.unmodifiableList(deck);

        return new GameState(oxygen, temperature, result, newPlacedTiles, newUnplacedSlots, newDeck);
    }
}
