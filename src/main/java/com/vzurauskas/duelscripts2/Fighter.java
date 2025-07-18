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
    private Script script;

    public Fighter(String name, int hitPoints) {
        this(name, hitPoints, new DefaultScript());
    }

    public Fighter(String name, int hitPoints, Script script) {
        this.name = name;
        this.hitPoints = hitPoints;
        this.parryingBodyPart = BodyPart.TORSO;
        this.weapon = new Weapon("Fist", 3);
        this.strikesCarriedOut = new ArrayList<>();
        this.strikesSuffered = new ArrayList<>();
        this.script = script;
    }

    public void equipWeapon(Weapon weapon) {
        this.weapon = weapon;
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

    public void parry(Fighter opponent) {
        BodyPart parryBodyPart = script.parry(this, opponent);
        this.parryingBodyPart = parryBodyPart;
    }

    public void strike(Fighter target) {
        BodyPart targetBodyPart = script.strike(this, target);
        Strike strike = weapon.strike(
            this, targetBodyPart, target.parryingBodyPart
        );
        strikesCarriedOut.add(strike);
        target.takeHit(strike);
    }

    public void takeHit(Strike strike) {
        strikesSuffered.add(strike);
        if (!strike.wasParried()) {
            hitPoints -= strike.damage();
        }
    }

    public List<Strike> strikesCarriedOut() {
        return strikesCarriedOut;
    }

    public List<Strike> strikesSuffered() {
        return strikesSuffered;
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
