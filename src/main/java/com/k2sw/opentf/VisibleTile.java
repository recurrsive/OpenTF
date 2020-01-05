package com.k2sw.opentf;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisibleTile {
    private TileSlotType tileSlotType;
    private ResourceBonus[] bonuses;
    private Tile tile;
    private int q;
    private int r;

    public int getQ() {
        return q;
    }

    public int getR() {
        return r;
    }

    public VisibleTile(TileSlotType tileSlotType, ResourceBonus[] bonuses, Tile tile, int q, int r) {
        this.tileSlotType = tileSlotType;
        this.bonuses = bonuses;
        this.tile = tile;
        this.q = q;
        this.r = r;
    }

    public TileSlotType getTileSlotType() {
        return tileSlotType;
    }

    public ResourceBonus[] getBonuses() {
        return bonuses;
    }

    public Tile getTile() {
        return tile;
    }
}
