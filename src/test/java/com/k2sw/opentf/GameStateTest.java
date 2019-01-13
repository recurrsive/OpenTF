package com.k2sw.opentf;

import com.k2sw.opentf.effects.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class GameStateTest {
    //region placeTile tests
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

        assertEquals(expectedPlacedTiles, initialState.getPlacedTiles());
    }
    //endregion

    //region getTilesByType tests
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
    //endregion

    //region isFinalRound tests
    @Test
    public void isFinalRoundShouldReturnTrueIf13Oxygen8TemperatureAnd9Oceans() {
        GameStateBuilder initialState = new GameStateBuilder();
        TileSlot tile1 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile2 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile3 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile4 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile5 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile6 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile7 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile8 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile9 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        Tile ocean = new Tile(Global.NO_PLAYER, TileType.Ocean);
        Map<TileSlot, Tile> placed = new HashMap<>();
        placed.put(tile1, ocean); placed.put(tile2, ocean); placed.put(tile3, ocean); placed.put(tile4, ocean); placed.put(tile5, ocean);
        placed.put(tile6, ocean); placed.put(tile7, ocean); placed.put(tile8, ocean); placed.put(tile9, ocean);
        initialState.withPlacedTiles(placed).withTemperature(8).withOxygen(13);

        assertTrue(GameStateFunctions.isFinalRound(initialState));
    }
    @Test
    public void isFinalRoundShouldReturnFalseIfAnyConditionNotMet() {
        TileSlot tile1 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile2 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile3 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile4 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile5 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile6 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile7 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile8 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile9 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        Tile ocean = new Tile(Global.NO_PLAYER, TileType.Ocean);
        Map<TileSlot, Tile> placed = new HashMap<>();
        placed.put(tile1, ocean); placed.put(tile2, ocean); placed.put(tile3, ocean); placed.put(tile4, ocean); placed.put(tile5, ocean);
        placed.put(tile6, ocean); placed.put(tile7, ocean); placed.put(tile8, ocean); placed.put(tile9, ocean);
        Map<TileSlot, Tile> placed2 = new HashMap<>();
        placed2.put(tile1, ocean); placed2.put(tile2, ocean);


        assertFalse(GameStateFunctions.isFinalRound(new GameStateBuilder().withTemperature(8).withOxygen(13).withPlacedTiles(placed2)));
        assertFalse(GameStateFunctions.isFinalRound(new GameStateBuilder().withTemperature(6).withOxygen(13).withPlacedTiles(placed)));
        assertFalse(GameStateFunctions.isFinalRound(new GameStateBuilder().withTemperature(8).withOxygen(11).withPlacedTiles(placed)));
    }
    //endregion

    //region doAction tests
    @Test
    public void doActionShouldReturnOnlyPassActionIfPlayerHasPassed() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withPassed(true);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        GameState initialState = initialStateBuilder.build();

        GameState[] finalResults = GameStateFunctions.doAction(initialStateBuilder, id);

        assertEquals(1, finalResults.length);
        assertEquals(initialState, finalResults[0]);
    }

    @Test
    public void doActionShouldPlayEachCardInHandIfEnoughMC() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 10);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        Set<Card> hand = new HashSet<>();
        hand.add(StandardCards.importOfAdvancedGHG); hand.add(StandardCards.importedGHG); hand.add(StandardCards.giantIceAsteroid);
        player.withHand(hand);
        GameState initialState = initialStateBuilder.build();

        GameState[] finalResults = GameStateFunctions.doAction(initialStateBuilder, id);

        assertEquals(6, finalResults.length);
        assertEquals(1, finalResults[0].getPlayerByID(id).getTableau().length);
        assertEquals(2, finalResults[0].getPlayerByID(id).getHand().size());
        assertEquals(1, finalResults[1].getPlayerByID(id).getTableau().length);
        assertEquals(2, finalResults[1].getPlayerByID(id).getHand().size());
        //Standard project sellPatents checks
        assertEquals(0, finalResults[2].getPlayerByID(id).getTableau().length);
        assertEquals(2, finalResults[2].getPlayerByID(id).getHand().size());
        assertEquals(1, finalResults[2].getDiscard().size());
        assertEquals(0, finalResults[3].getPlayerByID(id).getTableau().length);
        assertEquals(1, finalResults[3].getPlayerByID(id).getHand().size());
        assertEquals(2, finalResults[3].getDiscard().size());
        assertEquals(0, finalResults[4].getPlayerByID(id).getTableau().length);
        assertEquals(0, finalResults[4].getPlayerByID(id).getHand().size());
        assertEquals(3, finalResults[4].getDiscard().size());
        //Passing check
        assertTrue(finalResults[5].getPlayerByID(id).hasPassed());
    }
    @Test
    public void doActionShouldPlayEachStandardProject() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 25);
        GameStateBuilder initialStateBuilder = new GameStateBuilder()
                .withPlayers(new PlayerBuilder[]{player}).withTemperature(0).withOxygen(0);
        player.getHand().add(StandardCards.giantIceAsteroid);
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        TileSlot slot2 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        TileSlot slot3 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        slot1.addNeighbor(slot2);
        Set<TileSlot> unplaced = new HashSet<>();
        unplaced.add(slot1); unplaced.add(slot3);
        initialStateBuilder.withUnplacedSlots(unplaced);
        Map<TileSlot, Tile> placed = new HashMap<>();
        placed.put(slot2, new Tile(id, TileType.Plants));
        initialStateBuilder.withPlacedTiles(placed);

        GameState[] finalResults = GameStateFunctions.doAction(initialStateBuilder, id);

        assertEquals(7, finalResults.length);
        //sellPatents
        assertEquals(26, (int) finalResults[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(0, finalResults[0].getPlayerByID(id).getHand().size());
        assertEquals(1, finalResults[0].getDiscard().size());
        //powerPlant
        assertEquals(2, (int) finalResults[1].getPlayerByID(id).getProduction().get(ResourceType.Energy));
        //asteroid
        assertEquals(1, finalResults[2].getTemperature());
        assertEquals(1, finalResults[2].getPlayerByID(id).getTerraformingScore());
        //aquifer
        assertEquals(2, finalResults[3].getPlacedTiles().size());
        assertEquals(TileType.Ocean, finalResults[3].getPlacedTiles().get(slot3).getTileType());
        assertEquals(1, finalResults[3].getPlayerByID(id).getTerraformingScore());
        //greenery
        assertEquals(2, finalResults[4].getPlacedTiles().size());
        assertEquals(TileType.Plants, finalResults[4].getPlacedTiles().get(slot1).getTileType());
        assertEquals(1, finalResults[4].getOxygen());
        assertEquals(1, finalResults[4].getPlayerByID(id).getTerraformingScore());
        //city
        assertEquals(2, finalResults[5].getPlacedTiles().size());
        assertEquals(TileType.City, finalResults[5].getPlacedTiles().get(slot1).getTileType());
        assertEquals(2, (int) finalResults[5].getPlayerByID(id).getProduction().get(ResourceType.MegaCredits));
        //Passing
        assertTrue(finalResults[6].getPlayerByID(id).hasPassed());
    }
    @Test
    public void doActionShouldUseHeatAndPlants() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.Heat, 8).withAmount(ResourceType.Plants, 10);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player}).withOxygen(0).withTemperature(0);

        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        TileSlot slot2 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        TileSlot slot3 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        slot1.addNeighbor(slot3); slot2.addNeighbor(slot3);
        Set<TileSlot> unplaced = new HashSet<>();
        unplaced.add(slot1); unplaced.add(slot2);
        initialStateBuilder.withUnplacedSlots(unplaced);
        Map<TileSlot, Tile> placed = new HashMap<>();
        placed.put(slot3, new Tile(id, TileType.Plants));
        initialStateBuilder.withPlacedTiles(placed);

        GameState[] finalResults = GameStateFunctions.doAction(initialStateBuilder, id);

        assertEquals(4, finalResults.length);
        //heat
        assertEquals(0, (int) finalResults[0].getPlayerByID(id).getAmounts().get(ResourceType.Heat));
        assertEquals(1, finalResults[0].getTemperature());
        assertEquals(1, finalResults[0].getPlayerByID(id).getTerraformingScore());
        //plants
        assertEquals(2, finalResults[1].getPlacedTiles().size());
        assertEquals(1, finalResults[1].getOxygen());
        assertEquals(1, finalResults[1].getPlayerByID(id).getTerraformingScore());
        assertEquals(2, (int) finalResults[1].getPlayerByID(id).getAmounts().get(ResourceType.Plants));
        for (Tile tile : finalResults[1].getPlacedTiles().values())
            assertEquals(TileType.Plants, tile.getTileType());
        //plants
        assertEquals(2, finalResults[2].getPlacedTiles().size());
        assertEquals(1, finalResults[2].getOxygen());
        assertEquals(1, finalResults[2].getPlayerByID(id).getTerraformingScore());
        assertEquals(2, (int) finalResults[2].getPlayerByID(id).getAmounts().get(ResourceType.Plants));
        for (Tile tile : finalResults[2].getPlacedTiles().values())
            assertEquals(TileType.Plants, tile.getTileType());
        //Passing check
        assertTrue(finalResults[3].getPlayerByID(id).hasPassed());
    }
    @Test
    public void doActionShouldUseEachTableauActionThatCanBeActivated() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.Energy, 4);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});

        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.birds),
                new CardStateBuilder().withCard(StandardCards.steelWorks),
                new CardStateBuilder().withCard(StandardCards.ironWorks).withActivated(true),
                new CardStateBuilder().withCard(StandardCards.aquiferPumping)
        };
        player.withTableau(tableau);

        GameState[] finalResults = GameStateFunctions.doAction(initialStateBuilder, id);

        assertEquals(3, finalResults.length);
        //birds
        assertEquals(1, finalResults[0].getPlayerByID(id).findCard("Birds").getCounters());
        assertTrue(finalResults[0].getPlayerByID(id).findCard("Birds").isActivated());
        //steelWorks
        assertEquals(1, finalResults[1].getOxygen());
        assertEquals(0, (int) finalResults[1].getPlayerByID(id).getAmounts().get(ResourceType.Energy));
        assertTrue(finalResults[1].getPlayerByID(id).findCard("Steelworks").isActivated());
        //Passing check
        assertTrue(finalResults[2].getPlayerByID(id).hasPassed());
    }
    @Test
    public void doActionShouldPassIfNoCardsCreditsOrResources() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});

        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        TileSlot slot2 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        TileSlot slot3 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        slot1.addNeighbor(slot3); slot2.addNeighbor(slot3);
        Set<TileSlot> unplaced = new HashSet<>();
        unplaced.add(slot1); unplaced.add(slot2);
        initialStateBuilder.withUnplacedSlots(unplaced);
        Map<TileSlot, Tile> placed = new HashMap<>();
        placed.put(slot3, new Tile(id, TileType.Plants));
        initialStateBuilder.withPlacedTiles(placed);

        GameState initialState = initialStateBuilder.build();

        GameState[] finalResults = GameStateFunctions.doAction(initialStateBuilder, id);

        assertEquals(1, finalResults.length);
        //Passing check
        assertTrue(finalResults[0].getPlayerByID(id).hasPassed());
    }
    @Test
    public void doActionShouldReturnOnlyPassActionAndUsingPlantsIfPlayerHasPassed() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.Plants, 8);
        GameStateBuilder initialStateBuilder = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot tile1 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile2 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile3 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile4 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile5 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile6 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile7 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile8 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot tile9 = new TileSlot(TileSlotType.Ocean, new ResourceBonus[]{});
        TileSlot openTile = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        TileSlot playerTile = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        openTile.addNeighbor(playerTile);
        Set<TileSlot> unplaced = new HashSet<>(); unplaced.add(openTile);
        Tile ocean = new Tile(Global.NO_PLAYER, TileType.Ocean);
        Map<TileSlot, Tile> placed = new HashMap<>();
        placed.put(tile1, ocean); placed.put(tile2, ocean); placed.put(tile3, ocean); placed.put(tile4, ocean); placed.put(tile5, ocean);
        placed.put(tile6, ocean); placed.put(tile7, ocean); placed.put(tile8, ocean); placed.put(tile9, ocean);
        placed.put(playerTile, new Tile(id, TileType.City));
        initialStateBuilder.withPlacedTiles(placed).withUnplacedSlots(unplaced).withOxygen(13).withTemperature(8);

        GameState initialState = initialStateBuilder.build();

        GameState[] finalResults = GameStateFunctions.doAction(initialStateBuilder, id);

        assertEquals(2, finalResults.length);
        assertEquals(11, finalResults[0].getPlacedTiles().size());
        assertTrue(finalResults[1].getPlayerByID(id).hasPassed());
    }
    //endregion

    //region allPlayersPassed tests
    @Test
    public void allPlayersPassedShouldReturnTrueIfAllPlayersHavePassed() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(1);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withPassed(true);
        PlayerBuilder player2 = new PlayerBuilder().withPlayerID(id2).withPassed(true);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player, player2});

        assertTrue(GameStateFunctions.allPlayersPassed(initialState.build()));
    }
    @Test
    public void allPlayersPassedShouldReturnFalseIfAPlayerHasNotPassed() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(1);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withPassed(true);
        PlayerBuilder player2 = new PlayerBuilder().withPlayerID(id2).withPassed(false);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player, player2});

        assertFalse(GameStateFunctions.allPlayersPassed(initialState.build()));
    }
    //endregion

    //region nextPlayer tests
    @Test
    public void nextPlayerShouldReturnOtherPlayerIDWith2Players() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(1);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        PlayerBuilder player2 = new PlayerBuilder().withPlayerID(id2);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player, player2});

        assertEquals(id2, GameStateFunctions.nextPlayer(initialState.build(), id));
        assertEquals(id, GameStateFunctions.nextPlayer(initialState.build(), id2));
    }
    @Test
    public void nextPlayerShouldSamePlayerIf1Player() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});

        assertEquals(id, GameStateFunctions.nextPlayer(initialState.build(), id));
    }
    @Test
    public void nextPlayerShouldSamePlayerIfOpponentIsSinglePlayerOpponent() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(1);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        PlayerBuilder player2 = new PlayerBuilder().withPlayerID(id2).withSinglePlayerOpponent(true);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player, player2});

        assertEquals(id, GameStateFunctions.nextPlayer(initialState.build(), id));
    }
    //endregion

    //region nextGeneration tests
    @Test
    public void nextGenerationShouldAdd1ToGenNumber() {
        GameStateBuilder initialState = new GameStateBuilder().withGenerationNum(1);
        assertEquals(2, GameStateFunctions.nextGeneration(initialState.build()).getGenerationNum());
    }
    @Test
    public void nextGenerationShouldProduceResources() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(1);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        PlayerBuilder player2 = new PlayerBuilder().withPlayerID(id2)
                .withAmount(ResourceType.Plants, 3).withProduction(ResourceType.Plants, 3).withAmount(ResourceType.Energy, 5);
        GameState initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player, player2}).build();

        GameStateBuilder finalState = GameStateFunctions.nextGeneration(initialState);

        for (ResourceType type : ResourceType.values()) {
            assertEquals(1, (int) finalState.getPlayerByID(id).getAmounts().get(type));
        }
        assertEquals(6, (int) finalState.getPlayerByID(id2).getAmounts().get(ResourceType.Plants));
        assertEquals(6, (int) finalState.getPlayerByID(id2).getAmounts().get(ResourceType.Heat));
    }
    //endregion

    //region buyCard tests
    @Test
    public void buyCardShouldReturn2ResultsIfEnoughMC() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 3);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        List<Card> deck = new ArrayList<>();
        deck.add(StandardCards.trees);
        initialState.withDeck(deck);

        GameState[] finalStates = new BuyCardEffect().apply(initialState, id);

        assertEquals(2, finalStates.length);
        assertEquals(1, finalStates[0].getPlayerByID(id).getHand().size());
        assertEquals(0, (int) finalStates[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(0, finalStates[0].getDiscard().size());
        assertEquals(0, finalStates[1].getPlayerByID(id).getHand().size());
        assertEquals(3, (int) finalStates[1].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(1, finalStates[1].getDiscard().size());
    }
    @Test
    public void buyCardShouldReturn1ResultIfNotEnoughMC() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        List<Card> deck = new ArrayList<>();
        deck.add(StandardCards.trees);
        initialState.withDeck(deck);

        GameState[] finalStates = new BuyCardEffect().apply(initialState, id);

        assertEquals(1, finalStates.length);
        assertEquals(0, finalStates[0].getPlayerByID(id).getHand().size());
        assertEquals(1, finalStates[0].getDiscard().size());
    }
    @Test
    public void buyCardTwiceShouldReturn4ResultsIfEnoughMC() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 6);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        List<Card> deck = new ArrayList<>();
        deck.add(StandardCards.trees); deck.add(StandardCards.birds);
        initialState.withDeck(deck);

        GameState[] finalStates = new CompoundEffect(new Effect[]{new BuyCardEffect(), new BuyCardEffect()}).apply(initialState, id);

        assertEquals(4, finalStates.length);
        assertEquals(2, finalStates[0].getPlayerByID(id).getHand().size());
        assertEquals(0, (int) finalStates[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(0, finalStates[0].getDiscard().size());
        assertEquals(1, finalStates[1].getPlayerByID(id).getHand().size());
        assertEquals(3, (int) finalStates[1].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(1, finalStates[1].getDiscard().size());
        assertEquals(1, finalStates[2].getPlayerByID(id).getHand().size());
        assertEquals(3, (int) finalStates[2].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(1, finalStates[2].getDiscard().size());
        assertEquals(0, finalStates[3].getPlayerByID(id).getHand().size());
        assertEquals(6, (int) finalStates[3].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(2, finalStates[3].getDiscard().size());
    }
    @Test
    public void buyCardTwiceShouldReturn3ResultsIf3MC() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withAmount(ResourceType.MegaCredits, 3);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        List<Card> deck = new ArrayList<>();
        deck.add(StandardCards.trees); deck.add(StandardCards.birds);
        initialState.withDeck(deck);

        GameState[] finalStates = new CompoundEffect(new Effect[]{new BuyCardEffect(), new BuyCardEffect()}).apply(initialState, id);

        assertEquals(3, finalStates.length);
        assertEquals(1, finalStates[0].getPlayerByID(id).getHand().size());
        assertEquals(0, (int) finalStates[0].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(1, finalStates[0].getDiscard().size());
        assertEquals(1, finalStates[1].getPlayerByID(id).getHand().size());
        assertEquals(0, (int) finalStates[1].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(1, finalStates[1].getDiscard().size());
        assertEquals(0, finalStates[2].getPlayerByID(id).getHand().size());
        assertEquals(3, (int) finalStates[2].getPlayerByID(id).getAmounts().get(ResourceType.MegaCredits));
        assertEquals(2, finalStates[2].getDiscard().size());
    }
    //endregion

    //region scoreGame tests
    @Test
    public void scoreGameShouldScoreTerraformingScore() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id).withTerraformingScore(15);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});

        Map<PlayerID, Integer> finalScores = GameStateFunctions.scoreGame(initialState.build());

        assertEquals(1, finalScores.size());
        assertEquals(15, (int) finalScores.get(id));
    }
    @Test
    public void scoreGameShouldScoreVictoryScorers() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});

        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.largeConvoy),
                new CardStateBuilder().withCard(StandardCards.birds).withCounters(3)
        };
        player.withTableau(tableau);

        Map<PlayerID, Integer> finalScores = GameStateFunctions.scoreGame(initialState.build());

        assertEquals(1, finalScores.size());
        assertEquals(5, (int) finalScores.get(id));
    }
    @Test
    public void scoreGameShouldScorePlants() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        TileSlot slot2 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        TileSlot slot3 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        Tile alliedPlants = new Tile(id, TileType.Plants);
        Tile opponentPlants = new Tile(Global.NO_PLAYER, TileType.Plants);
        Map<TileSlot, Tile> placed = new HashMap<>();
        placed.put(slot1, alliedPlants); placed.put(slot2, alliedPlants); placed.put(slot3, opponentPlants);
        initialState.withPlacedTiles(placed);

        Map<PlayerID, Integer> finalScores = GameStateFunctions.scoreGame(initialState.build());

        assertEquals(1, finalScores.size());
        assertEquals(2, (int) finalScores.get(id));
    }
    @Test
    public void scoreGameShouldScoreCities() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player});
        TileSlot slot1 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        TileSlot slot2 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        TileSlot slot3 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        TileSlot slot4 = new TileSlot(TileSlotType.Desert, new ResourceBonus[]{});
        slot3.addNeighbor(slot1); slot3.addNeighbor(slot2);
        slot4.addNeighbor(slot1);
        Tile plants = new Tile(id, TileType.Plants);
        Tile city = new Tile(id, TileType.City);
        Tile capital = new Tile(id, TileType.CapitalCity);
        Map<TileSlot, Tile> placed = new HashMap<>();
        placed.put(slot1, plants); placed.put(slot2, plants); placed.put(slot3, city); placed.put(slot4, capital);
        initialState.withPlacedTiles(placed);

        Map<PlayerID, Integer> finalScores = GameStateFunctions.scoreGame(initialState.build());

        assertEquals(1, finalScores.size());
        assertEquals(5, (int) finalScores.get(id));
    }
    @Test
    public void scoreGameShouldScoreAllPlayers() {
        PlayerID id = new PlayerID(0);
        PlayerBuilder player = new PlayerBuilder().withPlayerID(id);
        PlayerID id2 = new PlayerID(0);
        PlayerBuilder player2 = new PlayerBuilder().withPlayerID(id2).withTerraformingScore(1);
        GameStateBuilder initialState = new GameStateBuilder().withPlayers(new PlayerBuilder[]{player, player2});

        CardStateBuilder[] tableau = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.largeConvoy),
                new CardStateBuilder().withCard(StandardCards.birds).withCounters(3)
        };
        player.withTableau(tableau);
        CardStateBuilder[] tableau2 = new CardStateBuilder[]{
                new CardStateBuilder().withCard(StandardCards.nuclearZone)
        };
        player2.withTableau(tableau2);

        Map<PlayerID, Integer> finalScores = GameStateFunctions.scoreGame(initialState.build());

        assertEquals(2, finalScores.size());
        assertEquals(5, (int) finalScores.get(id));
        assertEquals(-1, (int) finalScores.get(id2));
    }
    @Test
    public void calculateWinnerShouldReturnPlayerIDOfPlayerWithHighestScore() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(1);
        Map<PlayerID, Integer> scores = new HashMap<>();
        scores.put(id, 5); scores.put(id2, 8);

        Set<PlayerID> finalWinners = GameStateFunctions.calculateWinners(scores);

        assertEquals(1, finalWinners.size());
        assertTrue(finalWinners.contains(id2));
    }
    @Test
    public void calculateWinnerShouldReturnPlayerIDsOfAllPlayersWithHighestScoresIfTie() {
        PlayerID id = new PlayerID(0);
        PlayerID id2 = new PlayerID(1);
        PlayerID id3 = new PlayerID(2);
        Map<PlayerID, Integer> scores = new HashMap<>();
        scores.put(id, 5); scores.put(id2, 8); scores.put(id3, 8);

        Set<PlayerID> finalWinners = GameStateFunctions.calculateWinners(scores);

        assertEquals(2, finalWinners.size());
        assertTrue(finalWinners.contains(id2));
        assertTrue(finalWinners.contains(id3));
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
