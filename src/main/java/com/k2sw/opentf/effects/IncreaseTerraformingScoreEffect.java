package com.k2sw.opentf.effects;

import com.k2sw.opentf.Effect;
import com.k2sw.opentf.GameState;
import com.k2sw.opentf.GameStateBuilder;
import com.k2sw.opentf.PlayerID;

public class IncreaseTerraformingScoreEffect implements Effect {
    private int amount;

    public IncreaseTerraformingScoreEffect(int amount) {
        this.amount = amount;
    }

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        state.getPlayerByID(currentPlayer).increaseTerraformingScore(amount);
        return new GameState[]{state.build()};
    }
}
