package com.k2sw.opentf;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PlayerBuilder implements PlayerOrBuilder {
    private Map<ResourceType, Integer> production;
    private Map<ResourceType, Integer> amounts;
    private int terraformingScore;
    private PlayerID playerID;
    private CardStateBuilder[] tableau;

    public PlayerBuilder() {
        this.production = new HashMap<>();
        this.amounts = new HashMap<>();
        this.terraformingScore = 0;
        this.playerID = null;
        this.tableau = new CardStateBuilder[0];

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
    }

    @Override
    public Map<ResourceType, Integer> getProduction() {
        return production;
    }

    @Override
    public Map<ResourceType, Integer> getAmounts() {
        return amounts;
    }

    @Override
    public int getTerraformingScore() {
        return terraformingScore;
    }

    @Override
    public PlayerID getPlayerID() {
        return playerID;
    }

    public CardStateBuilder[] getTableau() {
        return tableau;
    }

    public PlayerBuilder withProduction(ResourceType type, int amount) {
        this.production.put(type, amount);
        return this;
    }

    public PlayerBuilder withAmount(ResourceType type, int amount) {
        this.amounts.put(type, amount);
        return this;
    }

    public PlayerBuilder withTerraformingScore(int terraformingScore) {
        this.terraformingScore = terraformingScore;
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

    public Player build() {
        Map<ResourceType, Integer> newProduction = Collections.unmodifiableMap(production);
        Map<ResourceType, Integer> newAmounts = Collections.unmodifiableMap(amounts);

        CardState[] tableauResults = new CardState[tableau.length];
        for (int i = 0; i < tableau.length; i++){
            tableauResults[i] = tableau[i].build();
        }

        return new Player(newProduction, newAmounts, terraformingScore, playerID, tableauResults);
    }
}
