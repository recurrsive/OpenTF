package com.k2sw.opentf;

public class GenericScorer implements VictoryScorer {
    private int amount;

    public GenericScorer(int amount) {
        this.amount = amount;
    }

    public int score(GameState state) { return amount; }
}
