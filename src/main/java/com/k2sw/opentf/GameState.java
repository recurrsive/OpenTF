package com.k2sw.opentf;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
    private List<Card> discard;
    private int generationNum;
    private StandardBoard standardBoard;

    public StandardBoard getStandardBoard() {
        return standardBoard;
    }

    public GameState(int oxygen, int temperature, Player[] players, Map<TileSlot, Tile> placedTiles, Set<TileSlot> unplacedSlots, List<Card> deck, List<Card> discard, int generationNum, StandardBoard standardBoard) {
        this.oxygen = oxygen;
        this.temperature = temperature;
        this.players = players;
        this.placedTiles = placedTiles;
        this.unplacedSlots = unplacedSlots;
        this.deck = deck;
        this.discard = discard;
        this.generationNum = generationNum;
        this.standardBoard = standardBoard;

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

    public List<Tile> getTilesByType(TileType type){
        List<Tile> result = new ArrayList<>();
        for (Tile tile : placedTiles.values()) {
            if (tile.getTileType() == type) {
                result.add(tile);
            }
        }
        return result;
    }

    public Set<TileSlot> getUnplacedSlots() {
        return unplacedSlots;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public int getGenerationNum() {
        return generationNum;
    }

    public List<Card> getDiscard() {
        return discard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameState gameState = (GameState) o;
        return oxygen == gameState.oxygen &&
                temperature == gameState.temperature &&
                generationNum == gameState.generationNum &&
                Arrays.equals(players, gameState.players) &&
                Objects.equals(placedTiles, gameState.placedTiles) &&
                Objects.equals(unplacedSlots, gameState.unplacedSlots) &&
                Objects.equals(deck, gameState.deck) &&
                Objects.equals(discard, gameState.discard);
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
                ", discard=" + discard +
                ", generationNum=" + generationNum +
                '}';
    }

    public void save(Path path) throws IOException {
        JSONObject outer = new JSONObject();
        for (TileSlot slot : standardBoard.getAllSlots()) {
            JSONObject entry = new JSONObject()
                    .put("slot", slot.json());
            if (placedTiles.containsKey(slot))
                entry.put("tile", placedTiles.get(slot).toJson());
            outer.append("board", entry);
        }
        Files.write(path, ("export default " + outer.toString(2)).getBytes(StandardCharsets.UTF_8));
    }
}
