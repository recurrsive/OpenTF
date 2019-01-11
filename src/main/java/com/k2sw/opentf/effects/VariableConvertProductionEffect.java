package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

import java.util.*;

public class VariableConvertProductionEffect implements Effect {
    private ResourceType lostType;
    private ResourceType gainedType;

    public VariableConvertProductionEffect(ResourceType lostType, ResourceType gainedType) {
        this.lostType = lostType;
        this.gainedType = gainedType;
    }

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        int maxValue = state.getPlayerByID(currentPlayer).getProduction().get(lostType);
        ArrayList<Effect> effectList = new ArrayList<>();
        for (int x = 0; x <= maxValue; x++) {
            effectList.add(new CompoundEffect(new Effect[]{
                    new DecreaseProductionEffect(lostType, x),
                    new IncreaseProductionEffect(gainedType, x)
            }));
        }
        Effect[] effects = new Effect[effectList.size()];
        effectList.toArray(effects);
        return new OrEffect(effects).apply(state, currentPlayer);
    }
}
