package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

import java.util.*;

public class AddCountersToOtherCardEffect extends Effect {
    private CardTag[] tags;
    private int amount;

    public AddCountersToOtherCardEffect(CardTag[] tags, int amount) {
        this.tags = tags;
        this.amount = amount;
    }

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        GameState initialState = state.build();
        CardStateBuilder[] tableau = state.getPlayerByID(currentPlayer).getTableau();
        ArrayList<GameState> resultList = new ArrayList<>();
        for (int i = 0; i < tableau.length; i++) {
            boolean valid = false;
            for (CardTag tag : tags){
                if (tableau[i].getCard().hasTag(tag)) valid = true;
            }
            if (!tableau[i].getCard().hasTag(CardTag.UsesCounters)) valid = false;

            if (valid) {
                GameStateBuilder nextState = new GameStateBuilder(initialState);
                CardStateBuilder cardState = nextState.getPlayerByID(currentPlayer).getTableau()[i];
                cardState.withCounters(cardState.getCounters() + amount);
                resultList.add(nextState.build());
            }
        }
        GameState[] results = new GameState[resultList.size()];
        resultList.toArray(results);
        if (results.length == 0) return new GameState[]{state.build()};
        else return results;
    }

    @Override
    public String getText() {
        StringBuilder result = new StringBuilder();
        result.append("Add " + amount + " counters to a card with one of these tags:\n");
        for (int i = 0; i < tags.length; ++i) {
            result.append(tags[i].toString());
            if (i < tags.length - 1) {
                result.append(", ");
            }
        }
        return result.toString();
    }
}
