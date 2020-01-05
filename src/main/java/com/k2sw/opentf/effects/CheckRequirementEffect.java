package com.k2sw.opentf.effects;

import com.k2sw.opentf.*;

public class CheckRequirementEffect extends Effect {
    private Requirement req;

    public CheckRequirementEffect(Requirement req) {
        this.req = req;
    }

    @Override
    public GameState[] apply(GameStateBuilder state, PlayerID currentPlayer) {
        Requirement finalReq = req;
        for (CardStateBuilder cardState : state.getPlayerByID(currentPlayer).getTableau()){
            finalReq = cardState.getCard().getReducer().changeRequirement(finalReq);
        }
        if (finalReq.check(state, currentPlayer))
            return new GameState[]{state.build()};
        else
            return new GameState[0];
    }
}
