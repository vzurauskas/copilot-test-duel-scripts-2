package com.vzurauskas.duelscripts2;

public class DianaScript implements Script {
    @Override
    public BodyPart strike(Fighter self, Fighter opponent) {
        return BodyPart.LEGS;
    }
    
    @Override
    public BodyPart parry(Fighter self, Fighter opponent) {
        return BodyPart.TORSO;
    }
}
