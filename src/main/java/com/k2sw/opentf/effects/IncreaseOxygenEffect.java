package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

public class IncreaseOxygenEffect implements Effect {
    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        if (state.getOxygen() < Global.MAX_OXYGEN) {
            state.withOxygen(state.getOxygen() + 1);
            state.getPlayerByID(currentPlayer).increaseTerraformingScore(1);
        }
        return new GameState[]{state.build()};
    }
}
