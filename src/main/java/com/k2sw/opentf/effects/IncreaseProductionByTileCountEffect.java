package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

public class IncreaseProductionByTileCountEffect implements Effect {
    private ResourceType type;
    private TileType tileType;

    public IncreaseProductionByTileCountEffect(ResourceType type, TileType tileType) {
        this.type = type;
        this.tileType = tileType;
    }

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        int amount = 0;
        for (Tile tile : state.getPlacedTiles().values()){
            if (tile.getTileType() == tileType || (tileType == TileType.City && tile.getTileType() == TileType.CapitalCity)) amount++;
        }
        return new IncreaseProductionEffect(type, amount).apply(state, currentPlayer);
    }
}
