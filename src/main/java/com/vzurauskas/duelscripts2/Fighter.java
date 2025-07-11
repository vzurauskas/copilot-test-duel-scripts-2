package com.vzurauskas.duelscripts2;

import java.util.ArrayList;
import java.util.List;

public class Fighter {
    private final String name;
    private int hitPoints;
    private BodyPart parryingBodyPart;
    private Weapon weapon;
    private final List<Strike> strikesCarriedOut;

    public Fighter(String name, int hitPoints) {
        this.name = name;
        this.hitPoints = hitPoints;
        this.parryingBodyPart = BodyPart.TORSO;
        this.weapon = new Weapon("Fist", 3);
        this.strikesCarriedOut = new ArrayList<>();
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
        boolean wasParried = target.parryingBodyPart == bodyPart;
        int damage = wasParried ? 0 : weapon.damageFor(bodyPart);
        boolean wasCriticalHit = false;
        
        strikesCarriedOut.add(
            new Strike(bodyPart, damage, wasCriticalHit, wasParried)
        );
        if (!wasParried) {
            target.hitPoints -= damage;
        }
    }

    public List<Strike> strikesCarriedOut() {
        return strikesCarriedOut;
    }

    public void equipWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}
