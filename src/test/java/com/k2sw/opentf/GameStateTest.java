package com.k2sw.opentf;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class GameStateTest {
    @Test
    public void testBoard() {
        StandardBoard standardBoard = new StandardBoard();
        System.out.println(standardBoard);
    }

    @Test
    public void placeTileShouldThrowExceptionIfSlotNotInSet() {
        GameStateBuilder initialState = new GameStateBuilder();
        boolean thrown = false;
        try { initialState.placeTile(new TileSlot(TileSlotType.Desert, new ResourceBonus[0]), new Tile(new PlayerID(0), TileType.Ocean)); }
        catch (RuntimeException e) {
            if (e.getMessage().equals("Slot to be placed on is not in GameStateBuilder")) thrown = true;
        }
        assertTrue(thrown);
    }
    @Test
    public void placeTileShouldPlaceOceanTileOnSlotWhenExactlyOneOceanSlot() {
        GameStateBuilder initialState = new GameStateBuilder();
        PlayerID id = new PlayerID(0);
        TileSlot slot1 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]);
        Tile oceanTile1 = new Tile(id, TileType.Ocean);
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        initialState.withUnplacedSlots(unplacedSlots);

        Map<TileSlot, Tile> expectedPlacedTiles = new HashMap<>();
        expectedPlacedTiles.put(slot1, oceanTile1);
        GameStateBuilder expectedFinalState = new GameStateBuilder().withPlacedTiles(expectedPlacedTiles);

        initialState.placeTile(slot1, oceanTile1);

        assertEquals(initialState, expectedFinalState);
    }

    @Test
    public void getTilesByTypeShouldReturnEmptyListIfNoTiles() {
        GameStateBuilder initialState = new GameStateBuilder();
        assertEquals(0, initialState.getTileSlotsByType(TileSlotType.Ocean).size());
    }

    @Test
    public void getTilesByTypeShouldReturnEmptyListIfNoTilesOfType() {
        GameStateBuilder initialState = new GameStateBuilder();
        Set<TileSlot> unplaced = new HashSet<>();
        unplaced.add(new TileSlot(TileSlotType.Desert, new ResourceBonus[0]));
        initialState.withUnplacedSlots(unplaced);
        assertEquals(0, initialState.getTileSlotsByType(TileSlotType.Ocean).size());
    }

    @Test
    public void getTilesByTypeShouldReturnOceanTilesIfThereAreOceanTiles() {
        GameStateBuilder initialState = new GameStateBuilder();
        Set<TileSlot> unplaced = new HashSet<>();
        unplaced.add(new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]));
        unplaced.add(new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]));
        initialState.withUnplacedSlots(unplaced);
        assertEquals(2, initialState.getTileSlotsByType(TileSlotType.Ocean).size());
    }
}
