package com.k2sw.opentf;

public class OxygenRequirement extends Requirement {
    private int amount;
    private boolean greater;

    public OxygenRequirement(int amount, boolean greater) {
        this.amount = amount;
        this.greater = greater;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isGreater() {
        return greater;
    }

    @Override
    public boolean check(GameStateBuilder state, PlayerID currentPlayer) {
        if (greater)
            return state.getOxygen() >= amount;
        else
            return state.getOxygen() <= amount;
    }

    @Override
    public String getText() {
        if (greater) {
            return "At least " + amount + "% oxygen.";
        }
        else {
            return "At most " + amount + "% oxygen.";
        }
    }
}
