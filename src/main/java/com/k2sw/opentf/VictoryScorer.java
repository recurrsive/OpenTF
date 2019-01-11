package com.k2sw.opentf;

public interface VictoryScorer {
    public int score(GameState state, PlayerID owner);
}
