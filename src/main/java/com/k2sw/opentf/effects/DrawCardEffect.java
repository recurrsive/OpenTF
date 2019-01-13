package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

import java.util.*;

public class DrawCardEffect implements Effect {
    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        List<Card> deck = state.getDeck();
        List<Card> discard = state.getDiscard();

        if (deck.size() + discard.size() == 0) return new GameState[]{state.build()};
        else {
            if (deck.size() == 0) state.cycleDiscard();
            Card to_draw = deck.get(0);
            Set<Card> newHand = new HashSet<>(state.getPlayerByID(currentPlayer).getHand());
            newHand.add(to_draw);
            state.getPlayerByID(currentPlayer).withHand(newHand);
            state.getDeck().remove(0);
            return new GameState[]{state.build()};
        }
    }
}
