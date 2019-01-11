package com.k2sw.opentf;

import com.k2sw.opentf.effects.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class StandardCardsTest {
    //region trees tests
    @Test
    public void treesShouldPassIfExactlyEnoughTemperature() {
        GameStateBuilder initialState = new GameStateBuilder().withTemperature(-4);
        assertTrue(StandardCards.trees.getRequirement().check(initialState, Global.NO_PLAYER));
    }

    @Test
    public void treesShouldPassIfMoreThanEnoughTemperature() {
        GameStateBuilder initialState = new GameStateBuilder().withTemperature(5);
        assertTrue(StandardCards.trees.getRequirement().check(initialState, Global.NO_PLAYER));
    }

    @Test
    public void treesShouldFailIfNotEnoughTemperature() {
        GameStateBuilder initialState = new GameStateBuilder().withTemperature(-7);
        assertFalse(StandardCards.trees.getRequirement().check(initialState, Global.NO_PLAYER));
    }

    @Test
    public void treesShouldAdd3PlantProductionAnd1Plant() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withTemperature(0).withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        assertArrayEquals(StandardCards.trees.getPlayEffect().apply(initialStateBuilder, id), new GameState[]{
                new GameStateBuilder(initialState).withPlayers(new PlayerBuilder[]{
                        new PlayerBuilder().withPlayerID(id).withProduction(ResourceType.Plants, 4).withAmount(ResourceType.Plants, 1)
                }).build()
        });
    }

    @Test
    public void treesShouldScore1VictoryPoint() {
        GameState initialState = new GameStateBuilder().build();
        assertEquals(1, StandardCards.trees.getScorer().score(initialState, Global.NO_PLAYER));
    }

    @Test
    public void treesShouldFailIfNotEnoughMegaCredits() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withTemperature(0).withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        assertEquals(0, Card.play(StandardCards.trees, initialStateBuilder, id).length);
    }

    @Test
    public void treesShouldPassIfEnoughMegaCredits() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 50);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withTemperature(0).withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        assertEquals(1, Card.play(StandardCards.trees, initialStateBuilder, id).length);
    }

    @Test
    public void treesShouldDecreaseMegaCreditsBy13() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 50);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withTemperature(0).withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        assertEquals(37, (int) Card.play(StandardCards.trees, initialStateBuilder, id)[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
    }
    @Test
    public void treesShouldAddToTableau() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 13);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withTemperature(0).withPlayers(new PlayerBuilder[]{p1});

        GameState[] results = Card.play(StandardCards.trees, initialStateBuilder, id);

        assertEquals(1, results.length);
        assertEquals(1, results[0].getPlayerByID(id).getTableau().length);
        assertEquals(StandardCards.trees, results[0].getPlayerByID(id).getTableau()[0].getCard());
    }
    //endregion

    //region deepWellHeating tests
    @Test
    public void deepWellHeatingShouldAlwaysPass() {
        GameStateBuilder initialState = new GameStateBuilder();
        assertTrue(StandardCards.deepWellHeating.getRequirement().check(initialState, Global.NO_PLAYER));
    }

    @Test
    public void deepWellHeatingShouldAdd1EnergyProduction() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        assertEquals(2, (int) StandardCards.deepWellHeating.getPlayEffect().apply(initialStateBuilder, id)[0].getPlayerByID(id).getProduction().get(ResourceType.Energy));
    }

    @Test
    public void deepWellHeatingShouldRaiseTemperatureBy1IfLessThan13() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withTemperature(3).withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        assertEquals(4, StandardCards.deepWellHeating.getPlayEffect().apply(initialStateBuilder, id)[0].getTemperature());
    }

    @Test
    public void deepWellHeatingShouldNotRaiseTemperatureOrTerraformingScoreIfEqualTo8() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withTemperature(8).withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);

        GameState[] finalStates = StandardCards.deepWellHeating.getPlayEffect().apply(initialStateBuilder, id);

        assertEquals(8, finalStates[0].getTemperature());
        assertEquals(0, finalStates[0].getPlayerByID(id).getTerraformingScore());
    }

    @Test
    public void deepWellHeatingShouldAdd1TerraformingScoreIfRaisesTemperature() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withTerraformingScore(0);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withTemperature(5).withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        assertEquals(1, StandardCards.deepWellHeating.getPlayEffect().apply(initialStateBuilder, id)[0].getPlayerByID(id).getTerraformingScore());
    }

    @Test
    public void deepWellHeatingShouldReturnTwoResultsIfPlayerHas1Steel() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 13).withAmount(ResourceType.Steel, 1);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withPlayers(new PlayerBuilder[]{p1});

        GameState[] finalResults = Card.play(StandardCards.deepWellHeating, initialStateBuilder, id);
        assertEquals(2, finalResults.length);
        assertEquals(0, (int) finalResults[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(1, (int) finalResults[0].getPlayerByID(id).getAmounts().get(ResourceType.Steel));
        assertEquals(2, (int) finalResults[1].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(0, (int) finalResults[1].getPlayerByID(id).getAmounts().get(ResourceType.Steel));
    }

    @Test
    public void deepWellHeatingShouldReturnThreeResultsIfPlayerHas2Steel() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 13).withAmount(ResourceType.Steel, 2);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);

        GameState[] finalResults = Card.play(StandardCards.deepWellHeating, initialStateBuilder, id);
        assertEquals(3, finalResults.length);
        assertEquals(0, (int) finalResults[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(2, (int) finalResults[0].getPlayerByID(id).getAmounts().get(ResourceType.Steel));
        assertEquals(2, (int) finalResults[1].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(1, (int) finalResults[1].getPlayerByID(id).getAmounts().get(ResourceType.Steel));
        assertEquals(4, (int) finalResults[2].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(0, (int) finalResults[2].getPlayerByID(id).getAmounts().get(ResourceType.Steel));
    }

    @Test
    public void deepWellHeatingShouldReturnTwoResultsIfPlayerHas2SteelAnd11MC() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 11).withAmount(ResourceType.Steel, 2);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);

        GameState[] finalResults = Card.play(StandardCards.deepWellHeating, initialStateBuilder, id);
        assertEquals(2, finalResults.length);
        assertEquals(0, (int) finalResults[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(1, (int) finalResults[0].getPlayerByID(id).getAmounts().get(ResourceType.Steel));
        assertEquals(2, (int) finalResults[1].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(0, (int) finalResults[1].getPlayerByID(id).getAmounts().get(ResourceType.Steel));
    }
    //endregion

    //region subterraneanReservoir tests
    @Test
    public void subterraneanReservoirShouldFailIfNoTiles() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withTerraformingScore(0);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        assertEquals(0, Card.play(StandardCards.subterraneanReservoir, initialStateBuilder, id).length);
    }

    @Test
    public void subterraneanReservoirShouldFailIfNoOceanTiles() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withTerraformingScore(0);
        TileSlot tile1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        Set<TileSlot> tiles = new HashSet<>();
        tiles.add(tile1);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withPlayers(new PlayerBuilder[]{p1}).withUnplacedSlots(tiles);
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        assertEquals(0, Card.play(StandardCards.subterraneanReservoir, initialStateBuilder, id).length);
    }

    @Test
    public void subterraneanReservoirShouldPlaceOceanWhenExactlyOneOceanSlot() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]);
        Tile oceanTile = new Tile(Global.NO_PLAYER, TileType.Ocean);
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        initialState.withUnplacedSlots(unplacedSlots);

        Map<TileSlot, Tile> expectedPlacedTiles = new HashMap<>();
        expectedPlacedTiles.put(slot1, oceanTile);

        GameState[] finalStates = StandardCards.subterraneanReservoir.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(finalStates[0].getPlacedTiles(), expectedPlacedTiles);
    }

    @Test
    public void subterraneanReservoirShouldPlaceOceanForEachOceanSlot() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withTerraformingScore(0);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]);
        TileSlot slot2 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]);
        Tile oceanTile = new Tile(Global.NO_PLAYER, TileType.Ocean);
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        unplacedSlots.add(slot2);
        initialState.withUnplacedSlots(unplacedSlots);

        Map<TileSlot, Tile> expectedPlacedTiles1 = new HashMap<>();
        expectedPlacedTiles1.put(slot1, oceanTile);
        Set<TileSlot> expectedUnplacedSlots1 = new HashSet<>();
        expectedUnplacedSlots1.add(slot2);
        PlayerBuilder expectedPlayer = new PlayerBuilder(player.build()).withTerraformingScore(1);
        Map<TileSlot, Tile> expectedPlacedTiles2 = new HashMap<>();
        expectedPlacedTiles2.put(slot2, oceanTile);
        Set<TileSlot> expectedUnplacedSlots2 = new HashSet<>();
        expectedUnplacedSlots2.add(slot1);
        GameState[] expectedFinalStates = new GameState[]{
                new GameStateBuilder().withUnplacedSlots(expectedUnplacedSlots1)
                        .withPlacedTiles(expectedPlacedTiles1)
                        .withPlayers(new PlayerBuilder[]{expectedPlayer}).build(),
                new GameStateBuilder().withUnplacedSlots(expectedUnplacedSlots2)
                        .withPlacedTiles(expectedPlacedTiles2)
                        .withPlayers(new PlayerBuilder[]{expectedPlayer}).build()};

        GameState[] finalStates = StandardCards.subterraneanReservoir.getPlayEffect().apply(initialState, id);

        assertTrue(expectedFinalStates.length == finalStates.length
                && (expectedFinalStates[0].equals(finalStates[0]) || expectedFinalStates[0].equals(finalStates[1]))
                && (expectedFinalStates[1].equals(finalStates[0]) || expectedFinalStates[0].equals(finalStates[0]))
                && (!finalStates[0].equals(finalStates[1]))
        );
    }

    @Test
    public void subterraneanReservoirShouldAdd1TerraformingScoreIfPlacesOcean() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withTerraformingScore(0);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]);
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        initialState.withUnplacedSlots(unplacedSlots);

        GameState[] finalStates = StandardCards.subterraneanReservoir.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates[0].getPlayerByID(id).getTerraformingScore());
    }

    @Test
    public void subterraneanReservoirShouldGiveTileResourcesSteelPlantsTitanium() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withTerraformingScore(0);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{
                new ResourceBonus(ResourceBonusType.Steel, 1),
                new ResourceBonus(ResourceBonusType.Plants, 2),
                new ResourceBonus(ResourceBonusType.Titanium, 3)
        });
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        initialState.withUnplacedSlots(unplacedSlots);

        Map<ResourceType, Integer> expectedAmounts = TestHelpers.makePlayerProp(0, 1, 3, 0, 2, 0);
        expectedAmounts = Collections.unmodifiableMap(expectedAmounts);

        GameState[] finalStates = StandardCards.subterraneanReservoir.getPlayEffect().apply(initialState, id);

        assertEquals(expectedAmounts, finalStates[0].getPlayerByID(id).getAmounts());
    }

    @Test
    public void subterraneanReservoirShouldGiveTileResourcesCards() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withTerraformingScore(0);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{
                new ResourceBonus(ResourceBonusType.Cards, 2)
        });
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        initialState.withUnplacedSlots(unplacedSlots);
        GameStateBuilder expectedState = new GameStateBuilder(initialState.build());
        List<Card> deck = new ArrayList<>();
        deck.add(StandardCards.trees);
        deck.add(StandardCards.deepWellHeating);
        initialState.withDeck(deck);
        Set<Card> expectedHand = new HashSet<>(deck);
        expectedState.getPlayerByID(id).withHand(expectedHand);
        Tile oceanTile = new Tile(Global.NO_PLAYER, TileType.Ocean);
        Map<TileSlot, Tile> expectedPlacedTiles = new HashMap<>();
        expectedPlacedTiles.put(slot1, oceanTile);
        expectedState.withPlacedTiles(expectedPlacedTiles);
        expectedState.withUnplacedSlots(new HashSet<>());
        expectedState.getPlayerByID(id).withTerraformingScore(1);

        GameState[] finalStates = StandardCards.subterraneanReservoir.getPlayEffect().apply(initialState, id);

        assertEquals(expectedState.build(), finalStates[0]);
    }

    @Test
    public void subterraneanReservoirShouldGiveTileResourcesMCFromAdjacentOceans() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot tile1 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]);
        TileSlot tile2 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]);
        TileSlot tile3 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]);
        TileSlot desertTile = new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]);
        desertTile.addNeighbor(tile1);
        desertTile.addNeighbor(tile2);
        desertTile.addNeighbor(tile3);
        Tile oceanTile = new Tile(Global.NO_PLAYER, TileType.Ocean);
        Set<TileSlot> unplaced = new HashSet<>();
        unplaced.add(desertTile);
        Map<TileSlot, Tile> placed = new HashMap<>();
        placed.put(tile1, oceanTile);
        placed.put(tile2, oceanTile);
        placed.put(tile3, oceanTile);
        initialState.withUnplacedSlots(unplaced).withPlacedTiles(placed);

        GameState[] finalStates = StandardCards.subterraneanReservoir.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(6, (int) finalStates[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
    }
    //endregion

    //region domedCrater tests
    @Test
    public void domedCraterShouldPassIfExactlyEnoughOxygen() {
        GameStateBuilder initialState = new GameStateBuilder().withOxygen(7);
        assertTrue(StandardCards.domedCrater.getRequirement().check(initialState, Global.NO_PLAYER));
    }

    @Test
    public void domedCraterShouldPassIfLessThanRequiredOxygen() {
        GameStateBuilder initialState = new GameStateBuilder().withOxygen(5);
        assertTrue(StandardCards.domedCrater.getRequirement().check(initialState, Global.NO_PLAYER));
    }

    @Test
    public void domedCraterShouldFailIfTooMuchOxygen() {
        GameStateBuilder initialState = new GameStateBuilder().withOxygen(12);
        assertFalse(StandardCards.domedCrater.getRequirement().check(initialState, Global.NO_PLAYER));
    }

    @Test
    public void domedCraterShouldAdd3PlantDecrease1EnergyProductionIncrease3MCProduction() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withOxygen(0).withPlayers(new PlayerBuilder[]{p1});
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        initialState.withUnplacedSlots(unplacedSlots);

        GameState[] finalStates = StandardCards.domedCrater.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(0, (int) finalStates[0].getPlayerByID(id).getProduction().get(ResourceType.Energy));
        assertEquals(4, (int) finalStates[0].getPlayerByID(id).getProduction().get(ResourceType.MegaCredits));
        assertEquals(3, (int) finalStates[0].getPlayerByID(id).getAmounts().get(ResourceType.Plants));
    }

    @Test
    public void domedCraterShouldFailIfNoDesertTiles() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withTerraformingScore(0);
        TileSlot tile1 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        Set<TileSlot> tiles = new HashSet<>();
        tiles.add(tile1);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withPlayers(new PlayerBuilder[]{p1}).withUnplacedSlots(tiles);
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        assertEquals(0, Card.play(StandardCards.domedCrater, initialStateBuilder, id).length);
    }

    @Test
    public void domedCraterShouldPlaceOneCityForEachDesert() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withTerraformingScore(0);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        TileSlot slot2 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        Tile cityTile = new Tile(id, TileType.City);
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        unplacedSlots.add(slot2);
        initialState.withUnplacedSlots(unplacedSlots);

        Map<TileSlot, Tile> expectedPlacedTiles1 = new HashMap<>();
        expectedPlacedTiles1.put(slot1, cityTile);
        Set<TileSlot> expectedUnplacedSlots1 = new HashSet<>();
        expectedUnplacedSlots1.add(slot2);
        PlayerBuilder expectedPlayer = new PlayerBuilder(player.build())
                .changeAmount(ResourceType.Plants, 3)
                .changeProduction(ResourceType.Energy, -1)
                .changeProduction(ResourceType.MegaCredits, 3);
        Map<TileSlot, Tile> expectedPlacedTiles2 = new HashMap<>();
        expectedPlacedTiles2.put(slot2, cityTile);
        Set<TileSlot> expectedUnplacedSlots2 = new HashSet<>();
        expectedUnplacedSlots2.add(slot1);
        GameState[] expectedFinalStates = new GameState[]{
                new GameStateBuilder().withUnplacedSlots(expectedUnplacedSlots1)
                        .withPlacedTiles(expectedPlacedTiles1)
                        .withPlayers(new PlayerBuilder[]{expectedPlayer}).build(),
                new GameStateBuilder().withUnplacedSlots(expectedUnplacedSlots2)
                        .withPlacedTiles(expectedPlacedTiles2)
                        .withPlayers(new PlayerBuilder[]{expectedPlayer}).build()};

        GameState[] finalStates = StandardCards.domedCrater.getPlayEffect().apply(initialState, id);

        assertTrue(expectedFinalStates.length == finalStates.length
                && (expectedFinalStates[0].equals(finalStates[0]) || expectedFinalStates[0].equals(finalStates[1]))
                && (expectedFinalStates[1].equals(finalStates[0]) || expectedFinalStates[0].equals(finalStates[0]))
                && (!finalStates[0].equals(finalStates[1]))
        );
    }

    @Test
    public void domedCraterShouldNotPlaceCityOnTileAdjacentToCity() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        TileSlot slot2 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        TileSlot slot3 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        TileSlot slot4 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        slot1.addNeighbor(slot2);
        slot3.addNeighbor(slot4);
        Tile cityTile = new Tile(id, TileType.City);
        Tile capitalCityTile = new Tile(id, TileType.CapitalCity);
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        unplacedSlots.add(slot1);
        Map<TileSlot, Tile> placed = new HashMap<>();
        placed.put(slot2, cityTile);
        placed.put(slot4, capitalCityTile);
        initialState.withUnplacedSlots(unplacedSlots).withPlacedTiles(placed);

        GameState[] finalStates = StandardCards.domedCrater.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(2, finalStates[0].getPlacedTiles().size());
    }
    //endregion

    //region martianRails tests
    @Test
    public void martianRailsShouldFailIfNoEnergy() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{p1});
        assertEquals(0, StandardCards.martianRails.getActionEffect().apply(initialState, id).length);
    }

    @Test
    public void martianRailsShouldAdd1MCIf1City() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.Energy, 1);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{p1});
        Map<TileSlot, Tile> placed = new HashMap<>();
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        Tile city = new Tile(id, TileType.City);
        placed.put(slot1, city);
        initialState.withPlacedTiles(placed);

        GameState[] results = StandardCards.martianRails.getActionEffect().apply(initialState, id);

        assertEquals(1, results.length);
        assertEquals(1, (int) results[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
    }

    @Test
    public void martianRailsShouldAdd2MCIf1OwnedCityAnd1EnemyCity() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(1);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.Energy, 1);
        PlayerBuilder p2 = new PlayerBuilder().withPlayerID(id2);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{p1, p2});
        Map<TileSlot, Tile> placed = new HashMap<>();
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        TileSlot slot2 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        Tile city1 = new Tile(id, TileType.City);
        Tile city2 = new Tile(id2, TileType.CapitalCity);
        placed.put(slot1, city1);
        placed.put(slot2, city2);
        initialState.withPlacedTiles(placed);

        GameState[] results = StandardCards.martianRails.getActionEffect().apply(initialState, id);

        assertEquals(1, results.length);
        assertEquals(2, (int) results[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
    }
    //endregion

    //region asteroid tests
    @Test
    public void asteroidShouldOnlyReturnOneStateIfNoOpponents() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        assertEquals(1, StandardCards.asteroid.getPlayEffect().apply(initialState, id).length);
    }

    @Test
    public void asteroidShouldReduceOpponentPlantsTo0IfOneOpponentWithLessThan3Plants() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(1);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        PlayerBuilder player2 = new PlayerBuilder().withPlayerID(id2).withAmount(ResourceType.Plants, 2);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player, player2});

        GameState[] results = StandardCards.asteroid.getPlayEffect().apply(initialState, id);
        assertEquals(1, results.length);
        assertEquals(0, (int) results[0].getPlayerByID(id2).getAmounts().get(ResourceType.Plants));
    }

    @Test
    public void asteroidShouldReduceOpponentPlantsBy3IfOneOpponentWithMoreThan3Plants() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(1);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        PlayerBuilder player2 = new PlayerBuilder().withPlayerID(id2).withAmount(ResourceType.Plants, 4);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player, player2});

        GameState[] results = StandardCards.asteroid.getPlayEffect().apply(initialState, id);
        assertEquals(1, results.length);
        assertEquals(1, (int) results[0].getPlayerByID(id2).getAmounts().get(ResourceType.Plants));
    }

    @Test
    public void asteroidShouldReturnOneStateWhereEachOpponentHas3LessPlantsIfTwoOpponents() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(1);
        PlayerID id3 = new PlayerID(2);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        PlayerBuilder player2 = new PlayerBuilder().withPlayerID(id2).withAmount(ResourceType.Plants, 4);
        PlayerBuilder player3 = new PlayerBuilder().withPlayerID(id3).withAmount(ResourceType.Plants, 2);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player, player2, player3});

        GameState[] results = StandardCards.asteroid.getPlayEffect().apply(initialState, id);
        assertEquals(2, results.length);
        assertEquals(1, (int) results[0].getPlayerByID(id2).getAmounts().get(ResourceType.Plants));
        assertEquals(0, (int) results[1].getPlayerByID(id3).getAmounts().get(ResourceType.Plants));
    }

    @Test
    public void asteroidShouldReturnTwoResultsIfPlayerHas1Titanium() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 14).withAmount(ResourceType.Titanium, 1);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);

        GameState[] finalResults = Card.play(StandardCards.asteroid, initialStateBuilder, id);
        assertEquals(2, finalResults.length);
        assertEquals(0, (int) finalResults[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(3, (int) finalResults[0].getPlayerByID(id).getAmounts().get(ResourceType.Titanium));
        assertEquals(3, (int) finalResults[1].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(2, (int) finalResults[1].getPlayerByID(id).getAmounts().get(ResourceType.Titanium));
    }

    @Test
    public void asteroidShouldReturnThreeResultsIfPlayerHas2Titanium() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 14).withAmount(ResourceType.Titanium, 2);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);

        GameState[] finalResults = Card.play(StandardCards.asteroid, initialStateBuilder, id);
        assertEquals(3, finalResults.length);
        assertEquals(0, (int) finalResults[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(4, (int) finalResults[0].getPlayerByID(id).getAmounts().get(ResourceType.Titanium));
        assertEquals(3, (int) finalResults[1].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(3, (int) finalResults[1].getPlayerByID(id).getAmounts().get(ResourceType.Titanium));
        assertEquals(6, (int) finalResults[2].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(2, (int) finalResults[2].getPlayerByID(id).getAmounts().get(ResourceType.Titanium));
    }

    @Test
    public void asteroidShouldReturnTwoResultsIfPlayerHas2TitaniumAnd11MC() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 11).withAmount(ResourceType.Titanium, 2);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withPlayers(new PlayerBuilder[]{p1});
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);

        GameState[] finalResults = Card.play(StandardCards.asteroid, initialStateBuilder, id);
        assertEquals(2, finalResults.length);
        assertEquals(0, (int) finalResults[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(3, (int) finalResults[0].getPlayerByID(id).getAmounts().get(ResourceType.Titanium));
        assertEquals(3, (int) finalResults[1].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(2, (int) finalResults[1].getPlayerByID(id).getAmounts().get(ResourceType.Titanium));
    }
    //endregion

    //region searchForLife tests
    @Test
    public void searchForLifeShouldNotAddCounterIfTopCardOfDeckDoesNotHaveMicrobeTag() {
        PlayerID id = new PlayerID(0);
        CardStateBuilder[] tableau = new CardStateBuilder[]{new CardStateBuilder().withCard(StandardCards.searchForLife)};
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withTableau(tableau).withAmount(ResourceType.MegaCredits, 1);
        List<Card> deck = new ArrayList<>();
        deck.add(StandardCards.trees);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player}).withDeck(deck);

        GameState[] results = StandardCards.searchForLife.getActionEffect().apply(initialState, id);
        assertEquals(1, results.length);
        assertEquals(0, results[0].getPlayerByID(id).findCard("Search For Life").getCounters());
    }

    @Test
    public void searchForLifeShouldAddCounterIfTopCardOfDeckHasMicrobeTag() {
        PlayerID id = new PlayerID(0);
        Card card = TestHelpers.makeTestCardWithTags(new CardTag[]{CardTag.Microbes}, "card");
        CardStateBuilder[] tableau = new CardStateBuilder[]{new CardStateBuilder().withCard(StandardCards.searchForLife)};
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withTableau(tableau).withAmount(ResourceType.MegaCredits, 1);
        List<Card> deck = new ArrayList<>();
        deck.add(card);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player}).withDeck(deck);

        GameState[] results = StandardCards.searchForLife.getActionEffect().apply(initialState, id);
        assertEquals(1, results.length);
        assertEquals(1, results[0].getPlayerByID(id).findCard("Search For Life").getCounters());
    }

    @Test
    public void searchForLifeShouldScore0PointsIfNoCounter() {
        PlayerID id = new PlayerID(0);
        CardStateBuilder[] tableau = new CardStateBuilder[]{new CardStateBuilder().withCard(StandardCards.searchForLife)};
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withTableau(tableau);
        GameState initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player}).build();
        assertEquals(0, StandardCards.searchForLife.getScorer().score(initialState, id));
    }

    @Test
    public void searchForLifeShouldScore3PointsIfHasCounter() {
        PlayerID id = new PlayerID(0);
        CardStateBuilder[] tableau = new CardStateBuilder[]{new CardStateBuilder().withCard(StandardCards.searchForLife).withCounters(1)};
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withTableau(tableau);
        GameState initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player}).build();
        assertEquals(3, StandardCards.searchForLife.getScorer().score(initialState, id));
    }
    //endregion

    //region capitalCity tests
    @Test
    public void capitalShouldPlaceCityOnDesert() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withProduction(ResourceType.Energy, 2);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        Tile capitalCityTile = new Tile(id, TileType.CapitalCity);
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        initialState.withUnplacedSlots(unplacedSlots);

        Map<TileSlot, Tile> expectedPlacedTiles = new HashMap<>();
        expectedPlacedTiles.put(slot1, capitalCityTile);

        GameState[] finalStates = StandardCards.capital.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(finalStates[0].getPlacedTiles(), expectedPlacedTiles);
    }

    @Test
    public void capitalShouldNotPlaceCityOnTileAdjacentToCity() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        TileSlot slot2 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        TileSlot slot3 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        TileSlot slot4 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        slot1.addNeighbor(slot2);
        slot3.addNeighbor(slot4);
        Tile cityTile = new Tile(id, TileType.City);
        Tile capitalCityTile = new Tile(id, TileType.CapitalCity);
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        unplacedSlots.add(slot1);
        Map<TileSlot, Tile> placed = new HashMap<>();
        placed.put(slot2, cityTile);
        placed.put(slot4, capitalCityTile);
        initialState.withUnplacedSlots(unplacedSlots).withPlacedTiles(placed);

        GameState[] finalStates = StandardCards.capital.getPlayEffect().apply(initialState, id);

        assertEquals(0, finalStates.length);
    }
    //endregion

    //region waterImportFromEuropa tests
    @Test
    public void waterImportFromEuropaShouldFailIfLessThan12MCAndNoTitanium() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 11);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});

        assertEquals(0, StandardCards.waterImportFromEuropa.getActionEffect().apply(initialState, id).length);
    }

    @Test
    public void waterImportFromEuropaShouldUseTitanium() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 12).withAmount(ResourceType.Titanium, 1);
        Set<TileSlot> unplaced = new HashSet<>();
        TileSlot slot1 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]);
        unplaced.add(slot1);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player}).withUnplacedSlots(unplaced);

        GameState[] finalResults = StandardCards.waterImportFromEuropa.getActionEffect().apply(initialState, id);
        assertEquals(2, finalResults.length);
        assertEquals(0, (int) finalResults[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(1, (int) finalResults[0].getPlayerByID(id).getAmounts().get(ResourceType.Titanium));
        assertEquals(3, (int) finalResults[1].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(0, (int) finalResults[1].getPlayerByID(id).getAmounts().get(ResourceType.Titanium));
    }

    @Test
    public void waterImportFromEuropaShouldScore1If1JovianTag() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 12).withAmount(ResourceType.Titanium, 1);
        Set<TileSlot> unplaced = new HashSet<>();
        TileSlot slot1 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]);
        unplaced.add(slot1);
        Card card1 = TestHelpers.makeTestCardWithTags(new CardTag[]{CardTag.Jovian}, "card1");
        CardStateBuilder cardState1 = new CardStateBuilder().withCard(card1);
        CardStateBuilder[] tableau = new CardStateBuilder[]{cardState1};
        player.withTableau(tableau);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player}).withUnplacedSlots(unplaced);

        assertEquals(1, StandardCards.waterImportFromEuropa.getScorer().score(initialState.build(), id));
    }

    @Test
    public void waterImportFromEuropaShouldScore2If2JovianTags() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 12).withAmount(ResourceType.Titanium, 1);
        Set<TileSlot> unplaced = new HashSet<>();
        TileSlot slot1 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]);
        unplaced.add(slot1);
        Card card1 = TestHelpers.makeTestCardWithTags(new CardTag[]{CardTag.Jovian}, "card1");
        CardStateBuilder cardState1 = new CardStateBuilder().withCard(card1);
        CardStateBuilder cardState2 = new CardStateBuilder().withCard(card1);
        CardStateBuilder[] tableau = new CardStateBuilder[]{cardState1, cardState2};
        player.withTableau(tableau);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player}).withUnplacedSlots(unplaced);

        assertEquals(2, StandardCards.waterImportFromEuropa.getScorer().score(initialState.build(), id));
    }
    //endregion

    //region equatorialMagnetizer tests
    @Test
    public void equatorialMagnetizerShouldRaiseTerraformingScoreBy1AndDecreaseEnergyBy1() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});

        GameState[] finalStates = StandardCards.equatorialMagnetizer.getActionEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(1, finalStates[0].getPlayerByID(id).getTerraformingScore());
        assertEquals(0, (int) finalStates[0].getPlayerByID(id).getProduction().get(ResourceType.Energy));
    }
    //endregion

    //region noctisCity tests
    @Test
    public void noctisCityShouldPlaceCityOnNoctisSlot() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withProduction(ResourceType.Energy, 2);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Noctis, new ResourceBonus[0]);
        Tile cityTile = new Tile(id, TileType.City);
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        initialState.withUnplacedSlots(unplacedSlots);

        Map<TileSlot, Tile> expectedPlacedTiles = new HashMap<>();
        expectedPlacedTiles.put(slot1, cityTile);

        GameState[] finalStates = StandardCards.noctisCity.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(finalStates[0].getPlacedTiles(), expectedPlacedTiles);
    }

    @Test
    public void noctisCityShouldPlaceCityOnNoctisSlotEvenIfAdjacentToCity() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withProduction(ResourceType.Energy, 2);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Noctis, new ResourceBonus[0]);
        TileSlot slot2 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        slot1.addNeighbor(slot2);
        Tile cityTile = new Tile(id, TileType.City);
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        Map<TileSlot, Tile> placedTiles = new HashMap<>();
        placedTiles.put(slot2, cityTile);
        initialState.withUnplacedSlots(unplacedSlots).withPlacedTiles(placedTiles);

        Map<TileSlot, Tile> expectedPlacedTiles = new HashMap<>();
        expectedPlacedTiles.put(slot1, cityTile);
        expectedPlacedTiles.put(slot2, cityTile);

        GameState[] finalStates = StandardCards.noctisCity.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(finalStates[0].getPlacedTiles(), expectedPlacedTiles);
    }

    @Test
    public void noctisCityShouldNotPlaceCityOnNonNoctisSlots() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withProduction(ResourceType.Energy, 2);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Noctis, new ResourceBonus[0]);
        TileSlot slot2 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        TileSlot slot3 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]);
        Tile cityTile = new Tile(id, TileType.City);
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        unplacedSlots.add(slot2);
        unplacedSlots.add(slot3);
        initialState.withUnplacedSlots(unplacedSlots);

        Map<TileSlot, Tile> expectedPlacedTiles = new HashMap<>();
        expectedPlacedTiles.put(slot1, cityTile);

        GameState[] finalStates = StandardCards.noctisCity.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(finalStates[0].getPlacedTiles(), expectedPlacedTiles);
    }
    //endregion

    //region importedHydrogen tests
    @Test
    public void importedHydrogenShouldAdd3PlantsIfNoCardStatesWithUsesCountersAndNoOceanTiles() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});

        GameState[] finalStates = StandardCards.importedHydrogen.getPlayEffect().apply(initialState, id);
        assertEquals(3, finalStates.length);
        assertEquals(3, (int) finalStates[0].getPlayerByID(id).getAmounts().get(ResourceType.Plants));
    }

    @Test
    public void importedHydrogenShouldAdd3Plants3MicrobesAnd2AnimalsIfAppropriateCardStatesAndNoOceanTiles() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        Card card1 = TestHelpers.makeTestCardWithTags(new CardTag[]{CardTag.Microbes, CardTag.UsesCounters}, "card1");
        Card card2 = TestHelpers.makeTestCardWithTags(new CardTag[]{CardTag.Animals, CardTag.UsesCounters}, "card2");
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(card1),
                new CardStateBuilder().withCard(card2)
        };
        player.withTableau(tableau);

        GameState[] finalStates = StandardCards.importedHydrogen.getPlayEffect().apply(initialState, id);
        assertEquals(3, finalStates.length);
        assertEquals(3, (int) finalStates[0].getPlayerByID(id).getAmounts().get(ResourceType.Plants));
        assertEquals(3, finalStates[1].getPlayerByID(id).findCard("card1").getCounters());
        assertEquals(2, finalStates[2].getPlayerByID(id).findCard("card2").getCounters());
    }

    @Test
    public void importedHydrogenShouldReturn6StatesWith2CardsWithCountersAnd2OceanSlots() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        Card card1 = TestHelpers.makeTestCardWithTags(new CardTag[]{CardTag.Microbes, CardTag.UsesCounters}, "card1");
        Card card2 = TestHelpers.makeTestCardWithTags(new CardTag[]{CardTag.Animals, CardTag.UsesCounters}, "card2");
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(card1),
                new CardStateBuilder().withCard(card2)
        };
        player.withTableau(tableau);
        TileSlot slot1 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]);
        TileSlot slot2 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]);
        Set<TileSlot> unplaced = new HashSet<>();
        unplaced.add(slot1);
        unplaced.add(slot2);
        initialState.withUnplacedSlots(unplaced);

        GameState[] finalStates = StandardCards.importedHydrogen.getPlayEffect().apply(initialState, id);
        assertEquals(6, finalStates.length);
        for (GameState state : finalStates) {
            assertEquals(1, state.getPlacedTiles().size());
            assertTrue(state.getPlayerByID(id).getAmounts().get(ResourceType.Plants) +
                    state.getPlayerByID(id).findCard("card1").getCounters() +
                    state.getPlayerByID(id).findCard("card2").getCounters() <= 3);
        }
    }
    //endregion

    //region researchOutpost tests
    @Test
    public void researchOutpostReducerShouldReduceCostBy1() {
        assertEquals(9, StandardCards.researchOutpost.getReducer().reduceCost(new CardTag[0], false, 10));
        assertEquals(12, StandardCards.researchOutpost.getReducer().reduceCost(new CardTag[]{CardTag.Microbes}, false, 13));
    }

    @Test
    public void researchOutpostShouldReduceCostOfCardsBy1() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 10);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        Card card1 = TestHelpers.makeTestCardWithCost(10, "card1");
        CardStateBuilder researchOutpost = new CardStateBuilder().withCard(StandardCards.researchOutpost);
        player.withTableau(new CardStateBuilder[]{researchOutpost});

        GameState[] finalStates = Card.play(card1, initialState, id);
        assertEquals(1, finalStates.length);
        assertEquals(1, (int) finalStates[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
    }

    @Test
    public void researchOutpostShouldEachReduceCostOfCardsBy1IfTwo() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 10);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        Card card1 = TestHelpers.makeTestCardWithCost(10, "card1");
        CardStateBuilder researchOutpost1 = new CardStateBuilder().withCard(StandardCards.researchOutpost);
        CardStateBuilder researchOutpost2 = new CardStateBuilder().withCard(StandardCards.researchOutpost);
        player.withTableau(new CardStateBuilder[]{researchOutpost1, researchOutpost2});

        GameState[] finalStates = Card.play(card1, initialState, id);
        assertEquals(1, finalStates.length);
        assertEquals(2, (int) finalStates[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
    }
    //endregion

    //region roverConstruction tests
    @Test
    public void roverConstructionShouldNotIncreaseMCIfNoCityPlaced() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.roverConstruction)
        };
        initialState.getPlayerByID(id).withTableau(tableau);

        GameState[] finalStates = new PlaceCityTileEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(0, (int) finalStates[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
    }

    @Test
    public void roverConstructionShouldIncreaseMCBy2IfCityPlaced() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.roverConstruction)
        };
        initialState.getPlayerByID(id).withTableau(tableau);
        Set<TileSlot> unplaced = new HashSet<>();
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        unplaced.add(slot1);
        initialState.withUnplacedSlots(unplaced);

        GameState[] finalStates = new PlaceCityTileEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(2, (int) finalStates[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
    }

    @Test
    public void roverConstructionShouldIncreaseMCBy2IfCapitalCityPlaced() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.roverConstruction)
        };
        initialState.getPlayerByID(id).withTableau(tableau);
        Set<TileSlot> unplaced = new HashSet<>();
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        unplaced.add(slot1);
        initialState.withUnplacedSlots(unplaced);

        GameState[] finalStates = new PlaceCapitalCityTileEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(2, (int) finalStates[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
    }

    @Test
    public void roverConstructionShouldIncreaseMCBy2IfOffBoardCityPlaced() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.roverConstruction)
        };
        initialState.getPlayerByID(id).withTableau(tableau);

        GameState[] finalStates = new PlaceOffBoardCityEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(2, (int) finalStates[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
    }

    @Test
    public void roverConstructionShouldIncreaseMCBy2IfOpponentCityPlaced() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(1);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        PlayerBuilder player2 = new PlayerBuilder().withPlayerID(id2);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player, player2});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.roverConstruction)
        };
        player.withTableau(tableau);
        Set<TileSlot> unplaced = new HashSet<>();
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        unplaced.add(slot1);
        initialState.withUnplacedSlots(unplaced);

        GameState[] finalStates = new PlaceCapitalCityTileEffect().apply(initialState, id2);

        assertEquals(1, finalStates.length);
        assertEquals(2, (int) finalStates[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
    }

    @Test
    public void roverConstructionShouldEachIncreaseMCBy2IfCityPlacedIfTwo() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.roverConstruction),
                new CardStateBuilder().withCard(StandardCards.roverConstruction)
        };
        initialState.getPlayerByID(id).withTableau(tableau);
        Set<TileSlot> unplaced = new HashSet<>();
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        unplaced.add(slot1);
        initialState.withUnplacedSlots(unplaced);

        GameState[] finalStates = new PlaceCityTileEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(4, (int) finalStates[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
    }
    //endregion

    //region phobosSpaceHaven tests
    @Test
    public void phobosSpaceHavenShouldPlaceCityTileWithoutUsingSlot() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});

        GameState[] finalStates = StandardCards.phobosSpaceHaven.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(1, finalStates[0].getPlacedTiles().size());
    }
    //endregion

    //region adaptationTechnology tests
    @Test
    public void adaptationTechnologyShouldAllowCardToBePlayedAt2LowerTemperature() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player}).withTemperature(3);
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.adaptationTechnology)
        };
        initialState.getPlayerByID(id).withTableau(tableau);
        Card card1 = TestHelpers.makeTestCardWithReq(new TemperatureRequirement(5, true), "card1");

        assertEquals(1, Card.play(card1, initialState, id).length);
    }

    @Test
    public void adaptationTechnologyShouldAllowCardToBePlayedAt2HigherTemperature() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player}).withTemperature(7);
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.adaptationTechnology)
        };
        initialState.getPlayerByID(id).withTableau(tableau);
        Card card1 = TestHelpers.makeTestCardWithReq(new TemperatureRequirement(5, false), "card1");

        assertEquals(1, Card.play(card1, initialState, id).length);
    }

    @Test
    public void adaptationTechnologyShouldAllowCardToBePlayedAt2LowerOxygen() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player}).withOxygen(3);
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.adaptationTechnology)
        };
        initialState.getPlayerByID(id).withTableau(tableau);
        Card card1 = TestHelpers.makeTestCardWithReq(new OxygenRequirement(5, true), "card1");

        assertEquals(1, Card.play(card1, initialState, id).length);
    }

    @Test
    public void adaptationTechnologyShouldAllowCardToBePlayedAt2HigherOxygen() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player}).withOxygen(7);
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.adaptationTechnology)
        };
        initialState.getPlayerByID(id).withTableau(tableau);
        Card card1 = TestHelpers.makeTestCardWithReq(new OxygenRequirement(5, false), "card1");

        assertEquals(1, Card.play(card1, initialState, id).length);
    }

    @Test
    public void adaptationTechnologyShouldAllowCardToBePlayedWith2FewerOceans() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.adaptationTechnology)
        };
        initialState.getPlayerByID(id).withTableau(tableau);
        Map<TileSlot, Tile> placed = new HashMap<>();
        Tile oceanTile = new Tile(Global.NO_PLAYER, TileType.Ocean);
        placed.put(new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]), oceanTile);
        placed.put(new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]), oceanTile);
        initialState.withPlacedTiles(placed);
        Card card1 = TestHelpers.makeTestCardWithReq(new OceanRequirement(4, true), "card1");

        assertEquals(1, Card.play(card1, initialState, id).length);
    }

    @Test
    public void adaptationTechnologyShouldAllowCardToBePlayedWith2MoreOceans() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.adaptationTechnology)
        };
        initialState.getPlayerByID(id).withTableau(tableau);
        Map<TileSlot, Tile> placed = new HashMap<>();
        Tile oceanTile = new Tile(Global.NO_PLAYER, TileType.Ocean);
        placed.put(new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]), oceanTile);
        placed.put(new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]), oceanTile);
        initialState.withPlacedTiles(placed);
        Card card1 = TestHelpers.makeTestCardWithReq(new OceanRequirement(0, false), "card1");

        assertEquals(1, Card.play(card1, initialState, id).length);
    }

    @Test
    public void adaptationTechnologyShouldAllowCardToBePlayedAt2LowerTemperatureEachIfTwo() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player}).withTemperature(3);
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.adaptationTechnology),
                new CardStateBuilder().withCard(StandardCards.adaptationTechnology)
        };
        initialState.getPlayerByID(id).withTableau(tableau);
        Card card1 = TestHelpers.makeTestCardWithReq(new TemperatureRequirement(7, true), "card1");

        assertEquals(1, Card.play(card1, initialState, id).length);
    }
    //endregion

    //region moholeArea tests
    @Test
    public void moholeAreaShouldPlaceTileOnOcean() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]);
        Tile oceanTile = new Tile(id, TileType.Special);
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        initialState.withUnplacedSlots(unplacedSlots);

        Map<TileSlot, Tile> expectedPlacedTiles = new HashMap<>();
        expectedPlacedTiles.put(slot1, oceanTile);

        GameState[] finalStates = StandardCards.moholeArea.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(finalStates[0].getPlacedTiles(), expectedPlacedTiles);
    }

    @Test
    public void moholeAreaShouldFailIfNoOceanTiles() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withTerraformingScore(0);
        TileSlot tile1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        Set<TileSlot> tiles = new HashSet<>();
        tiles.add(tile1);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withPlayers(new PlayerBuilder[]{p1}).withUnplacedSlots(tiles);
        GameState initialState = initialStateBuilder.build();
        initialStateBuilder = new GameStateBuilder(initialState);
        assertEquals(0, Card.play(StandardCards.moholeArea, initialStateBuilder, id).length);
    }
    //endregion

    //region optimalAerobraking tests
    @Test
    public void optimalAerobrakingShouldGive3MCAnd3HeatWhenSpaceEventPlayed() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.optimalAerobraking)
        };
        initialState.getPlayerByID(id).withTableau(tableau);
        Card card1 = TestHelpers.makeTestCardWithTags(new CardTag[]{CardTag.Space, CardTag.Event}, "card1");

        GameState[] finalResults = Card.play(card1, initialState, id);

        assertEquals(1, finalResults.length);
        assertEquals(3, (int) finalResults[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(3, (int) finalResults[0].getPlayerByID(id).getAmounts().get(ResourceType.Heat));
    }

    @Test
    public void optimalAerobrakingShouldGive3MCAnd3HeatEachWhenSpaceEventPlayedIfTwo() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.optimalAerobraking),
                new CardStateBuilder().withCard(StandardCards.optimalAerobraking)
        };
        initialState.getPlayerByID(id).withTableau(tableau);
        Card card1 = TestHelpers.makeTestCardWithTags(new CardTag[]{CardTag.Space, CardTag.Event}, "card1");

        GameState[] finalResults = Card.play(card1, initialState, id);

        assertEquals(1, finalResults.length);
        assertEquals(6, (int) finalResults[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(6, (int) finalResults[0].getPlayerByID(id).getAmounts().get(ResourceType.Heat));
    }
    //endregion

    //region predators tests
    @Test
    public void predatorsShouldFailIfNoCards() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});

        assertEquals(0, StandardCards.predators.getActionEffect().apply(initialState, id).length);
    }

    @Test
    public void predatorsShouldFailIfNoOtherCards() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.predators).withCounters(3)
        };
        initialState.getPlayerByID(id).withTableau(tableau);

        assertEquals(0, StandardCards.predators.getActionEffect().apply(initialState, id).length);
    }

    @Test
    public void predatorsShouldFailIfNoOtherCardsWithAnimalCounters() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.predators).withCounters(3),
                new CardStateBuilder().withCard(StandardCards.searchForLife).withCounters(1)
        };
        player.withTableau(tableau);

        assertEquals(0, StandardCards.predators.getActionEffect().apply(initialState, id).length);
    }

    @Test
    public void predatorsShouldSteal1FailIfNoCardsWithUsesCounters() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(1);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        PlayerBuilder player2 = new PlayerBuilder().withPlayerID(id2);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player, player2});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.predators).withCounters(3)
        };
        CardStateBuilder[] tableau2 = new CardStateBuilder[]{
                new CardStateBuilder().withCard(TestHelpers.makeTestCardWithTags(new CardTag[]{CardTag.Animals}, "card1"))
        };
        player.withTableau(tableau);
        player2.withTableau(tableau2);

        GameState[] finalStates = StandardCards.predators.getActionEffect().apply(initialState, id);

        assertEquals(0, finalStates.length);
    }

    @Test
    public void predatorsShouldSteal1CounterFromOpponentsCardWithAnimalCountersAndUsesCounters() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(1);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        PlayerBuilder player2 = new PlayerBuilder().withPlayerID(id2);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player, player2});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.predators).withCounters(3)
        };
        CardStateBuilder[] tableau2 = new CardStateBuilder[]{
                new CardStateBuilder().withCard(TestHelpers.makeTestCardWithTags(new CardTag[]{CardTag.Animals, CardTag.UsesCounters}, "card1")).withCounters(3)
        };
        player.withTableau(tableau);
        player2.withTableau(tableau2);

        GameState[] finalStates = StandardCards.predators.getActionEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(4, finalStates[0].getPlayerByID(id).findCard("Predators").getCounters());
        assertEquals(2, finalStates[0].getPlayerByID(id2).findCard("card1").getCounters());
    }

    @Test
    public void predatorsShouldSteal1CounterFromEachCardWithAnimalCountersAndUsesCounters() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(1);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        PlayerBuilder player2 = new PlayerBuilder().withPlayerID(id2);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player, player2});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.predators).withCounters(3),
                new CardStateBuilder().withCard(TestHelpers.makeTestCardWithTags(new CardTag[]{CardTag.Animals, CardTag.UsesCounters}, "card2")).withCounters(3)
        };
        CardStateBuilder[] tableau2 = new CardStateBuilder[]{
                new CardStateBuilder().withCard(TestHelpers.makeTestCardWithTags(new CardTag[]{CardTag.Animals, CardTag.UsesCounters}, "card1")).withCounters(3)
        };
        player.withTableau(tableau);
        player2.withTableau(tableau2);

        GameState[] finalStates = StandardCards.predators.getActionEffect().apply(initialState, id);

        assertEquals(2, finalStates.length);
    }

    @Test
    public void predatorsShouldScore1PerCounter() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.predators).withCounters(3)
        };
        player.withTableau(tableau);

        assertEquals(3, StandardCards.predators.getScorer().score(initialState.build(), id));
    }

    @Test
    public void predatorsShouldFailIfOnlyCardIsPets() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(1);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        PlayerBuilder player2 = new PlayerBuilder().withPlayerID(id2);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player, player2});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.predators).withCounters(3)
        };
        CardStateBuilder[] tableau2 = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.pets).withCounters(3)
        };
        player.withTableau(tableau);
        player2.withTableau(tableau2);

        GameState[] finalStates = StandardCards.predators.getActionEffect().apply(initialState, id);

        assertEquals(0, finalStates.length);
    }
    //endregion

    //region arcticAlgae tests
    @Test
    public void arcticAlgaeShouldAdd2PlantsWhenOceanPlayed() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 11);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.arcticAlgae)
        };
        initialState.getPlayerByID(id).withTableau(tableau);
        Set<TileSlot> unplaced = new HashSet<>();
        unplaced.add(new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]));
        initialState.withUnplacedSlots(unplaced);

        GameState[] finalStates = Card.play(StandardCards.subterraneanReservoir, initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(2, (int) finalStates[0].getPlayerByID(id).getAmounts().get(ResourceType.Plants));
    }

    @Test
    public void arcticAlgaeShouldAdd2PlantsWhenOpponentOceanPlayed() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(2);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        PlayerBuilder player2 = new PlayerBuilder().withPlayerID(id2).withAmount(ResourceType.MegaCredits, 11);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player, player2});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.arcticAlgae)
        };
        player.withTableau(tableau);
        Set<TileSlot> unplaced = new HashSet<>();
        unplaced.add(new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]));
        initialState.withUnplacedSlots(unplaced);

        GameState[] finalStates = Card.play(StandardCards.subterraneanReservoir, initialState, id2);

        assertEquals(1, finalStates.length);
        assertEquals(2, (int) finalStates[0].getPlayerByID(id).getAmounts().get(ResourceType.Plants));
    }

    @Test
    public void arcticAlgaeShouldAdd2PlantsEachWhenOceanPlayedIfTwo() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 11);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.arcticAlgae),
                new CardStateBuilder().withCard(StandardCards.arcticAlgae)
        };
        initialState.getPlayerByID(id).withTableau(tableau);
        Set<TileSlot> unplaced = new HashSet<>();
        unplaced.add(new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]));
        initialState.withUnplacedSlots(unplaced);

        GameState[] finalStates = Card.play(StandardCards.subterraneanReservoir, initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(4, (int) finalStates[0].getPlayerByID(id).getAmounts().get(ResourceType.Plants));
    }
    //endregion

    //region regolithEaters tests
    @Test
    public void regolithEatersPrimaryShouldAdd1Counter() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});

        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.regolithEaters)
        };
        player.withTableau(tableau);

        GameState[] finalStates = StandardCards.regolithEaters.getActionEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(1, finalStates[0].getPlayerByID(id).findCard("Regolith Eaters").getCounters());
    }

    @Test
    public void regolithEatersSecondaryShouldRemove2CountersAndRaiseOxygen() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});

        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.regolithEaters).withCounters(2)
        };
        player.withTableau(tableau);

        GameState[] finalStates = StandardCards.regolithEaters.getActionEffect().apply(initialState, id);

        assertEquals(2, finalStates.length);
        assertEquals(3, finalStates[0].getPlayerByID(id).findCard("Regolith Eaters").getCounters());
        assertEquals(0, finalStates[1].getPlayerByID(id).findCard("Regolith Eaters").getCounters());
        assertEquals(1, finalStates[1].getOxygen());
        assertEquals(1, finalStates[1].getPlayerByID(id).getTerraformingScore());
    }

    @Test
    public void regolithEatersSecondaryShouldNotRaiseTerraformingScoreIfDoesntRaiseOxygen() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player}).withOxygen(13);

        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.regolithEaters).withCounters(2)
        };
        player.withTableau(tableau);

        GameState[] finalStates = StandardCards.regolithEaters.getActionEffect().apply(initialState, id);

        assertEquals(2, finalStates.length);
        assertEquals(3, finalStates[0].getPlayerByID(id).findCard("Regolith Eaters").getCounters());
        assertEquals(0, finalStates[1].getPlayerByID(id).findCard("Regolith Eaters").getCounters());
        assertEquals(13, finalStates[1].getOxygen());
        assertEquals(0, finalStates[1].getPlayerByID(id).getTerraformingScore());
    }
    //endregion

    //region nitrogenRichAsteroid tests
    @Test
    public void nitrogenRichAsteroidShouldAdd1PlantProductionIfNoPlantTags() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});

        GameState[] finalStates = StandardCards.nitrogenRichAsteroid.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(2, (int) finalStates[0].getPlayerByID(id).getProduction().get(ResourceType.Plants));
    }

    @Test
    public void nitrogenRichAsteroidShouldAdd4PlantProductionIf3PlantTags() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.trees),
                new CardStateBuilder().withCard(StandardCards.trees),
                new CardStateBuilder().withCard(StandardCards.trees)
        };
        player.withTableau(tableau);

        GameState[] finalStates = StandardCards.nitrogenRichAsteroid.getPlayEffect().apply(initialState, id);

        assertEquals(2, finalStates.length);
        assertEquals(2, (int) finalStates[0].getPlayerByID(id).getProduction().get(ResourceType.Plants));
        assertEquals(5, (int) finalStates[1].getPlayerByID(id).getProduction().get(ResourceType.Plants));
    }
    //endregion

    //region naturalPreserve tests
    @Test
    public void naturalPreserveShouldPlaceTileOnDesert() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        Set<TileSlot> unplaced = new HashSet<>();
        unplaced.add(slot1);
        initialState.withUnplacedSlots(unplaced);

        GameState[] finalStates = StandardCards.naturalPreserve.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(1, finalStates[0].getPlacedTiles().size());
    }

    @Test
    public void naturalPreserveShouldNotPlaceTileIfAdjacentToOtherTile() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        TileSlot slot2 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        TileSlot slot3 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        TileSlot slot4 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        TileSlot slot12 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        TileSlot slot22 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        TileSlot slot32 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        TileSlot slot42 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        slot1.addNeighbor(slot12);
        slot2.addNeighbor(slot22);
        slot3.addNeighbor(slot32);
        slot4.addNeighbor(slot42);
        Set<TileSlot> unplaced = new HashSet<>();
        unplaced.add(slot1);
        unplaced.add(slot2);
        unplaced.add(slot3);
        unplaced.add(slot4);
        initialState.withUnplacedSlots(unplaced);
        Map<TileSlot, Tile> placed = new HashMap<>();
        placed.put(slot12, new Tile(Global.NO_PLAYER, TileType.Ocean));
        placed.put(slot22, new Tile(id, TileType.City));
        placed.put(slot32, new Tile(id, TileType.Plants));
        placed.put(slot42, new Tile(id, TileType.Special));
        initialState.withPlacedTiles(placed);

        GameState[] finalStates = StandardCards.naturalPreserve.getPlayEffect().apply(new GameStateBuilder(initialState.build()), id);

        assertEquals(1, finalStates.length);
    }
    //endregion

    //region fish tests
    @Test
    public void fishShouldReduce1OpponentPlantProduction() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(1);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        PlayerBuilder player2 = new PlayerBuilder().withPlayerID(id2);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player, player2});

        GameState[] finalStates = StandardCards.fish.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(0, (int) finalStates[0].getPlayerByID(id2).getProduction().get(ResourceType.Plants));
    }

    @Test
    public void fishShouldSucceedEvenIfNoOpponent() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});

        GameState[] finalStates = StandardCards.fish.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
    }
    //endregion

    //region beamFromAThoriumAsteroid tests
    @Test
    public void beamFromAThoriumAsteroidShouldFailIfNoJovianTags() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});

        assertEquals(0, Card.play(StandardCards.beamFromAThoriumAsteroid, initialState, id).length);
    }

    @Test
    public void beamFromAThoriumAsteroidShouldPassIfJovianTag() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 32);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(TestHelpers.makeTestCardWithTags(new CardTag[]{CardTag.Jovian}, "card1"))
        };
        player.withTableau(tableau);

        assertEquals(1, Card.play(StandardCards.beamFromAThoriumAsteroid, initialState, id).length);
    }
    //endregion

    //region mangrove tests
    @Test
    public void mangroveShouldPlacePlantsOnOceanTile() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[0]);
        Tile oceanTile = new Tile(id, TileType.Plants);
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        initialState.withUnplacedSlots(unplacedSlots);

        Map<TileSlot, Tile> expectedPlacedTiles = new HashMap<>();
        expectedPlacedTiles.put(slot1, oceanTile);

        GameState[] finalStates = StandardCards.mangrove.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(finalStates[0].getPlacedTiles(), expectedPlacedTiles);
    }
    //endregion

    //region miningRights tests
    @Test
    public void miningRightsShouldFailIfNoResourceBonuses() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        initialState.withUnplacedSlots(unplacedSlots);

        GameState[] finalStates = StandardCards.miningRights.getPlayEffect().apply(initialState, id);

        assertEquals(0, finalStates.length);
    }

    @Test
    public void miningRightsShouldAdd1SteelProductionWhenPlacedOnSteelBonus() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{new ResourceBonus(ResourceBonusType.Steel, 1)});
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        initialState.withUnplacedSlots(unplacedSlots);

        GameState[] finalStates = StandardCards.miningRights.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(2, (int) finalStates[0].getPlayerByID(id).getProduction().get(ResourceType.Steel));
    }

    @Test
    public void miningRightsShouldAdd1TitaniumProductionWhenPlacedOnTitaniumBonus() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{new ResourceBonus(ResourceBonusType.Titanium, 1)});
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        initialState.withUnplacedSlots(unplacedSlots);

        GameState[] finalStates = StandardCards.miningRights.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(2, (int) finalStates[0].getPlayerByID(id).getProduction().get(ResourceType.Titanium));
    }
    //endregion

    //region powerGrid tests
    @Test
    public void powerGridShouldAdd1EnergyProductionWithNoOtherCards() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});

        GameState[] finalStates = StandardCards.powerGrid.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(2, (int) finalStates[0].getPlayerByID(id).getProduction().get(ResourceType.Energy));
    }

    @Test
    public void powerGridShouldAdd3EnergyProductionWith2OtherEnergyTags() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.powerGrid)
        };
        player.withTableau(tableau);

        GameState[] finalStates = StandardCards.powerGrid.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(3, (int) finalStates[0].getPlayerByID(id).getProduction().get(ResourceType.Energy));
    }
    //endregion

    //region artificialLake tests
    @Test
    public void artificialLakeShouldPlaceOceanOnDesertSlot() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        Tile oceanTile = new Tile(Global.NO_PLAYER, TileType.Ocean);
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        initialState.withUnplacedSlots(unplacedSlots);

        Map<TileSlot, Tile> expectedPlacedTiles = new HashMap<>();
        expectedPlacedTiles.put(slot1, oceanTile);

        GameState[] finalStates = StandardCards.artificialLake.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(finalStates[0].getPlacedTiles(), expectedPlacedTiles);
    }
    //endregion

    //region urbanizedArea tests
    @Test
    public void urbanizedAreaShouldNotPlaceCityNextTo0Or1Cities() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        TileSlot slot2 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        TileSlot slot3 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        slot1.addNeighbor(slot2);
        Tile cityTile = new Tile(id, TileType.City);
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        unplacedSlots.add(slot3);
        Map<TileSlot, Tile> placed = new HashMap<>();
        placed.put(slot2, cityTile);
        initialState.withUnplacedSlots(unplacedSlots).withPlacedTiles(placed);

        GameState[] finalStates = StandardCards.urbanizedArea.getPlayEffect().apply(initialState, id);

        assertEquals(0, finalStates.length);
    }
    @Test
    public void urbanizedAreaShouldPlaceCityNextTo2Cities() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        TileSlot slot2 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        TileSlot slot3 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        slot1.addNeighbor(slot2); slot1.addNeighbor(slot3);
        Tile cityTile = new Tile(id, TileType.City);
        Set<TileSlot> unplacedSlots = new HashSet<>();
        unplacedSlots.add(slot1);
        Map<TileSlot, Tile> placed = new HashMap<>();
        placed.put(slot2, cityTile);
        placed.put(slot3, cityTile);
        initialState.withUnplacedSlots(unplacedSlots).withPlacedTiles(placed);

        GameState[] finalStates = StandardCards.urbanizedArea.getPlayEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(3, finalStates[0].getPlacedTiles().size());
    }
    //endregion

    //region ecologicalZone tests
    @Test
    public void ecologicalZoneShouldFailIfNoAlliedGreenery() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(1);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 12);
        PlayerBuilder player2 = new PlayerBuilder().withPlayerID(id2);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player, player2});
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        Tile greenery = new Tile(id2, TileType.Plants);
        Map<TileSlot, Tile> placed = new HashMap<>();
        placed.put(slot1, greenery);
        initialState.withPlacedTiles(placed);

        GameState[] finalStates = Card.play(StandardCards.ecologicalZone, initialState, id);

        assertEquals(0, finalStates.length);
    }
    @Test
    public void ecologicalZoneShouldPlaceNextToGreenery() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(1);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 12);
        PlayerBuilder player2 = new PlayerBuilder().withPlayerID(id2);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player, player2});
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        TileSlot slot2 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        slot1.addNeighbor(slot2);
        Set<TileSlot> unplaced = new HashSet<>();
        unplaced.add(slot1);
        Tile greenery = new Tile(id, TileType.Plants);
        Map<TileSlot, Tile> placed = new HashMap<>();
        placed.put(slot2, greenery);
        initialState.withPlacedTiles(placed).withUnplacedSlots(unplaced);

        GameState[] finalStates = Card.play(StandardCards.ecologicalZone, initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(new Tile(id, TileType.Special), finalStates[0].getPlacedTiles().get(slot1));
    }
    @Test
    public void ecologicalZoneShouldGet1CounterWhenPlantCardPlayed() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 13);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.ecologicalZone)
        };
        player.withTableau(tableau);

        GameState[] finalStates = Card.play(StandardCards.trees, initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(1, finalStates[0].getPlayerByID(id).findCard("Ecological Zone").getCounters());
    }
    @Test
    public void ecologicalZoneShouldGet1CounterWhenPlantAndAnimalCardPlayed() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.ecologicalZone)
        };
        player.withTableau(tableau);

        GameState[] finalStates = Card.play(TestHelpers.makeTestCardWithTags(new CardTag[]{CardTag.Plants, CardTag.Animals}, "card1"), initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(2, finalStates[0].getPlayerByID(id).findCard("Ecological Zone").getCounters());
    }
    @Test
    public void ecologicalZoneShouldScore2PointsFor4Counters() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.ecologicalZone).withCounters(4)
        };
        player.withTableau(tableau);

        assertEquals(2, StandardCards.ecologicalZone.getScorer().score(initialState.build(), id));
    }
    @Test
    public void ecologicalZoneShouldScore4PointsFor9Counters() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.ecologicalZone).withCounters(9)
        };
        player.withTableau(tableau);

        assertEquals(4, StandardCards.ecologicalZone.getScorer().score(initialState.build(), id));
    }
    //endregion

    //region zeppelins tests
    @Test
    public void zeppelinsShouldAdd1MCIf1City() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{p1});
        Map<TileSlot, Tile> placed = new HashMap<>();
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        Tile city = new Tile(id, TileType.City);
        placed.put(slot1, city);
        initialState.withPlacedTiles(placed);

        GameState[] results = StandardCards.zeppelins.getPlayEffect().apply(initialState, id);

        assertEquals(1, results.length);
        assertEquals(2, (int) results[0].getPlayerByID(id).getProduction().get(ResourceType.MegaCredits));
    }

    @Test
    public void zeppelinsShouldAdd2MCIf1OwnedCityAnd1EnemyCity() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(1);
        PlayerBuilder p1 = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.Energy, 1);
        PlayerBuilder p2 = new PlayerBuilder().withPlayerID(id2);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{p1, p2});
        Map<TileSlot, Tile> placed = new HashMap<>();
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        TileSlot slot2 = new TileSlot(TileSlotType.Desert, new ResourceBonus[0]);
        Tile city1 = new Tile(id, TileType.City);
        Tile city2 = new Tile(id2, TileType.CapitalCity);
        placed.put(slot1, city1);
        placed.put(slot2, city2);
        initialState.withPlacedTiles(placed);

        GameState[] results = StandardCards.zeppelins.getPlayEffect().apply(initialState, id);

        assertEquals(1, results.length);
        assertEquals(3, (int) results[0].getPlayerByID(id).getProduction().get(ResourceType.MegaCredits));
    }
    //endregion

    //region herbivores tests
    @Test
    public void herbivoresShouldGet1CounterWhenGreeneryPlaced() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 12);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});

        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.herbivores)
        };
        player.withTableau(tableau);

        TileSlot slot1 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        Set<TileSlot> unplaced = new HashSet<>();
        unplaced.add(slot1);
        initialState.withUnplacedSlots(unplaced);

        GameState[] finalStates = new PlaceMangrovePlantTileEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(1, finalStates[0].getPlayerByID(id).findCard("Herbivores").getCounters());
    }
    //endregion

    //region insulation tests
    @Test
    public void insulationShouldReturn1Plus1GameStateForEach1HeatProductionWhereRemoves1HeatProductionAndAdds1MCProduciton() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withProduction(ResourceType.Heat, 2);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});

        GameState[] finalStates = StandardCards.insulation.getPlayEffect().apply(initialState, id);

        assertEquals(3, finalStates.length);
        assertEquals(2, (int) finalStates[0].getPlayerByID(id).getProduction().get(ResourceType.Heat));
        assertEquals(1, (int) finalStates[0].getPlayerByID(id).getProduction().get(ResourceType.MegaCredits));
        assertEquals(1, (int) finalStates[1].getPlayerByID(id).getProduction().get(ResourceType.Heat));
        assertEquals(2, (int) finalStates[1].getPlayerByID(id).getProduction().get(ResourceType.MegaCredits));
        assertEquals(0, (int) finalStates[2].getPlayerByID(id).getProduction().get(ResourceType.Heat));
        assertEquals(3, (int) finalStates[2].getPlayerByID(id).getProduction().get(ResourceType.MegaCredits));
    }
    //endregion

    //region shuttles tests
    @Test
    public void shuttlesShouldReduceSpaceEventCostBy2() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 14);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder researchOutpost = new CardStateBuilder().withCard(StandardCards.shuttles);
        player.withTableau(new CardStateBuilder[]{researchOutpost});

        GameState[] finalStates = Card.play(StandardCards.asteroid, initialState, id);
        assertEquals(1, finalStates.length);
        assertEquals(2, (int) finalStates[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
    }
    @Test
    public void shuttlesShouldNotReduceNonSpaceEventCostBy2() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 3);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        CardStateBuilder researchOutpost = new CardStateBuilder().withCard(StandardCards.shuttles);
        player.withTableau(new CardStateBuilder[]{researchOutpost});

        GameState[] finalStates = Card.play(StandardCards.searchForLife, initialState, id);
        assertEquals(1, finalStates.length);
        assertEquals(0, (int) finalStates[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
    }
    //endregion

    //region importedNitrogen tests
    @Test
    public void importedNitrogenShouldReturn2StatesIf2CardsWithMicrobesAndNoAnimals() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});

        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(TestHelpers.makeTestCardWithTags(new CardTag[]{CardTag.Microbes, CardTag.UsesCounters}, "card1")),
                new CardStateBuilder().withCard(TestHelpers.makeTestCardWithTags(new CardTag[]{CardTag.Microbes, CardTag.UsesCounters}, "card2"))
        };
        player.withTableau(tableau);

        GameState[] finalStates = StandardCards.importedNitrogen.getPlayEffect().apply(initialState, id);

        assertEquals(2, finalStates.length);
    }
    //endregion

    //region immigrantCity tests
    @Test
    public void immigrantCityShouldAdd1MCProductionWhenAlliedCityPlaced() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});

        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.immigrantCity)
        };
        player.withTableau(tableau);

        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        Set<TileSlot> unplaced = new HashSet<>();
        unplaced.add(slot1);
        initialState.withUnplacedSlots(unplaced);

        GameState[] finalStates = new PlaceCityTileEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(2, (int) finalStates[0].getPlayerByID(id).getProduction().get(ResourceType.MegaCredits));
    }

    @Test
    public void immigrantCityShouldAdd1MCProductionWhenOpponentCityPlaced() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        PlayerBuilder player2 = new PlayerBuilder().withPlayerID(id2);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player, player2});

        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.immigrantCity)
        };
        player.withTableau(tableau);

        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        Set<TileSlot> unplaced = new HashSet<>();
        unplaced.add(slot1);
        initialState.withUnplacedSlots(unplaced);

        GameState[] finalStates = new PlaceCityTileEffect().apply(initialState, id2);

        assertEquals(1, finalStates.length);
        assertEquals(2, (int) finalStates[0].getPlayerByID(id).getProduction().get(ResourceType.MegaCredits));
    }
    //endregion
}
/*
PlayerID id = new PlayerID(0);
PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});

CardStateBuilder[] tableau = new CardStateBuilder[]{
        new CardStateBuilder().withCard(StandardCards.card)
};
player.withTableau(tableau);

TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
Set<TileSlot> unplaced = new HashSet<>();
unplaced.add(slot1);
initialState.withUnplacedSlots(unplaced);

GameState[] finalStates = StandardCards.card.getPlayEffect().apply(initialState, id);
*/
