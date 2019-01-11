package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

public class SearchForLifeActionEffect implements Effect {
    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        CardStateBuilder cardState = state.getPlayerByID(currentPlayer).findCard("Search For Life");
        if (state.getDeck().size() > 0 && state.getDeck().get(0).hasTag(CardTag.Microbes))
            cardState.withCounters(1);
        return new GameState[]{state.build()};
    }
}
