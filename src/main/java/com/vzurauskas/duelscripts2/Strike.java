package com.vzurauskas.duelscripts2;

public class Strike {
    private final Fighter striker;
    private final FighterBodyPart target;
    private final int damage;
    private final boolean criticalHit;
    private final FighterBodyPart parried;

    public Strike(
        Fighter striker, FighterBodyPart target, int damage, 
        boolean criticalHit, FighterBodyPart parried
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
        return parried.equals(target);
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
               striker.equals(strike.striker) &&
               target.equals(strike.target) &&
               parried.equals(strike.parried);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(
            striker, target, damage, criticalHit, parried
        );
    }
}
