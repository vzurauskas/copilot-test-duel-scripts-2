package com.vzurauskas.duelscripts2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CombatTest {

    @Test
    void oneStrikesAnotherInHead() {
        Fighter alice = new Fighter("Alice", 100);
        Fighter bob = new Fighter("Bob", 100);
        
        alice.strike(bob, BodyPart.HEAD);
        
        assertTrue(bob.getHitPoints() < 100);
    }
}
