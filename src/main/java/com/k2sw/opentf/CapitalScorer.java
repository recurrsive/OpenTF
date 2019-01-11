package com.k2sw.opentf;

import java.util.ArrayList;
import java.util.Map;

public class CapitalScorer implements VictoryScorer {
    @Override
    public int score(GameState state, PlayerID owner) {
        TileSlot capitalSlot = null;
        Map<TileSlot, Tile> placedTiles = state.getPlacedTiles();
        for (TileSlot slot : placedTiles.keySet()){
            if (placedTiles.get(slot).getTileType() == TileType.CapitalCity) capitalSlot = slot;
        }
        ArrayList<TileSlot> neighbors = capitalSlot.getNeighbors();
        int adjacentOceans = 0;
        for (TileSlot neighbor : neighbors) {
            if (placedTiles.get(neighbor).getTileType() == TileType.Ocean) adjacentOceans++;
        }
        return adjacentOceans;
    }
}
