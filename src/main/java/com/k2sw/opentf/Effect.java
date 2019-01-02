package com.k2sw.opentf;

public interface Effect {
    GameState[] apply(GameStateBuilder state, PlayerID currentPlayer);
}
