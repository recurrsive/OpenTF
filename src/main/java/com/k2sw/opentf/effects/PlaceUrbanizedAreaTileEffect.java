package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;
import java.util.*;

public class PlaceUrbanizedAreaTileEffect implements Effect {
    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        Set<TileSlot> unplaced = new HashSet<>(state.getTileSlotsByType(TileSlotType.Desert));
        Set<TileSlot> available = new HashSet<>();
        for (TileSlot slot : unplaced) {
            int adjacentCityCount = 0;
            for (TileSlot neighbor : slot.getNeighbors()) {
                if (state.getPlacedTiles().containsKey(neighbor) && state.getPlacedTiles().get(neighbor).getTileType() == TileType.City)
                    adjacentCityCount++;
            }
            if (adjacentCityCount >= 2) available.add(slot);
        }
        TileSlot[] availableSlots = new TileSlot[available.size()];
        available.toArray(availableSlots);
        GameState initialState = state.build();
        GameState[] results = EffectHelpers.permuteTiles(initialState, currentPlayer, availableSlots, TileType.City);
        if (results.length == 0) return new GameState[0];
        else return GameStateFunctions.triggerSearch(new TriggerType[]{TriggerType.CityPlaced}, results, currentPlayer);
    }
}
