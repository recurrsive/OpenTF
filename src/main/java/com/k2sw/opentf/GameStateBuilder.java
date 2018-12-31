package com.k2sw.opentf;

public class GameStateBuilder implements GameStateOrBuilder {
    //Between 0 and 14
    private int oxygen;
    //Between -30 and 10, in increments of 2
    private int temperature;

    public GameStateBuilder(int oxygen, int temperature) {
        this.oxygen = oxygen;
        this.temperature = temperature;
    }

    public int getOxygen() {
        return oxygen;
    }

    public void setOxygen(int oxygen) {
        this.oxygen = oxygen;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public GameState build() {
        return new GameState(this.oxygen, this.temperature);
    }
}
