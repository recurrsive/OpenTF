package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

import java.util.ArrayList;

public class DecreaseOpponentAmountEffect implements Effect {
    private ResourceType type;
    private int amount;

    public DecreaseOpponentAmountEffect(ResourceType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        if (state.getPlayers().length == 1) return new GameState[]{state.build()};
        ArrayList<GameState> resultList = new ArrayList<>();
        GameState initialState = state.build();
        for (int i = 0; i < state.getPlayers().length; i++) {
            PlayerBuilder player = state.getPlayers()[i];
            if (player.getPlayerID() != currentPlayer) {
                GameStateBuilder nextStateBuilder = new GameStateBuilder(initialState);
                if (player.getAmounts().get(type) <= amount) nextStateBuilder.getPlayerByID(player.getPlayerID()).withAmount(type, 0);
                else nextStateBuilder.getPlayerByID(player.getPlayerID()).changeAmount(type, -amount);
                resultList.add(nextStateBuilder.build());
            }
        }
        GameState[] results = new GameState[resultList.size()];
        resultList.toArray(results);
        return results;
    }
}
