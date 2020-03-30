package dev.glycine.pracstation.models;

import lombok.Getter;

import java.util.HashMap;

public class Station {
    @Getter
    private static final Station instance = new Station();
    @Getter
    private static final HashMap<Integer, Turnout> turnouts = Turnout.getTurnouts();

    public Station() {
        Turnout.setTurnouts(35);
    }
}
