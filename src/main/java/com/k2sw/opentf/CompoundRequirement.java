package com.k2sw.opentf;

public class CompoundRequirement extends Requirement {
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

    @Override
    public String getText() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < reqs.length; ++i) {
            result.append(reqs[i].getText());
            if (i < reqs.length - 1) {
                result.append("\n");
            }
        }
        return result.toString();
    }
}
