package com.k2sw.opentf;

public class IncreaseTemperatureEffect implements Effect {
    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        if (state.getTemperature() < 13) {
            state.withTemperature(state.getTemperature() + 1);
            state.getPlayerByID(currentPlayer).withTerraformingScore(state.getPlayerByID(currentPlayer).getTerraformingScore()+1);
        }
        return new GameState[]{state.build()};
    }
}
