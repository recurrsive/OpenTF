package com.k2sw.opentf;

import com.k2sw.opentf.effects.*;

import java.util.*;

public class GameStateFunctions {
    public static boolean isFinalRound(GameStateBuilder state){
        return state.getOxygen() == Global.MAX_OXYGEN
                && state.getTemperature() == Global.MAX_TEMPERATURE
                && state.getTilesByType(TileType.Ocean).size() == Global.MAX_OCEANS;
    }

    public static boolean isFinalRoundSinglePlayer(GameStateBuilder state) {
        return state.getGenerationNum() == 14;
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

    public static GameState[] doAction(GameStateBuilder state, PlayerID currentPlayer) {
        if (GameStateFunctions.isFinalRound(state)) {
            return new OrEffect(new Effect[]{
                    new CompoundEffect(new Effect[]{
                        new DecreaseAmountEffect(ResourceType.Plants, 8),
                        new PlacePlantsTileEffect(),
                        new IncreaseOxygenEffect()
                    }),
                    new PassEffect()}).apply(state, currentPlayer);
        }
        else if (!state.getPlayerByID(currentPlayer).hasPassed()) {
            ArrayList<Effect> actionList = new ArrayList<>();
            for (Card card : state.getPlayerByID(currentPlayer).getHand()) {
                actionList.add(new PlayCardEffect(card));
            }
            for (CardStateBuilder playedCard : state.getPlayerByID(currentPlayer).getTableau()) {
                actionList.add(new UseTableauActionEffect(playedCard.getCard().getName()));
            }
            Collections.addAll(actionList, StandardProjects.projects);
            actionList.add(new CompoundEffect(new Effect[]{
                    new DecreaseAmountEffect(ResourceType.Heat, 8),
                    new IncreaseTemperatureEffect()
            }));
            actionList.add(new CompoundEffect(new Effect[]{
                    new DecreaseAmountEffect(ResourceType.Plants, 8),
                    new PlacePlantsTileEffect(),
                    new IncreaseOxygenEffect()
            }));
            actionList.add(new PassEffect());
            Effect[] actions = new Effect[actionList.size()];
            actionList.toArray(actions);
            return new OrEffect(actions).apply(state, currentPlayer);
        } else {
            return new PassEffect().apply(state, currentPlayer);
        }
    }

    public static boolean allPlayersPassed(GameState state) {
        boolean allPassed = true;
        for (Player p : state.getPlayers()) {
            allPassed = allPassed && p.hasPassed();
        }
        return allPassed;
    }

    public static PlayerID nextPlayer(GameState state, PlayerID id) {
        ArrayList<PlayerID> playerIDS = new ArrayList<>();
        for (Player p : state.getPlayers()) {
            if (!p.isSinglePlayerOpponent())
                playerIDS.add(p.getPlayerID());
        }
        int nextPlayerInd = playerIDS.indexOf(id)+1;
        if (nextPlayerInd == playerIDS.size()) nextPlayerInd = 0;
        return playerIDS.get(nextPlayerInd);
    }

    public static GameStateBuilder nextGeneration(GameState state) {
        GameStateBuilder nextState = new GameStateBuilder(state);
        nextState.withGenerationNum(nextState.getGenerationNum()+1);
        for (PlayerBuilder player : nextState.getPlayers()) {
            player.changeAmount(ResourceType.Heat, player.getAmounts().get(ResourceType.Energy));
            player.withAmount(ResourceType.Energy, 0);
            for (ResourceType type : ResourceType.values()) {
                player.changeAmount(type, player.getProduction().get(type));
            }

            for (CardStateBuilder playedCard : player.getTableau()) {
                playedCard.withActivated(false);
            }
        }
        return nextState;
    }

    public static GameState[] buyCards(GameStateBuilder state, PlayerID currentPlayer) {
        return new CompoundEffect(new Effect[]{
                new BuyCardEffect(), new BuyCardEffect(), new BuyCardEffect(), new BuyCardEffect()
        }).apply(state, currentPlayer);
    }

    public static Map<PlayerID, Integer> scoreGame(GameState state) {
        Map<PlayerID, Integer> scores = new HashMap<>();
        Map<TileSlot, Tile> placed = state.getPlacedTiles();
        for (Player p : state.getPlayers()) {
            int score = p.getTerraformingScore();
            for (CardState playedCard : p.getTableau()) {
                score += playedCard.getCard().getScorer().score(state, p.getPlayerID());
            }
            for (TileSlot slot : placed.keySet()) {
                Tile tile = placed.get(slot);
                if (tile.getOwnerID() == p.getPlayerID()) {
                    if (tile.getTileType() == TileType.Plants)
                        score++;
                    else if (tile.getTileType() == TileType.City || tile.getTileType() == TileType.CapitalCity) {
                        for (TileSlot adj : slot.getNeighbors()) {
                            if (placed.containsKey(adj) && placed.get(adj).getTileType() == TileType.Plants)
                                score++;
                        }
                    }
                }
            }
            scores.put(p.getPlayerID(), score);
        }
        return scores;
    }

    public static Set<PlayerID> calculateWinners(Map<PlayerID, Integer> scores) {
        int best = 0;
        Set<PlayerID> winners = new HashSet<>();
        for (PlayerID id : scores.keySet()) {
            if (scores.get(id) == best) winners.add(id);
            else if (scores.get(id) > best) {
                winners = new HashSet<>();
                winners.add(id);
                best = scores.get(id);
            }
        }
        return winners;
    }


}

