package com.vzurauskas.duelscripts2;

public class Combat {
    private final Fighter fighter1;
    private final Fighter fighter2;

    public Combat(Fighter fighter1, Fighter fighter2) {
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
    }

    public CombatResult nextTurn() {
        return new CombatResult();
    }
}
