package com.k2sw.opentf;

public class PointsPerGlobalTagScorer implements VictoryScorer {
    private CardTag tag;
    private int points;
    private int req;

    public PointsPerGlobalTagScorer(CardTag tag, int points, int req) {
        this.tag = tag;
        this.points = points;
        this.req = req;
    }

    @Override
    public int score(GameState state, PlayerID owner) {
        CardState[] cardStates = state.getAllTableaus();
        int count = 0;
        for (CardState cardState : cardStates) {
            if (cardState.getCard().hasTag(tag)) count++;
        }
        count -= count % req;
        return points * (count/req);
    }
}
