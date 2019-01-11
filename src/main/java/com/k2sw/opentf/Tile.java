package com.k2sw.opentf;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return Objects.equals(ownerID, tile.ownerID) &&
                tileType == tile.tileType;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "ownerID=" + ownerID +
                ", tileType=" + tileType +
                '}';
    }
}
