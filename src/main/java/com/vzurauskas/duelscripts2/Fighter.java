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
        FighterBodyPart parryBodyPart = script.parry(this, opponent);
        this.parryingBodyPart = parryBodyPart.bodyPart();
    }

    public void strike(Fighter target) {
        FighterBodyPart targetBodyPart = script.strike(this, target);
        this.strike(targetBodyPart);
    }

    public void strike(FighterBodyPart target) {
        Strike strike = weapon.strike(this, target);
        strikesCarriedOut.add(strike);
        target.fighter().takeHit(strike);
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

    public Strike lastStrikeCarriedOut() {
        return strikesCarriedOut.get(strikesCarriedOut.size() - 1);
    }

    public List<Strike> strikesSuffered() {
        return strikesSuffered;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public BodyPart parryingBodyPart() {
        return parryingBodyPart;
    }

    public FighterBodyPart head() {
        return new FighterBodyPart(this, BodyPart.HEAD);
    }

    public FighterBodyPart torso() {
        return new FighterBodyPart(this, BodyPart.TORSO);
    }

    public FighterBodyPart legs() {
        return new FighterBodyPart(this, BodyPart.LEGS);
    }

    public FighterBodyPart parryingFighterBodyPart() {
        return new FighterBodyPart(this, parryingBodyPart);
    }

    public FighterBodyPart bodyPart(BodyPart bodyPart) {
        return new FighterBodyPart(this, bodyPart);
    }
}
