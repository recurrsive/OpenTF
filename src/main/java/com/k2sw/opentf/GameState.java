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
    private List<Card> deck;

    public GameState(int oxygen, int temperature, Player[] players, Map<TileSlot, Tile> placedTiles, Set<TileSlot> unplacedSlots, List<Card> deck) {
        this.oxygen = oxygen;
        this.temperature = temperature;
        this.players = players;
        this.placedTiles = placedTiles;
        this.unplacedSlots = unplacedSlots;
        this.deck = deck;
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

    public CardState[] getAllTableaus() {
        ArrayList<CardState> playedCardList = new ArrayList<>();
        for (Player player : players) {
            Collections.addAll(playedCardList, player.getTableau());
        }
        CardState[] results = new CardState[playedCardList.size()];
        playedCardList.toArray(results);
        return results;
    }

    public Map<TileSlot, Tile> getPlacedTiles() {
        return placedTiles;
    }

    public Set<TileSlot> getUnplacedSlots() {
        return unplacedSlots;
    }

    public List<Card> getDeck() {
        return deck;
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
                Objects.equals(unplacedSlots, gameState.unplacedSlots) &&
                Objects.equals(deck, gameState.deck);
    }

    @Override
    public String toString() {
        return "GameState{" +
                "oxygen=" + oxygen +
                ", temperature=" + temperature +
                ", players=" + Arrays.toString(players) +
                ", placedTiles=" + placedTiles +
                ", unplacedSlots=" + unplacedSlots +
                ", deck=" + deck +
                '}';
    }
}
