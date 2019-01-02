package com.k2sw.opentf;

import java.util.Map;

public interface PlayerOrBuilder {
    Map<ResourceType, Integer> getProduction();
    Map<ResourceType, Integer> getAmounts();
    int getTerraformingScore();
    PlayerID getPlayerID();
}
