package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

public class NitrogenRichAsteroidConditionalRaiseProductionEffect extends Effect {
    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        if (state.getPlayerByID(currentPlayer).getTagCount(CardTag.Plants) >= 3)
            return new IncreaseProductionEffect(ResourceType.Plants, 4).apply(state, currentPlayer);
        else
            return new GameState[0];
    }

    @Override
    public String getText() {
        return "Gain 4 Plants production if you have 3 or more Plants tags, otherwise gain 1.";
    }
}
