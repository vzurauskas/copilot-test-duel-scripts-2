package com.vzurauskas.duelscripts2;

public class Fighter {
    private final String name;
    private int hitPoints;
    private BodyPart parryingBodyPart;
    private Weapon weapon;

    public Fighter(String name, int hitPoints) {
        this.name = name;
        this.hitPoints = hitPoints;
        this.parryingBodyPart = BodyPart.TORSO;
        this.weapon = new Weapon("Fist", 0);
    }

    public int hitPoints() {
        return hitPoints;
    }

    public String name() {
        return name;
    }

    public boolean isAlive() {
        return hitPoints > 0;
    }

    public void parry(BodyPart bodyPart) {
        this.parryingBodyPart = bodyPart;
    }

    public void strike(Fighter target, BodyPart bodyPart) {
        if (target.parryingBodyPart != bodyPart) {
            int baseDamage = bodyPart.damage();
            int weaponDamage = weapon.damage();
            target.hitPoints -= (baseDamage + weaponDamage);
        }
    }

    public void equipWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}
