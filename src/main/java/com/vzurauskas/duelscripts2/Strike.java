package com.vzurauskas.duelscripts2;

public class Strike {
    private final BodyPart target;
    private final int damage;
    private final boolean criticalHit;
    private final boolean parried;

    public Strike(BodyPart target, int damage, boolean criticalHit, boolean parried) {
        this.target = target;
        this.damage = damage;
        this.criticalHit = criticalHit;
        this.parried = parried;
    }

    public BodyPart target() {
        return target;
    }

    public int damage() {
        return damage;
    }

    public boolean wasCriticalHit() {
        return criticalHit;
    }

    public boolean wasParried() {
        return parried;
    }
}
