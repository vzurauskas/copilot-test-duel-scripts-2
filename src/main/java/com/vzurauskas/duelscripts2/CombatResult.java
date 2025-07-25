package com.vzurauskas.duelscripts2;

import java.util.ArrayList;
import java.util.List;

public class CombatResult {
    private final Fighter winner;
    private final Fighter fighter1;
    private final Fighter fighter2;
    private final List<TurnResult> turnHistory;
    
    public CombatResult(Fighter winner, Fighter fighter1, Fighter fighter2, List<TurnResult> turnHistory) {
        this.winner = winner;
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
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
        result.append("Fight is over\n");
        result.append("\n=== Summary Statistics ===\n");
        result.append("Total turns: ").append(turnHistory.size()).append("\n");
        result.append(fighter1.name()).append(" total damage: ").append(calculateTotalDamage(fighter1)).append("\n");
        result.append(fighter2.name()).append(" total damage: ").append(calculateTotalDamage(fighter2)).append("\n");
        result.append(fighter1.name()).append(" critical hits: ").append(calculateCriticalHits(fighter1)).append("\n");
        result.append(fighter2.name()).append(" critical hits: ").append(calculateCriticalHits(fighter2));
        
        return result.toString();
    }
    
    private int calculateTotalDamage(Fighter fighter) {
        return fighter.strikesCarriedOut().stream()
            .mapToInt(strike -> strike.damage())
            .sum();
    }
    
    private int calculateCriticalHits(Fighter fighter) {
        return (int) fighter.strikesCarriedOut().stream()
            .filter(strike -> strike.isCritical())
            .count();
    }
}
