package com.k2sw.opentf;

public class CompoundRequirement implements Requirement {
    private Requirement req1;
    private Requirement req2;

    @Override
    public boolean check(GameState state) {
        return req1.check(state) && req2.check(state);
    }
}
