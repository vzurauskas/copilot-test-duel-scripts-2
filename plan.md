# Duel Scripts 2 - TDD Implementation Plan

## Goal
Implement a turn-based combat system where two fighters can engage in combat, strike each other, and take damage based on body part targeting and parrying mechanics.

## Implementation Plan

### Phase 1: Core Combat Turn
**Objective**: Two fighters can engage in a single turn of combat

#### Test 1: Simple Single Strike
- [x] `oneStrikesAnotherInHead()`
  - Two fighters with 100 HP each
  - Alice strikes Bob's HEAD
  - No parrying mechanics yet
  - Bob should take damage and have reduced HP

#### Test 2: Basic Parry Blocks Strike
- [x] `parryBlocksStrike()`
  - Two fighters with 100 HP each
  - Bob parries HEAD
  - Alice strikes Bob's HEAD
  - Bob should take no damage

#### Test 3: Basic Combat Turn with Both Strikes Landing
- [x] `bothStrikesLandWhenNoParrying()`
  - Two fighters with 100 HP each
  - Alice strikes Bob's HEAD, parries LEGS
  - Bob strikes Alice's TORSO, parries HEAD  
  - Both strikes land (no parrying blocks them)
  - Both fighters should have reduced HP

#### Test 4: Basic Combat Turn with Both Strikes Parried
- [x] `bothStrikesAreBlockedWhenParrying()`

#### Test 5: Damage Varies by Body Part
- [x] `damageVariesByBodyPart()`
  - Test HEAD strikes deal more damage than TORSO
  - Test TORSO strikes deal more damage than LEGS
  - Verify damage multipliers work correctly

### Phase 2: Fighter Properties and State
**Objective**: Fighters have proper attributes and state management

#### Test 6: Fighter Creation and Properties
- [x] `fighterHasNameAndHitPoints()`
  - Fighter has name, hit points
  - Can retrieve all properties correctly
  - Fighter starts alive with full HP

#### Test 7: Fighter Death State
- [x] `fighterDiesWhenHitPointsReachZero()`
  - Fighter with 1 HP takes damage
  - Fighter becomes dead (not alive)

### Phase 3: Weapon System
**Objective**: Fighters use weapons that affect damage calculation

#### Test 8: Basic Weapon Damage
- [x] `weaponAffectsDamageCalculation()`
  - Fighter with stronger weapon deals more damage
  - Base damage calculation includes weapon damage

#### Test 9: Damage calculation formula
- [x] `damageCalculationUsesMultiplicativeFormula()`
  - Hit damage = weapon damage * body part modifier.

#### Test 10: Critical Hit System
- [x] `criticalHitDealsDoubleDamage()`
  - Weapons have critical hit chance
  - Critical hits deal double damage
  - Critical hit calculation works correctly

### Phase 4: Complete Combat System
**Objective**: Full turn resolution with all mechanics

#### Test 11: Complex Combat Scenario
- [x] `complexCombatScenarioWorksCorrectly()`
  - Multiple mechanics working together
  - Weapon damage + body part multipliers + critical hits
  - Proper damage calculation and HP updates

#### Test 12: Basic Combat Result
- [x] `combatReturnsResult()`
  - Combat method returns a CombatResult object
  - Result contains basic outcome information

#### Test 13: Combat Result Damage Information
- [x] `combatResultContainsDamageDealt()`
  - Result includes damage dealt to each fighter

#### Test 14: Fighter keeps history of strikes carried out.
- [x] `fighterKeepsHistoryOfStrikesCarriedOut()`
  - After Alice strikes Bob, Alice remembers the strike.
  - After Alice strikes Bob a second time, she remembers both strikes.

#### Test 15: Fighter keeps history of strikes suffered.
- [x] `fighterKeepsHistoryOfStrikesSuffered()`
  - After Alice strikes Bob, Bob remembers the strike.
  - After Alice strikes Bob a second time, Bob remembers both strikes.
  - One of the strikes is critical hit.

#### Test 16: Combat Result Strike Information
- [x] `combatResultContainsStrikeOutcomes()`
  - Result shows which strikes landed vs blocked

#### Test 17: Combat Result Strike Information with different fighters
- [x] `combatResultForDifferentFightersContainsStrikeOutcomes()`
  - Different fighters than in previous test.
  - Result shows which strikes landed vs blocked.

## Success Criteria
- All tests pass
- Clean, readable code with no duplication
- Proper separation of concerns
- Core combat mechanics fully functional
- Foundation ready for scripting system (Phase 5)

## Notes
- Each test should be implemented one at a time following Red-Green-Refactor
- Commit after each green test with proper structural/behavioral change designation
- Keep implementations minimal - only enough code to make tests pass

## Future Domain Improvements (Post-Phase 4)

### FighterBodyPart Rich Domain Object Enhancements
After completing the current FighterBodyPart refactoring, consider these domain improvements:

1. **Damage Calculation Encapsulation**
   - Move `target.bodyPart().multiplier()` logic into `FighterBodyPart.damageMultiplier()`
   - Encapsulate body part damage logic within the domain object

2. **Strike Receiving Behavior**
   - Add `FighterBodyPart.receiveStrike(weapon, striker)` method
   - Let the body part handle strike reception logic

3. **Parrying Intelligence**
   - Add `FighterBodyPart.attemptStrike(weapon, striker)` method
   - Move parrying logic into the body part itself

4. **Fighter.strike() Simplification**
   - Leverage rich FighterBodyPart behavior to simplify Fighter methods
   - Reduce parameter passing and external logic

5. **Rich Factory Methods**
   - Add `fighter.bodyPartToStrike(BodyPart)` conversion method
   - Add context-aware body part creation methods

These improvements would further encapsulate behavior in domain objects and reduce coupling between classes.



## Next
- Finish refactoring Combat and CombatResult.
  - Fighter shouldn't need both FighterBodyPart parryingFighterBodyPart() and BodyPart parryingBodyPart().
  - Refactor Fighter#strike - no chain.
- See if Weapon can be refactored.