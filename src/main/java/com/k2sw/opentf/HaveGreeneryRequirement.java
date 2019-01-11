package com.k2sw.opentf;

import java.util.*;

public class HaveGreeneryRequirement implements Requirement {
    @Override
    public boolean check(GameStateBuilder state, PlayerID currentPlayer) {
        Map<TileSlot, Tile> placed = state.getPlacedTiles();
        for (Tile tile : placed.values()) {
            if (tile.getTileType() == TileType.Plants && tile.getOwnerID().equals(currentPlayer))
                return true;
        }
        return false;
    }
}
