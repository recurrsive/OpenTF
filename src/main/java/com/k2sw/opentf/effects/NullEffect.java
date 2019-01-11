package com.k2sw.opentf.effects;

import com.k2sw.opentf.Effect;
import com.k2sw.opentf.GameState;
import com.k2sw.opentf.GameStateBuilder;
import com.k2sw.opentf.PlayerID;

public class NullEffect implements Effect {
    public NullEffect() {}

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        return new GameState[]{state.build()};
    }
}
