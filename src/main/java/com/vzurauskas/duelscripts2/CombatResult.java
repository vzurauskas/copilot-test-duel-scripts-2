package com.vzurauskas.duelscripts2;

public class CombatResult {
    private final String description;

    public CombatResult() {
        this.description = """
            Alice strikes Bob's HEAD with Sword for 34 damage (critical hit!)
            Bob strikes Alice's TORSO with Axe - BLOCKED by parry.
            
            Alice: 0 damage
            Bob: 0 damage
        """;
    }

    public String description() {
        return description;
    }
}
