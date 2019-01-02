package com.k2sw.opentf;

public class CardStateBuilder implements CardStateOrBuilder {
    private Card card;
    private int counters;
    private boolean activated;

    public CardStateBuilder(Card card, int counters, boolean activated) {
        this.card = card;
        this.counters = counters;
        this.activated = activated;
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

    public void setCard(Card card) {
        this.card = card;
    }

    public void setCounters(int counters) {
        this.counters = counters;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public CardState build(){
        return new CardState(card, counters, activated);
    }
}
