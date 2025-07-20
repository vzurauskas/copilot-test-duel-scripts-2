package com.vzurauskas.duelscripts2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class FixedScript implements Script {
    private final List<Function<Fighter, FighterBodyPart>> strikeTargets;
    private final List<Function<Fighter, FighterBodyPart>> parryTargets;

    public FixedScript(
        Function<Fighter, FighterBodyPart> strikeTarget, 
        Function<Fighter, FighterBodyPart> parryTarget
    ) {
        this(Arrays.asList(strikeTarget), Arrays.asList(parryTarget));
    }

    public FixedScript(
        List<Function<Fighter, FighterBodyPart>> strikeTargets, 
        List<Function<Fighter, FighterBodyPart>> parryTargets
    ) {
        this.strikeTargets = new ArrayList<>(strikeTargets);
        this.parryTargets = new ArrayList<>(parryTargets);
    }

    @Override
    public FighterBodyPart strike(Fighter self, Fighter opponent) {
        if (strikeTargets.isEmpty()) {
            throw new IllegalStateException("No more strike targets available");
        }
        return strikeTargets.remove(0).apply(opponent);
    }

    @Override
    public FighterBodyPart parry(Fighter self, Fighter opponent) {
        if (parryTargets.isEmpty()) {
            throw new IllegalStateException("No more parry targets available");
        }
        return parryTargets.remove(0).apply(self);
    }
}
