package com.k2sw.opentf.effects;

import com.k2sw.opentf.Effect;
import com.k2sw.opentf.GameState;
import com.k2sw.opentf.GameStateBuilder;
import com.k2sw.opentf.PlayerID;

public class PassEffect implements Effect {
    public PassEffect() {}

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        state.getPlayerByID(currentPlayer).withPassed(true);
        return new GameState[]{state.build()};
    }
}
