# Duel Scripts 2 - Phase 5: Complete Fights Implementation Plan

## Goal
Extend the combat system to support complete fights that continue until one fighter dies, transitioning from single-turn combat to full duels.

## Implementation Plan

### Phase 5: Complete Fight System
**Objective**: Fighters can engage in a complete fight that continues until one dies

#### Test 1: Fight Continues Until One Fighter Dies
- [x] `fightContinuesUntilOneFighterDies()`
  - Two fighters with equal HP start a fight
  - Fight should continue through multiple turns
  - Fight ends when one fighter reaches 0 HP
  - Winner should be alive, loser should be dead

#### Test 2: Fight Result Contains Winner Information
- [ ] `fightResultContainsWinnerInformation()`
  - Complete fight between two fighters
  - Result should identify the winner
  - Result should indicate the fight is over

#### Test 3: Fight Result Contains Turn History
- [ ] `fightResultContainsTurnHistory()`
  - Complete fight generates multiple turns
  - Result contains all turn results from the fight
  - Can review what happened each turn

#### Test 4: Fight Handles Uneven Fighter Strengths
- [ ] `fightHandlesUnevenFighterStrengths()`
  - Fighter with stronger weapon wins consistently
  - Fighter with more HP has advantage
  - Verify fight mechanics work with different fighter setups

#### Test 5: Fight Result Provides Summary Statistics
- [ ] `fightResultProvidesSummaryStatistics()`
  - Result includes total turns fought
  - Result includes total damage dealt by each fighter
  - Result includes number of critical hits landed

#### Test 6: Fight Handles Edge Case of Simultaneous Death
- [ ] `fightHandlesSimultaneousDeath()`
  - Both fighters deal lethal damage in same turn
  - Fight ends appropriately
  - Result handles draw/tie scenario

#### Test 7: Fight Description Provides Readable Narrative
- [ ] `fightDescriptionProvidesReadableNarrative()`
  - Fight result includes human-readable description
  - Description covers key moments of the fight
  - Describes final outcome and winner

#### Test 8: Fight Validates Fighter Readiness
- [ ] `fightValidatesFighterReadiness()`
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

## Domain Model Considerations
- `Combat` class to orchestrate complete duels
- `CombatResult` class to capture complete fight outcomes
- Fighters don't need to be reset between fights. Dead fighter is dead and can no longer fight.
- Ensure turn-by-turn combat remains clean and testable