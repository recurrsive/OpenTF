package com.k2sw.opentf;

import java.util.Collections;
import java.util.*;

public class PlayerBuilder {
    private Map<ResourceType, Integer> production;
    private Map<ResourceType, Integer> amounts;
    private int terraformingScore;
    private PlayerID playerID;
    private CardStateBuilder[] tableau;
    private Set<Card> hand;
    private boolean passed;
    private boolean singlePlayerOpponent;

    public PlayerBuilder() {
        this.production = new HashMap<>();
        this.amounts = new HashMap<>();
        this.terraformingScore = 0;
        this.playerID = null;
        this.tableau = new CardStateBuilder[0];
        this.hand = new HashSet<>();
        this.passed = false;
        this.singlePlayerOpponent = false;

        for (ResourceType type : ResourceType.values()){
            production.put(type, 1);
            amounts.put(type, 0);
        }
    }

    public PlayerBuilder(Player template){
        production = new HashMap<>();
        production.putAll(template.getProduction());
        amounts = new HashMap<>();
        amounts.putAll(template.getAmounts());
        terraformingScore = template.getTerraformingScore();
        playerID = template.getPlayerID();
        CardStateBuilder[] results = new CardStateBuilder[template.getTableau().length];
        for (int i = 0; i < results.length; i++){
            results[i] = new CardStateBuilder(template.getTableau()[i]);
        }
        tableau = results;
        hand = new HashSet<>(template.getHand());
        passed = template.hasPassed();
        singlePlayerOpponent = template.isSinglePlayerOpponent();
    }

    public Map<ResourceType, Integer> getProduction() {
        return production;
    }

    public Map<ResourceType, Integer> getAmounts() {
        return amounts;
    }

    public int getTerraformingScore() {
        return terraformingScore;
    }

    public PlayerID getPlayerID() {
        return playerID;
    }

    public CardStateBuilder[] getTableau() {
        return tableau;
    }

    public CardStateBuilder findCard(String name) {
        for (CardStateBuilder cardState : tableau){
            if (cardState.getCard().getName().equals(name)) return cardState;
        }
        return null;
    }

    public int getTagCount(CardTag tag) {
        int total = 0;
        for (CardStateBuilder cardState : tableau){
            if (cardState.getCard().hasTag(tag)) total++;
        }
        return total;
    }

    public PlayerBuilder withProduction(ResourceType type, int amount) {
        production.put(type, amount);
        return this;
    }

    public PlayerBuilder withAmount(ResourceType type, int amount) {
        amounts.put(type, amount);
        return this;
    }

    public PlayerBuilder changeProduction(ResourceType type, int amount) {
        production.put(type, production.get(type) + amount);
        return this;
    }

    public PlayerBuilder changeAmount(ResourceType type, int amount) {
        amounts.put(type, amounts.get(type) + amount);
        return this;
    }

    public PlayerBuilder withTerraformingScore(int terraformingScore) {
        this.terraformingScore = terraformingScore;
        return this;
    }

    public PlayerBuilder increaseTerraformingScore(int amount) {
        this.terraformingScore += amount;
        return this;
    }

    public PlayerBuilder withPlayerID(PlayerID playerID) {
        this.playerID = playerID;
        return this;
    }

    public PlayerBuilder withTableau(CardStateBuilder[] tableau) {
        this.tableau = tableau;
        return this;
    }

    public Set<Card> getHand() {
        return hand;
    }

    public PlayerBuilder withHand(Set<Card> hand) {
        this.hand = hand;
        return this;
    }

    public boolean hasPassed() {
        return passed;
    }

    public PlayerBuilder withPassed(boolean passed) {
        this.passed = passed;
        return this;
    }

    public boolean isSinglePlayerOpponent() {
        return singlePlayerOpponent;
    }

    public PlayerBuilder withSinglePlayerOpponent(boolean singlePlayerOpponent) {
        this.singlePlayerOpponent = singlePlayerOpponent;
        return this;
    }

    public Player build() {
        Map<ResourceType, Integer> newProduction = Collections.unmodifiableMap(production);
        Map<ResourceType, Integer> newAmounts = Collections.unmodifiableMap(amounts);

        CardState[] tableauResults = new CardState[tableau.length];
        for (int i = 0; i < tableau.length; i++){
            tableauResults[i] = tableau[i].build();
        }

        Set<Card> newHand = Collections.unmodifiableSet(hand);
        return new Player(newProduction, newAmounts, terraformingScore, playerID, tableauResults, newHand, passed, singlePlayerOpponent);
    }


    @Override
    public String toString() {
        return "PlayerBuilder{" +
                "production=" + production +
                ", amounts=" + amounts +
                ", terraformingScore=" + terraformingScore +
                ", playerID=" + playerID +
                ", tableau=" + Arrays.toString(tableau) +
                ", hand=" + hand +
                ", passed=" + passed +
                '}';
    }
}
