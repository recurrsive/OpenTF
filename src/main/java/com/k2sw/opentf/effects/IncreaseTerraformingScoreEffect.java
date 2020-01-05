package com.k2sw.opentf.effects;

import com.k2sw.opentf.Effect;
import com.k2sw.opentf.GameState;
import com.k2sw.opentf.GameStateBuilder;
import com.k2sw.opentf.PlayerID;

public class IncreaseTerraformingScoreEffect extends Effect {
    private int amount;

    public IncreaseTerraformingScoreEffect(int amount) {
        this.amount = amount;
    }

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        state.getPlayerByID(currentPlayer).increaseTerraformingScore(amount);
        return new GameState[]{state.build()};
    }

    @Override
    public String getText() {
        return "Increase your terraforming score by " + amount;
    }
}
