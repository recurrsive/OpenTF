package com.k2sw.opentf;

public class StandardCards {
    private static Card trees = new Card("Trees", 13, CardType.Auto,
            new CardTag[]{CardTag.Plants}, new OxygenRequirement(-4, true),
            new CompoundEffect(
                    new IncreaseProductionEffect(ResourceType.Plants, 3),
                    new IncreaseAmountEffect(ResourceType.Plants, 1)),
            Global.NULL_EFFECT,
            new GenericScorer(1));
    private static Card deepWellHeating = new Card("Deep Well Heating", 13, CardType.Auto,
            new CardTag[]{CardTag.Energy, CardTag.Steel}, Global.NO_REQUIREMENT,
            new CompoundEffect(
                    new IncreaseProductionEffect(ResourceType.Energy, 1),
                    new IncreaseTemperatureEffect()
            ),
            Global.NULL_EFFECT,
            new GenericScorer(0));
    private static Card subterraneanReservoir = new Card("Subterranean Reservoir", 11, CardType.Event,
            new CardTag[]{CardTag.Event}, Global.NO_REQUIREMENT,
            new PlaceOceanTileEffect(),
            Global.NULL_EFFECT,
            new GenericScorer(0));
    public static Card[] cards = new Card[]{
            trees,
            deepWellHeating,
            subterraneanReservoir
    };
}

