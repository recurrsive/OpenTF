package com.k2sw.opentf;

public class ResourceBonus {
    private ResourceBonusType bonusType;
    private int count = 0;

    public ResourceBonus(ResourceBonusType bonusType, int count) {
        this.bonusType = bonusType;
        this.count = count;
    }
}
