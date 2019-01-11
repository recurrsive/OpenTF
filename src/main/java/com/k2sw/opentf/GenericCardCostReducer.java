package com.k2sw.opentf;

public class GenericCardCostReducer implements Reducer {
    private int discount;

    public GenericCardCostReducer(int discount) {
        this.discount = discount;
    }

    @Override
    public int reduceCost(CardTag[] tags, boolean genericProject, int amount) {
        if (genericProject) return amount;
        return amount - discount;
    }

    @Override
    public Requirement changeRequirement(Requirement req) {
        return req;
    }
}
