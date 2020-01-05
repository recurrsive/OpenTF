package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;
import java.util.*;

public class UseTableauActionEffect extends Effect {
    private String cardName;

    public UseTableauActionEffect(String cardName) {
        this.cardName = cardName;
    }

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        CardStateBuilder playedCard = state.getPlayerByID(currentPlayer).findCard(cardName);
        if (playedCard.getCard().getActionEffect() instanceof NullEffect || playedCard.isActivated())
            return new GameState[0];
        else
            playedCard.withActivated(true);
            return playedCard.getCard().getActionEffect().apply(state, currentPlayer);
    }
}
