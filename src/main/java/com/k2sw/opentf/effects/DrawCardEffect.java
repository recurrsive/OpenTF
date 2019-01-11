package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

import java.util.*;

public class DrawCardEffect implements Effect {
    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        if (state.getDeck().size() == 0) return new GameState[0];
        Card to_draw = state.getDeck().get(0);
        Set<Card> newHand = new HashSet<>(state.getPlayerByID(currentPlayer).getHand());
        newHand.add(to_draw);
        state.getPlayerByID(currentPlayer).withHand(newHand);
        state.getDeck().remove(0);
        return new GameState[]{state.build()};
    }
}
