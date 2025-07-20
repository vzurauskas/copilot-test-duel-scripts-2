package com.vzurauskas.duelscripts2;

public class Strike {
    private final Fighter striker;
    private final FighterBodyPart target;
    private final int damage;
    private final boolean criticalHit;
    private final boolean wasParried;

    private Strike(
        Fighter striker, FighterBodyPart target, int damage, 
        boolean criticalHit, boolean wasParried
    ) {
        this.striker = striker;
        this.target = target;
        this.damage = damage;
        this.criticalHit = criticalHit;
        this.wasParried = wasParried;
    }

    public static Strike hit(
        Fighter striker, FighterBodyPart target, int damage, 
        boolean criticalHit
    ) {
        return new Strike(striker, target, damage, criticalHit, false);
    }

    public static Strike parried(Fighter striker, FighterBodyPart target) {
        return new Strike(striker, target, 0, false, true);
    }

    public int damage() {
        return damage;
    }

    public boolean wasParried() {
        return wasParried;
    }

    public String damageReport() {
        return "%s: %d damage".formatted(target.fighter().name(), wasParried() ? 0 : damage);
    }

    public String description() {
        String criticalHitSuffix = criticalHit ? " (critical hit!)" : "";
        String parriedSuffix = wasParried() ? " - BLOCKED by parry." : "";
        
        return "%s strikes %s with %s for %d damage%s%s".formatted(
            striker.name(), target, striker.getWeapon().name(), 
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
               wasParried == strike.wasParried &&
               striker.equals(strike.striker) &&
               target.equals(strike.target);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(
            striker, target, damage, criticalHit, wasParried
        );
    }
}
