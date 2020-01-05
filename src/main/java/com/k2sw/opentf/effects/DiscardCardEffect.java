package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;
import java.util.*;

public class DiscardCardEffect extends Effect {
    private Card card;

    public DiscardCardEffect(Card card) {
        this.card = card;
    }

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        state.addToDiscard(card);
        state.getPlayerByID(currentPlayer).getHand().remove(card);
        return new GameState[]{state.build()};
    }

    @Override
    public String getText() {
        return "Discard a card.";
    }
}
