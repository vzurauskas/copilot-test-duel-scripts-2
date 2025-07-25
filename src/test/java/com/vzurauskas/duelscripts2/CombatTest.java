package com.vzurauskas.duelscripts2;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CombatTest {

    @Test
    void oneStrikesAnotherInHead() {
        Fighter alice = new Fighter(
            "Alice", 100, 
            new FixedScript(Fighter::head, Fighter::torso)
        );
        Fighter bob = new Fighter("Bob", 100);
        
        alice.strike(bob);
        
        assertTrue(bob.hitPoints() < 100);
    }

    @Test
    void parryBlocksStrike() {
        Fighter alice = new Fighter(
            "Alice", 100, 
            new FixedScript(Fighter::head, Fighter::torso)
        );
        Fighter bob = new Fighter(
            "Bob", 100, 
            new FixedScript(Fighter::torso, Fighter::head)
        );
        
        bob.parry(alice);
        alice.strike(bob);
        
        assertEquals(100, bob.hitPoints());
    }

    @Test
    void bothStrikesLandWhenNoParrying() {
        Fighter alice = new Fighter(
            "Alice", 100, 
            new FixedScript(Fighter::torso, Fighter::legs)
        );
        Fighter bob = new Fighter(
            "Bob", 100, 
            new FixedScript(Fighter::head, Fighter::head)
        );
        
        alice.parry(bob);
        bob.strike(alice);
        bob.parry(alice);
        alice.strike(bob);
        
        assertTrue(alice.hitPoints() < 100);
        assertTrue(bob.hitPoints() < 100);
    }

    @Test
    void bothStrikesAreBlockedWhenParrying() {
        Fighter alice = new Fighter(
            "Alice", 100, 
            new FixedScript(Fighter::torso, Fighter::head)
        );
        Fighter bob = new Fighter(
            "Bob", 100, 
            new FixedScript(Fighter::head, Fighter::torso)
        );
        
        alice.parry(bob);
        bob.strike(alice);
        bob.parry(alice);
        alice.strike(bob);
        
        assertEquals(100, alice.hitPoints());
        assertEquals(100, bob.hitPoints());
    }

    @Test
    void damageVariesByBodyPart() {
        Fighter alice = new Fighter(
            "Alice", 100,
            new FixedScript(
                List.of(Fighter::head, Fighter::torso, Fighter::legs), 
                List.of(Fighter::torso)
            )
        );
        Fighter bob = new Fighter(
            "Bob", 100, 
            new FixedScript(Fighter::torso, Fighter::torso)
        );
        Fighter charlie = new Fighter(
            "Charlie", 100, 
            new FixedScript(Fighter::torso, Fighter::head)
        );
        Fighter dave = new Fighter(
            "Dave", 100, 
            new FixedScript(Fighter::torso, Fighter::head)
        );
        
        bob.parry(alice);
        alice.strike(bob);
        charlie.parry(alice);
        alice.strike(charlie);
        dave.parry(alice);
        alice.strike(dave);

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
        Fighter bob = new Fighter(
            "Bob", 100, 
            new FixedScript(Fighter::head, Fighter::torso)
        );
        
        assertTrue(alice.isAlive());
        
        bob.strike(alice);
        
        assertFalse(alice.isAlive());
    }

    @Test
    void weaponAffectsDamageCalculation() {
        Fighter alice = new Fighter(
            "Alice", 100, 
            new FixedScript(Fighter::head, Fighter::torso)
        );
        Fighter bob = new Fighter("Bob", 100);
        Fighter charlie = new Fighter(
            "Charlie", 100, 
            new FixedScript(Fighter::head, Fighter::torso)
        );
        
        // Alice has no weapon (base damage)
        alice.strike(bob);
        int baseDamage = 100 - bob.hitPoints();
        
        // Charlie has a stronger weapon
        charlie.equipWeapon(new Weapon("Sword", 5));
        charlie.strike(alice);
        int weaponDamage = 100 - alice.hitPoints();
        
        assertTrue(weaponDamage > baseDamage);
    }

    @Test
    void damageCalculationUsesMultiplicativeFormula() {
        Fighter alice = new Fighter(
            "Alice", 100, 
            new FixedScript(Fighter::head, Fighter::torso)
        );
        Fighter bob = new Fighter("Bob", 100);
        
        alice.equipWeapon(new Weapon("Sword", 12));
        
        alice.strike(bob);
        int expectedDamage = (int)(12 * 1.7); 
        int actualDamage = 100 - bob.hitPoints();
        
        assertEquals(expectedDamage, actualDamage);
    }

    @Test
    void criticalHitDealsDoubleDamage() {
        Fighter alice = new Fighter(
            "Alice", 100, 
            new FixedScript(Fighter::head, Fighter::torso)
        );
        Fighter bob = new Fighter("Bob", 100);
        
        // Weapon with 100% critical hit chance for testing
        Weapon criticalWeapon = new Weapon("Critical Sword", 10, 1.0);
        alice.equipWeapon(criticalWeapon);
        
        alice.strike(bob);
        int actualDamage = 100 - bob.hitPoints();
        
        int expectedCriticalDamage = (int)((10 * 1.7) * 2);
        assertEquals(expectedCriticalDamage, actualDamage);
    }

    @Test
    void complexCombatScenarioWorksCorrectly() {
        Fighter alice = new Fighter(
            "Alice", 100, 
            new FixedScript(Fighter::torso, Fighter::legs)
        );
        Fighter bob = new Fighter(
            "Bob", 100, 
            new FixedScript(Fighter::head, Fighter::head)
        );
        
        alice.equipWeapon(new Weapon("Sword", 8));
        bob.equipWeapon(new Weapon("Axe", 6, 1.0));
        
        alice.parry(bob);
        bob.strike(alice);
        bob.parry(alice);
        alice.strike(bob);
        
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
        TurnResult result = combat.nextTurn();
        
        assertNotNull(result.description());
    }
    
    @Test
    void combatResultContainsDamageDealt() {
        Fighter alice = new Fighter(
            "Alice", 100, 
            new FixedScript(Fighter::head, Fighter::torso)
        );
        
        Fighter bob = new Fighter(
            "Bob", 100, 
            new FixedScript(Fighter::torso, Fighter::head)
        );
        
        // Set up weapons - doesn't matter much since both 
        // strikes will be blocked
        alice.equipWeapon(new Weapon("Sword", 20, 1.0)); 
        bob.equipWeapon(new Weapon("Axe", 10));
        
        Combat combat = new Combat(alice, bob);
        TurnResult result = combat.nextTurn();
        
        assertTrue(result.description().contains("Alice: 0 damage"));
        assertTrue(result.description().contains("Bob: 0 damage"));
    }
    
    @Test
    void combatResultContainsStrikeOutcomes() {
        Fighter alice = new Fighter(
            "Alice", 100, 
            new FixedScript(Fighter::head, Fighter::torso)
        );
        
        Fighter bob = new Fighter(
            "Bob", 100, 
            new FixedScript(Fighter::torso, Fighter::legs)
        );
        
        alice.equipWeapon(new Weapon("Sword", 10, 1.0));
        bob.equipWeapon(new Weapon("Axe", 10));
        
        Combat combat = new Combat(alice, bob);
        TurnResult result = combat.nextTurn();
        
        String description = result.description();
        
        assertTrue(
            description.contains(
                "Alice strikes Bob's head with Sword for 34 damage " +
                "(critical hit!)"
            ),
            "Expected Alice's strike description. Actual: " + description
        );
        assertTrue(
            description.contains(
                "Bob strikes Alice's torso with Axe for 0 damage - " +
                "BLOCKED by parry."
            ),
            "Expected Bob's blocked strike description. Actual: " + description
        );
    }
    
    @Test
    void combatResultForDifferentFightersContainsStrikeOutcomes() {
        Fighter charlie = new Fighter(
            "Charlie", 100, 
            new FixedScript(Fighter::torso, Fighter::head)
        );
        Fighter diana = new Fighter(
            "Diana", 100, 
            new FixedScript(Fighter::legs, Fighter::torso)
        );
        
        charlie.equipWeapon(new Weapon("Mace", 15));
        diana.equipWeapon(new Weapon("Dagger", 8, 1.0));
        
        Combat combat = new Combat(charlie, diana);
        TurnResult result = combat.nextTurn();
        
        String description = result.description();
        
        assertTrue(
            description.contains(
                "Charlie strikes Diana's torso with Mace for 0 damage - " +
                "BLOCKED by parry."
            ),
            "Expected description to contain Charlie's blocked strike. " +
                "Actual: " + description
        );
        assertTrue(
            description.contains(
                "Diana strikes Charlie's legs with Dagger for 8 damage " +
                "(critical hit!)"
            ),
            "Expected description to contain Diana's critical strike. " +
                "Actual: " + description
        );
    }
    
    @Test
    void fighterKeepsHistoryOfStrikesCarriedOut() {
        Fighter alice = new Fighter(
            "Alice", 100,
            new FixedScript(
                List.of(Fighter::head, Fighter::torso), 
                List.of(Fighter::torso)
            )
        );
        Fighter bob = new Fighter(
            "Bob", 100, new FixedScript(Fighter::torso, Fighter::torso)
        );
        
        alice.strike(bob);
        
        assertEquals(1, alice.strikesCarriedOut().size());
        Strike expectedFirstStrike = Strike.hit(
            alice, bob.head(), 5, false
        );
        assertEquals(expectedFirstStrike, alice.strikesCarriedOut().get(0));
        
        bob.parry(alice);
        alice.strike(bob);
        
        assertEquals(2, alice.strikesCarriedOut().size());
        Strike expectedSecondStrike = Strike.parried(alice, bob.torso());
        assertEquals(expectedSecondStrike, alice.strikesCarriedOut().get(1));
    }
    
    @Test
    void fighterKeepsHistoryOfStrikesSuffered() {
        Fighter alice = new Fighter(
            "Alice", 100, 
            new FixedScript(
                List.of(Fighter::head, Fighter::torso), 
                List.of(Fighter::torso)
            )
        );
        Fighter bob = new Fighter(
            "Bob", 100,
            new FixedScript(
                List.of(Fighter::torso), 
                List.of(Fighter::head)
            )
        );
        
        alice.equipWeapon(new Weapon("Critical Sword", 4, 1.0));
        alice.strike(bob);
        
        assertEquals(1, bob.strikesSuffered().size());
        Strike expectedFirstStrike = Strike.hit(
            alice, bob.head(), 12, true
        );
        assertEquals(expectedFirstStrike, bob.strikesSuffered().get(0));
        
        alice.equipWeapon(new Weapon("Normal Sword", 3));
        bob.parry(alice);
        alice.strike(bob);
        
        assertEquals(2, bob.strikesSuffered().size());
        Strike expectedSecondStrike = Strike.hit(
            alice, bob.torso(), 3, false
        );
        assertEquals(expectedSecondStrike, bob.strikesSuffered().get(1));
    }
    
    @Test
    void fightContinuesUntilOneFighterDies() {
        Fighter alice = new Fighter("Alice", 150);
        Fighter bob = new Fighter("Bob", 100);
        
        Combat combat = new Combat(alice, bob);
        CombatResult result = combat.fight();
        
        assertTrue(
            !alice.isAlive() || !bob.isAlive(),
            "One fighter should be dead"
        );
        assertTrue(
            alice.isAlive() || bob.isAlive(),
            "One fighter should be alive"
        );
    }
    
    @Test
    void combatResultContainsWinnerInformation() {
        Fighter alice = new Fighter("Alice", 150);
        Fighter bob = new Fighter("Bob", 100);
        
        Combat combat = new Combat(alice, bob);
        CombatResult result = combat.fight();
        
        String output = result.toString();
        assertTrue(
            output.contains("=== Final Result ==="),
            "Result should contain final result section but got: " + output
        );
        assertTrue(
            output.contains("Alice wins"),
            "Result should identify Alice as winner"
        );
        assertTrue(
            output.contains("Fight is over"),
            "Result should indicate fight is over"
        );
    }
    
    @Test
    void combatResultContainsTurnHistory() {
        Fighter alice = new Fighter("Alice", 150);
        Fighter bob = new Fighter("Bob", 100);
        
        Combat combat = new Combat(alice, bob);
        CombatResult result = combat.fight();
        
        String output = result.toString();
        
        assertTrue(
            output.contains("=== Turn 1 ==="),
            "Result should contain turn headers but got: " + output
        );
        assertTrue(
            output.contains("Alice strikes Bob's head with Fist"),
            "Result should contain Alice's strike description but got: " + output
        );
        assertTrue(
            output.contains("Bob strikes Alice's head with Fist"),
            "Result should contain Bob's strike description but got: " + output
        );
        assertTrue(
            output.contains("5 damage"),
            "Result should contain specific damage amounts but got: " + output
        );
        assertTrue(
            output.contains("Alice: 5 damage") || output.contains("Bob: 5 damage"),
            "Result should contain damage summary for fighters but got: " + output
        );
    }
    
    @Test
    void combatHandlesUnevenFighterStrengths() {
        // Fighter with stronger weapon should have advantage
        Fighter alice = new Fighter("Alice", 100);
        Fighter bob = new Fighter("Bob", 100);
        
        alice.equipWeapon(new Weapon("Strong Sword", 20));
        bob.equipWeapon(new Weapon("Weak Dagger", 5));
        
        Combat combat = new Combat(alice, bob);
        CombatResult result = combat.fight();
        
        assertTrue(
            alice.isAlive() && !bob.isAlive(),
            "Alice with stronger weapon should win but got: " + result.toString()
        );
    }
    
    @Test
    void fighterWithMoreHitPointsHasAdvantage() {
        Fighter charlie = new Fighter("Charlie", 200);
        Fighter diana = new Fighter("Diana", 50);
        
        Combat combat = new Combat(charlie, diana);
        CombatResult result = combat.fight();
        
        assertTrue(
            charlie.isAlive() && !diana.isAlive(),
            "Charlie with more HP should win but got: " + result.toString()
        );
    }
}
