package com.k2sw.opentf;

public class CardState implements CardStateOrBuilder {
    private Card card;
    private int counters;
    private boolean activated;

    public CardState(Card card, int counters, boolean activated) {
        this.card = card;
        this.counters = counters;
        this.activated = activated;
    }

    public Card getCard() {
        return card;
    }

    public int getCounters() {
        return counters;
    }

    public boolean isActivated() {
        return activated;
    }
}
