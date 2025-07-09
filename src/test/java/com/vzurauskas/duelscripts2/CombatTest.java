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

    @Test
    void fighterDiesWhenHitPointsReachZero() {
        Fighter alice = new Fighter("Alice", 1);
        Fighter bob = new Fighter("Bob", 100);
        
        assertTrue(alice.isAlive());
        
        bob.strike(alice, BodyPart.HEAD);
        
        assertFalse(alice.isAlive());
    }

    @Test
    void weaponAffectsDamageCalculation() {
        Fighter alice = new Fighter("Alice", 100);
        Fighter bob = new Fighter("Bob", 100);
        Fighter charlie = new Fighter("Charlie", 100);
        
        // Alice has no weapon (base damage)
        alice.strike(bob, BodyPart.HEAD);
        int baseDamage = 100 - bob.hitPoints();
        
        // Charlie has a stronger weapon
        charlie.equipWeapon(new Weapon("Sword", 5));
        charlie.strike(alice, BodyPart.HEAD);
        int weaponDamage = 100 - alice.hitPoints();
        
        assertTrue(weaponDamage > baseDamage);
    }

    @Test
    void damageCalculationUsesMultiplicativeFormula() {
        Fighter alice = new Fighter("Alice", 100);
        Fighter bob = new Fighter("Bob", 100);
        
        alice.equipWeapon(new Weapon("Sword", 12));
        
        alice.strike(bob, BodyPart.HEAD);
        int expectedDamage = (int)(12 * 1.7); 
        int actualDamage = 100 - bob.hitPoints();
        
        assertEquals(expectedDamage, actualDamage);
    }

    @Test
    void criticalHitDealsDoubleDamage() {
        Fighter alice = new Fighter("Alice", 100);
        Fighter bob = new Fighter("Bob", 100);
        
        // Weapon with 100% critical hit chance for testing
        Weapon criticalWeapon = new Weapon("Critical Sword", 10, 1.0);
        alice.equipWeapon(criticalWeapon);
        
        alice.strike(bob, BodyPart.HEAD);
        int actualDamage = 100 - bob.hitPoints();
        
        int expectedCriticalDamage = (int)((10 * 1.7) * 2);
        assertEquals(expectedCriticalDamage, actualDamage);
    }
}
