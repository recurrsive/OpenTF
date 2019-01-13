package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;
import java.util.*;

public class PlayCardEffect implements Effect {
    private Card card;

    public PlayCardEffect(Card card) {
        this.card = card;
    }

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        ArrayList<TriggerType> triggerTypeList = new ArrayList<>();
        if (card.hasTag(CardTag.Space) && card.hasTag(CardTag.Event)) triggerTypeList.add(TriggerType.SpaceEventPlayed);
        if (card.hasTag(CardTag.Plants)) triggerTypeList.add(TriggerType.PlantTagPlayed);
        if (card.hasTag(CardTag.Microbes)) triggerTypeList.add(TriggerType.MicrobeTagPlayed);
        if (card.hasTag(CardTag.Animals)) triggerTypeList.add(TriggerType.AnimalTagPlayed);
        TriggerType[] triggerTypes = new TriggerType[triggerTypeList.size()];
        triggerTypeList.toArray(triggerTypes);

        return GameStateFunctions.triggerSearch(triggerTypes, new CompoundEffect(new Effect[]{
                new AddCardToTableauEffect(card),
                new DiscardCardEffect(card),
                new CheckRequirementEffect(card.getRequirement()),
                new PayForCostEffect(card.getTags(), false, card.getCost()),
                card.getPlayEffect()
        }).apply(state, currentPlayer), currentPlayer);
    }
}
