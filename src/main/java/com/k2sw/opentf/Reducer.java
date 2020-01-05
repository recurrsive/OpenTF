package com.k2sw.opentf;

public abstract class Reducer {
    public abstract int reduceCost(CardTag[] tags, boolean genericProject, int amount);
    public abstract Requirement changeRequirement(Requirement req);

    public String getText() {
        return "Reduces the cost of various projects.";
    }
}
