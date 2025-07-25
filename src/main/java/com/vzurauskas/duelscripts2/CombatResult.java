package com.vzurauskas.duelscripts2;

import java.util.ArrayList;
import java.util.List;

public class CombatResult {
    private final Fighter winner;
    private final List<TurnResult> turnHistory;
    
    public CombatResult(Fighter winner, List<TurnResult> turnHistory) {
        this.winner = winner;
        this.turnHistory = new ArrayList<>(turnHistory);
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        
        for (TurnResult turn : turnHistory) {
            result.append(turn.description()).append("\n");
        }
        
        result.append("=== Final Result ===\n");
        result.append(winner.name()).append(" wins\n");
        result.append("Fight is over");
        
        return result.toString();
    }
}
