package com.k2sw.opentf.runner;

import com.k2sw.opentf.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;

public class WriteSampleBoard {
    public static void main(String[] args) throws IOException {
        PlayerID id = new PlayerID(0);
        GameStateBuilder state = new GameStateBuilder();
        state = state.withUnplacedSlots(new HashSet<>(state.getStandardBoard().getAllSlots()));
        TileSlot slot = state.getStandardBoard().at(1, 2);
        state.placeTile(slot, new Tile(id, TileType.Plants));
        state.placeTile(state.getStandardBoard().at(3, 0), new Tile(id, TileType.Ocean));
        state.placeTile(state.getStandardBoard().at(5, 5), new Tile(id, TileType.CapitalCity));
        state.placeTile(state.getStandardBoard().at(7, 2), new Tile(id, TileType.Special));
        state.build().save(Paths.get("tf-js/src/board.js"));
    }
}
