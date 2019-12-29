package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;
import java.util.*;

public class SellPatentsEffect implements Effect {
    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        Set<Card> handSet = state.getPlayerByID(currentPlayer).getHand();
        Card[] hand = new Card[handSet.size()];
        handSet.toArray(hand);
        GameState[] results = new GameState[hand.length];
        GameState initialState = state.build();

        for (int x = 0; x < hand.length; x++){
            GameStateBuilder nextState = new GameStateBuilder(initialState);
            ArrayList<Effect> effectList = new ArrayList<>();
            effectList.add(new DiscardCardEffect(hand[x]));
            effectList.add(new IncreaseAmountEffect(ResourceType.MegaCredits, 1));
            Effect[] effects = new Effect[effectList.size()];
            effectList.toArray(effects);
            results[x] = new CompoundEffect(effects).apply(nextState, currentPlayer)[0];
        }

        return results;
    }
}
