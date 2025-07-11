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
        
        String f1StrikeDesc = buildStrikeDescription(
            fighter1.name(), fighter2.name(), f1LastStrike, fighter1.getWeapon().name()
        );
        String f2StrikeDesc = buildStrikeDescription(
            fighter2.name(), fighter1.name(), f2LastStrike, fighter2.getWeapon().name()
        );
        
        int f1Damage = f2LastStrike.wasParried() ? 0 : f2LastStrike.damage();
        int f2Damage = f1LastStrike.wasParried() ? 0 : f1LastStrike.damage();
        
        return """
            %s
            %s
            
            %s: %d damage
            %s: %d damage""".formatted(
            f1StrikeDesc, f2StrikeDesc,
            fighter1.name(), f1Damage,
            fighter2.name(), f2Damage
        );
    }
    
    private String buildStrikeDescription(String attackerName, String defenderName, Strike strike, String weaponName) {
        String criticalHitSuffix = strike.wasCriticalHit() ? " (critical hit!)" : "";
        String parriedSuffix = strike.wasParried() ? " - BLOCKED by parry." : "";
        
        return "%s strikes %s's %s with %s for %d damage%s%s".formatted(
            attackerName, defenderName, strike.target(), weaponName, 
            strike.damage(), criticalHitSuffix, parriedSuffix
        );
    }
}
