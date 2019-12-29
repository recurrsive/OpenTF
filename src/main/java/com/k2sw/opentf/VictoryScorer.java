package com.k2sw.opentf;

public interface VictoryScorer {
    int score(GameState state, PlayerID owner);
}
