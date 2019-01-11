package com.k2sw.opentf;

public interface Reducer {
    int reduceCost(CardTag[] tags, boolean genericProject, int amount);
    Requirement changeRequirement(Requirement req);
}
