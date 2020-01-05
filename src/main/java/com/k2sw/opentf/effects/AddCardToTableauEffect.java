package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;
import java.util.*;

public class AddCardToTableauEffect extends Effect {
    private Card card;

    public AddCardToTableauEffect(Card card) {
        this.card = card;
    }

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        CardStateBuilder[] nextTableau = new CardStateBuilder[state.getPlayerByID(currentPlayer).getTableau().length+1];
        System.arraycopy(state.getPlayerByID(currentPlayer).getTableau(), 0, nextTableau, 0, state.getPlayerByID(currentPlayer).getTableau().length);
        nextTableau[nextTableau.length-1] = new CardStateBuilder().withCard(card).withActivated(!(card.getActionEffect() instanceof NullEffect));
        state.getPlayerByID(currentPlayer).withTableau(nextTableau);
        return new GameState[]{state.build()};
    }
}
