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

    public int damageFor(BodyPart bodyPart) {
        return strikeFor(bodyPart).damage();
    }
    
    public WeaponStrike strikeFor(BodyPart bodyPart) {
        int baseDamage = (int)(damage * bodyPart.multiplier());
        boolean isCritical = Math.random() < criticalHitChance;
        int finalDamage = isCritical ? baseDamage * 2 : baseDamage;
        return new WeaponStrike(finalDamage, isCritical);
    }
}
