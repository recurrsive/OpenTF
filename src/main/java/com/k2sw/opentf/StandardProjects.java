package com.k2sw.opentf;

import com.k2sw.opentf.effects.*;

public class StandardProjects {
    public static Effect sellPatents = new SellPatentsEffect();

    public static Effect powerPlant = new CompoundEffect(new Effect[]{
            new PayForCostEffect(new CardTag[0], true, 11),
            new IncreaseProductionEffect(ResourceType.Energy, 1)
    });

    public static Effect asteroid = new CompoundEffect(new Effect[]{
            new PayForCostEffect(new CardTag[0], true, 14),
            new IncreaseTemperatureEffect()
    });

    public static Effect aquifer = new CompoundEffect(new Effect[]{
            new PayForCostEffect(new CardTag[0], true, 18),
            new PlaceOceanTileEffect()
    });

    public static Effect greenery = new CompoundEffect(new Effect[]{
            new PayForCostEffect(new CardTag[0], true, 23),
            new PlacePlantsTileEffect(),
            new IncreaseOxygenEffect()
    });

    public static Effect city = new CompoundEffect(new Effect[]{
            new PayForCostEffect(new CardTag[0], true, 25),
            new PlaceCityTileEffect(),
            new IncreaseProductionEffect(ResourceType.MegaCredits, 1)
    });

    public static Effect[] projects = new Effect[]{
            sellPatents,
            powerPlant,
            asteroid,
            aquifer,
            greenery,
            city
    };
}
