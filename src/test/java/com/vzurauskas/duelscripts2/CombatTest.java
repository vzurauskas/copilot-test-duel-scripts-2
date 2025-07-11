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

    @Test
    void complexCombatScenarioWorksCorrectly() {
        Fighter alice = new Fighter("Alice", 100);
        Fighter bob = new Fighter("Bob", 100);
        
        alice.equipWeapon(new Weapon("Sword", 8));
        bob.equipWeapon(new Weapon("Axe", 6, 1.0));
        
        alice.parry(BodyPart.LEGS);
        bob.strike(alice, BodyPart.HEAD);
        bob.parry(BodyPart.HEAD);
        alice.strike(bob, BodyPart.TORSO);
        
        int bobExpectedHP = 100 - 8;
        assertEquals(bobExpectedHP, bob.hitPoints());
        
        int aliceExpectedHP = 100 - 20;
        assertEquals(aliceExpectedHP, alice.hitPoints());
        
        assertTrue(alice.isAlive());
        assertTrue(bob.isAlive());
    }
    
    @Test
    void combatReturnsResult() {
        Fighter alice = new Fighter("Alice", 100);
        Fighter bob = new Fighter("Bob", 100);
        
        Combat combat = new Combat(alice, bob);
        CombatResult result = combat.nextTurn();
        
        assertNotNull(result.description());
    }
    
    @Test
    void combatResultContainsDamageDealt() {
        Fighter alice = new Fighter("Alice", 100, new Script() {
            @Override
            public BodyPart strike(Fighter self, Fighter opponent) {
                return BodyPart.HEAD;
            }
            
            @Override
            public BodyPart parry(Fighter self, Fighter opponent) {
                return BodyPart.TORSO;
            }
        });
        
        Fighter bob = new Fighter("Bob", 100, new Script() {
            @Override
            public BodyPart strike(Fighter self, Fighter opponent) {
                return BodyPart.TORSO;
            }
            
            @Override
            public BodyPart parry(Fighter self, Fighter opponent) {
                return BodyPart.HEAD; // Block Alice's HEAD strike
            }
        });
        
        // Set up weapons - doesn't matter much since both strikes will be blocked
        alice.equipWeapon(new Weapon("Sword", 20, 1.0)); 
        bob.equipWeapon(new Weapon("Axe", 10)); 
        
        Combat combat = new Combat(alice, bob);
        CombatResult result = combat.nextTurn();
        
        assertTrue(result.description().contains("Alice: 0 damage"));
        assertTrue(result.description().contains("Bob: 0 damage"));
    }
    
    @Test
    void combatResultContainsStrikeOutcomes() {
        Fighter alice = new Fighter("Alice", 100, new Script() {
            @Override
            public BodyPart strike(Fighter self, Fighter opponent) {
                return BodyPart.HEAD;
            }
            
            @Override
            public BodyPart parry(Fighter self, Fighter opponent) {
                return BodyPart.TORSO;
            }
        });
        
        Fighter bob = new Fighter("Bob", 100, new Script() {
            @Override
            public BodyPart strike(Fighter self, Fighter opponent) {
                return BodyPart.TORSO;
            }
            
            @Override
            public BodyPart parry(Fighter self, Fighter opponent) {
                return BodyPart.LEGS; // Don't parry HEAD so Alice's strike lands
            }
        });
        
        alice.equipWeapon(new Weapon("Sword", 10, 1.0));
        bob.equipWeapon(new Weapon("Axe", 10));
        
        Combat combat = new Combat(alice, bob);
        CombatResult result = combat.nextTurn();
        
        String description = result.description();
        
        assertTrue(
            description.contains("Alice strikes Bob's HEAD with Sword for 34 damage (critical hit!)"),
            "Expected Alice's strike description. Actual: " + description
        );
        assertTrue(
            description.contains("Bob strikes Alice's TORSO with Axe for 0 damage - BLOCKED by parry."),
            "Expected Bob's blocked strike description. Actual: " + description
        );
    }
    
    @Test
    void combatResultForDifferentFightersContainsStrikeOutcomes() {
        Fighter charlie = new Fighter("Charlie", 100, new CharlieScript());
        Fighter diana = new Fighter("Diana", 100, new DianaScript());
        
        charlie.equipWeapon(new Weapon("Mace", 15));
        diana.equipWeapon(new Weapon("Dagger", 8, 1.0));
        
        Combat combat = new Combat(charlie, diana);
        CombatResult result = combat.nextTurn();
        
        String description = result.description();
        
        assertTrue(
            description.contains("Charlie strikes Diana's TORSO with Mace for 0 damage - BLOCKED by parry."),
            "Expected description to contain Charlie's blocked strike. Actual: " + description
        );
        assertTrue(
            description.contains("Diana strikes Charlie's LEGS with Dagger for 8 damage (critical hit!)"),
            "Expected description to contain Diana's critical strike. Actual: " + description
        );
    }
    
    @Test
    void fighterKeepsHistoryOfStrikesCarriedOut() {
        Fighter alice = new Fighter("Alice", 100);
        Fighter bob = new Fighter("Bob", 100);
        
        alice.strike(bob, BodyPart.HEAD);
        
        assertEquals(1, alice.strikesCarriedOut().size());
        Strike firstStrike = alice.strikesCarriedOut().get(0);
        assertEquals(BodyPart.HEAD, firstStrike.target());
        assertEquals(5, firstStrike.damage());
        assertFalse(firstStrike.wasCriticalHit());
        assertFalse(firstStrike.wasParried());
        
        bob.parry(BodyPart.TORSO);
        alice.strike(bob, BodyPart.TORSO);
        
        assertEquals(2, alice.strikesCarriedOut().size());
        Strike secondStrike = alice.strikesCarriedOut().get(1);
        assertEquals(BodyPart.TORSO, secondStrike.target());
        assertEquals(0, secondStrike.damage());
        assertFalse(secondStrike.wasCriticalHit());
        assertTrue(secondStrike.wasParried());
    }
    
    @Test
    void fighterKeepsHistoryOfStrikesSuffered() {
        Fighter alice = new Fighter("Alice", 100);
        Fighter bob = new Fighter("Bob", 100);
        
        alice.equipWeapon(new Weapon("Critical Sword", 4, 1.0));
        alice.strike(bob, BodyPart.HEAD);
        
        assertEquals(1, bob.strikesSuffered().size());
        Strike firstStrike = bob.strikesSuffered().get(0);
        assertEquals(BodyPart.HEAD, firstStrike.target());
        assertEquals(12, firstStrike.damage());
        assertTrue(firstStrike.wasCriticalHit());
        assertFalse(firstStrike.wasParried());
        
        alice.equipWeapon(new Weapon("Normal Sword", 3));
        bob.parry(BodyPart.HEAD);
        alice.strike(bob, BodyPart.TORSO);
        
        assertEquals(2, bob.strikesSuffered().size());
        Strike secondStrike = bob.strikesSuffered().get(1);
        assertEquals(BodyPart.TORSO, secondStrike.target());
        assertEquals(3, secondStrike.damage());
        assertFalse(secondStrike.wasCriticalHit());
        assertFalse(secondStrike.wasParried());
    }
}
