package com.vzurauskas.duelscripts2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FixedScript implements Script {
    private final List<BodyPart> strikeTargets;
    private final List<BodyPart> parryTargets;

    public FixedScript(BodyPart strikeTarget, BodyPart parryTarget) {
        this(Arrays.asList(strikeTarget), Arrays.asList(parryTarget));
    }

    public FixedScript(List<BodyPart> strikeTargets, List<BodyPart> parryTargets) {
        this.strikeTargets = new ArrayList<>(strikeTargets);
        this.parryTargets = new ArrayList<>(parryTargets);
    }

    @Override
    public FighterBodyPart strike(Fighter self, Fighter opponent) {
        if (strikeTargets.isEmpty()) {
            throw new IllegalStateException("No more strike targets available");
        }
        return opponent.bodyPart(strikeTargets.remove(0));
    }

    @Override
    public BodyPart parry(Fighter self, Fighter opponent) {
        if (parryTargets.isEmpty()) {
            throw new IllegalStateException("No more parry targets available");
        }
        return parryTargets.remove(0);
    }
}
