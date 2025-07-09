package com.vzurauskas.duelscripts2;

public class Fighter {
    private final String name;
    private int hitPoints;
    private BodyPart parryingBodyPart;

    public Fighter(String name, int hitPoints) {
        this.name = name;
        this.hitPoints = hitPoints;
        this.parryingBodyPart = BodyPart.TORSO;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void parry(BodyPart bodyPart) {
        this.parryingBodyPart = bodyPart;
    }

    public void strike(Fighter target, BodyPart bodyPart) {
        if (target.parryingBodyPart != bodyPart) {
            target.hitPoints -= bodyPart.getDamage();
        }
    }
}
