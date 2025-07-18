package com.vzurauskas.duelscripts2;

public interface Script {
    FighterBodyPart strike(Fighter self, Fighter opponent);
    FighterBodyPart parry(Fighter self, Fighter opponent);
}
