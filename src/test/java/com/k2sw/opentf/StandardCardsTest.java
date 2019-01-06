package com.k2sw.opentf;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class StandardCardsTest {
    @Test
    public void treesShouldPassIfExactlyEnoughOxygen() {
        GameStateBuilder initialState = new GameStateBuilder().withOxygen(-4);
        assertTrue(StandardCards.cards[0].getRequirement().check(initialState));
    }
    @Test
    public void treesShouldPassIfMoreThanEnoughOxygen() {
        GameStateBuilder initialState = new GameStateBuilder().withOxygen(5);
        assertTrue(StandardCards.cards[0].getRequirement().check(initialState));
    }
    @Test
    public void treesShouldFailIfNotEnoughOxygen() {
        GameStateBuilder initialState = new GameStateBuilder().withOxygen(-7);
        assertFalse(StandardCards.cards[0].getRequirement().check(initialState));
    }
    @Test
    public void treesShouldAdd3PlantProductionAnd1Plant() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withOxygen(0).withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        assertArrayEquals(StandardCards.cards[0].getPlayEffect().apply(initialStateBuilder, id), new GameState[]{
                new GameStateBuilder(initialState).withPlayers(new PlayerBuilder[]{
                        new PlayerBuilder().withPlayerID(id).withProduction(ResourceType.Plants, 4).withAmount(ResourceType.Plants, 1)
                }).build()
        });
    }
    @Test
    public void treesShouldScore1VictoryPoint() {
        GameState initialState = new GameStateBuilder().build();
        assertEquals(1, StandardCards.cards[0].getScorer().score(initialState));
    }
    @Test
    public void treesShouldFailIfNotEnoughMegaCredits() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withOxygen(0).withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        assertEquals(0, Card.play(StandardCards.cards[0], initialStateBuilder, id).length);
    }
    @Test
    public void treesShouldPassIfEnoughMegaCredits() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 50);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withOxygen(0).withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        assertEquals(1, Card.play(StandardCards.cards[0], initialStateBuilder, id).length);
    }
    @Test
    public void treesShouldDecreaseMegaCreditsBy13() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 50);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withOxygen(0).withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        assertEquals(37, (int) Card.play(StandardCards.cards[0], initialStateBuilder, id)[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
    }
    @Test
    public void deepWellHeatingShouldAlwaysPass() {
        GameStateBuilder initialState = new GameStateBuilder();
        assertTrue(StandardCards.cards[1].getRequirement().check(initialState));
    }
    @Test
    public void deepWellHeatingShouldAdd1EnergyProduction() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        assertEquals(2, (int) StandardCards.cards[1].getPlayEffect().apply(initialStateBuilder, id)[0].getPlayerByID(id).getProduction().get(ResourceType.Energy));

    }
    @Test
    public void deepWellHeatingShouldRaiseTemperatureBy1IfLessThan13() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withTemperature(3).withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        assertEquals(4, StandardCards.cards[1].getPlayEffect().apply(initialStateBuilder, id)[0].getTemperature());
    }
    @Test
    public void deepWellHeatingShouldNotRaiseTemperatureIfEqualTo13() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withTemperature(13).withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        assertEquals(13, StandardCards.cards[1].getPlayEffect().apply(initialStateBuilder, id)[0].getTemperature());
    }
    @Test
    public void deepWellHeatingShouldAdd1TerraformingScoreIfRaisesTemperature() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withTerraformingScore(0);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withTemperature(5).withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        assertEquals(1, StandardCards.cards[1].getPlayEffect().apply(initialStateBuilder, id)[0].getPlayerByID(id).getTerraformingScore());
    }

    @Test
    public void subterraneanReservoirShouldFailIfNoTiles() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withTerraformingScore(0);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        assertEquals(0, Card.play(StandardCards.cards[2], initialStateBuilder, id).length);
    }
    @Test
    public void subterraneanReservoirShouldFailIfNoOceanTiles() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withTerraformingScore(0);
        TileSlot tile1 = new TileSlot(TileSlotType.Dessert, new ResourceBonus[]{});
        Set<TileSlot> tiles = new HashSet<>();
        tiles.add(tile1);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withPlayers(new PlayerBuilder[]{p1}).withUnplacedSlots(tiles);
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        assertEquals(0, Card.play(StandardCards.cards[2], initialStateBuilder, id).length);
    }
    @Test
    public void subterraneanReservoirShouldPlaceOceanOn1OceanTile() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withTerraformingScore(0);
        TileSlot tile1 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        Set<TileSlot> tiles = new HashSet<>();
        tiles.add(tile1);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withPlayers(new PlayerBuilder[]{p1}).withUnplacedSlots(tiles);
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        Map<TileSlot, Tile> expectedPlacedTiles = new HashMap<>();
        expectedPlacedTiles.put(tile1, new Tile(id, TileType.Ocean));
        assertEquals(1, Card.play(StandardCards.cards[2], initialStateBuilder, id).length);
    }
}
