package com.k2sw.opentf;

import java.util.*;

public class TestHelpers {
    public static Map<ResourceType, Integer> makePlayerProp (int mc, int steel, int titanium, int heat, int plants, int energy){
        Map<ResourceType, Integer> expectedAmounts = new HashMap<>();
        expectedAmounts.put(ResourceType.MegaCredits, mc);
        expectedAmounts.put(ResourceType.Steel, steel);
        expectedAmounts.put(ResourceType.Titanium, titanium);
        expectedAmounts.put(ResourceType.Heat, heat);
        expectedAmounts.put(ResourceType.Plants, plants);
        expectedAmounts.put(ResourceType.Energy, energy);
        return expectedAmounts;
    }

    public static Card makeTestCardWithTags(CardTag[] tags, String name){
        return new Card(name, 0, CardType.Auto, tags, Global.NO_REQUIREMENT, Global.NULL_EFFECT, Global.NULL_EFFECT, Global.NULL_EFFECT, new TriggerType[0], Global.ZERO_SCORER, Global.NO_REDUCER);
    }
    public static Card makeTestCardWithCost(int cost, String name){
        return new Card(name, cost, CardType.Auto, new CardTag[0], Global.NO_REQUIREMENT, Global.NULL_EFFECT, Global.NULL_EFFECT, Global.NULL_EFFECT, new TriggerType[0], Global.ZERO_SCORER, Global.NO_REDUCER);
    }
    public static Card makeTestCardWithReq(Requirement req, String name){
        return new Card(name, 0, CardType.Auto, new CardTag[0], req, Global.NULL_EFFECT, Global.NULL_EFFECT, Global.NULL_EFFECT, new TriggerType[0], Global.ZERO_SCORER, Global.NO_REDUCER);
    }
}
