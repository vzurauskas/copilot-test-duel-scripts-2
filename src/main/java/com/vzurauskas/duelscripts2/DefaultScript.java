package com.vzurauskas.duelscripts2;

public class DefaultScript implements Script {
    @Override
    public FighterBodyPart strike(Fighter self, Fighter opponent) {
        return opponent.head();
    }
    
    @Override
    public FighterBodyPart parry(Fighter self, Fighter opponent) {
        return self.torso();
    }
}
