package com.k2sw.opentf;

import java.util.ArrayList;
import java.util.Collections;

public class CompoundEffect implements Effect {
    private Effect effect1;
    private Effect effect2;

    public CompoundEffect(Effect effect1, Effect effect2) {
        this.effect1 = effect1;
        this.effect2 = effect2;
    }

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        GameState[] next_states = effect1.apply(state, currentPlayer);
        ArrayList<GameState> final_states = new ArrayList<>();
        for (GameState next : next_states){
            Collections.addAll(final_states, effect2.apply(new GameStateBuilder(next), currentPlayer));
        }
        GameState[] results = new GameState[final_states.size()];
        final_states.toArray(results);
        return results;
    }
}
