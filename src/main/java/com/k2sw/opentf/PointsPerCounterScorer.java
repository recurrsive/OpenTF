package com.k2sw.opentf;

public class PointsPerCounterScorer implements VictoryScorer {
    private String name;
    private int points;
    private int numRequired;

    public PointsPerCounterScorer(String name, int points, int numRequired) {
        this.name = name;
        this.points = points;
        this.numRequired = numRequired;
    }

    @Override
    public int score(GameState state, PlayerID owner) {
        CardState cardState = state.getPlayerByID(owner).findCard(name);
        int counters = cardState.getCounters(); counters -= counters % numRequired;
        return points * (counters / numRequired);
    }
}
