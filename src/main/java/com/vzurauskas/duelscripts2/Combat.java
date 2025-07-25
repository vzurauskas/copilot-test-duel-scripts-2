package com.vzurauskas.duelscripts2;

import java.util.ArrayList;
import java.util.List;

public class Combat {
    private final Fighter fighter1;
    private final Fighter fighter2;
    private int turnNumber;

    public Combat(Fighter fighter1, Fighter fighter2) {
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
        this.turnNumber = 1;
    }

    public TurnResult nextTurn() {
        fighter1.parry(fighter2);
        fighter2.parry(fighter1);
        
        fighter1.strike(fighter2);
        fighter2.strike(fighter1);
        
        TurnResult result = new TurnResult(
            turnNumber,
            fighter1.lastStrikeCarriedOut(), 
            fighter2.lastStrikeCarriedOut()
        );
        
        turnNumber++;
        return result;
    }
    
    public CombatResult fight() {
        List<TurnResult> turnHistory = new ArrayList<>();
        while (fighter1.isAlive() && fighter2.isAlive()) {
            turnHistory.add(nextTurn());
        }
        Fighter winner = fighter1.isAlive() ? fighter1 : fighter2;
        return new CombatResult(winner, turnHistory);
    }
}
