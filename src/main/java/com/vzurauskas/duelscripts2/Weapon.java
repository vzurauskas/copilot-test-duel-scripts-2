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
    
    public String name() {
        return name;
    }

    public Strike strike(Fighter striker, Fighter defender, BodyPart target, BodyPart parried) {
        if (parried == target) {
            return new Strike(striker, defender, target, 0, false, parried);
        } else {
            int baseDamage = (int)(damage * target.multiplier());
            boolean isCritical = Math.random() < criticalHitChance;
            int finalDamage = isCritical ? baseDamage * 2 : baseDamage;
            return new Strike(striker, defender, target, finalDamage, isCritical, parried);
        }
    }
}
