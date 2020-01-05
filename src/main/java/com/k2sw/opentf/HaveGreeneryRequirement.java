package com.k2sw.opentf;

import java.util.*;

public class HaveGreeneryRequirement extends Requirement {
    @Override
    public boolean check(GameStateBuilder state, PlayerID currentPlayer) {
        Map<TileSlot, Tile> placed = state.getPlacedTiles();
        for (Tile tile : placed.values()) {
            if (tile.getTileType() == TileType.Plants && tile.getOwnerID().equals(currentPlayer))
                return true;
        }
        return false;
    }

    @Override
    public String getText() {
        return "Have a greenery.";
    }
}
