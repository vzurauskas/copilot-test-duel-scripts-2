package com.vzurauskas.duelscripts2;

public class CombatResult {
    private final Fighter winner;
    
    public CombatResult(Fighter winner) {
        this.winner = winner;
    }
    
    @Override
    public String toString() {
        return winner.name() + " wins\nFight is over";
    }
}
