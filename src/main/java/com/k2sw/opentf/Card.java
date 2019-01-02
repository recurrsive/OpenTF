package com.k2sw.opentf;

public class Card {
    private String name;
    private int cost;
    private CardType type;
    private CardTag[] tags;
    private Effect playEffect;
    private Effect actionEffect;
    private Requirement requirement;

    public Card(String name, int cost, CardType type, CardTag[] tags, Requirement requirement, Effect playEffect, Effect actionEffect) {
        this.name = name;
        this.cost = cost;
        this.type = type;
        this.tags = tags;
        this.playEffect = playEffect;
        this.actionEffect = actionEffect;
        this.requirement = requirement;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public CardType getType() {
        return type;
    }

    public CardTag[] getTags() {
        return tags;
    }

    public Effect getPlayEffect() {
        return playEffect;
    }

    public Effect getActionEffect() {
        return actionEffect;
    }

    public Requirement getRequirement() {
        return requirement;
    }
}
