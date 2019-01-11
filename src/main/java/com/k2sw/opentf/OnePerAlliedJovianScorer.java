package com.k2sw.opentf;

public class OnePerAlliedJovianScorer implements VictoryScorer {

    @Override
    public int score(GameState state, PlayerID owner) {
        int result = 0;
        for (CardState cardState : state.getPlayerByID(owner).getTableau())
            if (cardState.getCard().hasTag(CardTag.Jovian))
                result++;
        return result;
    }
}
