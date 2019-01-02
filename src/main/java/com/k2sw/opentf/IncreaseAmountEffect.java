package com.k2sw.opentf;

import java.util.Map;

public class IncreaseAmountEffect implements Effect {
    private ResourceType type;
    private int amount;

    public IncreaseAmountEffect(ResourceType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        Map<ResourceType, Integer> amounts = state.getPlayerByID(currentPlayer).getAmounts();

        amounts.put(type, amounts.get(type) + amount);

        return new GameState[]{state.build()};
    }
}
