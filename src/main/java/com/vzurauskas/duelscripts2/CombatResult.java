package com.vzurauskas.duelscripts2;

public class CombatResult {
    private final String description;

    public CombatResult() {
        this.description = "Alice: 0 damage\nBob: 0 damage";
    }

    public String description() {
        return description;
    }
}
