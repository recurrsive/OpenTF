package com.k2sw.opentf;

public class OceanRequirement implements Requirement {
    private int amount;
    private boolean greater;

    public OceanRequirement(int amount, boolean greater) {
        this.amount = amount;
        this.greater = greater;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isGreater() {
        return greater;
    }

    @Override
    public boolean check(GameStateBuilder state, PlayerID currentPlayer) {
        int oceanCount = 0;
        for (Tile tile : state.getPlacedTiles().values()) {
            if (tile.getTileType() == TileType.Ocean) oceanCount++;
        }
        if (greater)
            return oceanCount >= amount;
        else
            return oceanCount <= amount;
    }
}
