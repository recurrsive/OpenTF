package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

public class DecreaseProductionEffect extends Effect {
    private ResourceType type;
    private int amount;

    public DecreaseProductionEffect(ResourceType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        state.getPlayerByID(currentPlayer).changeProduction(type, -amount);

        if (state.getPlayerByID(currentPlayer).getProduction().get(type) < 0) return new GameState[0];
        else return new GameState[]{state.build()};
    }

    @Override
    public String getText() {
        return "Costs " + amount + " " + type + " production.";
    }
}
