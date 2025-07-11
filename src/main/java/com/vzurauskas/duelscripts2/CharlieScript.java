package com.vzurauskas.duelscripts2;

public class CharlieScript implements Script {
    @Override
    public BodyPart strike(Fighter self, Fighter opponent) {
        return BodyPart.TORSO;
    }
    
    @Override
    public BodyPart parry(Fighter self, Fighter opponent) {
        return BodyPart.HEAD;
    }
}
