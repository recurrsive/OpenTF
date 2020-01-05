package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

public class IncreaseProductionByTagCountEffect extends Effect {
    private ResourceType type;
    private CardTag tag;
    private int points;
    private int req;

    public IncreaseProductionByTagCountEffect(ResourceType type, CardTag tag) {
        this.type = type;
        this.tag = tag;
        this.points = 1;
        this.req = 1;
    }

    public IncreaseProductionByTagCountEffect(ResourceType type, CardTag tag, int points, int req) {
        this.type = type;
        this.tag = tag;
        this.points = points;
        this.req = req;
    }

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        int amount = state.getPlayerByID(currentPlayer).getTagCount(tag);
        amount -= amount % req;
        return new IncreaseProductionEffect(type, points*amount).apply(state, currentPlayer);
    }

    @Override
    public String getText() {
        return "Increase your " + type.toString() + " production by 1 for every " + req + " " + type.toString() + " tag(s).";
    }
}
