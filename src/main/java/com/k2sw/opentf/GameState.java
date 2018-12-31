package com.k2sw.opentf;

public class GameState implements GameStateOrBuilder {
    //Between 0 and 13
    private int oxygen;
    //Between -30 and 10, in increments of 2
    private int temperature;

    public GameState(int oxygen, int temperature) {
        this.oxygen = oxygen;
        this.temperature = temperature;
    }

    public int getOxygen() {
        return oxygen;
    }

    public int getTemperature() {
        return temperature;
    }
}
