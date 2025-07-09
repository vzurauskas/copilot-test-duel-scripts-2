package com.vzurauskas.duelscripts2;

public enum BodyPart {
    HEAD(1.7), TORSO(1.0), LEGS(0.5);
    
    private final double multiplier;
    
    BodyPart(double multiplier) {
        this.multiplier = multiplier;
    }
    
    public double multiplier() {
        return multiplier;
    }
}
