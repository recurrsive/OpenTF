package com.k2sw.opentf;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PlaceOceanTileEffect implements Effect {
    private GameState placeTile(GameStateBuilder state, PlayerID currentPlayer, TileSlot tile){
        Set<TileSlot> unplaced = state.getUnplacedSlots();
        unplaced.remove(tile);
        state.getPlacedTiles().put(tile, new Tile(currentPlayer, TileType.Ocean));
        PlayerBuilder player = state.getPlayerByID(currentPlayer);
        player.withTerraformingScore(player.getTerraformingScore()+1);
        return state.build();
    }

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        Set<TileSlot> unplaced = new HashSet<>(state.getUnplacedSlots());
        unplaced.removeIf(tile -> tile.getTileSlotType() != TileSlotType.Ocean);
        TileSlot[] availableSlots = new TileSlot[unplaced.size()];
        unplaced.toArray(availableSlots);
        GameState[] results = new GameState[availableSlots.length];
        GameState initialState = state.build();
        for (int i = 0; i < availableSlots.length; i++){
            GameStateBuilder initialStateBuilder = new GameStateBuilder(initialState);
            results[i] = placeTile(initialStateBuilder, currentPlayer, availableSlots[i]);
        }
        return results;
    }
}
