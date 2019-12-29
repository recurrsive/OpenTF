package com.k2sw.opentf;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisibleTile {
    private TileSlotType tileSlotType;
    private ResourceBonus[] bonuses;
    private Tile tile;
    private int row;
    private int col;

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public VisibleTile(TileSlotType tileSlotType, ResourceBonus[] bonuses, Tile tile, int row, int col) {
        this.tileSlotType = tileSlotType;
        this.bonuses = bonuses;
        this.tile = tile;
        this.row = row;
        this.col = col;
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
