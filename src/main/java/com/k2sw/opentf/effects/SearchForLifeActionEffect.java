package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

public class SearchForLifeActionEffect extends Effect {
    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        CardStateBuilder cardState = state.getPlayerByID(currentPlayer).findCard("Search For Life");
        if (state.getDeck().size() > 0 && state.getDeck().get(0).hasTag(CardTag.Microbes)) {
            cardState.withCounters(1);
        }
        GameStateBuilder builder = new GameStateBuilder(state.build());
        return new DiscardTopCardEffect().apply(builder, currentPlayer);
    }

    @Override
    public String getText() {
        return "Discard the top card of the deck. If it has a microbe tag, put a resource on this card.";
    }
}
