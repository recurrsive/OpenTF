package com.k2sw.opentf;

import java.util.Collections;
import java.util.Map;

public class PlayerBuilder implements PlayerOrBuilder {
    private Map<ResourceType, Integer> production;
    private Map<ResourceType, Integer> amounts;
    private int terraformingScore;
    private PlayerID playerID;
    private CardStateBuilder[] tableau;

    public PlayerBuilder(Map<ResourceType, Integer> production, Map<ResourceType, Integer> amounts, int terraformingScore, PlayerID playerID, CardStateBuilder[] tableau) {
        this.production = production;
        this.amounts = amounts;
        this.terraformingScore = terraformingScore;
        this.playerID = playerID;
        this.tableau = tableau;
    }

    public PlayerBuilder(Player template){
        production = template.getProduction();
        amounts = template.getAmounts();
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

    public void setProduction(Map<ResourceType, Integer> production) {
        this.production = production;
    }

    public void setAmounts(Map<ResourceType, Integer> amounts) {
        this.amounts = amounts;
    }

    public void setTerraformingScore(int terraformingScore) {
        this.terraformingScore = terraformingScore;
    }

    public void setPlayerID(PlayerID playerID) {
        this.playerID = playerID;
    }

    public void setTableau(CardStateBuilder[] tableau) {
        this.tableau = tableau;
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
