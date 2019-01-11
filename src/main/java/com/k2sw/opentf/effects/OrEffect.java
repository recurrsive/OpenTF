package com.k2sw.opentf.effects;

import com.k2sw.opentf.Effect;
import com.k2sw.opentf.GameState;
import com.k2sw.opentf.GameStateBuilder;
import com.k2sw.opentf.PlayerID;

import java.util.*;

public class OrEffect implements Effect {
    private Effect[] effects;

    public OrEffect(Effect[] effects) {
        this.effects = effects;
    }

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        GameState initialState = state.build();
        List<GameState> resultList = new ArrayList<>();
        for (int i = 0; i < effects.length; i++){
            GameStateBuilder nextState = new GameStateBuilder(initialState);
            Collections.addAll(resultList, effects[i].apply(nextState, currentPlayer));
        }
        GameState[] results = new GameState[resultList.size()];
        resultList.toArray(results);
        return results;
    }
}
