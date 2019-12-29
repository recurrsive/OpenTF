package com.k2sw.opentf;

import java.util.*;
import java.util.function.BiConsumer;

public class StandardBoard {

    private TileSlot steel(int n) {
        return new TileSlot(TileSlotType.Desert, new ResourceBonus[]{new ResourceBonus(ResourceBonusType.Steel, n)});
    }

    private TileSlot plant(int n) {
        return new TileSlot(TileSlotType.Desert, new ResourceBonus[]{new ResourceBonus(ResourceBonusType.Plants, n)});
    }

    private TileSlot card(int n) {
        return new TileSlot(TileSlotType.Desert, new ResourceBonus[]{new ResourceBonus(ResourceBonusType.Cards, n)});
    }

    private TileSlot titanium(int n) {
        return new TileSlot(TileSlotType.Desert, new ResourceBonus[]{new ResourceBonus(ResourceBonusType.Titanium, n)});
    }

    private TileSlot blank() {
        return new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
    }

    private TileSlot noctis(TileSlot slot) {
        return new TileSlot(TileSlotType.Noctis, slot.getBonuses());
    }

    private TileSlot ocean(TileSlot slot) {
        return new TileSlot(TileSlotType.Ocean, slot.getBonuses());
    }

    private TileSlot pavonis() {
        return new TileSlot(TileSlotType.Desert, new ResourceBonus[]{new ResourceBonus(ResourceBonusType.Titanium, 1),
                new ResourceBonus(ResourceBonusType.Plants, 1)});
    }

    private TileSlot[][] rows;

    public ArrayList<TileSlot> getAllSlots() {
        ArrayList<TileSlot> result = new ArrayList<>();
        for (TileSlot[] row : rows) {
            Collections.addAll(result, row);
        }
        return result;
    }

    public TileSlot at(int row, int col) {
        return rows[row][col];
    }

    public int getWidth(int row) { return rows[row].length; }

    public int getHeight() { return rows.length; }

    public StandardBoard() {
        rows = new TileSlot[][]{
                {steel(2), ocean(steel(2)), blank(), ocean(card(1)), ocean(blank())},
                {blank(), steel(1), blank(), blank(), blank(), ocean(card(2))},
                {card(1), blank(), blank(), blank(), blank(), blank(), steel(1)},
                {pavonis(), plant(1), plant(1), plant(1), plant(2), plant(1), plant(1), ocean(plant(2))},
                {plant(2), plant(2), noctis(plant(2)), ocean(plant(2)), ocean(plant(2)), ocean(plant(2)), plant(2), plant(2), plant(2)},
                {plant(1), plant(2), plant(1), plant(1), plant(1), ocean(plant(1)), ocean(plant(1)), ocean(plant(1))},
                {blank(), blank(), blank(), blank(), blank(), plant(1), blank()},
                {steel(2), blank(), card(1), card(1), blank(), titanium(1)},
                {steel(1), steel(2), blank(), blank(), ocean(titanium(2))}
        };
        for (int i = 0; i < this.rows.length; i++) {
            for (int j = 0; j < this.rows[i].length; j++) {
                final int ii = i;
                final int jj = j;
                BiConsumer<Integer, Integer> addNeighbor = (i2, j2) -> {
                    if (i2 >= 0 && i2 < rows.length && j2 >= 0 && j2 < rows[i2].length) {
                        rows[ii][jj].addNeighbor(rows[i2][j2]);
                        rows[i2][j2].addNeighbor(rows[ii][jj]);
                    }
                };
                addNeighbor.accept(i, j + 1);
                addNeighbor.accept(i - 1, j);
                if (i <= 4)
                    addNeighbor.accept(i - 1, j - 1);
                else
                    addNeighbor.accept(i - 1, j + 1);
            }
        }
    }
}