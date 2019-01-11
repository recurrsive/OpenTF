package com.k2sw.opentf;

import java.util.*;

public class Player{
    private Map<ResourceType, Integer> production;
    private Map<ResourceType, Integer> amounts;
    private int terraformingScore;
    private PlayerID playerID;
    private CardState[] tableau;
    private Set<Card> hand;

    public Player(Map<ResourceType, Integer> production, Map<ResourceType, Integer> amounts, int terraformingScore, PlayerID playerID, CardState[] tableau, Set<Card> hand) {
        this.production = production;
        this.amounts = amounts;
        this.terraformingScore = terraformingScore;
        this.playerID = playerID;
        this.tableau = tableau;
        this.hand = hand;
    }

    public Map<ResourceType, Integer> getProduction() {
        return production;
    }

    public Map<ResourceType, Integer> getAmounts() {
        return amounts;
    }

    public int getTerraformingScore() {
        return terraformingScore;
    }

    public PlayerID getPlayerID() {
        return playerID;
    }

    public CardState[] getTableau() {
        return tableau;
    }

    public Set<Card> getHand() {
        return hand;
    }

    public CardState findCard(String name) {
        for (CardState card : tableau){
            if (card.getCard().getName().equals(name)) return card;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return terraformingScore == player.terraformingScore &&
                Objects.equals(production, player.production) &&
                Objects.equals(amounts, player.amounts) &&
                Objects.equals(playerID, player.playerID) &&
                Arrays.equals(tableau, player.tableau) &&
                Objects.equals(hand, player.hand);
    }

    @Override
    public String toString() {
        return "Player{" +
                "production=" + production +
                ", amounts=" + amounts +
                ", terraformingScore=" + terraformingScore +
                ", playerID=" + playerID +
                ", tableau=" + Arrays.toString(tableau) +
                ", hand=" + hand +
                '}';
    }
}
