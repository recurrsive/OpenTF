package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

import java.util.*;

public class BuyCardEffect implements Effect {
    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        List<Card> deck = state.getDeck();
        List<Card> discard = state.getDiscard();

        if (deck.size() + discard.size() == 0) return new GameState[]{state.build()};
        else {
            return new OrEffect(new Effect[]{
                    new CompoundEffect(new Effect[]{
                            new PayForCostEffect(new CardTag[0], false, 3),
                            new DrawCardEffect()
                    }),
                    new DiscardTopCardEffect()
            }).apply(state, currentPlayer);
        }
    }
}
