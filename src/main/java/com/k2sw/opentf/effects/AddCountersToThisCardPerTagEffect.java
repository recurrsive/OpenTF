package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

public class AddCountersToThisCardPerTagEffect extends Effect {
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

    @Override
    public String getText() {
        StringBuilder result = new StringBuilder();
        result.append("Add 1 counter to this card per tag for each of these tags:\n");
        for (CardTag tag : tags) {
            result.append(tag.toString());
            result.append(", ");
        }
        return result.toString();
    }
}
