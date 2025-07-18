package com.vzurauskas.duelscripts2;

public class Strike {
    private final Fighter striker;
    private final Fighter defender;
    private final BodyPart target;
    private final int damage;
    private final boolean criticalHit;
    private final BodyPart parried;

    public Strike(
        Fighter striker, Fighter defender, BodyPart target, int damage, 
        boolean criticalHit, BodyPart parried
    ) {
        this.striker = striker;
        this.defender = defender;
        this.target = target;
        this.damage = damage;
        this.criticalHit = criticalHit;
        this.parried = parried;
    }

    public int damage() {
        return damage;
    }

    public boolean wasParried() {
        return parried == target;
    }

    public String damageReport() {
        return "%s: %d damage".formatted(defender.name(), wasParried() ? 0 : damage);
    }

    public String description() {
        String criticalHitSuffix = criticalHit ? " (critical hit!)" : "";
        String parriedSuffix = wasParried() ? " - BLOCKED by parry." : "";
        
        return "%s strikes %s's %s with %s for %d damage%s%s".formatted(
            striker.name(), defender.name(), target, striker.getWeapon().name(), 
            damage, criticalHitSuffix, parriedSuffix
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Strike strike = (Strike) obj;
        return damage == strike.damage &&
               criticalHit == strike.criticalHit &&
               striker.equals(strike.striker) &&
               defender.equals(strike.defender) &&
               target == strike.target &&
               parried == strike.parried;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(
            striker, defender, target, damage, criticalHit, parried
        );
    }
}
