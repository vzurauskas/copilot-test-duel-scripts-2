package com.vzurauskas.duelscripts2;

import java.util.ArrayList;
import java.util.List;

public class Fighter {
    private final String name;
    private int hitPoints;
    private BodyPart parryingBodyPart;
    private Weapon weapon;
    private final List<Strike> strikesCarriedOut;
    private final List<Strike> strikesSuffered;

    public Fighter(String name, int hitPoints) {
        this.name = name;
        this.hitPoints = hitPoints;
        this.parryingBodyPart = BodyPart.TORSO;
        this.weapon = new Weapon("Fist", 3);
        this.strikesCarriedOut = new ArrayList<>();
        this.strikesSuffered = new ArrayList<>();
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
        if (wasParried) {
            Strike strike = new Strike(bodyPart, 0, false, true);
            strikesCarriedOut.add(strike);
            target.strikesSuffered.add(strike);
        } else {
            WeaponStrike weaponStrike = weapon.strikeFor(bodyPart);
            Strike strike = new Strike(
                bodyPart, weaponStrike.damage(), weaponStrike.isCritical(), false
            );
            strikesCarriedOut.add(strike);
            target.strikesSuffered.add(strike);
            
            target.hitPoints -= weaponStrike.damage();
        }
    }

    public List<Strike> strikesCarriedOut() {
        return strikesCarriedOut;
    }

    public List<Strike> strikesSuffered() {
        return strikesSuffered;
    }

    public void equipWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}
