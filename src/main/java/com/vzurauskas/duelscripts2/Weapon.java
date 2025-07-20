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

    public Strike strike(Fighter striker, FighterBodyPart target) {
        if (target.isBeingParried()) {
            return Strike.parried(striker, target);
        } else {
            int baseDamage = target.calculateBaseDamage(damage);
            boolean isCritical = Math.random() < criticalHitChance;
            int finalDamage = isCritical ? baseDamage * 2 : baseDamage;
            return Strike.hit(striker, target, finalDamage, isCritical);
        }
    }
}
