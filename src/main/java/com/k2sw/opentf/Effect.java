package com.k2sw.opentf;

public abstract class Effect {
    public abstract GameState[] apply(GameStateBuilder state, PlayerID currentPlayer);

    public String getText() {
        return "Change the board in some way.";
    }
}
