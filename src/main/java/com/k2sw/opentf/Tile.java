package com.k2sw.opentf;

public class Tile {
    private PlayerID ownerID;
    private TileType tileType;

    public Tile(PlayerID ownerID, TileType tileType) {
        this.ownerID = ownerID;
        this.tileType = tileType;
    }

    public PlayerID getOwnerID() {
        return ownerID;
    }

    public TileType getTileType() {
        return tileType;
    }
}
