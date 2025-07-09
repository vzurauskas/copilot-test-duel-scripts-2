package com.vzurauskas.duelscripts2;

public enum BodyPart {
    HEAD(15), TORSO(10), LEGS(5);
    
    private final int damage;
    
    BodyPart(int damage) {
        this.damage = damage;
    }
    
    public int damage() {
        return damage;
    }
}
