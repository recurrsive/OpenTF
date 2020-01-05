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

    public VisibleTile[][] getVisibleTiles() {
        int[] startQ = {0, -1, -2, -3, -4, -4, -4, -4, -4};

        VisibleTile[][] result = new VisibleTile[standardBoard.getHeight()][];
        for (int i=0; i < standardBoard.getHeight(); i++) {
            result[i] = new VisibleTile[standardBoard.getWidth(i)];
            for (int j=0; j < standardBoard.getWidth(i); j++) {
                TileSlot slot = standardBoard.at(i, j);
                result[i][j] = new VisibleTile(slot.getTileSlotType(), slot.getBonuses(), placedTiles.getOrDefault(slot, null), startQ[i] + j, i - 4);
            }
        }
        return result;
    }

    private String cardText(Card c) {
        StringBuilder text = new StringBuilder();
        if (!c.getPlayEffect().equals(Global.NULL_EFFECT)) {
            text.append(c.getPlayEffect().getText());
        }
        if (!c.getActionEffect().equals(Global.NULL_EFFECT)) {
            text.append("\nACTION:\n");
            text.append(c.getActionEffect().getText());
        }
        if (!c.getTriggeredEffect().equals(Global.NULL_EFFECT)) {
            text.append("\nWHEN ");
            for (int i = 0; i < c.getTriggerTypes().length; ++i) {
                text.append(c.getTriggerTypes()[i].toString());
                if (i < c.getTriggerTypes().length - 1) {
                    text.append(" OR ");
                }
                else {
                    text.append(":\n");
                }
            }
            text.append(c.getTriggeredEffect());
        }
        if (!c.getReducer().equals(Global.NO_REDUCER)) {
            text.append("\n");
            text.append(c.getReducer().getText());
        }
        return text.toString();
    }

    private boolean requirementsMet(Card card, GameState state, PlayerID id) {
        GameStateBuilder builder = new GameStateBuilder(state);
        return card.getRequirement().check(builder, id);
    }

    private VisibleCard[] getVisibleHand(PlayerID id) {
        ArrayList<VisibleCard> result = new ArrayList<>();
        for (Card c : getPlayerByID(id).getHand()) {
            ArrayList<String> tags = new ArrayList<>();
            for (CardTag tag : c.getTags()) {
                tags.add(tag.toString());
            }
            String[] stags = new String[tags.size()];
            tags.toArray(stags);

            VisibleCard card = new VisibleCard(c.getName(), EffectHelpers.reduceCost(c.getCost(), false, c.getTags(), new GameStateBuilder(this), id), 0, c.getRequirement().getText(),
                    stags, cardText(c), requirementsMet(c, this, id), false);
            result.add(card);
        }
        VisibleCard[] cards = new VisibleCard[result.size()];
        result.toArray(cards);

        return cards;
    }

    private VisibleCard[] getVisibleTableau(PlayerID id) {
        ArrayList<VisibleCard> result = new ArrayList<>();
        for (CardState cs : getPlayerByID(id).getTableau()) {
            Card c = cs.getCard();

            ArrayList<String> tags = new ArrayList<>();
            for (CardTag tag : c.getTags()) {
                tags.add(tag.toString());
            }
            String[] stags = new String[tags.size()];
            tags.toArray(stags);

            VisibleCard card = new VisibleCard(c.getName(), EffectHelpers.reduceCost(c.getCost(), false, c.getTags(), new GameStateBuilder(this), id), cs.getCounters(), c.getRequirement().getText(),
                    stags, cardText(c), requirementsMet(c, this, id), cs.isActivated());
            result.add(card);
        }
        VisibleCard[] cards = new VisibleCard[result.size()];
        result.toArray(cards);

        return cards;
    }

    private VisibleResources getVisibleResources(PlayerID id) {
        Player player = getPlayerByID(id);
        int score = player.getTerraformingScore();
        VisibleResource mc = new VisibleResource(player.getAmounts().get(ResourceType.MegaCredits),
                player.getProduction().get(ResourceType.MegaCredits));
        VisibleResource steel = new VisibleResource(player.getAmounts().get(ResourceType.Steel),
                player.getProduction().get(ResourceType.Steel));
        VisibleResource titanium = new VisibleResource(player.getAmounts().get(ResourceType.Titanium),
                player.getProduction().get(ResourceType.Titanium));
        VisibleResource plants = new VisibleResource(player.getAmounts().get(ResourceType.Plants),
                player.getProduction().get(ResourceType.Plants));
        VisibleResource energy = new VisibleResource(player.getAmounts().get(ResourceType.Energy),
                player.getProduction().get(ResourceType.Energy));
        VisibleResource heat = new VisibleResource(player.getAmounts().get(ResourceType.Heat),
                player.getProduction().get(ResourceType.Heat));

        return new VisibleResources(score, mc, steel, titanium, plants, energy, heat);
    }

    private VisiblePlayer[] getVisiblePlayers() {
        ArrayList<VisiblePlayer> result = new ArrayList<>();
        for (Player p : players) {
            PlayerID id = p.getPlayerID();
            result.add(new VisiblePlayer(id.getId(), getVisibleHand(id),
                    getVisibleTableau(id), getVisibleResources(id)));
        }
        VisiblePlayer[] players = new VisiblePlayer[result.size()];
        result.toArray(players);

        return players;
    }

    public VisibleGameState getVisible() {
        int active = 0;
        return new VisibleGameState(getVisiblePlayers(), active, getVisibleTiles());
    }

    public void save(Path path) throws IOException {
        JSONObject outer = new JSONObject();
        for (TileSlot slot : standardBoard.getAllSlots()) {
            JSONObject entry = new JSONObject()
                    .put("slot", slot.json());
            if (placedTiles.containsKey(slot))
                entry.put("tile", placedTiles.get(slot).toJson());
            outer.append("gamestate", entry);
        }
        Files.write(path, ("export default " + outer.toString(2)).getBytes(StandardCharsets.UTF_8));
    }
}
