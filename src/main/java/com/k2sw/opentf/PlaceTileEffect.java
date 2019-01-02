package com.k2sw.opentf;

public class PlaceTileEffect implements Effect {
    private TileType slotType;
    private TileType placedType;

    public PlaceTileEffect(TileType slotType, TileType placedType) {
        this.slotType = slotType;
        this.placedType = placedType;
    }

    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        return new GameState[0];
    }
}
