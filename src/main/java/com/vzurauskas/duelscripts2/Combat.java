package com.vzurauskas.duelscripts2;

public class Combat {
    private final Fighter fighter1;
    private final Fighter fighter2;

    public Combat(Fighter fighter1, Fighter fighter2) {
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
    }

    public CombatResult nextTurn() {
        // Fighter 1 chooses actions
        BodyPart f1StrikeTarget = fighter1.getScript().strike(fighter1, fighter2);
        BodyPart f1ParryTarget = fighter1.getScript().parry(fighter1, fighter2);
        
        // Fighter 2 chooses actions  
        BodyPart f2StrikeTarget = fighter2.getScript().strike(fighter2, fighter1);
        BodyPart f2ParryTarget = fighter2.getScript().parry(fighter2, fighter1);
        
        // Execute parries first
        fighter1.parry(f1ParryTarget);
        fighter2.parry(f2ParryTarget);
        
        // Execute strikes
        fighter1.strike(fighter2, f1StrikeTarget);
        fighter2.strike(fighter1, f2StrikeTarget);
        
        return new CombatResult(fighter1, fighter2);
    }
}
