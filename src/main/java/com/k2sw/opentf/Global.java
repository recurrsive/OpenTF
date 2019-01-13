package com.k2sw.opentf;

import com.k2sw.opentf.effects.NullEffect;

public class Global {
    public static PlayerID NO_PLAYER = new PlayerID(-1);
    public static int MIN_OXYGEN = 0; public static int MAX_OXYGEN = 13;
    public static int MIN_TEMPERATURE = -30; public static int MAX_TEMPERATURE = 8;
    public static int MAX_OCEANS = 9;
    public static NullEffect NULL_EFFECT = new NullEffect();
    public static NoRequirement NO_REQUIREMENT = new NoRequirement();
    public static VictoryScorer ZERO_SCORER = new GenericScorer(0);
    public static Reducer NO_REDUCER = new GenericCardCostReducer(0);
    public static Card NO_CARD = null;
}
