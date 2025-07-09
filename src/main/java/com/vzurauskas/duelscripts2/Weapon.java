package com.vzurauskas.duelscripts2;

public class Weapon {
    private final String name;
    private final int damage;
    private final double criticalHitChance;

    public Weapon(String name, int damage) {
        this(name, damage, 0.0);
    }

    public Weapon(String name, int damage, double criticalHitChance) {
        this.name = name;
        this.damage = damage;
        this.criticalHitChance = criticalHitChance;
    }

    public int damage() {
        return damage;
    }

    public double criticalHitChance() {
        return criticalHitChance;
    }

    public int damageFor(BodyPart bodyPart) {
        int baseDamage = (int)(damage * bodyPart.multiplier());
        if (Math.random() < criticalHitChance) {
            baseDamage *= 2;
        }
        return baseDamage;
    }
}
