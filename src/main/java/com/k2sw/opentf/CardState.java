package com.k2sw.opentf;

import java.util.Objects;

public class CardState {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardState cardState = (CardState) o;
        return counters == cardState.counters &&
                activated == cardState.activated &&
                Objects.equals(card, cardState.card);
    }

    @Override
    public String toString() {
        return "CardState{" +
                "card=" + card +
                ", counters=" + counters +
                ", activated=" + activated +
                '}';
    }
}
