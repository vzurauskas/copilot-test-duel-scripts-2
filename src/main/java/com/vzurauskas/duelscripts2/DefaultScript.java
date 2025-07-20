package com.vzurauskas.duelscripts2;

public class DefaultScript implements Script {
    @Override
    public BodyPart strike(Fighter self, Fighter opponent) {
        return opponent.head();
    }

    @Override
    public BodyPart parry(Fighter self, Fighter opponent) {
        return self.torso();
    }
}