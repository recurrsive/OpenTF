package com.k2sw.opentf;

public class TagCountRequirement extends Requirement {
    private CardTag tag;
    private int count;

    public TagCountRequirement(CardTag tag, int count) {
        this.tag = tag;
        this.count = count;
    }

    @Override
    public boolean check(GameStateBuilder state, PlayerID currentPlayer) {
        return state.getPlayerByID(currentPlayer).getTagCount(tag) >= count;
    }

    @Override
    public String getText() {
        return "At least " + count + " " + tag.toString() + " tags.";
    }
}
