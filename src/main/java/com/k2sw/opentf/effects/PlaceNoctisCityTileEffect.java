package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

import java.util.*;

public class PlaceNoctisCityTileEffect extends Effect {
    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        Set<TileSlot> unplaced = new HashSet<>(state.getTileSlotsByType(TileSlotType.Noctis));
        if (unplaced.size() != 1)
            throw new RuntimeException("Tried to place Noctis City, but could not find a TileSlot with type Noctis in unplacedSlots.");
        TileSlot[] availableSlots = new TileSlot[unplaced.size()]; unplaced.toArray(availableSlots);
        TileSlot slot = availableSlots[0];
        state.placeTile(slot, new Tile(currentPlayer, TileType.City));
        for (ResourceBonus bonus : slot.getBonuses()) {
            state = new GameStateBuilder(GameStateFunctions.getResources(state, currentPlayer, bonus));
        }
        return GameStateFunctions.triggerSearch(new TriggerType[]{TriggerType.CityPlaced}, new GameState[]{GameStateFunctions.getMCFromAdjacent(state, currentPlayer, slot)}, currentPlayer);
    }

    @Override
    public String getText() {
        return "Place a city tile on the reserved space.";
    }
}
