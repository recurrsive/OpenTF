package com.k2sw.opentf;

public class GenericCardCostReducer extends Reducer {
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

    @Override
    public String getText() {
        return "Reduces the cost of each card you play by " + discount + ".";
    }
}
