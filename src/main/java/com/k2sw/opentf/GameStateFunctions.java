package com.k2sw.opentf;

public class GameStateFunctions {

    public static boolean isOver(GameState b){
        return b.getOxygen() == Global.MAX_OXYGEN && b.getTemperature() == Global.MAX_TEMPERATURE;
    }
}

