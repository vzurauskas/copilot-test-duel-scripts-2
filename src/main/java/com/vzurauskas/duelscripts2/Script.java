package com.vzurauskas.duelscripts2;

public interface Script {
    BodyPart strike(Fighter self, Fighter opponent);
    BodyPart parry(Fighter self, Fighter opponent);
}
