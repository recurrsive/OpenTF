package com.k2sw.opentf;

import com.k2sw.opentf.effects.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Card {
    private String name;
    private int cost;
    private CardType type;
    private CardTag[] tags;
    private Requirement requirement;
    private Effect playEffect;
    private Effect actionEffect;
    private Effect triggeredEffect;
    private TriggerType[] triggerTypes;
    private VictoryScorer scorer;
    private Reducer reducer;

    public Card(String name, int cost, CardType type, CardTag[] tags, Requirement requirement, Effect playEffect, Effect actionEffect, Effect triggeredEffect, TriggerType[] triggerTypes, VictoryScorer scorer, Reducer reducer) {
        this.name = name;
        this.cost = cost;
        this.type = type;
        this.tags = tags;
        this.requirement = requirement;
        this.playEffect = playEffect;
        this.actionEffect = actionEffect;
        this.triggeredEffect = triggeredEffect;
        this.triggerTypes = triggerTypes;
        this.scorer = scorer;
        this.reducer = reducer;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public CardType getType() {
        return type;
    }

    public CardTag[] getTags() {
        return tags;
    }

    public Effect getPlayEffect() {
        return playEffect;
    }

    public Effect getActionEffect() {
        return actionEffect;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public VictoryScorer getScorer() {
        return scorer;
    }

    public Reducer getReducer() {
        return reducer;
    }

    public Effect getTriggeredEffect() {
        return triggeredEffect;
    }

    public TriggerType[] getTriggerTypes() {
        return triggerTypes;
    }

    public boolean hasTag(CardTag tag) {
        for (CardTag t : tags){
            if (t.equals(tag)) return true;
        }
        return false;
    }

    public static GameState[] play(Card card, GameStateBuilder state, PlayerID currentPlayer){
        ArrayList<TriggerType> triggerTypeList = new ArrayList<>();
        if (card.hasTag(CardTag.Space) && card.hasTag(CardTag.Event)) triggerTypeList.add(TriggerType.SpaceEventPlayed);
        if (card.hasTag(CardTag.Plants)) triggerTypeList.add(TriggerType.PlantTagPlayed);
        if (card.hasTag(CardTag.Microbes)) triggerTypeList.add(TriggerType.MicrobeTagPlayed);
        if (card.hasTag(CardTag.Animals)) triggerTypeList.add(TriggerType.AnimalTagPlayed);
        TriggerType[] triggerTypes = new TriggerType[triggerTypeList.size()];
        triggerTypeList.toArray(triggerTypes);

        return GameStateFunctions.triggerSearch(triggerTypes, new CompoundEffect(new Effect[]{
                new AddCardToTableauEffect(card),
                new CheckRequirementEffect(card.getRequirement()),
                new PayForCostEffect(card.getTags(), false, card.cost),
                card.playEffect
        }).apply(state, currentPlayer), currentPlayer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cost == card.cost &&
                Objects.equals(name, card.name) &&
                type == card.type &&
                Arrays.equals(tags, card.tags) &&
                Objects.equals(requirement, card.requirement) &&
                Objects.equals(playEffect, card.playEffect) &&
                Objects.equals(actionEffect, card.actionEffect) &&
                Objects.equals(triggeredEffect, card.triggeredEffect) &&
                Arrays.equals(triggerTypes, card.triggerTypes) &&
                Objects.equals(scorer, card.scorer) &&
                Objects.equals(reducer, card.reducer);
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name+
                '}';
    }
}
