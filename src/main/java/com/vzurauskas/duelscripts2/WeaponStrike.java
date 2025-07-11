package com.vzurauskas.duelscripts2;

public class WeaponStrike {
    private final int damage;
    private final boolean isCritical;

    public WeaponStrike(int damage, boolean isCritical) {
        this.damage = damage;
        this.isCritical = isCritical;
    }

    public int damage() {
        return damage;
    }

    public boolean isCritical() {
        return isCritical;
    }
}
