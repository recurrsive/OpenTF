package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

public class AddCountersToThisCardPerTagEffect implements Effect {
    private String name;
    private CardTag[] tags;

    public AddCountersToThisCardPerTagEffect(String name, CardTag[] tags) {
        this.name = name;
        this.tags = tags;
    }

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        int amount = 0;
        CardStateBuilder[] tableau = state.getPlayerByID(currentPlayer).getTableau();
        for (CardTag cardTag : tableau[tableau.length-1].getCard().getTags()){
            for (CardTag tag : tags) {
                if (cardTag == tag) amount++;
            }
        }
        CardStateBuilder cardState = state.getPlayerByID(currentPlayer).findCard(name);
        cardState.withCounters(cardState.getCounters()+amount);
        return new GameState[]{state.build()};
    }
}
