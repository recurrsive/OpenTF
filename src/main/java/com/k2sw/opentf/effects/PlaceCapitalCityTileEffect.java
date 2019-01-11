package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlaceCapitalCityTileEffect implements Effect {
    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        Set<TileSlot> unplaced = new HashSet<>(state.getTileSlotsByType(TileSlotType.Desert));
        EffectHelpers.removeInvalidSlots(state, unplaced, new TileSlotType[0], new TileType[]{TileType.City, TileType.CapitalCity});
        TileSlot[] availableSlots = new TileSlot[unplaced.size()];
        unplaced.toArray(availableSlots);
        List<GameState> valid = new ArrayList<>();
        GameState initialState = state.build();
        for (TileSlot available : availableSlots) {
            GameStateBuilder initialStateBuilder = new GameStateBuilder(initialState);
            initialStateBuilder.placeTile(available, new Tile(currentPlayer, TileType.CapitalCity));
            for (ResourceBonus bonus : available.getBonuses()) {
                initialStateBuilder = new GameStateBuilder(GameStateFunctions.getResources(initialStateBuilder, currentPlayer, bonus));
            }
            valid.add(GameStateFunctions.getMCFromAdjacent(initialStateBuilder, currentPlayer, available));
        }
        GameState[] results = new GameState[valid.size()];
        valid.toArray(results);
        if (results.length == 0) return new GameState[]{state.build()};
        return GameStateFunctions.triggerSearch(new TriggerType[]{TriggerType.CityPlaced}, results, currentPlayer);
    }
}
