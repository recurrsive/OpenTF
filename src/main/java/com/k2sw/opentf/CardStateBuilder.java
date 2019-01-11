package com.k2sw.opentf;

public class CardStateBuilder {
    private Card card;
    private int counters;
    private boolean activated;

    public CardStateBuilder() {
        this.card = null;
        this.counters = 0;
        this.activated = false;
    }

    public CardStateBuilder(CardState template){
        card = template.getCard();
        counters = template.getCounters();
        activated = template.isActivated();
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

    public CardStateBuilder withCard(Card card) {
        this.card = card;
        return this;
    }

    public CardStateBuilder withCounters(int counters) {
        this.counters = counters;
        return this;
    }

    public CardStateBuilder withActivated(boolean activated) {
        this.activated = activated;
        return this;
    }

    public CardState build(){
        return new CardState(card, counters, activated);
    }

    @Override
    public String toString() {
        return "CardStateBuilder{" +
                "card=" + card +
                ", counters=" + counters +
                ", activated=" + activated +
                '}';
    }
}
