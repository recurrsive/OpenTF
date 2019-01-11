package com.k2sw.opentf;

import com.k2sw.opentf.effects.*;

import java.util.*;

public class GameStateFunctions {

    public static boolean isOver(GameState b){
        return b.getOxygen() == Global.MAX_OXYGEN && b.getTemperature() == Global.MAX_TEMPERATURE;
    }

    public static GameState getResources(GameStateBuilder state, PlayerID id, ResourceBonus bonus){
        Effect tileResourceEffect = new NullEffect();
        switch(bonus.getBonusType()){
            case Plants: tileResourceEffect = new IncreaseAmountEffect(ResourceType.Plants, bonus.getCount()); break;
            case Steel: tileResourceEffect = new IncreaseAmountEffect(ResourceType.Steel, bonus.getCount()); break;
            case Titanium: tileResourceEffect = new IncreaseAmountEffect(ResourceType.Titanium, bonus.getCount()); break;
            case Cards: {
                Effect[] draws = new Effect[bonus.getCount()];
                for (int i = 0; i < draws.length; i++) draws[i] = new DrawCardEffect();
                tileResourceEffect = new CompoundEffect(draws);
            }
        }
        GameState[] result = tileResourceEffect.apply(state, id);
        if (result.length > 1) throw new RuntimeException("getResources got more than one resulting state");
        return result[0];
    }

    public static GameState getMCFromAdjacent(GameStateBuilder state, PlayerID id, TileSlot slot){
        int result = 0;
        for (TileSlot neighbor : slot.getNeighbors()) {
            if (state.getPlacedTiles().containsKey(neighbor) && state.getPlacedTiles().get(neighbor).getTileType() == TileType.Ocean)
                result += 2;
        }
        Effect effect = new IncreaseAmountEffect(ResourceType.MegaCredits, result);
        GameState[] finalStates = effect.apply(state, id);
        if (finalStates.length > 1) throw new RuntimeException("getMCFromAdjacent got more than one resulting state");
        return finalStates[0];
    }

    public static GameState[] triggerSearch(TriggerType[] types, GameState[] states, PlayerID id) {
        ArrayList<GameState> resultList = new ArrayList<>();
        for (GameState state : states) {
            GameStateBuilder stateBuilder = new GameStateBuilder(state);
            GameState[] nextResults = new GameState[]{stateBuilder.build()};
            for (PlayerBuilder player : stateBuilder.getPlayers()) {
                CardStateBuilder[] tableau = player.getTableau();
                for (CardStateBuilder cardState : tableau) {
                    boolean shouldTrigger = false;
                    for (TriggerType type : types) {
                        for (TriggerType cardType : cardState.getCard().getTriggerTypes()) {
                            if (type == cardType) shouldTrigger = true;
                        }
                    }
                    if (shouldTrigger) {
                        ArrayList<GameState> next_states = new ArrayList<>();
                        Effect effect = cardState.getCard().getTriggeredEffect();
                        for (GameState prev_state : nextResults){
                            Collections.addAll(next_states, effect.apply(new GameStateBuilder(prev_state), player.getPlayerID()));
                        }
                        nextResults = new GameState[next_states.size()];
                        next_states.toArray(nextResults);
                    }
                }
            }
            Collections.addAll(resultList, nextResults);
        }
        GameState[] results = new GameState[resultList.size()];
        resultList.toArray(results);
        return results;
    }
}

