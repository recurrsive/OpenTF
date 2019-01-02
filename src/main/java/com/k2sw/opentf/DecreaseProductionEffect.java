package com.k2sw.opentf;

import java.util.Map;

public class DecreaseProductionEffect implements Effect {
    private ResourceType type;
    private int amount;

    public DecreaseProductionEffect(ResourceType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        Map<ResourceType, Integer> production = state.getPlayerByID(currentPlayer).getProduction();

        production.put(type, production.get(type) + amount);

        if (production.get(type) < 0) return new GameState[0];
        else return new GameState[]{state.build()};
    }
}
