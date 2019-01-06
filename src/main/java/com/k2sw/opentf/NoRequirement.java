package com.k2sw.opentf;

public class NoRequirement implements Requirement {
    @Override
    public boolean check(GameStateBuilder state) {
        return true;
    }
}
