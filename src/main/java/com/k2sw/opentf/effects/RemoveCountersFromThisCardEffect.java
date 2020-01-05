package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

public class RemoveCountersFromThisCardEffect extends Effect {
    private String name;
    private int amount;

    public RemoveCountersFromThisCardEffect(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        CardStateBuilder cardState = state.getPlayerByID(currentPlayer).findCard(name);
        if (cardState.getCounters() < amount) return new GameState[0];
        cardState.withCounters(cardState.getCounters()-amount);
        return new GameState[]{state.build()};
    }

    @Override
    public String getText() {
        return "Remove " + amount + " resource(s) from this card.";
    }
}
