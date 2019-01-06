package com.k2sw.opentf;

public class TemperatureRequirement implements Requirement {
    private int amount;
    private boolean greater;

    public TemperatureRequirement(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean check(GameStateBuilder state) {
        if (greater)
            return state.getTemperature() >= amount;
        else
            return state.getTemperature() <= amount;
    }
}
