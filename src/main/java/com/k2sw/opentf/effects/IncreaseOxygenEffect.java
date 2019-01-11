package com.k2sw.opentf.effects;

import com.k2sw.opentf.Effect;
import com.k2sw.opentf.GameState;
import com.k2sw.opentf.GameStateBuilder;
import com.k2sw.opentf.PlayerID;

public class IncreaseOxygenEffect implements Effect {
    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        if (state.getOxygen() < 13) {
            state.withOxygen(state.getOxygen() + 1);
            state.getPlayerByID(currentPlayer).increaseTerraformingScore(1);
        }
        return new GameState[]{state.build()};
    }
}
