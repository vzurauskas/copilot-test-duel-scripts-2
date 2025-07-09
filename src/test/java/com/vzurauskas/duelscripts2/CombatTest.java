package com.vzurauskas.duelscripts2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CombatTest {

    @Test
    void oneStrikesAnotherInHead() {
        Fighter alice = new Fighter("Alice", 100);
        Fighter bob = new Fighter("Bob", 100);
        
        alice.strike(bob, BodyPart.HEAD);
        
        assertTrue(bob.hitPoints() < 100);
    }

    @Test
    void parryBlocksStrike() {
        Fighter alice = new Fighter("Alice", 100);
        Fighter bob = new Fighter("Bob", 100);
        
        bob.parry(BodyPart.HEAD);
        alice.strike(bob, BodyPart.HEAD);
        
        assertEquals(100, bob.hitPoints());
    }

    @Test
    void bothStrikesLandWhenNoParrying() {
        Fighter alice = new Fighter("Alice", 100);
        Fighter bob = new Fighter("Bob", 100);
        
        alice.parry(BodyPart.LEGS);
        bob.strike(alice, BodyPart.HEAD);
        bob.parry(BodyPart.HEAD);
        alice.strike(bob, BodyPart.TORSO);
        
        assertTrue(alice.hitPoints() < 100);
        assertTrue(bob.hitPoints() < 100);
    }

    @Test
    void bothStrikesAreBlockedWhenParrying() {
        Fighter alice = new Fighter("Alice", 100);
        Fighter bob = new Fighter("Bob", 100);
        
        alice.parry(BodyPart.HEAD);
        bob.strike(alice, BodyPart.HEAD);
        bob.parry(BodyPart.TORSO);
        alice.strike(bob, BodyPart.TORSO);
        
        assertEquals(100, alice.hitPoints());
        assertEquals(100, bob.hitPoints());
    }

    @Test
    void damageVariesByBodyPart() {
        Fighter alice = new Fighter("Alice", 100);
        Fighter bob = new Fighter("Bob", 100);
        Fighter charlie = new Fighter("Charlie", 100);
        Fighter dave = new Fighter("Dave", 100);
        
        bob.parry(BodyPart.TORSO);
        alice.strike(bob, BodyPart.HEAD);
        charlie.parry(BodyPart.HEAD);
        alice.strike(charlie, BodyPart.TORSO);
        dave.parry(BodyPart.HEAD);
        alice.strike(dave, BodyPart.LEGS);

        int headDamage = 100 - bob.hitPoints();
        int torsoDamage = 100 - charlie.hitPoints();
        int legsDamage = 100 - dave.hitPoints();
        
        assertTrue(headDamage > torsoDamage);
        assertTrue(torsoDamage > legsDamage);
    }

    @Test
    void fighterHasNameAndHitPoints() {
        Fighter fighter = new Fighter("Alice", 100);
        
        assertEquals("Alice", fighter.name());
        assertEquals(100, fighter.hitPoints());
        assertTrue(fighter.isAlive());
    }
}
