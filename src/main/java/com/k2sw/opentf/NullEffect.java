package com.k2sw.opentf;

public class NullEffect implements Effect {
    public NullEffect() {}

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        return new GameState[]{state.build()};
    }
}
