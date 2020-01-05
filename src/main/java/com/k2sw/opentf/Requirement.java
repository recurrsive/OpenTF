package com.k2sw.opentf;

public abstract class Requirement {
    public abstract boolean check(GameStateBuilder state, PlayerID currentPlayer);

    public String getText() {
        return "A requirement.";
    }
}
