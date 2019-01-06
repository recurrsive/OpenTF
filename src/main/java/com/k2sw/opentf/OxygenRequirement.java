package com.k2sw.opentf;

public class OxygenRequirement implements Requirement {
    private int amount;
    private boolean greater;

    public OxygenRequirement(int amount, boolean greater) {
        this.amount = amount;
        this.greater = greater;
    }

    @Override
    public boolean check(GameStateBuilder state) {
        if (greater)
            return state.getOxygen() >= amount;
        else
            return state.getOxygen() <= amount;
    }
}
