package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;
import java.util.*;

public class PlaceMangrovePlantTileEffect implements Effect {
    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        Set<TileSlot> unplaced = new HashSet<>(state.getTileSlotsByType(TileSlotType.Ocean));
        TileSlot[] availableSlots = new TileSlot[unplaced.size()];
        unplaced.toArray(availableSlots);
        GameState[] results = new GameState[availableSlots.length];
        GameState initialState = state.build();
        for (int i = 0; i < availableSlots.length; i++){
            GameStateBuilder initialStateBuilder = new GameStateBuilder(initialState);
            initialStateBuilder.placeTile(availableSlots[i], new Tile(currentPlayer, TileType.Plants));
            for (ResourceBonus bonus : availableSlots[i].getBonuses()) {
                initialStateBuilder = new GameStateBuilder(GameStateFunctions.getResources(initialStateBuilder, currentPlayer, bonus));
            }
            results[i] = GameStateFunctions.getMCFromAdjacent(initialStateBuilder, currentPlayer, availableSlots[i]);
        }
        if (results.length == 0) return new GameState[]{state.build()};
        else return GameStateFunctions.triggerSearch(new TriggerType[]{TriggerType.PlantsPlaced}, results, currentPlayer);
    }
}
