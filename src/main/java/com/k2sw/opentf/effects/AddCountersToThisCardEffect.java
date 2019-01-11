package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

import java.util.ArrayList;

public class AddCountersToThisCardEffect implements Effect {
    private String name;
    private int amount;

    public AddCountersToThisCardEffect(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        CardStateBuilder cardState = state.getPlayerByID(currentPlayer).findCard(name);
        cardState.withCounters(cardState.getCounters()+amount);
        return new GameState[]{state.build()};
    }
}
