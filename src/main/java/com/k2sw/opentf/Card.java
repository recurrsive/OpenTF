package com.k2sw.opentf;

public class Card {
    private String name;
    private int cost;
    private CardType type;
    private CardTag[] tags;
    private Effect playEffect;
    private Effect actionEffect;
    private Requirement requirement;
    private VictoryScorer scorer;

    public Card(String name, int cost, CardType type, CardTag[] tags, Requirement requirement, Effect playEffect, Effect actionEffect, VictoryScorer scorer) {
        this.name = name;
        this.cost = cost;
        this.type = type;
        this.tags = tags;
        this.playEffect = playEffect;
        this.actionEffect = actionEffect;
        this.requirement = requirement;
        this.scorer = scorer;
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

    public VictoryScorer getScorer() {
        return scorer;
    }

    public static GameState[] play(Card card, GameStateBuilder state, PlayerID currentPlayer){
        if (card.requirement.check(state)){
            return (new CompoundEffect(new DecreaseAmountEffect(ResourceType.MegaCredits, card.cost), card.playEffect)).apply(state, currentPlayer);
        } else return new GameState[0];
    }
}
