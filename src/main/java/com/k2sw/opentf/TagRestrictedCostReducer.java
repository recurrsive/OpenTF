package com.k2sw.opentf;

public class TagRestrictedCostReducer implements Reducer {
    private CardTag tag;
    private int discount;

    public TagRestrictedCostReducer(CardTag tag, int discount) {
        this.tag = tag;
        this.discount = discount;
    }

    @Override
    public int reduceCost(CardTag[] tags, boolean genericProject, int amount) {
        boolean reduce = false;
        for (CardTag t : tags) {
            reduce = reduce || (t == tag);
        }
        if (reduce) return amount - discount;
        else return amount;
    }

    @Override
    public Requirement changeRequirement(Requirement req) {
        return req;
    }
}
