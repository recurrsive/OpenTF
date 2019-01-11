package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

public class NitrogenRichAsteroidConditionalRaiseProductionEffect implements Effect {
    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        if (state.getPlayerByID(currentPlayer).getTagCount(CardTag.Plants) >= 3)
            return new IncreaseProductionEffect(ResourceType.Plants, 4).apply(state, currentPlayer);
        else
            return new GameState[0];
    }
}
