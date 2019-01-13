package com.k2sw.opentf;

public class ActionMessage {
    private ActionType type;
    private String actionName;

    public ActionMessage(ActionType type, String actionName) {
        this.type = type;
        this.actionName = actionName;
    }

    public ActionType getType() {
        return type;
    }

    public String getActionName() {
        return actionName;
    }
}
