package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

import java.util.HashSet;
import java.util.Set;

public class PlaceArtificialLakeTileEffect implements Effect {

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        if (state.getTilesByType(TileType.Ocean).size() == Global.MAX_OCEANS)
            return new GameState[]{state.build()};
        else {
            Set<TileSlot> unplaced = new HashSet<>(state.getTileSlotsByType(TileSlotType.Desert));
            TileSlot[] availableSlots = new TileSlot[unplaced.size()];
            unplaced.toArray(availableSlots);
            GameState initialState = state.build();
            GameState[] results = EffectHelpers.permuteTiles(initialState, currentPlayer, availableSlots, TileType.Ocean);
            if (results.length == 0)
                return new GameState[]{state.build()};
            else
                return GameStateFunctions.triggerSearch(new TriggerType[]{TriggerType.OceanPlaced}, results, currentPlayer);
        }
    }
}
