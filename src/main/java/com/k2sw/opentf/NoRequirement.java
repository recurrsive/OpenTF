package com.k2sw.opentf;

public class NoRequirement extends Requirement {
    @Override
    public boolean check(GameStateBuilder state, PlayerID currentPlayer) {
        return true;
    }

    @Override
    public String getText() {
        return "";
    }
}
