package com.vzurauskas.duelscripts2;

public interface Script {
    FighterBodyPart strike(Fighter self, Fighter opponent);
    BodyPart parry(Fighter self, Fighter opponent);
}
