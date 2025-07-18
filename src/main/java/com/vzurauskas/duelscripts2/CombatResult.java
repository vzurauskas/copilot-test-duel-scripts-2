package com.vzurauskas.duelscripts2;

import java.util.List;

public class CombatResult {
    private final Fighter fighter1;
    private final Fighter fighter2;

    public CombatResult(Fighter fighter1, Fighter fighter2) {
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
    }

    public String description() {
        List<Strike> f1Strikes = fighter1.strikesCarriedOut();
        List<Strike> f2Strikes = fighter2.strikesCarriedOut();
        
        Strike f1LastStrike = f1Strikes.get(f1Strikes.size() - 1);
        Strike f2LastStrike = f2Strikes.get(f2Strikes.size() - 1);
        
        String f1StrikeDesc = f1LastStrike.description();
        String f2StrikeDesc = f2LastStrike.description();
        
        return """
            %s
            %s
            
            %s
            %s
        """.formatted(
            f1StrikeDesc,
            f2StrikeDesc,
            f2LastStrike.damageReport(),
            f1LastStrike.damageReport()
        );
    }
}
