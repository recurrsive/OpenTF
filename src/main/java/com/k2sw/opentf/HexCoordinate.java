package com.k2sw.opentf;

import java.util.ArrayList;
import java.util.Objects;

public class HexCoordinate {
    private int x;
    private int y;
    private int z;
    private int radius;

    public HexCoordinate(int x, int y, int z, int radius) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
    }

    private int[][] deltas = {
            {0, -1, 1},
            {1, -1, 0},
            {1, 0, -1},
            {0, 1, -1},
            {-1, 1, 0},
            {-1, 0, 1}
    };

    public HexCoordinate[] getNeighbors() {
        

        return new HexCoordinate[]{
                new HexCoordinate(x, y - 1),
                new HexCoordinate(x + 1, y - 1),
                new HexCoordinate(x + 1, y),
                new HexCoordinate(x + 1, y + 1),
                new HexCoordinate(x, y + 1),
                new HexCoordinate(x - 1, y),
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HexCoordinate that = (HexCoordinate) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
