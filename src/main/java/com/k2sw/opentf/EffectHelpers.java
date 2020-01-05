package com.k2sw.opentf;

import java.util.Set;

public class EffectHelpers {
    public static void removeInvalidSlots(GameStateBuilder state, Set<TileSlot> slots, TileSlotType[] invalidSlotTypes, TileType[] invalidTileTypes){
        for (TileSlot slot : slots) {
            if (invalidSlotTypes.length > 0) {
                for (TileSlotType slotType : invalidSlotTypes)
                    if (slot.getTileSlotType().equals(slotType))
                        slots.remove(slot);
            }
            if (invalidTileTypes.length > 0) {
                Set<Tile> adjacent = state.getAdjacentPlacedTiles(slot);
                for (Tile tile : adjacent)
                    for (TileType type : invalidTileTypes)
                        if (tile.getTileType() == type)
                            slots.remove(slot);
            }
        }
    }

    public static GameState[] permuteTiles(GameState initialState, PlayerID currentPlayer, TileSlot[] availableSlots, TileType type) {
        GameState[] results = new GameState[availableSlots.length];
        for (int i = 0; i < availableSlots.length; i++){
            Tile tileToPlace;
            if (type == TileType.Ocean) tileToPlace = new Tile(Global.NO_PLAYER, type);
            else tileToPlace = new Tile(currentPlayer, type);
            GameStateBuilder initialStateBuilder = new GameStateBuilder(initialState);
            initialStateBuilder.placeTile(availableSlots[i], tileToPlace);
            for (ResourceBonus bonus : availableSlots[i].getBonuses()) {
                initialStateBuilder = new GameStateBuilder(GameStateFunctions.getResources(initialStateBuilder, currentPlayer, bonus));
            }
            results[i] = GameStateFunctions.getMCFromAdjacent(initialStateBuilder, currentPlayer, availableSlots[i]);
        }
        return results;
    }

    public static int reduceCost(int cost, boolean isStandardProject, CardTag[] tags,
                                  GameStateBuilder state, PlayerID currentPlayer) {
        int finalCost = cost;
        for (CardStateBuilder card : state.getPlayerByID(currentPlayer).getTableau()) {
            finalCost = card.getCard().getReducer().reduceCost(tags, isStandardProject, finalCost);
        }
        if (finalCost < 0) {
            return 0;
        }
        else {
            return finalCost;
        }
    }
}
