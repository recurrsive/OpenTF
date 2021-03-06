package com.k2sw.opentf;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class TileSlot {
    private TileSlotType tileSlotType;
    private ResourceBonus[] bonuses;
    private ArrayList<TileSlot> neighbors = new ArrayList<>();

    public TileSlot(TileSlotType tileSlotType, ResourceBonus[] bonuses) {
        this.tileSlotType = tileSlotType;
        this.bonuses = bonuses;
    }

    public TileSlotType getTileSlotType() {
        return tileSlotType;
    }

    public ArrayList<TileSlot> getNeighbors() {
        return neighbors;
    }

    public ResourceBonus[] getBonuses() {
        return bonuses;
    }

    // Only call this method during initialization
    void addNeighbor(TileSlot neighbor) {
        neighbors.add(neighbor);
    }

    public boolean isNeighbor(TileSlot slot) {
        return neighbors.contains(slot);
    }

    public JSONObject json() {
        return new JSONObject().put("slotType", this.tileSlotType.toString());
    }
}
