package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

public class PlaceOffBoardCityEffect extends Effect {
    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        Tile placedCity = new Tile(currentPlayer, TileType.City);
        state.getPlacedTiles().put(new TileSlot(TileSlotType.Desert, new ResourceBonus[0]), placedCity);
        return GameStateFunctions.triggerSearch(new TriggerType[]{TriggerType.CityPlaced}, new GameState[]{state.build()}, currentPlayer);
    }

    @Override
    public String getText() {
        return "Place a colony city.";
    }
}
