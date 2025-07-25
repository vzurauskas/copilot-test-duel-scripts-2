# Duel Scripts 2 - Phase 5: Complete Fights Implementation Plan

## Goal
Extend the combat system to support complete fights that continue until one fighter dies, transitioning from single-turn combat to full duels.

## Expected behaviour
- As combat goes on, CombatResult keeps getting updated with the description of what happened each turn.
- When combat ends, CombatResult contains summary and outcome, at the bottom after all individual turns.
- When combat ends, CombatResult is printed into standard output.

## Implementation details
- `Combat` class to orchestrate complete duels
- `CombatResult` class to capture complete fight outcomes
- Fighters don't need to be reset between fights. Dead fighter is dead and can no longer fight.
- Ensure turn-by-turn combat remains clean and testable
- CombatResult is printed whole to avoid it having getters. We should try to avoid getters as much as possible.

## Implementation Plan

### Phase 5: Complete Fight System
**Objective**: Fighters can engage in a complete fight that continues until one dies

#### Test 1: Combat Continues Until One Fighter Dies
- [x] `combatContinuesUntilOneFighterDies()`
  - Two fighters with equal HP start a fight
  - Fight should continue through multiple turns
  - Fight ends when one fighter reaches 0 HP
  - Winner should be alive, loser should be dead

#### Test 2: Combat Result Contains Winner Information
- [x] `combatResultContainsWinnerInformation()`
  - Complete fight between two fighters
  - Result should identify the winner
  - Result should indicate the fight is over

#### Test 3: Combat Result Contains Turn History
- [x] `combatResultContainsTurnHistory()`
  - Complete fight generates multiple turns
  - Result contains all turn results from the fight
  - Can review what happened each turn

#### Test 4: Combat Handles Uneven Fighter Strengths
- [x] `combatHandlesUnevenFighterStrengths()`
  - Fighter with stronger weapon wins consistently
  - Fighter with more HP has advantage
  - Verify fight mechanics work with different fighter setups

#### Test 5: Combat Result Provides Summary Statistics
- [ ] `combatResultProvidesSummaryStatistics()`
  - Result includes total turns fought
  - Result includes total damage dealt by each fighter
  - Result includes number of critical hits landed

#### Test 6: Combat Handles Edge Case of Simultaneous Death
- [ ] `combatHandlesSimultaneousDeath()`
  - Both fighters deal lethal damage in same turn
  - Fight ends appropriately
  - Result handles draw/tie scenario

#### Test 7: Combat Description Provides Readable Narrative
- [ ] `combatDescriptionProvidesReadableNarrative()`
  - Fight result includes human-readable description
  - Description covers key moments of the fight
  - Describes final outcome and winner

#### Test 8: Combat Validates Fighter Readiness
- [ ] `combatValidatesFighterReadiness()`
  - Cannot start fight with dead fighter
  - Cannot start fight with fighter at 0 HP
  - Appropriate error handling for invalid fight setup

## Success Criteria
- All tests pass
- Clean, readable code with no duplication
- Complete fight mechanics functional
- Proper fight state management
- Foundation ready for advanced scripting features

## Notes
- Each test should be implemented one at a time following Red-Green-Refactor
- Commit after each green test with proper structural/behavioral change designation
- Keep implementations minimal - only enough code to make tests pass
- Focus on the fight orchestration layer while preserving existing combat mechanics