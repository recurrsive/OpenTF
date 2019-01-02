package com.k2sw.opentf;

public class StandardCards {
    private static Card Trees = new Card("Trees", 13, CardType.Event, new CardTag[]{CardTag.Event}, new OxygenRequirement(-4, true),
            new CompoundEffect(
                    new IncreaseProductionEffect(ResourceType.Plants, 3),
                    new IncreaseAmountEffect(ResourceType.Plants, 1)),
            Global.NULL_EFFECT);
    public static Card[] cards = new Card[]{
            Trees
    };
}

