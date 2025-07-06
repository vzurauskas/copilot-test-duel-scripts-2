package com.vzurauskas.duelscripts2;

public class Fighter {
    private final String name;
    private int hitPoints;

    public Fighter(String name, int hitPoints) {
        this.name = name;
        this.hitPoints = hitPoints;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void strike(Fighter target, BodyPart bodyPart) {
        target.hitPoints -= 10;
    }
}
