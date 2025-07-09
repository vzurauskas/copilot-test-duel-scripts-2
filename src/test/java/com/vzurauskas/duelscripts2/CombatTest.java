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

    @Test
    void parryBlocksStrike() {
        Fighter alice = new Fighter("Alice", 100);
        Fighter bob = new Fighter("Bob", 100);
        
        bob.parry(BodyPart.HEAD);
        alice.strike(bob, BodyPart.HEAD);
        
        assertEquals(100, bob.getHitPoints());
    }

    @Test
    void bothStrikesLandWhenNoParrying() {
        Fighter alice = new Fighter("Alice", 100);
        Fighter bob = new Fighter("Bob", 100);
        
        alice.parry(BodyPart.LEGS);
        bob.strike(alice, BodyPart.HEAD);
        bob.parry(BodyPart.HEAD);
        alice.strike(bob, BodyPart.TORSO);
        
        assertTrue(alice.getHitPoints() < 100);
        assertTrue(bob.getHitPoints() < 100);
    }

    @Test
    void bothStrikesAreBlockedWhenParrying() {
        Fighter alice = new Fighter("Alice", 100);
        Fighter bob = new Fighter("Bob", 100);
        
        alice.parry(BodyPart.HEAD);
        bob.strike(alice, BodyPart.HEAD);
        bob.parry(BodyPart.TORSO);
        alice.strike(bob, BodyPart.TORSO);
        
        assertEquals(100, alice.getHitPoints());
        assertEquals(100, bob.getHitPoints());
    }
}
