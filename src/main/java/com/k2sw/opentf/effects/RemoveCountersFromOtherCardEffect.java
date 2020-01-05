package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

import java.util.*;

public class RemoveCountersFromOtherCardEffect extends Effect {
    String name;
    private CardTag[] types;
    private int amount;

    public RemoveCountersFromOtherCardEffect(String name, CardTag[] types, int amount) {
        this.name = name;
        this.types = types;
        this.amount = amount;
    }

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        ArrayList<GameState> resultList = new ArrayList<>();
        GameState initialState = state.build();
        for (int i = 0; i < state.getPlayers().length; i++) {
            PlayerBuilder player = state.getPlayers()[i];
            for (int cs = 0; cs < player.getTableau().length; cs++) {
                ArrayList<CardTag> targetableTypes = new ArrayList<>();
                Collections.addAll(targetableTypes, types);
                CardStateBuilder cardState = player.getTableau()[cs];
                boolean targetable = false;
                for (CardTag tag : targetableTypes) {
                    if (cardState.getCard().hasTag(tag) && !player.getTableau()[cs].getCard().getName().equals(name))
                        targetable = true;
                }
                targetable = targetable && cardState.getCard().hasTag(CardTag.UsesCounters) && !cardState.getCard().hasTag(CardTag.CantRemoveCounters);
                int counters = cardState.getCounters();

                if (targetable && counters >= 1) {
                    GameStateBuilder nextStateBuilder = new GameStateBuilder(initialState);
                    if (counters <= amount)
                        nextStateBuilder.getPlayerByID(player.getPlayerID()).getTableau()[cs].withCounters(0);
                    else
                        nextStateBuilder.getPlayerByID(player.getPlayerID()).getTableau()[cs].withCounters(counters - amount);
                    resultList.add(nextStateBuilder.build());

                }
            }
        }
        GameState[] results = new GameState[resultList.size()];
        resultList.toArray(results);
        if (results.length == 0) return new GameState[0];
        return results;
    }

    @Override
    public String getText() {
        StringBuilder result = new StringBuilder();
        result.append("Remove " + amount + " counters from a card with one of these tags:\n");
        for (int i = 0; i < types.length; ++i) {
            result.append(types[i].toString());
            if (i < types.length - 1) {
                result.append(", ");
            }
        }
        return result.toString();
    }
}
