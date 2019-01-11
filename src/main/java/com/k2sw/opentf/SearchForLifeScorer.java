package com.k2sw.opentf;

public class SearchForLifeScorer implements VictoryScorer {
    @Override
    public int score(GameState state, PlayerID owner) {
        CardState cardState = state.getPlayerByID(owner).findCard("Search For Life");
        if (cardState.getCounters() >= 1) return 3;
        return 0;
    }
}
