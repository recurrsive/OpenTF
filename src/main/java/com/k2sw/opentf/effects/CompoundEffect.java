package com.k2sw.opentf.effects;

import com.k2sw.opentf.Effect;
import com.k2sw.opentf.GameState;
import com.k2sw.opentf.GameStateBuilder;
import com.k2sw.opentf.PlayerID;

import java.util.ArrayList;
import java.util.Collections;

public class CompoundEffect implements Effect {
    private Effect[] effects;

    public CompoundEffect(Effect[] effects) {
        if (effects.length == 0) throw new RuntimeException("Tried to initialize a CompoundEffect with no child effects");
        this.effects = effects;
    }

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        GameState[] results = new GameState[]{state.build()};
        for (Effect effect : effects) {
            ArrayList<GameState> next_states = new ArrayList<>();
            for (GameState prev_state : results){
                Collections.addAll(next_states, effect.apply(new GameStateBuilder(prev_state), currentPlayer));
            }
            results = new GameState[next_states.size()];
            next_states.toArray(results);
        }
        return results;
    }
}
