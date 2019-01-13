package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

public class DiscardTopCardEffect implements Effect {
    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        state.addToDiscard(state.getDeck().get(0));
        return new GameState[]{state.build()};
    }
}
