package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

import java.util.ArrayList;
import java.util.Collections;

public class PayForCostEffect implements Effect {
    private int cost;
    private boolean isStandardProject;
    private CardTag[] tags;

    public PayForCostEffect(CardTag[] tags, boolean isStandardProject, int cost) {
        this.cost = cost;
        this.isStandardProject = isStandardProject;
        this.tags = tags;
    }

    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        int initialCost = cost;
        for (CardStateBuilder cardState : state.getPlayerByID(currentPlayer).getTableau()){
            initialCost = cardState.getCard().getReducer().reduceCost(tags, isStandardProject, initialCost);
        }

        ArrayList<CardTag> tagList = new ArrayList<>();
        Collections.addAll(tagList, tags);

        int maxSteel;
        int maxTitanium;
        int steel = state.getPlayerByID(currentPlayer).getAmounts().get(ResourceType.Steel);
        int titanium = state.getPlayerByID(currentPlayer).getAmounts().get(ResourceType.Titanium);
        if (tagList.contains(CardTag.Steel)) {
            maxSteel = (initialCost+1)/2;
            if (maxSteel > steel) maxSteel = steel;
        } else maxSteel = 0;
        if (tagList.contains(CardTag.Space)) {
            maxTitanium = (initialCost+2)/3;
            if (maxTitanium > titanium) maxTitanium = titanium;
        } else maxTitanium = 0;


        ArrayList<GameState> resultList = new ArrayList<>();
        for (int s = 0; s <= maxSteel; s++){
            for (int t = 0; t <= maxTitanium; t++){
                int finalCost = initialCost - 2*s - 3*t; if (finalCost < 0) finalCost = 0;
                GameState[] nextResults = new CompoundEffect(new Effect[]{
                        new DecreaseAmountEffect(ResourceType.Steel, s),
                        new DecreaseAmountEffect(ResourceType.Titanium, t),
                        new DecreaseAmountEffect(ResourceType.MegaCredits, finalCost)
                }).apply(new GameStateBuilder(state.build()), currentPlayer);
                Collections.addAll(resultList, nextResults);
            }
        }
        GameState[] results = new GameState[resultList.size()];
        resultList.toArray(results);
        return results;
    }
}
