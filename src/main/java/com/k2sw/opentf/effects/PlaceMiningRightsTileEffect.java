package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;
import java.util.*;

public class PlaceMiningRightsTileEffect implements Effect {
    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        Set<TileSlot> unplaced = new HashSet<>(state.getTileSlotsByType(TileSlotType.Desert));
        Set<TileSlot> available = new HashSet<>();
        for (TileSlot slot : unplaced) {
            if (slot.getBonuses().length > 0) available.add(slot);
        }
        TileSlot[] availableSlots = new TileSlot[available.size()];
        available.toArray(availableSlots);
        GameState initialState = state.build();
        GameState[] results = new GameState[availableSlots.length];
        for (int i = 0; i < availableSlots.length; i++){
            GameStateBuilder initialStateBuilder = new GameStateBuilder(initialState);
            initialStateBuilder.placeTile(availableSlots[i], new Tile(currentPlayer, TileType.Special));
            for (ResourceBonus bonus : availableSlots[i].getBonuses()) {
                initialStateBuilder = new GameStateBuilder(GameStateFunctions.getResources(initialStateBuilder, currentPlayer, bonus));
                if (bonus.getBonusType().equals(ResourceBonusType.Steel))
                    initialStateBuilder.getPlayerByID(currentPlayer).changeProduction(ResourceType.Steel, 1);
                if (bonus.getBonusType().equals(ResourceBonusType.Titanium))
                    initialStateBuilder.getPlayerByID(currentPlayer).changeProduction(ResourceType.Titanium, 1);
            }
            results[i] = GameStateFunctions.getMCFromAdjacent(initialStateBuilder, currentPlayer, availableSlots[i]);
        }
        if (results.length == 0) return new GameState[0];
        else return results;
    }
}
