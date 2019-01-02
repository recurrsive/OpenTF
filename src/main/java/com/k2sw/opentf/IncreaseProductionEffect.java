package com.k2sw.opentf;

import java.util.Map;

public class IncreaseProductionEffect implements Effect {
    private ResourceType type;
    private int amount;

    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        Map<ResourceType, Integer> production = state.getPlayerByID(currentPlayer).getProduction();

        production.put(type, production.get(type) + amount);

        return new GameState[]{state.build()};
    }
}
