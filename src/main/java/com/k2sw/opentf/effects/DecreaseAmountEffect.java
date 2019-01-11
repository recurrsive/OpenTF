package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

public class DecreaseAmountEffect implements Effect {
    private ResourceType type;
    private int amount;

    public DecreaseAmountEffect(ResourceType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        state.getPlayerByID(currentPlayer).changeAmount(type, -amount);

        if (state.getPlayerByID(currentPlayer).getAmounts().get(type) < 0) return new GameState[0];
        return new GameState[]{state.build()};
    }
}
