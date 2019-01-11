package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

public class IncreaseProductionEffect implements Effect {
    private ResourceType type;
    private int amount;

    public IncreaseProductionEffect(ResourceType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        state.getPlayerByID(currentPlayer).changeProduction(type, amount);

        return new GameState[]{state.build()};
    }
}
