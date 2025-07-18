package com.vzurauskas.duelscripts2;

public class Strike {
    private final Fighter striker;
    private final BodyPart target;
    private final int damage;
    private final boolean criticalHit;
    private final BodyPart parried;

    public Strike(
        Fighter striker, BodyPart target, int damage, boolean criticalHit, 
        BodyPart parried
    ) {
        this.striker = striker;
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

    public String description(String defenderName) {
        String criticalHitSuffix = criticalHit ? " (critical hit!)" : "";
        String parriedSuffix = wasParried() ? " - BLOCKED by parry." : "";
        
        return "%s strikes %s's %s with %s for %d damage%s%s".formatted(
            striker.name(), defenderName, target, striker.getWeapon().name(), 
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
               target == strike.target &&
               parried == strike.parried;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(
            striker, target, damage, criticalHit, parried
        );
    }
}
