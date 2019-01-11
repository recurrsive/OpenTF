package com.k2sw.opentf;

public class CompoundRequirement implements Requirement {
    private Requirement[] reqs;

    public CompoundRequirement(Requirement[] reqs) {
        this.reqs = reqs;
    }

    @Override
    public boolean check(GameStateBuilder state, PlayerID currentPlayer) {
        boolean valid = true;
        for (Requirement req : reqs) {
            valid = req.check(state, currentPlayer);
        }
        return valid;
    }
}
