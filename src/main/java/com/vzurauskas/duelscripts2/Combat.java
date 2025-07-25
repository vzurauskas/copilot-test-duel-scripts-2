package com.vzurauskas.duelscripts2;

public class Combat {
    private final Fighter fighter1;
    private final Fighter fighter2;

    public Combat(Fighter fighter1, Fighter fighter2) {
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
    }

    public TurnResult nextTurn() {
        fighter1.parry(fighter2);
        fighter2.parry(fighter1);
        
        fighter1.strike(fighter2);
        fighter2.strike(fighter1);
        
        return new TurnResult(
            fighter1.lastStrikeCarriedOut(), 
            fighter2.lastStrikeCarriedOut()
        );
    }
    
    public CombatResult fight() {
        while (fighter1.isAlive() && fighter2.isAlive()) {
            nextTurn();
        }
        return new CombatResult();
    }
}
